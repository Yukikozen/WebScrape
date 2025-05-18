/* 
* @author  Jerry Tan (1902130)
* @version 0.1
* @since   2020-02- 08
*/

package ICT1009JAVA;

import java.util.ArrayList;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MoneySense extends HTMLpage {
    private String cat;
    private static final String name = "MoneySense";
    private ArrayList <MoneySenseArt> art = new ArrayList <MoneySenseArt>();

    public MoneySense() {
    }

    public MoneySense(Document html, String url, String cat) {
        super(html,url);
        this.cat = cat;
    }

    public String getCat() {
        return this.cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public MoneySense cat(String cat) {
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

    public void appendMoneySArt(MoneySenseArt x){
        this.art.add(x);
    }

    public ArrayList<MoneySenseArt> getArtArray(){
        return this.art;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MoneySense)) {
            return false;
        }
        MoneySense moneySense = (MoneySense) o;
        return Objects.equals(cat, moneySense.cat);
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
