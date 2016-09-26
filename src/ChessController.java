import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessController extends JFrame{
    private ChessBoard chessBoard = new ChessBoard(new MyGameListener());
    private JPanel jPanel = null;
    private JLabel jLabel = null;
    private int count = 0;

    public ChessController(){
        this.setTitle("双人五子棋");
        this.setSize(new Dimension(650, 680));
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        /*
         *添加一块棋盘
         */
        this.setLayout(null);
        jPanel = new JPanel();
        jLabel = new JLabel();
        Icon icon = chessBoard.init();
        jLabel.setIcon(icon);
        jLabel.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());
        jPanel.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());
        jPanel.add(jLabel);
        this.add(jPanel);
        this.setVisible(true);
        jPanel.addMouseListener(new MyMouseListener());
    }


    public void init(){
        chessBoard.init();
    }



    /**
     * 游戏结果监听器
     * */
    class MyGameListener implements GameListener{


        @Override
        public void blackWin() {
            showResult(true);
        }

        @Override
        public void whiteWin() {
            showResult(false);
        }

        @Override
        public void draw(ImageIcon imageIcon) {
            jLabel.setIcon(imageIcon);
        }
    }
    /**
     * 鼠标按键监听器
     * */
    class MyMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(count ++ % 2 == 0){
                boolean flag = chessBoard.addBlack(e.getX(),e.getY());
                if (!flag){
                    count --;
                }
            }else{
                boolean flag = chessBoard.addWhite(e.getX(),e.getY());
                if (!flag){
                    count --;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    /**
     * 比赛结束，显示结果
     * */
    private void showResult(boolean flag){
        if(flag){
            JOptionPane.showInternalMessageDialog(ChessController.this.getContentPane(),
                    "黑方胜","游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showInternalMessageDialog(ChessController.this.getContentPane(),
                    "白方胜","游戏结束", JOptionPane.INFORMATION_MESSAGE);
        }

        int option = JOptionPane.showConfirmDialog(ChessController.this.getContentPane(),
                "是否重新开始一局？", "游戏提示", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null);
        switch (option) {
            case JOptionPane.YES_NO_OPTION:
                init();
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);
        }
    }
}
