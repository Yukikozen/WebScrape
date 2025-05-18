/* 
* @author  Jerry Tan (1902130)
* @version 0.1
* @since   2020-02- 08
*/

package ICT1009JAVA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter; 

/**
 * Hello world!
 *
 */
public class App {
    public static Document doc = null;
    public static ArrayList<HTMLpage> x = new ArrayList<HTMLpage>();

    public static void moneysmart(String urlc, int opop) throws IOException, InterruptedException {
        // FileWriter file1 = new FileWriter("main.txt");
        // FileWriter file2 = new FileWriter("Art.txt");
        // FileWriter file3 = new FileWriter("Skipped.txt");
        RandomUserAgent ss = new RandomUserAgent();
        String kol;

        try {
            String url = urlc;
            int count = 0;
            int count2 = 0;
            int end = opop;
            int i = 0;
            boolean success = false;
            while (true) {
                count++;
                // System.out.println(count);
                // System.out.println(url);
                kol = ss.getRandomUserAgent();
                // System.out.println(kol);
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
                    // file1.write(url);
                    // file1.write("\n");
                    // file1.write(doc.toString());
                    // file1.write("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n\n");
                    // if(doc != null) {
                    // System.out.println("have");
                    // }
                    MoneySense xxx = new MoneySense(doc, url, "");
                    Elements dodo = xxx.getArt();
                    // System.out.println(dodo.size());
                    // System.out.println(x.getTitle());
                    // System.out.println(((MoneySense)x).getCat());
                    // System.out.println(((MoneySense)x).getNextPage());
                    // System.out.println(x.getBody().toString());
                    ArrayList<String> used = new ArrayList<String>();
                    for (Element kkk : dodo) {
                        url = kkk.attr("href");
                        if (used.contains(url)) {
                            continue;
                        }
                        used.add(url);
                        // file1.write(url);
                        // file1.write("\n");
                        // System.out.println(url);
                        MoneySenseArt xx = null;
                        while (true) {
                            kol = ss.getRandomUserAgent();
                            // System.out.println(kol);
                            doc = Jsoup.connect(url).timeout(30 * 1000).userAgent(kol).referrer("http://www.google.com")
                                    .followRedirects(true).get();
                            // if(doc != null) {
                            // System.out.println("have");
                            // }
                            xx = new MoneySenseArt(doc, url);
                            if (xx.getArtAuthorName() != null) {
                                break;
                            }
                        }
                        count2++;
                        // System.out.println("in msa");
                        ((MoneySense) xxx).appendMoneySArt(xx);
                        // System.out.println("appended");
                        // file2.write(url);
                        // file2.write("\n");
                        // file2.write(doc.toString());
                        // file2.write("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n\n");
                        // TimeUnit.SECONDS.sleep(1);
                        // System.out.println(xx.getArtAuthorName());
                        // System.out.println(xx.getArtAuthorInfo());
                        // System.out.println(xx.getArtDate());
                        // System.out.println(xx.getArtNumCmt());
                        // System.out.println(xx.getArtTitle());
                        // System.out.println(xx.getArtContent());
                    }
                    String oooo = xxx.getNextPage();
                    if (oooo != null) {
                        url = oooo;
                        x.add(xxx);
                    }
                    TimeUnit.MICROSECONDS.sleep(500);
                }

            }
            // file1.close();
            // file2.close();
            int toto = 0;
            int art = 0;
            for (HTMLpage os : x) {
                for (MoneySenseArt lo : ((MoneySense) os).getArtArray()) {
                    art++;
                    String ldl = lo.getArtAuthorName();
                    if (ldl == null) {
                        toto++;
                        // file3.write(os.getHtml().toString());
                        // file3.write("\n-----------------------------------------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        continue;
                    }
                    System.out.println(ldl);
                    System.out.println(lo.getArtTitle());
                    System.out.println(lo.getArtAuthorInfo());
                    System.out.println(lo.getArtDate());
                    System.out.println(lo.getArtNumCmt());
                    TextSummarizer test222 = new TextSummarizer(lo.getArtContent());
                    String tt = test222.summarize(1);
                    System.out.println(tt);
                    System.out.println(
                            "--------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
            // System.out.println(toto);
            // System.out.println(count2);
            // System.out.println(art);
            // for(MoneySenseArt lo:((MoneySense) x).getArtArray()){
            // System.out.println(lo.getArtAuthorName());
            // System.out.println(lo.getArtAuthorInfo());
            // System.out.println(lo.getArtDate());
            // System.out.println(lo.getArtNumCmt());
            // }

            // System.out.println(((MoneySense)x).getArtArray().size());
            // System.out.println(((MoneySense)x).getArtArray().get(0).getArtContent());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        moneysmart("https://blog.moneysmart.sg/category/budgeting/", 3);

    }
}
