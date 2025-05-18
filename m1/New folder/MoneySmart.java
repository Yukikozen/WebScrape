/* 
* @author           Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.util.ArrayList;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MoneySmart extends HTMLpage {
    private String cat;
    private static final String name = "MoneySmart";
    private ArrayList <MoneySmartArt> art = new ArrayList<MoneySmartArt>();

    public MoneySmart() {
    }

    public MoneySmart(Document html, String url, String cat) {
        super(html,url);
        this.cat = cat;
    }

    public String getCat() {
        return this.cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public MoneySmart cat(String cat) {
        this.cat = cat;
        return this;
    }

    public String getNextPage(){
        Element nextpage = super.getBody().select(".nav-links > a").first();
        return nextpage.attr("href");
    }

    public Elements getArt(){
        Elements art = super.getBody().select("header > h3 > a");
        return art;
    }

    public void appendMoneySArt(MoneySmartArt x) {
        this.art.add(x);
    }

    public ArrayList<MoneySmartArt> getArtArray() {
        return this.art;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MoneySmart)) {
            return false;
        }
        MoneySmart MoneySmart = (MoneySmart) o;
        return Objects.equals(cat, MoneySmart.cat);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cat);
    }

    @Override
    public String toString() {
        return "{" +
            " cat='" + getCat() + "'" +
            "}";
    }


}
