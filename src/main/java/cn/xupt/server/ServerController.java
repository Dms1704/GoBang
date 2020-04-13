package cn.xupt.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 中央控制器
 */
public class ServerController {
    private ServerSocket server;
    private int port;
    private Socket socket = null;
    private DataInputStream is;
    private PrintStream ps;
    private List<Player> players;
    private List<Composition> compositions;
    private Link link;
    private Separate separate;

    /**
     * 负责连接的线程
     */
    class Link extends Thread{
        public void run() {
            while (true){
                if (players.size() < 2){   //小于10开始连接
                    try {
                        socket = server.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Player player = new Player(socket);
                    synchronized (new Link()) {
                        players.add(player);
                    }
                    System.out.println(player.name);
                    player.send("LINK:SUCCESS");
                    player.start();
                }
                else {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /*
     * 负责分组的线程
     */
    class Separate extends Thread{
        public void run() {
            while (true){
                int i = 0;
                Player black = null;
                Player white = null;
                synchronized (new Separate()){
                    for (Player player:players){
                        if (player.getStatus() == 1){
                            black = player;
                            break;
                        }
                    }
                    for (Player player:players){
                        if (player.getStatus() == 1 && player != black){
                            white = player;
                            break;
                        }
                    }
                }
                if (black != null && white != null){
                    compositions.add(new Composition(black, white));
                    black.setStatus(2);
                    white.setStatus(2);
                    black.send("MATCH:"+white.name+":0");       //发送对手信息和自己的颜色
                    System.out.println("white"+white.name);
                    System.out.println("black"+black.name);
                    white.send("MATCH:"+black.name+":1");
                }
            }
        }
    }

    /**
     * 负责存储各个客户端的输入流输出流和name
     */
    class Player extends Thread{
        Socket s;
        String name;
        InetAddress ip;
        PrintStream ps;
        DataInputStream ds;
        int status = 0;     //0表示未点击匹配的，1表示点击匹配的, 2表示匹配中

        /**
         * player线程主要工作：负责对应客户端的通信任务
         */
        public void run() {
            while (true){
                String line = null;
                try {
                    line = ds.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line != null){
                    StringTokenizer st = new StringTokenizer(line, ":");
                    String key = st.nextToken();
                    String valueName = null;
                    String value = null;
                    if (key.equalsIgnoreCase("MATCH")){
                        status = 1;
                    }
                    else if (key.equalsIgnoreCase("MSG")){
                        valueName = st.nextToken();
                        value = st.nextToken();
                        for (Player player:players){
                            if (valueName.equals(player.name)){   //找到匹配的player
                                player.send("MSG:"+value);
                            }
                        }
                    }
                    else if (key.equalsIgnoreCase("QUIT")){
                        System.out.println(this.name);
                        disconnect(this);
                        return;
                    }
                }
            }
        }

        public void send(String msg){
            ps.println(msg);
            ps.flush();
        }

        public Player(Socket s) {
            this.s = s;
            try {
                ps = new PrintStream(s.getOutputStream());
                ds =new DataInputStream(s.getInputStream());
                StringTokenizer st = new StringTokenizer(ds.readLine(), ":");
                if (st.nextToken().equalsIgnoreCase("LINK")){
                    name = st.nextToken();
                }
                this.ip = s.getLocalAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void close(){
            try {
                ds.close();
                ps.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "s=" + s +
                    ", name='" + name + '\'' +
                    ", ip=" + ip +
                    ", ps=" + ps +
                    ", ds=" + ds +
                    ", status=" + status +
                    '}';
        }
    }

    /**
     * 断开连接
     * @param player
     */
    public synchronized void disconnect(Player player){
        player.send("QUIT:"+player.name);
        players.remove(player);
    }

    /**
     * 初始化
     */
    public void init() {
        port = 8888;
        players = new Vector<>();   //构造线程安全的Vector
        compositions = new Vector<>();
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        link = new Link();
        link.start();
        separate = new Separate();
        separate.start();
    }

    /**
     * 一场棋局
     */
    class Composition {
        private Player black;
        private Player white;

        public Composition(Player black, Player white) {
            this.black = black;
            this.white = white;
        }
    }
}
