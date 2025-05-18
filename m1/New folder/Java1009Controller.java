/* 
* @author           Tan Fu Wei (1902130)
* @version          1.0
* @since            2020-02-21 
*/

package m1;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import m1.WebScraper;
import java.util.HashMap;
import com.opencsv.CSVWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Java1009Controller implements Initializable {

    private Stage stage;
    @FXML
    private ComboBox<String> website_ddl;
    @FXML
    private ComboBox<String> cat_ddl;
    @FXML
    private Button scrape_btn;
    @FXML
    private Button msexport_btn;
    @FXML
    private Button redditexport_btn;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    @FXML
    private TableView<MSDisplay> mstableview;
    @FXML
    private TableColumn<MSDisplay, String> mstablecol_title;
    @FXML
    private TableColumn<MSDisplay, String> mstablecol_sum;
    @FXML
    private TableColumn<MSDisplay, String> mstablecol_cmt;
    @FXML
    private TableColumn<MSDisplay, String> mstablecol_url;
    @FXML
    private TableView<RedditDisplay> reddittableview;
    @FXML
    private TableColumn<RedditDisplay, String> redditcol_title;
    @FXML
    private TableColumn<RedditDisplay, String> redditcol_content;
    @FXML
    private TableColumn<RedditDisplay, String> redditcol_cmt;
    @FXML
    private TableColumn<RedditDisplay, String> redditcol_likes;
    @FXML
    private TableColumn<RedditDisplay, String> redditcol_url;
    @FXML
    private TableColumn<MSDisplay, Void> mscolBtn;
    @FXML
    private TableColumn<RedditDisplay, Void> redditcolBtn;
    @FXML
    private PieChart redditpie;
    @FXML
    private BarChart<String, Number> msbarchart;
    @FXML
    private PieChart mspie;

    ObservableList<String> website_options = FXCollections.observableArrayList("MoneySmart", "Reddit");
    ObservableList<String> catergory_options = FXCollections.observableArrayList("Budgeting", "Business", "Career");
    HashMap<String, HTMLpage> moneys = new HashMap<String, HTMLpage>();
    HashMap<String, HTMLpage> reddit = new HashMap<String, HTMLpage>();
    HashMap<String, Integer> sentimentcount = new HashMap<String, Integer>();

    /**
     * Initializes the controller class.
     * 
     * @return
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        website_ddl.setItems(website_options);
        cat_ddl.setItems(catergory_options);
        msexport_btn.setDisable(true);
        redditexport_btn.setDisable(true);
        mstablecol_title.setCellValueFactory(new PropertyValueFactory<MSDisplay, String>("title"));
        mstablecol_sum.setCellValueFactory(new PropertyValueFactory<MSDisplay, String>("sum"));
        mstablecol_cmt.setCellValueFactory(new PropertyValueFactory<MSDisplay, String>("cmt"));
        mstablecol_url.setCellValueFactory(new PropertyValueFactory<MSDisplay, String>("url"));
        redditcol_title.setCellValueFactory(new PropertyValueFactory<RedditDisplay, String>("title"));
        redditcol_content.setCellValueFactory(new PropertyValueFactory<RedditDisplay, String>("content"));
        redditcol_cmt.setCellValueFactory(new PropertyValueFactory<RedditDisplay, String>("cmt"));
        redditcol_likes.setCellValueFactory(new PropertyValueFactory<RedditDisplay, String>("likes"));
        redditcol_url.setCellValueFactory(new PropertyValueFactory<RedditDisplay, String>("url"));
    }

    public void clearStHM() {
        sentimentcount.put("Very Negative", 0);
        sentimentcount.put("Negative", 0);
        sentimentcount.put("Neutral", 0);
        sentimentcount.put("Positive", 0);
        sentimentcount.put("Very Positive", 0);
    }

    public ObservableList<PieChart.Data> countSt() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(String i:sentimentcount.keySet()){
            if(sentimentcount.get(i) > 0){
                pieChartData.add(new PieChart.Data(i + "( " + sentimentcount.get(i) + " )", sentimentcount.get(i)));
            }
        }
        return pieChartData;
    }

    @FXML
    private void scrapebtnhandler(ActionEvent event) throws IOException, InterruptedException {
        String url = "";
        switch(String.valueOf(cat_ddl.getValue())){
            case "Budgeting":
                url = url + "budgeting";
                break;
            case "Business":
                url = url + "business";
                break;
            case "Career":
                url = url + "career";
                break;
            default:
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Null error");
                alert.setContentText("Please select something!");
                alert.showAndWait();
        }
        switch(String.valueOf(website_ddl.getValue())){
            case "MoneySmart":
                clearStHM();
                url = "https://blog.moneysmart.sg/category/" + url;
                scrape_btn.setDisable(true);
                if(moneys.size() == 0){moneys = WebScraper.moneysmart(url, 2);}
                else {moneys.putAll(WebScraper.moneysmart(url, 2));}
                ObservableList<MSDisplay> mstableviewd = FXCollections.observableArrayList();
                HashMap<String, Integer> author = new HashMap<String, Integer>();
                XYChart.Series<String, Number> dS1 = new XYChart.Series<String, Number>();
                ObservableList<PieChart.Data> mspieChartData = FXCollections.observableArrayList();
                dS1.setName("Author");
                for (String i : moneys.keySet()) {
                    MoneySmartArt obj = (MoneySmartArt) moneys.get(i);
                    int x = sentimentcount.get(obj.getTS());
                    sentimentcount.replace(obj.getTS(), x+1);
                    if(author.get(obj.getArtAuthorName())==null){author.put(obj.getArtAuthorName(), 1);}
                    else{author.put(obj.getArtAuthorName(), author.get(obj.getArtAuthorName())+1);}
                    MSDisplay insert = new MSDisplay(obj);
                    mstableviewd.add(insert);
                }
                for(String i:author.keySet()){
                    dS1.getData().add(new XYChart.Data<String, Number>(i, author.get(i)));
                }
                mspie.getData().clear();
                msbarchart.getData().clear();
                mspieChartData = countSt();
                mspie.setTitle("Sentiment");
                mspie.getData().addAll(mspieChartData);
                msbarchart.getData().addAll(dS1);
                mstableview.setItems(mstableviewd);
                msaddbutton();
                if(moneys.size() > 0){msexport_btn.setDisable(false);}
                break;
            case "Reddit":
                clearStHM();
                url = "https://old.reddit.com/r/" + url;
                scrape_btn.setDisable(true);
                if(reddit.size() == 0){reddit = WebScraper.reddit(url, 1);}
                else {reddit.putAll(WebScraper.reddit(url, 1));}
                ObservableList<RedditDisplay> reddittableviewd = FXCollections.observableArrayList();
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                for(String i:reddit.keySet()){
                    RedditPost obj = (RedditPost) reddit.get(i);
                    int x = sentimentcount.get(obj.getBS());
                    sentimentcount.replace(obj.getBS(), x+1);
                    RedditDisplay insert = new RedditDisplay(obj);
                    reddittableviewd.add(insert);
                }
                redditpie.getData().clear();
                pieChartData = countSt();
                // redditpie = new PieChart(pieChartData);
                redditpie.setTitle("Sentiment");
                redditpie.getData().addAll(pieChartData);
                reddittableview.setItems(reddittableviewd);
                redditaddbutton();
                if(reddit.size() > 0){redditexport_btn.setDisable(false);}
                break;
            default:
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Null error");
                alert.setContentText("Please select something!");
                alert.showAndWait();
        }
        scrape_btn.setDisable(false);

    }

    private void msaddbutton() {
        Callback<TableColumn<MSDisplay, Void>, TableCell<MSDisplay, Void>> cellFactory = new Callback<TableColumn<MSDisplay, Void>, TableCell<MSDisplay, Void>>() {
            @Override
            public TableCell<MSDisplay, Void> call(final TableColumn<MSDisplay, Void> param) {
                final TableCell<MSDisplay, Void> cell = new TableCell<MSDisplay, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            MSDisplay data = getTableView().getItems().get(getIndex());
                            MoneySmartArt details = (MoneySmartArt) moneys.get(data.getUrl());
                            Dialog dialog = new Dialog();
                            dialog.initStyle(StageStyle.DECORATED);
                            dialog.setTitle("Detail");
                            ButtonType Close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

                            GridPane grid = new GridPane();
                            grid.setHgap(50);
                            grid.setVgap(10);

                            Label lbl = new Label();
                            Label lbl2 = new Label();
                            Label lbl3 = new Label();
                            Label lbl4 = new Label();
                            TextArea ta1 = new TextArea();
                            lbl.setText("Title: " + details.getPostTitle());
                            lbl.setTextAlignment(TextAlignment.LEFT);
                            lbl.setFont(Font.font(18));
                            grid.add(lbl, 0, 0);
                            lbl2.setText("Author: " + details.getArtAuthorName());
                            lbl2.setFont(Font.font(18));
                            grid.add(lbl2, 0, 1);
                            lbl3.setText("Date of Published: " + details.getArtDate());
                            lbl3.setFont(Font.font(18));
                            grid.add(lbl3, 0, 2);
                            lbl4.setText("Content: ");
                            lbl4.setFont(Font.font(18));
                            grid.add(lbl4, 0, 3);
                            ta1.setText(details.getPostBody());
                            ta1.setWrapText(true);
                            ta1.setEditable(true);
                            ta1.setFont(Font.font(16));
                            grid.add(ta1, 0, 4);

                            dialog.getDialogPane().setContent(grid);
                            dialog.getDialogPane().getButtonTypes().addAll(Close);

                            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                            stage.showAndWait();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        mscolBtn.setCellFactory(cellFactory);
    }

    private void redditaddbutton() {
        Callback<TableColumn<RedditDisplay, Void>, TableCell<RedditDisplay, Void>> cellFactory = new Callback<TableColumn<RedditDisplay, Void>, TableCell<RedditDisplay, Void>>() {
            @Override
            public TableCell<RedditDisplay, Void> call(final TableColumn<RedditDisplay, Void> param) {
                final TableCell<RedditDisplay, Void> cell = new TableCell<RedditDisplay, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RedditDisplay data = getTableView().getItems().get(getIndex());
                            RedditPost details = (RedditPost) reddit.get(data.getUrl());
                            Dialog dialog = new Dialog();
                            dialog.initStyle(StageStyle.DECORATED);
                            dialog.setTitle("Detail");
                            ButtonType Close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

                            GridPane grid = new GridPane();
                            grid.setHgap(50);
                            grid.setVgap(10);

                            Label lbl = new Label();
                            Label lbl2 = new Label();
                            Label lbl3 = new Label();
                            Label lbl4 = new Label();
                            Label lbl5 = new Label();
                            TextArea ta1 = new TextArea();
                            TextArea ta2 = new TextArea();
                            lbl.setText("Title: " + details.getPostTitle());
                            lbl.setTextAlignment(TextAlignment.LEFT);
                            lbl.setFont(Font.font(18));
                            grid.add(lbl, 0, 0);
                            lbl2.setText("Likes: " + details.getPostScore());
                            lbl2.setFont(Font.font(18));
                            grid.add(lbl2, 0, 1);
                            lbl3.setText("Content: ");
                            lbl3.setFont(Font.font(18));
                            grid.add(lbl3, 0, 2);
                            ta1.setText(details.getPostBody());
                            ta1.setWrapText(true);
                            ta1.setEditable(true);
                            ta1.setFont(Font.font(16));
                            grid.add(ta1, 0, 3);
                            lbl4.setText("Number of Comments: " + details.getNoComments());
                            lbl4.setFont(Font.font(18));
                            grid.add(lbl4, 0, 4);
                            lbl5.setText("Comments: ");
                            lbl5.setFont(Font.font(18));
                            grid.add(lbl5, 0, 5);
                            ta2.setText(details.getCommentsStringformatted());
                            ta2.setWrapText(true);
                            ta2.setEditable(true);
                            ta2.setFont(Font.font(16));
                            grid.add(ta2, 0, 6);

                            dialog.getDialogPane().setContent(grid);
                            dialog.getDialogPane().getButtonTypes().addAll(Close);

                            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                            stage.showAndWait();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        redditcolBtn.setCellFactory(cellFactory);

    }

    @FXML
    private void msexporthandler(ActionEvent event) throws IOException, InterruptedException {
        if(moneys.size() > 0){
            FileWriter mscsv = new FileWriter("MoneySmart.csv");
            CSVWriter write = new CSVWriter(mscsv);
            redditexport_btn.setDisable(true);
            msexport_btn.setDisable(true);
            String[] head = {"URL", "Title", "Content", "Summerized Content", "Date of Published", "Author", "Author Info"};
            write.writeNext(head);
            for (String i : moneys.keySet()) {
                MoneySmartArt obj = (MoneySmartArt) moneys.get(i);
                String[] insert = {obj.getUrl(), obj.getPostTitle(), obj.getPostBody(), obj.getArtSum(), obj.getArtDate(), obj.getArtAuthorName(), obj.getArtAuthorInfo()};
                write.writeNext(insert);
                write.flush();
            }
            write.close();
            if(reddit.size() > 0){
                redditexport_btn.setDisable(false);
            }
            msexport_btn.setDisable(false);
        }
        else{
            System.out.println("no data");
        }
    }

    @FXML
    private void redditexporthandler(ActionEvent event) throws InterruptedException {
        if(reddit.size() > 0){
            FileWriter redditcsv;
            try {
                redditcsv = new FileWriter("Reddit.csv");
                CSVWriter write = new CSVWriter(redditcsv, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
                redditexport_btn.setDisable(true);
                msexport_btn.setDisable(true);
                String[] head = {"URL", "Title", "Content", "Number of Like", "Comments"};
                write.writeNext(head);
                for (String i : reddit.keySet()) {
                    RedditPost obj = (RedditPost) reddit.get(i);
                    String[] insert = {i, obj.getPostTitle(), obj.getPostBody(), obj.getPostScore(), obj.getCommentsString()};
                    write.writeNext(insert);
                    write.flush();
                }
                write.close();
                redditexport_btn.setDisable(false);
                if(moneys.size() > 0){
                    msexport_btn.setDisable(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            System.out.println("no data");
        }
    }

}
