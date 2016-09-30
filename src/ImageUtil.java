import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class ImageUtil {
    /**
     * 缩放图像（按比例缩放）
     * @param srcImageFile 源图像文件地址
     */
    public final static BufferedImage scale(String srcImageFile,
                                  int targetWidth, int targetHeight) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            width = targetWidth;
            height = targetHeight;

            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //ImageIO.write(tag, "JPEG", baos);// 输出到文件流

            //ImageIcon imageIcon = new ImageIcon(baos.toByteArray());
            return tag;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
