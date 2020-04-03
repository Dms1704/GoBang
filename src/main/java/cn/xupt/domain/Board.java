package cn.xupt.domain;

public class Board {
    private double backWidth = 550;     //棋盘背景宽
    private double backHeight = 750;     //棋盘背景高
    private double width = 500;         //棋盘宽
    private int rowTitle = 20;
    private int peice[][] = new int [20][20];    //存放落子情况

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
            break;
        }
        if (count == 5)
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
            break;
        }
        if (count == 5)
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
            if (peice[i+1][j+1] == who)
                count++;
            break;
        }
        for (int i=row, j=col; i>=1 && j>=1; i--, j--){
            if (peice[i-1][j-1] == who)
                count++;
            break;
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
            if (peice[i+1][j-1] == who)
                count++;
            break;
        }
        for (int i=row, j=col; i>=1 && j<rowTitle; i--, j++){
            if (peice[i-1][j+1] == who)
                count++;
            break;
        }
        if (count == 5)
            return true;
        else
            return false;
    }

    public double getBackWidth() {
        return backWidth;
    }

    public void setBackWidth(double backWidth) {
        this.backWidth = backWidth;
    }

    public double getBackHeight() {
        return backHeight;
    }

    public void setBackHeight(double backHeight) {
        this.backHeight = backHeight;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
