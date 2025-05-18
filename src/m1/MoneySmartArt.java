/* 
* @author           Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MoneySmartArt extends HTMLpage implements webInter {
    private String titlesentiment;

    public MoneySmartArt(Document h, String s, String ts){
        super(h,s);
        this.titlesentiment = ts;
    }

    public String getTS(){
        return this.titlesentiment;
    }

    public void setTS(String ts){
        this.titlesentiment = ts;
    }

    public String getPostTitle(){
        Element t = super.getBody().selectFirst("h1.post-title");
        return t.text();
    }

    public String getPostBody(){
        Element c = super.getBody().selectFirst("div.col-md-10.col-sm-10.col-xs-12.lg-legacy-content");
        return c.text();
    }

    public String getArtDate(){
        Element d = super.getBody().selectFirst("span.article-date");
        return d.text();
    }

    public String getArtNumCmt(){
        Element nc = super.getBody().selectFirst("a.disqus-comment-count");
        return nc.text();
    }

    public String getArtAuthorName(){
        Element an = super.getBody().select("div.author-info > h3 > a").first();
        if(an == null){
            return null;
        }
        return an.text();
    }

    public String getArtAuthorInfo(){
        Element ai = super.getBody().selectFirst("div.author-info > p");
        return ai.text();
    }

    public String getArtSum(){
        TextSummarizer sum = new TextSummarizer(getPostBody());
        String sumed = sum.summarize(1);
        return sumed;
    }
}