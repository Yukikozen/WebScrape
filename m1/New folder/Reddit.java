/* 
* @author           Pak Shao Kai (1902698)
* @Contribution     Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Reddit extends HTMLpage {

    private String cat;
    private String subredditname;
    private ArrayList<RedditPost> post = new ArrayList<RedditPost>();

    public Reddit() {
    }

    public Reddit(Document html, String url, String subredditname) {
        super(html, url);
        this.subredditname = subredditname;
    }

    public String getsubredditname() {
        return this.subredditname;
    }

    public void setsubredditname(String cat) {
        this.subredditname = cat;
    }

    public Elements getPostContainer() {
        Elements postContainer = super.getBody().select("div#siteTable");
        return postContainer;
    }

    public Elements getPost(Elements ele) {
        Elements post = ele.select("div.Thing");
        return post;
    }

    public String getPostTotalNumComment() {
        Elements numComment = super.getBody().select("a.bylink.comments.may-blank");
        return numComment.text();
    }

    public String getPostLink(Element ele) {
        String postLink = ele.select("a.bylink.comments.may-blank").attr("href");
        return postLink;
    }

    public String getNextPage() {
        Element nextpage = super.getBody().select("span.next-button > a").first();
        return nextpage.attr("href");
    }

}
