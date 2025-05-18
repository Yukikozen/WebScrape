/* 
* @author  Jerry Tan (1902130)
* @version 0.1
* @since   2020-02- 08
*/

package ICT1009JAVA;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MoneySenseArt extends HTMLpage {
    public MoneySenseArt(Document h, String s){
        super(h,s);
    }

    public String getArtTitle(){
        Element t = super.getBody().selectFirst("h1.post-title");
        return t.text();
    }

    public String getArtContent(){
        Element c = super.getBody().selectFirst("div.col-md-10.col-sm-10.col-xs-12.lg-legacy-content");
        return c.text();
    }

    public String getArtDate(){
        //System.out.println("before");
        Element d = super.getBody().selectFirst("span.article-date");
        //System.out.println("After");
        return d.text();
    }

    public String getArtNumCmt(){
        // System.out.println("before");
        Element nc = super.getBody().selectFirst("a.disqus-comment-count");
        //System.out.println("After");
        return nc.text();
    }

    public String getArtAuthorName(){
        // System.out.println("before");
        Element an = super.getBody().select("div.author-info > h3 > a").first();
        if(an == null){
            return null;
        }
        // System.out.println("After");
        return an.text();
    }

    public String getArtAuthorInfo(){
        //System.out.println("before");
        Element ai = super.getBody().selectFirst("div.author-info > p");
        //System.out.println("After");
        return ai.text();
    }
}