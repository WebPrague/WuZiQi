import mina.MinaUtil;
import mina.MyData;
import mina.SimpleMinaListener;
import org.apache.mina.core.session.IoSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;

/**
 * 控制界面
 * */
public class ChessController extends JFrame {
    private ChessBoard chessBoard = new ChessBoard(new MyGameListener());
    private JPanel jPanel = null;
    private JLabel jLabel = null;
    private boolean canPlay;

    //局数计数
    private int count = 0;
    //部署计数
    private int stepCount = 0;

    //MINA
    private MinaUtil minaUtil = null;
    private boolean isServer = false;


    public ChessController(){

        this.setTitle("联机对战五子棋");
        this.setSize(new Dimension(650, 695));
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

        //设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu settingMenu = new JMenu("功能");
        jMenuBar.add(settingMenu);

        JMenuItem inviteOtherItem = new JMenuItem("邀请别人");
        JMenuItem acceptInviteItem = new JMenuItem("接受邀请");
        JMenuItem exitItem = new JMenuItem("退出");
        settingMenu.add(inviteOtherItem);
        settingMenu.add(acceptInviteItem);
        settingMenu.add(exitItem);


        JMenu helpMenu = new JMenu("帮助");
        jMenuBar.add(helpMenu);

        JMenuItem helpItem = new JMenuItem("关于");
        helpMenu.add(helpItem);


        inviteOtherItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inviteOther();
            }
        });
        acceptInviteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptInvite();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Icon authorIcon = new ImageIcon("zp.png");
                JOptionPane.showMessageDialog(ChessController.this.getContentPane(),"作者：张鹏（14级软件工程2班）\n邮箱：zhangpeng@imudges.com\n版本：1.1.0beta","关于",JOptionPane. PLAIN_MESSAGE,authorIcon);
            }
        });

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
        count ++;
        stepCount = 0;
        if ((isServer && count % 2 == 1)||(!isServer && count % 2 == 0)){
            canPlay = true;
        }else {
            canPlay = false;
        }
        if (count > 1){
            if (canPlay){
                setTitle("轮到你下了哦");
            }else{
                setTitle("轮到对方下了哦");
            }
        }
    }

    private void inviteOther(){
        //System.out.println("邀请别人");
        try{
            JOptionPane.showInternalMessageDialog(ChessController.this.getContentPane(),
                    "你的IP地址为：" + InetAddress.getLocalHost().getHostAddress() ,"邀请别人", JOptionPane.INFORMATION_MESSAGE);
            isServer = true;
            minaUtil = MinaUtil.getInstance(new MySimpleMinaListener(),true,null);
            canPlay = true;
            setTitle("轮到你下了哦");
        }catch (Exception e){
            JOptionPane.showInternalMessageDialog(ChessController.this.getContentPane(),
                    "发生未知错误" ,"邀请别人", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acceptInvite(){
        //
        String b = JOptionPane.showInputDialog("请输入邀请方IP地址：");
        if (!Tool.ipCheck(b)){
            JOptionPane.showInternalMessageDialog(ChessController.this.getContentPane(),
                    "IP地址格式错误" ,"接受邀请", JOptionPane.INFORMATION_MESSAGE);
        }else {
            isServer = false;
            minaUtil = MinaUtil.getInstance(new MySimpleMinaListener(),false,b);
            canPlay = false;
            setTitle("轮到对方下了哦");
        }
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
//            if(count ++ % 2 == 0){
//                boolean flag = chessBoard.addBlack(e.getX(),e.getY());
//                if (!flag){
//                    count --;
//                }
//            }else{
//                boolean flag = chessBoard.addWhite(e.getX(),e.getY());
//                if (!flag){
//                    count --;
//                }
//            }
            if(canPlay){
                MyData myData = new MyData();
                myData.x = e.getX();
                myData.y = e.getY();
                canPlay = false;
                minaUtil.send(myData);
                playChess(e.getX(), e.getY() , false);

                setTitle("轮到对方下了哦");
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

    class MySimpleMinaListener implements SimpleMinaListener{

        @Override
        public void onReceive(Object obj, IoSession ioSession) {
            MyData myData = (MyData)obj;
            playChess(myData.x, myData.y , true);
            canPlay = true;
            setTitle("轮到你下了哦");
        }
    }

    /**
     * 下棋
     * x,y坐标，flag == true黑棋 flag==false 白棋
     * */

    private void playChess(int x,int y, boolean isRemote){
        if (stepCount++ % 2 == 0){
            if (!chessBoard.addBlack(x,y)){
                stepCount --;
            }
        }else {
            if (!chessBoard.addWhite(x,y)){
                stepCount --;
            }
        }
    }

}
