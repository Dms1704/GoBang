package cn.xupt.ui;

import cn.xupt.domain.Board;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChessBoardUI{

    private Board board = new Board();
    private Pane pane = new Pane();
    private Image backImage = new Image("背景.jpg");
    private double width = board.getWidth() ;   //棋盘边长
    private BorderPane borderPane = new BorderPane();
    private Canvas canvas = new Canvas(board.getBackWidth(), board.getBackHeight());
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    /**
     * 绘制棋盘
     */
    public void drawChessBoard(){
        //背景图片
        gc.drawImage(backImage, 0, 0, backImage.getWidth(), backImage.getHeight(), 0, 0, board.getBackWidth(), board.getBackHeight());
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
    }

    /**
     * 绘制棋子
     */
    public void drawChessPiece(int x, int y, int flag){
        double pieceSize = 21;
        if (flag == 1){     //白旗
            gc.setFill(Color.WHITE);
            System.out.println(pieceSize/2);
            gc.setLineWidth(2);
            gc.strokeOval(x-pieceSize/2, y-pieceSize/2, pieceSize, pieceSize);
            gc.fillOval(x-pieceSize/2, y-pieceSize/2, pieceSize, pieceSize);
        }
        else if (flag == 2){      //黑棋
            gc.setFill(Color.BLACK);
            gc.fillOval(x-pieceSize/2, y-pieceSize/2, pieceSize, pieceSize);
        }
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
