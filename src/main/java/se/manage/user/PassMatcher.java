package se.manage.user;


import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PassMatcher {
    Pattern pattern;

    public PassMatcher() {
        pattern = Pattern.compile("[a-zA-Z0-9_]*");
    }


    public boolean validate(String str) {
        return pattern.matcher(str).matches();
    }
}
