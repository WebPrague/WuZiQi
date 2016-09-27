import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by HUPENG on 2016/9/25.
 */
public class ChessBoard {

    private BufferedImage bufferedImage = null;
    private GameListener gameListener;
    /**
     * 黑色：1
     * 白色：2
     * 未走过：0
     * */
    private int chesses[][] = new int[100][100];
    /**
     * 轮到黑色走:true
     * 轮到白色走：false
     * */
    private boolean flag = true;

    public ChessBoard(GameListener gameListener){
        this.gameListener = gameListener;
    }



    /**
     * 初始化一个棋盘
     * */
    public Icon init()  {
        bufferedImage = ImageUtil.scale("board.png",650,650);
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        gameListener.draw(imageIcon);
        for (int i = 0;i<GameConfig.Row ; i++){
            for (int j = 0;j<GameConfig.Column ; j++){
                chesses[i][j] = 0;
            }
        }
        flag = true;
        return imageIcon;
    }

    /**
     * 棋盘山增加一个黑色的棋子
     * */
    public boolean addBlack(int x, int y){
        Coord coord = getCoord(x,y);
        if (chesses[coord.x][coord.y] == 0 && flag){
            BufferedImage tempBufferedImage = ImageUtil.scale("black.png",GameConfig.ChessSize,GameConfig.ChessSize);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(tempBufferedImage,(int)(coord.x*32.5 + 17),(int)(coord.y*32.5 + 17),null);

            BufferedImage zxBufferedImage = ImageUtil.scale("zx.png",GameConfig.ChessSize,GameConfig.ChessSize);
            BufferedImage outBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),bufferedImage.getType());
            outBufferedImage.setData(bufferedImage.getData());
            g = outBufferedImage.getGraphics();
            g.drawImage(zxBufferedImage,(int)(coord.x*32.5 + 17),(int)(coord.y*32.5 + 17),null);

            ImageIcon imageIcon = new ImageIcon(outBufferedImage);
            gameListener.draw(imageIcon);
            chesses[coord.x][coord.y] = 1;
            flag = false;
            checkWiner();
            return true;
        }
        return false;
    }

    public boolean addWhite(int x, int y){
        Coord coord = getCoord(x,y);
        if (chesses[coord.x][coord.y] == 0 && !flag){
            BufferedImage tempBufferedImage = ImageUtil.scale("white.png",GameConfig.ChessSize,GameConfig.ChessSize);


            Graphics g = bufferedImage.getGraphics();
            g.drawImage(tempBufferedImage,(int)(coord.x*32.5 + 17),(int)(coord.y*32.5 + 17),null);

            BufferedImage zxBufferedImage = ImageUtil.scale("zx.png",GameConfig.ChessSize,GameConfig.ChessSize);
            BufferedImage outBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),bufferedImage.getType());
            outBufferedImage.setData(bufferedImage.getData());
            g = outBufferedImage.getGraphics();
            g.drawImage(zxBufferedImage,(int)(coord.x*32.5 + 17),(int)(coord.y*32.5 + 17),null);

            ImageIcon imageIcon = new ImageIcon(outBufferedImage);
            gameListener.draw(imageIcon);


            chesses[coord.x][coord.y] = 2;
            flag = true;
            checkWiner();
            return true;
        }
        return false;
    }

    /**
     * 棋盘上的坐标的封装
     * */
    class Coord{
        public int x;
        public int y;
    }

    /**
     * 普通坐标转到棋盘坐标
     * */
    private Coord getCoord(int x,int y){
        Coord coord = new Coord();
        coord.x = (int)((x-15.75)/32.5);
        coord.y = (int)((y-21.75)/32.5);
        System.out.println("X:" + coord.x + " Y:" + coord.y);
        return coord;
    }

    /**
     * 判断胜负
     * */
    private void checkWiner(){
        for (int i = 0 ; i < GameConfig.Row; i ++ ){
            for (int j = 0 ; j < GameConfig.Column ; j++ ){
                if (chesses[i][j]!=0){
                    if ((chesses[i][j] == (chesses[i+1][j]))&&(chesses[i][j] == (chesses[i+2][j]))
                            &&(chesses[i][j] == (chesses[i+3][j]))&&(chesses[i][j] == (chesses[i+4][j]))){
                        if (chesses[i][j] == 1){
                            gameListener.blackWin();
                        }else {
                            gameListener.whiteWin();
                        }
                        return;
                    }else{
                        if ((chesses[i][j] == (chesses[i][j+1]))&&(chesses[i][j] == (chesses[i][j+2]))
                                &&(chesses[i][j] == (chesses[i][j+3]))&&(chesses[i][j] == (chesses[i][j+4]))){
                            if (chesses[i][j] == 1){
                                gameListener.blackWin();
                            }else {
                                gameListener.whiteWin();
                            }
                            return;
                        }else{
                            if ((chesses[i][j] == (chesses[i+1][j+1]))&&(chesses[i][j] == (chesses[i+2][j+2]))
                                    &&(chesses[i][j] == (chesses[i+3][j+3]))&&(chesses[i][j] == (chesses[i+4][j+4]))){
                                if (chesses[i][j] == 1){
                                    gameListener.blackWin();
                                }else {
                                    gameListener.whiteWin();
                                }
                                return;
                            }else{
                                try {
                                    if ((chesses[i][j] == (chesses[i+1][j-1]))&&(chesses[i][j] == (chesses[i+2][j-2]))
                                            &&(chesses[i][j] == (chesses[i+3][j-3]))&&(chesses[i][j] == (chesses[i+4][j-4]))){
                                        if (chesses[i][j] == 1){
                                            gameListener.blackWin();
                                        }else {
                                            gameListener.whiteWin();
                                        }
                                        return;
                                    }
                                }catch (Exception e){
                                    return;
                                }

                            }
                        }
                    }
                }


            }
        }
    }
}
