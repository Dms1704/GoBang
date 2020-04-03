package cn.xupt.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

    public void start(Stage stage) throws Exception {
        ChessBoardUI chess = new ChessBoardUI();
        chess.drawChessBoard();
        chess.getPane().getChildren().add(chess.getCanvas());
        chess.drawChessPiece(200, 200, 2);
        chess.drawChessPiece(300, 300, 1);
        Scene scene = new Scene(chess.getPane(), chess.getBoard().getBackWidth(), chess.getBoard().getBackHeight());
        stage.setScene(scene);
        stage.setTitle("五子棋");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
