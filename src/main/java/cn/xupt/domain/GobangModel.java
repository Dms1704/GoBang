package cn.xupt.domain;

/**
 * 五子棋模型
 */
public class GobangModel {
    private double backWidth = 550;     //棋盘背景宽
    private double backHeight = 750;     //棋盘背景高
    private double width = 500;         //棋盘宽
    private int rowTitle = 20;
    private int peice[][] = new int [20][20];    //存放落子情况   0表示黑棋 1表示白棋 2表示无棋子

    /**
     * 判断row行有没有五子连珠
     * @param row :对应落子坐标的y
     * @param who :落子者
     * @return
     */
    public boolean judgeRow(int row, int who) {
        int count = 0;
        for (int i = 1; i < rowTitle; i++){
            if (peice[row][i] == who)
                count++;
        }
        if (count >= 5)
            return true;
        else
            return false;
    }

    /**
     * 判断col列有没有五子连珠
     * @param col :对应落子坐标的x
     * @param who :落子者
     * @return
     */
    public boolean judgeCol(int col, int who) {
        int count = 0;
        for (int i = 1; i < rowTitle; i++){
            if (peice[i][col] == who)
                count++;
        }
        if (count >= 5)
            return true;
        else
            return false;
    }

    /**
     * 判断主对角线方向有没有五子连珠
     * @param row:对应落子坐标y
     * @param col:对应落子坐标x
     * @param who
     * @return
     */
    public boolean judgeMainDiagonal(int row, int col, int who) {
        int count = 1;
        for (int i=row, j=col; i<rowTitle && j<rowTitle; i++, j++){
            if (i+1 != rowTitle && j+1 != rowTitle){
                if (peice[i+1][j+1] == who)
                    count++;
            }
        }
        for (int i=row, j=col; i>=1 && j>=1; i--, j--){
            if (i-1 != 0 && j-1 != 0){
                if (peice[i-1][j-1] == who)
                    count++;
            }
        }
        if (count == 5)
            return true;
        else
            return false;
    }

    /**
     * 判断落子点对应的副对角线上有没有五子连珠
     * @param row:落子点的y
     * @param col:落子点的x
     * @param who:落子者
     * @return
     */
    public boolean judgeDeputyDiagonal(int row, int col, int who) {
        int count = 1;
        for (int i=row, j=col; i<rowTitle && j>=1; i++, j--){
            if (i+1 != rowTitle && j-1 != 0){
                if (peice[i+1][j-1] == who)
                    count++;
            }
        }
        for (int i=row, j=col; i>=1 && j<rowTitle; i--, j++){
            if (i-1 != 0 && j+1 != rowTitle){
                if (peice[i-1][j+1] == who)
                    count++;
            }
        }
        if (count == 5)
            return true;
        else
            return false;
    }

    public double getBackWidth() {
        return backWidth;
    }

    public double getBackHeight() {
        return backHeight;
    }

    public double getWidth() {
        return width;
    }

    public int[][] getPeice() {
        return peice;
    }

    public void setPeice(int[][] peice) {
        this.peice = peice;
    }
}
