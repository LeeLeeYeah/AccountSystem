package se.manage.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class PostTemplate {
    private HttpHeaders headers;
    private MultiValueMap<String, String> mvm;

    public PostTemplate() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        mvm = new LinkedMultiValueMap<>();
    }

    public void add(String k, String v) {
        mvm.add(k, v);
    }

    public String post(String url) {
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(mvm, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, formEntity, String.class);
    }

}
