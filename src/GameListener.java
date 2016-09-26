import javax.swing.*;
import java.awt.*;

/**
 * Created by HUPENG on 2016/9/25.
 */
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
