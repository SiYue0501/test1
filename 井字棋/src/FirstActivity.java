import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FirstActivity extends JFrame {
    private JButton jButton01;

    public FirstActivity(){
        //取消布局管理器
        this.setLayout(null);
        this.setVisible(true);
        this.setLocation(500,100);
        this.setSize(700,500);
        //按钮设计
        jButton01=new JButton("网络对战");
        jButton01.setFont(new  java.awt.Font("华文行楷",  1, 40)); 
        jButton01.setForeground(Color.black);
        jButton01.setBounds(235,200,200,50);
        jButton01.setBorderPainted(true); 

        jButton01.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SecondActivity();
                dispose();

            }//鼠标按键在组件上单击时调用。

            @Override
            public void mousePressed(MouseEvent e) {

            }//鼠标按键在组件上按下时调用。

            @Override
            public void mouseReleased(MouseEvent e) {

            }//鼠标按钮在组件上释放时调用。

            @Override
            public void mouseEntered(MouseEvent e) {
            	

            }//鼠标进入到组件上时调用。

            @Override
            public void mouseExited(MouseEvent e) {

            }//鼠标离开组件时调用。
        });
        
        this.add(jButton01);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        String path = "image/img01.PNG";
        // 背景图片
        ImageIcon background = new ImageIcon(path);
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel(background);
        // 把标签的大小位置设置为图片刚好填充整个面板
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));


    }

}
