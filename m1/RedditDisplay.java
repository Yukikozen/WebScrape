/* 
* @author           Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.util.ArrayList;
import java.util.Objects;

public class RedditDisplay {
    private String title;
    private String content;
    private String cmt;
    private String likes;
    private String url;

    public RedditDisplay(RedditPost x){
        this.title = x.getPostTitle();
        this.content = x.getPostBody();
        this.cmt = x.getNoComments();
        this.likes = x.getPostScore();
        this.url = x.getUrl();
    }


    public RedditDisplay() {
    }

    public RedditDisplay(String title, String content, String cmt, String likes, String url) {
        this.title = title;
        this.content = content;
        this.cmt = cmt;
        this.likes = likes;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCmt() {
        return this.cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getLikes() {
        return this.likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RedditDisplay title(String title) {
        this.title = title;
        return this;
    }

    public RedditDisplay content(String content) {
        this.content = content;
        return this;
    }

    public RedditDisplay cmt(String cmt) {
        this.cmt = cmt;
        return this;
    }

    public RedditDisplay likes(String likes) {
        this.likes = likes;
        return this;
    }

    public RedditDisplay url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RedditDisplay)) {
            return false;
        }
        RedditDisplay redditDisplay = (RedditDisplay) o;
        return Objects.equals(title, redditDisplay.title) && Objects.equals(content, redditDisplay.content) && Objects.equals(cmt, redditDisplay.cmt) && Objects.equals(likes, redditDisplay.likes) && Objects.equals(url, redditDisplay.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, cmt, likes, url);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", cmt='" + getCmt() + "'" +
            ", likes='" + getLikes() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }

}