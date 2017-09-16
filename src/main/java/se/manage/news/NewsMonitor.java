package se.manage.news;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.manage.controller.MyServices;
import se.manage.stock.UserStock;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NewsMonitor extends Thread {
    @Autowired
    NewsRepository newsRepository;

    public NewsMonitor() {
        this.start();
    }

    String [] allStockCodes={
            "HOU",
            "DDC",
            "CEF.A",
            "BRB",
            "AYM",
            "AF",
            "BTE",
            "ECA",
            "WCP",
            "TCK.B",
            "ENB",
            "YRI",
            "FM",
            "BTO",
            "BBD.B"
    };

    private News[] getNewsOfOneStock(String stockCode){


        try {
            // 定义即将访问的链接
            String url = "http://web.tmxmoney.com/quote.php?qm_symbol=" + stockCode;

            // 访问链接并获取页面内容
            String result = SendGet(url);
            //System.out.println(result);

            String newsID[];

            String newsTime[];
            String headLine[];
            newsID = new String[3];
            newsTime = new String[3];
            headLine = new String[3];

            Pattern pattern;
            Matcher matcher;

            pattern = Pattern.compile("<br>\t\t\t(.+?)</span>");//2,3,4
            matcher = pattern.matcher(result);
            matcher.find();
            matcher.find();
            newsTime[0] = matcher.group(1);
            matcher.find();
            newsTime[1] = matcher.group(1);
            matcher.find();
            newsTime[2] = matcher.group(1);

            pattern = Pattern.compile("newsid=(.+?)&qm_symbol=");//1,2,3
            matcher = pattern.matcher(result);
            matcher.find();
            newsID[0] = matcher.group(1);
            matcher.find();
            newsID[1] = matcher.group(1);
            matcher.find();
            newsID[2] = matcher.group(1);


            //HOU 没有那个more。。。。。
            pattern = Pattern.compile("qm_symbol=" + stockCode + "\">(.+?)</a>");//2,3,4
            matcher = pattern.matcher(result);
            matcher.find();
            matcher.find();
            headLine[0] = matcher.group(1);
            matcher.find();
            headLine[1] = matcher.group(1);
            matcher.find();
            headLine[2] = matcher.group(1);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String grabTime = df.format(new Date());

            News results[];
            results = new News[3];
            results[0] = new News(newsID[0], stockCode, headLine[0], "http://web.tmxmoney.com/article.php?newsid=" + newsID[0] + "&qm_symbol=" + stockCode, newsTime[0], grabTime);
            results[1] = new News(newsID[1], stockCode, headLine[1], "http://web.tmxmoney.com/article.php?newsid=" + newsID[1] + "&qm_symbol=" + stockCode, newsTime[1], grabTime);
            results[2] = new News(newsID[2], stockCode, headLine[2], "http://web.tmxmoney.com/article.php?newsid=" + newsID[2] + "&qm_symbol=" + stockCode, newsTime[2], grabTime);
            return results;
        }
        catch (Exception e){
            return null;
        }
    }

    private void processNews(News news){
        List<News> records;
        records= this.newsRepository.findByNewsID(news.getNewsID());
        if (records.isEmpty()) {
            System.out.println("!!!!!New news detected!!!!!!\n"+news);
            sendNews(news,"junli.ye@outlook.com");
            this.newsRepository.save(news);
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                System.out.println("Crawler Started!");

                for (int i=0; i< allStockCodes.length;i++){
                    News threeNews[]=getNewsOfOneStock(allStockCodes[i]);
                    if (threeNews!=null) {
                        System.out.println(threeNews[0]);
                        System.out.println(threeNews[1]);
                        System.out.println(threeNews[2]);
                        processNews(threeNews[0]);
                        processNews(threeNews[1]);
                        processNews(threeNews[2]);
                    }
                    else
                        System.out.println("invalid stock code: "+allStockCodes[i]);
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                System.out.println("Crawler Finished!");
            }
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static String SendGet(String url) {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;

        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    private boolean sendNews(News news, String emailAddress){
        final String username = "junliye1994@gmail.com";
        final String password = "javatestforyile";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("junliye1994@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailAddress));
            message.setSubject("New Alarm for you stock: "+news.getStockCode());
            message.setText(news.toString());
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            return false;
        }

        return true;
    }
}
