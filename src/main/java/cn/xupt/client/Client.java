package cn.xupt.client;

import cn.xupt.ui.ChessBoardUI;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private ChessBoardUI chess = new ChessBoardUI();

    /**
     * 连接变量
     */
    private Socket socket = null;
    private String name;
    private DataInputStream ds;
    private PrintStream ps;
    private Lisener lisener;

    /**
     * UI初始化包括设置事件
     */
    public Client(){
        init();
    }

    public void init(){
        chess.drawChessBoard();
        for (int i=1; i<20; i++)
            for (int j=1; j<20; j++)
                chess.getGobangModel().getPeice()[i][j] = 2;    //初始化棋盘棋子信息
        chess.getLink().setOnMouseClicked(e->linkButtonClick());
        chess.getMatch().setOnMouseClicked(e->matchButtonClick());
        chess.getQuit().setOnMouseClicked(e->quitButtonClick());
    }

    /**
     * 点击建立连接事件
     */
    public void linkButtonClick(){
        name = chess.getNameField().getText();
        if (socket == null){
            try {
                socket = new Socket(InetAddress.getLocalHost(), 8888);
                if (socket != null){
                    ps = new PrintStream(socket.getOutputStream());
                    ps.println("LINK:"+name);
                    ps.flush();
                    //启动监听线程
                    lisener = new Lisener(socket, name, this);
                    lisener.start();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击匹配事件
     */
    public void matchButtonClick(){
        //发送匹配消息MATCH到服务器
        if (socket != null){
            ps.println("MATCH");
            ps.flush();
        }
    }

    /**
     * 点击退出连接事件
     */
    public void quitButtonClick(){
        //发送匹配消息MATCH到服务器
        if (socket != null){
            ps.println("QUIT");
            ps.flush();
            chess.getLinkInfo().setText("退出连接");
        }
    }

    public ChessBoardUI getChess() {
        return chess;
    }
}
