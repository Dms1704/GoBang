import cn.xupt.client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.*;

public class ClientTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Client client = new Client();
        Scene scene = new Scene(client.getChess().getPane(), client.getChess().getGobangModel().getBackWidth(), client.getChess().getGobangModel().getBackHeight());
        stage.setScene(scene);
        stage.setTitle("连接");
        stage.show();
    }
}
