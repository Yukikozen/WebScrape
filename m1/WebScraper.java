/* 
* @author           Tan Fu Wei (1902130)
* @Contribution     Pak Shao Kai (1902698)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.HashMap;

public class WebScraper {
    public static Document doc = null;
    public static ArrayList<HTMLpage> x = new ArrayList<HTMLpage>();
    static StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

    public static HashMap<String, HTMLpage> moneysmart(String urlc, int opop) throws IOException, InterruptedException {
        RandomUserAgent ss = new RandomUserAgent();
        String kol;
        HashMap<String, HTMLpage> moneys = new HashMap<String, HTMLpage>();

        try {
            String url = urlc;
            int count = 0;
            int count2 = 0;
            int end = opop;
            int i = 0;
            boolean success = false;
            while (true) {
                count++;
                kol = ss.getRandomUserAgent();
                if (count == end) {
                    break;
                }
                while (i < 3) {
                    try {
                        doc = Jsoup.connect(url).timeout(30 * 1000).userAgent(kol).referrer("http://www.google.com")
                                .followRedirects(true).get();
                        success = true;
                        break;
                    } catch (Exception ex) {
                    }
                    i++;
                }
                if (success) {
                    MoneySmart xxx = new MoneySmart(doc, url, "");
                    Elements dodo = xxx.getArt();
                    ArrayList<String> used = new ArrayList<String>();
                    for (Element kkk : dodo) {
                        url = kkk.attr("href");
                        if (used.contains(url)) {
                            continue;
                        }
                        used.add(url);
                        MoneySmartArt xx = null;
                        while (true) {
                            kol = ss.getRandomUserAgent();
                            doc = Jsoup.connect(url).timeout(30 * 1000).userAgent(kol).referrer("http://www.google.com")
                                    .followRedirects(true).get();
                            xx = new MoneySmartArt(doc, url, "");
                            if (xx.getArtAuthorName() != null) {
                                break;
                            }
                        }
                        count2++;
                        CoreDocument coreDocumentbody = new CoreDocument(xx.getPostTitle());
                        stanfordCoreNLP.annotate(coreDocumentbody);
                        List<CoreSentence> bodytext = coreDocumentbody.sentences();
                        int total = 0;
                        int ct = 0;
                        for (CoreSentence sentence : bodytext) {
                            String sentiment = sentence.sentiment();

                            if (sentiment.matches("Very positive")) {
                                total+=5;
                            } else if (sentiment.matches("Positive")) {
                                total+=4;
                            } else if (sentiment.matches("Neutral")) {
                                total+=3;
                            } else if (sentiment.matches("Negative")) {
                                total+=2;
                            } else {
                                total+=1;
                            }
                            ct++;
                        }
                        int res = Math.floorDiv(total, ct);
                        switch(res){
                            case 1:
                                xx.setTS("Very Negative");
                                break;
                            case 2:
                                xx.setTS("Negative");
                                break;
                            case 3:
                                xx.setTS("Neutral");
                                break;
                            case 4:
                                xx.setTS("Positive");
                                break;
                            default:
                                xx.setTS("Very Positive");
                                break;
                        }
                        moneys.put(url, xx);
                        ((MoneySmart) xxx).appendMoneySArt(xx);
                    }
                    String oooo = xxx.getNextPage();
                    if (oooo != null) {
                        url = oooo;
                        x.add(xxx);
                    }
                    TimeUnit.MICROSECONDS.sleep(500);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return moneys;
    }

    public static HashMap<String, HTMLpage> reddit(String urlc, int pages) {
        HashMap<String, HTMLpage> redditmm = new HashMap<String, HTMLpage>();
        int counter = 1;
        int commentCounterTotal = 1;
        
        String subredditlink = urlc;
        try {
            Document doc = Jsoup.connect(subredditlink).userAgent("Mozilla/5.0").timeout(30 * 1000).get();
            Reddit redditClass = new Reddit(doc, subredditlink, "");
            //i = number of pages to loop
            for (int i = 0; i < pages; i++) {
                Elements postcontainer = redditClass.getPostContainer();

                for (Element step : redditClass.getPost(postcontainer)) {
                    String postUrl = "";
                    //getting details of the posts
                    String totalComments = step.select("a.bylink.comments.may-blank").text();

                    //getting comments for a post
                    //checking if the post has more than 1 comments, if not ignore lol
                        try {

                            postUrl = redditClass.getPostLink(step);
                            Document commentDoc = Jsoup.connect(postUrl).userAgent("Mozilla/5.0").timeout(0).get();
                            RedditPost redditPostClass = new RedditPost(commentDoc, postUrl,"", null);
                            //sentiment for body
                            CoreDocument coreDocumentbody = new CoreDocument(redditPostClass.getPostTitle());
                            stanfordCoreNLP.annotate(coreDocumentbody);
                            List<CoreSentence> bodytext = coreDocumentbody.sentences();
                            int total = 0;
                            int count = 0;
                            for (CoreSentence sentence : bodytext) {
                                String sentiment = sentence.sentiment();

                                if (sentiment.matches("Very positive")) {
                                    total+=5;
                                } else if (sentiment.matches("Positive")) {
                                    total+=4;
                                } else if (sentiment.matches("Neutral")) {
                                    total+=3;
                                } else if (sentiment.matches("Negative")) {
                                    total+=2;
                                } else {
                                    total+=1;
                                }
                                count++;
                            }
                            int res = Math.floorDiv(total, count);
                            switch(res){
                                case 1:
                                    redditPostClass.setBS("Very Negative");
                                    break;
                                case 2:
                                    redditPostClass.setBS("Negative");
                                    break;
                                case 3:
                                    redditPostClass.setBS("Neutral");
                                    break;
                                case 4:
                                    redditPostClass.setBS("Positive");
                                    break;
                                default:
                                    redditPostClass.setBS("Very Positive");
                                    break;
                            }
                            ArrayList<String> cmts = new ArrayList<String>();
                            for (String commentStep : redditPostClass.getCommentsArray()) {
                                total = 0;
                                count = 0;
                                //sentiment for all the comments
                                CoreDocument coreDocument = new CoreDocument(commentStep);
                                stanfordCoreNLP.annotate(coreDocument);
                                List<CoreSentence> sentences = coreDocument.sentences();
                                for (CoreSentence sentence : sentences) {
                                    String sentiment = sentence.sentiment();

                                    if (sentiment.matches("Very positive")) {
                                        total+=5;
                                    } else if (sentiment.matches("Positive")) {
                                        total+=4;
                                    } else if (sentiment.matches("Neutral")) {
                                        total+=3;
                                    } else if (sentiment.matches("Negative")) {
                                        total+=2;
                                    } else {
                                        total+=1;
                                    }
                                    count++;
                                }
                                res = Math.floorDiv(total, count);
                                switch(res){
                                    case 1:
                                        cmts.add("Very Negative");
                                        break;
                                    case 2:
                                        cmts.add("Negative");
                                        break;
                                    case 3:
                                        cmts.add("Neutral");
                                        break;
                                    case 4:
                                        cmts.add("Positive");
                                        break;
                                    default:
                                        cmts.add("Very Positive");
                                        break;
                                }
                            }
                            redditPostClass.setCS(cmts);
                            redditmm.put(postUrl, redditPostClass);
                            commentCounterTotal++;
                            
                        } catch (Exception ex) {
                            System.out.println("Invalid url...");
                        }
                    counter++;

                    
                }
                Elements link1 = doc.select("span.next-button > a");
                String url = link1.attr("href");
                //String link = nxtpagelink.select("span.next-button a href").toString();
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).get();
            }

        } catch (Exception E) {
            System.out.println(E);
        }

        return redditmm;
        
    }

    public static int tryParseInt(String value) {
        try {
            int returnint = Integer.parseInt(value);
            return returnint;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
}
