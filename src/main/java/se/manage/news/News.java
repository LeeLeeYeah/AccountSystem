package se.manage.news;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Lee on 2016/9/8.
 */

@Entity
public class News {

    @Id
    private String newsID="";
    private String stockCode="";
    private String headLine="";
    private String link="";
    private String newsTime="";
    private String grabTime="";

    public News(String newsID, String stockCode, String headLine, String link, String newsTime, String grabTime) {
        this.newsID = newsID;
        this.stockCode = stockCode;
        this.headLine = headLine;
        this.link = link;
        this.newsTime = newsTime;
        this.grabTime = grabTime;
    }



    public String getNewsID() {
        return newsID;
    }

    public String getStockCode() {
        return stockCode;
    }



    public String getHeadLine() {
        return headLine;
    }

    public String getLink() {
        return link;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public String getGrabTime() {
        return grabTime;
    }


    public News() {

    }

    @Override
    public String toString() {
        return "News{" +
                "newsID='" + newsID + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", headLine='" + headLine + '\'' +
                ", link='" + link + '\'' +
                ", newsTime='" + newsTime + '\'' +
                ", grabTime='" + grabTime + '\'' +
                '}';
    }


}
