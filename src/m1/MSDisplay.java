/* 
* @author           Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.util.Objects;

public class MSDisplay {
    private String title;
    private String sum;
    private String cmt;
    private String url;

    public MSDisplay(MoneySmartArt x){
        this.title = x.getPostTitle();
        this.sum = x.getArtSum();
        this.cmt = x.getArtNumCmt();
        this.url = x.getUrl();
    }

    public MSDisplay() {
    }

    public MSDisplay(String title, String sum, String cmt, String url) {
        this.title = title;
        this.sum = sum;
        this.cmt = cmt;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSum() {
        return this.sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getCmt() {
        return this.cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MSDisplay title(String title) {
        this.title = title;
        return this;
    }

    public MSDisplay sum(String sum) {
        this.sum = sum;
        return this;
    }

    public MSDisplay cmt(String cmt) {
        this.cmt = cmt;
        return this;
    }

    public MSDisplay url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MSDisplay)) {
            return false;
        }
        MSDisplay mSDisplay = (MSDisplay) o;
        return Objects.equals(title, mSDisplay.title) && Objects.equals(sum, mSDisplay.sum) && Objects.equals(cmt, mSDisplay.cmt) && Objects.equals(url, mSDisplay.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, sum, cmt, url);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", sum='" + getSum() + "'" +
            ", cmt='" + getCmt() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}