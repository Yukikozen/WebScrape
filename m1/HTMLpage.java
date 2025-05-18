/* 
* @author  Jerry Tan (1902130)
* @version 0.1
* @since   2020-02- 08
*/

package ICT1009JAVA;

import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLpage {
    private Document html;
    private Element body;
    private Element head;
    private String title;
    private String url;
    

    public HTMLpage(Document h, String u){
        this.html = h;
        this.url = u;
        this.body = h.body();
        this.head = h.head();
        this.title = h.title();
    }


    public HTMLpage() {
    }

    public Document getHtml() {
        return this.html;
    }

    public void setHtml(Document html) {
        this.html = html;
    }

    public Element getBody() {
        return this.body;
    }

    public void setBody(Element body) {
        this.body = body;
    }

    public Element getHead() {
        return this.head;
    }

    public void setHead(Element head) {
        this.head = head;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HTMLpage html(Document html) {
        this.html = html;
        return this;
    }

    public HTMLpage body(Element body) {
        this.body = body;
        return this;
    }

    public HTMLpage head(Element head) {
        this.head = head;
        return this;
    }

    public HTMLpage title(String title) {
        this.title = title;
        return this;
    }

    public HTMLpage url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HTMLpage)) {
            return false;
        }
        HTMLpage hTMLpage = (HTMLpage) o;
        return Objects.equals(html, hTMLpage.html) && Objects.equals(body, hTMLpage.body) && Objects.equals(head, hTMLpage.head) && Objects.equals(title, hTMLpage.title) && Objects.equals(url, hTMLpage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(html, body, head, title, url);
    }

    @Override
    public String toString() {
        return "{" +
            " html='" + getHtml() + "'" +
            ", body='" + getBody() + "'" +
            ", head='" + getHead() + "'" +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }

    
}
