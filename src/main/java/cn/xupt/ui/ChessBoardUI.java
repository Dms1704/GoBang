package cn.xupt.ui;

import cn.xupt.domain.GobangModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ChessBoardUI{
    private Button quit = new Button("退出连接");
    private Text win = new Text();
    private HBox nameHBox = new HBox();
    private HBox linkHbox = new HBox();
    private VBox nameVBox = new VBox();
    private Button link = new Button("建立连接");
    private Button match = new Button("匹配");
    private Text linkInfo = new Text();
    private Text nameArea = new Text("name:");
    private TextField nameField = new TextField();
    private Text matchInfo = new Text();
    private GobangModel gobangModel = new GobangModel();
    private Pane pane = new Pane();
    private Image backImage = new Image("背景.jpg");
    private double width = gobangModel.getWidth() ;   //棋盘边长(正方形)
    private Canvas canvas = new Canvas(gobangModel.getBackWidth(), gobangModel.getBackHeight());
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    /**
     * 绘制棋盘
     */
    public void drawChessBoard(){
        //背景图片
        gc.drawImage(backImage, 0, 0, backImage.getWidth(), backImage.getHeight(), 0, 0, gobangModel.getBackWidth(), gobangModel.getBackHeight());
        //绘制棋盘
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeRect(25, 100, width, width);
        gc.setLineWidth(1);
        for (int i=1; i<20; i++){     //横线
            gc.strokeLine(25, 100+i*25, 25+width, 100+i*25);
        }
        for (int i=1; i<20; i++){     //竖线
            gc.strokeLine(25+i*25, 100, 25+i*25, 100+width);
        }
        //黑点大小
        int blackSpot = 7;
        gc.setFill(Color.BLACK);
        gc.fillOval(271.5, 346.5, blackSpot, blackSpot);
        gc.fillOval(96.5, 171.5, blackSpot, blackSpot);
        gc.fillOval(96.5, 521.5, blackSpot, blackSpot);
        gc.fillOval(446.5, 521.5, blackSpot, blackSpot);
        gc.fillOval(446.5, 171.5, blackSpot, blackSpot);
        nameHBox.getChildren().addAll(nameArea, nameField, win, quit);
        linkHbox.getChildren().addAll(link, linkInfo, match, matchInfo);
        nameVBox.getChildren().addAll(nameHBox, linkHbox);
        pane.getChildren().addAll(canvas, nameVBox);
    }

    /**
     * 绘制棋子
     */
    public boolean drawChessPiece(int x, int y, int flag){
        if (x>512.5 || x<37.5 || y>587.5 || y<112.5){
            return false;
        }
        double pieceSize = 21;
        if (flag == 1){     //白旗
            gc.setFill(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeOval(x-pieceSize/2, y-pieceSize/2, pieceSize, pieceSize);
            gc.fillOval(x-pieceSize/2, y-pieceSize/2, pieceSize, pieceSize);
        }
        else if (flag == 0){      //黑棋
            gc.setFill(Color.BLACK);
            gc.fillOval(x-pieceSize/2, y-pieceSize/2, pieceSize, pieceSize);
        }
        return true;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Image getBackImage() {
        return backImage;
    }

    public void setBackImage(Image backImage) {
        this.backImage = backImage;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public GobangModel getGobangModel() {
        return gobangModel;
    }

    public void setGobangModel(GobangModel gobangModel) {
        this.gobangModel = gobangModel;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public Button getLink() {
        return link;
    }

    public Button getMatch() {
        return match;
    }

    public Text getLinkInfo() {
        return linkInfo;
    }

    public Text getMatchInfo() {
        return matchInfo;
    }

    public TextField getNameField() {
        return nameField;
    }

    public Text getWin() {
        return win;
    }

    public Button getQuit() {
        return quit;
    }
}
