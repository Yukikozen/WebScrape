/* 
* @author           Pak Shao Kai (1902698)
* @Contribution     Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class RedditPost extends HTMLpage implements webInter{
    private String bodysentiment;
    private ArrayList<String> cmtsentiment;
    StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
    
    public RedditPost(Document html, String title, String bs, ArrayList<String> cs) {
        super(html, title);
        this.bodysentiment = bs;
        this.cmtsentiment = cs;
    }

    public String getPostTitle() {
        Elements title = super.getBody().select("a.title");
        return title.text();
    }

    public String getPostScore() {
        Elements score = super.getBody().select("div.score.unvoted");
        return score.text();
    }

    public String getPostBody() {
        Elements body = super.getBody().select("div.expando");
        if(body.text().isEmpty()){
            return "This post might contain a link or a image thus it is empty";
        }
        return body.text();
    }

    public Elements getCommentsContainer() {
        Elements commentsContainer = super.getBody().select("div.siteTable.nestedlisting");
        return commentsContainer;
    }

    public Elements getComments(Elements ele) {
        Elements comments = ele.select("div.entry > form > div.usertext-body > div > p");
        if(comments == null){
            return null;
        }
        return comments;
    }

    public ArrayList<String> getCommentsArray() {
        ArrayList<String> comment = new ArrayList<String>();
        Elements comments = getComments(getCommentsContainer());
        if(comments == null){
            return comment;
        }
        for(Element x : comments){
            comment.add(x.text());
        }
        return comment;
    }

    public String getNoComments() {
        ArrayList<String> comments = getCommentsArray();
        return String.valueOf(comments.size());
    }

    public String getCommentsString() {
        String comment = "";
        ArrayList<String> comments = getCommentsArray();
        if(comments.size() == 0){
            return "No Comment";
        }
        for(String x: getCommentsArray()){
            if (comment.equals("")){
                comment = x + ", ";
            }
            else{
                comment = comment + x + ", ";
            }
        }
        return comment.substring(0, comment.length()-2);
    }

    public String getCommentsStringformatted() {
        String comment = "";
        ArrayList<String> comments = getCommentsArray();
        ArrayList<String> sentiment = getCS();
        if(comments.size() == 0){
            return "No Comment";
        }
        for(int i=0; i < comments.size(); i++){
            if (comment.equals("")){
                comment = comments.get(i) + "\n" + sentiment.get(i) + "\n\n";
            }
            else{
                comment = comment + comments.get(i) + "\n" + sentiment.get(i) + "\n\n";
            }
        }
        return comment;
    }

    public void setBS(String bs){
        this.bodysentiment = bs;
    }

    public void setCS(ArrayList<String> cs){
        this.cmtsentiment = cs;
    }

    public String getBS(){
        return this.bodysentiment;
    }

    public ArrayList<String> getCS(){
        return this.cmtsentiment;
    }

}
