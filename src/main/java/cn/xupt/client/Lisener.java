package cn.xupt.client;

import com.sun.security.jgss.GSSUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class Lisener extends Thread {
    private Socket socket;
    private String name;
    private DataInputStream ds;
    private PrintStream ps;
    private Client client;
    private int color;
    private double cell;
    private String rival;

    /**
     * 客户端对应的监听线程
     * @param s
     * @param name
     * @param client
     */
    public Lisener(Socket s, String name, Client client){
        this.socket = s;
        this.name = name;
        this.client = client;
        cell = client.getChess().getGobangModel().getWidth()/20;
        if (socket != null){
            try {
                ds = new DataInputStream(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * lisener的主要任务工作
     */
    public void run() {
        String line = null;
        while (true){
            try {
                line = ds.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringTokenizer st = new StringTokenizer(line, ":");
            String key = st.nextToken();
            String value;
            if (key.equalsIgnoreCase("LINK")){      //若为连接信息
                value = st.nextToken();
                if (value.equalsIgnoreCase("SUCCESS")){
                    client.getChess().getLinkInfo().setText(value);
                }
            }
            else if (key.equalsIgnoreCase("MATCH")){    //若为匹配信息
                System.out.println(line);
                String valueName = st.nextToken();
                rival = valueName;
                client.getChess().getGc().strokeText(valueName, 50, 75);
                value = st.nextToken();
                //绘制五子棋界面
                if (value.equalsIgnoreCase("0")){   //黑子
                    client.getChess().getMatchInfo().setText("你执黑子，请先走");
                    color = 0;
                    chessMove();
                }
                else if (value.equalsIgnoreCase("1")){
                    client.getChess().getMatchInfo().setText("你执白子");
                    color = 1;
                }
            }
            else if (key.equalsIgnoreCase("MSG")){  //落子信息
                value = st.nextToken();
                //解析服务端传来的坐标并画棋子
                readMotion(value);
                StringTokenizer s = new StringTokenizer(value, " ");
                int i = Integer.parseInt(s.nextToken());
                int j = Integer.parseInt(s.nextToken());
                if (color == 1){
                    if (judgeWinner(i, j, 0)){ //对方赢了
                        client.getChess().getWin().setText("你输了");
                    }
                }
                else if (color == 0){
                    if (judgeWinner(i, j, 1)){ //对方赢了
                        client.getChess().getWin().setText("你输了");
                    }
                }
                //落子
                chessMove();
            }
        }
    }

    /**
     * 判断是否分出胜负
     * @return
     */
    public boolean judgeWinner(int row, int col, int color){
        if (client.getChess().getGobangModel().judgeCol(col, color) || client.getChess().getGobangModel().judgeRow(row, color) ||
        client.getChess().getGobangModel().judgeMainDiagonal(col, row, color) ||
        client.getChess().getGobangModel().judgeDeputyDiagonal(col, row, color)){   //若有一项为true则胜出
            return true;
        }
        return false;
    }

    /**
     * 解读服务端传来的落子坐标并画棋子
     */
    public void readMotion(String value){
        StringTokenizer s = new StringTokenizer(value, " ");
        int i = Integer.parseInt(s.nextToken());
        int j = Integer.parseInt(s.nextToken());
        if (color == 0) {
            client.getChess().drawChessPiece(25 + i * 25, 100 + j * 25, 1);
            client.getChess().getGobangModel().getPeice()[i][j] = 1;
        }
        else {
            client.getChess().drawChessPiece(25 + i * 25, 100 + j * 25, 0);
            client.getChess().getGobangModel().getPeice()[i][j] = 0;
        }
    }

    /**
     * 落子事件
     */
    public void chessMove(){
        client.getChess().getCanvas().setOnMouseClicked(e->{
            double a, b;
            int m, n;
            boolean flag;
            //获得鼠标点击坐标
            a = e.getX();
            b = e.getY();
            System.out.println(a);
            //映射数组地址
            m = (int)((a-25+cell/2)/cell);
            n = (int)((b-100+cell/2)/cell);
            flag = client.getChess().drawChessPiece(25 + m * 25, 100 + n * 25, color);
            System.out.println(flag);
            if (true == flag){      //若成功画出棋子
                send("MSG:"+rival+":"+m+" "+n);
                client.getChess().getGobangModel().getPeice()[m][n] = color;
                for (int x=1; x<20; x++) {
                    for (int y = 1; y < 20; y++) {
                        System.out.printf("%d ", client.getChess().getGobangModel().getPeice()[x][y]);
                    }
                    System.out.println();
                }
                if (color == 1){
                    if (judgeWinner(m, n, 1)){ //赢了
                        client.getChess().getWin().setText("你赢了");
                        System.out.println("你赢了bai");
                    }
                }
                else {
                    if (judgeWinner(m, n, 0)){ //赢了
                        client.getChess().getWin().setText("你赢了");
                        System.out.println("你赢了hei");
                    }
                }
            }
            client.getChess().getCanvas().setOnMouseClicked(null);
        });
    }

    /**
     * 向服务器发送消息
     * @param msg
     */
    public void send(String msg){
        ps.println(msg);
        ps.flush();
    }
}
