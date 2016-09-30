import javax.swing.*;
import java.awt.*;

public interface GameListener {
    /**
     * 黑色棋子胜
     * */
    public void blackWin();
    /**
     * 白色色棋子胜
     * */
    public void whiteWin();

    /**
     * 画图回掉
     * */
    public void draw(ImageIcon imageIcon);
}
