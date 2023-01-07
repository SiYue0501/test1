import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.SocketException;

public class SecondActivity extends JFrame{
    public TextField textField01;//字板
   // public TextField textField02;//字板
    private JButton jButton;//J按钮
    public String iP;
    public String port;
    private JComboBox<String>faceCombo;//选项盒子

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String path = "image/img01.PNG";
        // 背景图片
        ImageIcon background = new ImageIcon(path);//图片对象？
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel(background);
        // 把标签的大小位置设置为图片刚好填充整个面板
        label.setBounds(0, 0, this.getWidth(), this.getHeight());//“THIS”为面板，此处在于获取面板的宽高
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        JPanel imagePanel = (JPanel) this.getContentPane();//获取此面板的内容窗格
        imagePanel.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
       this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));//“MIN_VALUE”即最小值，

    }

    public SecondActivity(){
        this.setLayout(null);//版面布局
        this.setVisible(true);//此类可见
        this.setLocation(500,100);//屏幕中显示位置
        this.setSize(700,500);//默认大小
        textField01=new TextField("IP地址",20);//IP地址输入框标签
        textField01.setForeground(Color.blue);//字色
        faceCombo=new JComboBox<>();//建立选项框对象
        faceCombo.setForeground(Color.black);//设置字体颜色
        faceCombo.addItem("我方先行");//加入选项
        faceCombo.addItem("敌方先行");
        
        jButton=new JButton("开始游戏");
        jButton.setFont(new  java.awt.Font("华文行楷",  1, 25)); 
        jButton.setForeground(Color.white);//设置按钮背景颜色
        jButton.setBounds(250,200,200,50);//大小以及位置
        jButton.setBorderPainted(false); //是否显示边框：不显示
        jButton.setContentAreaFilled(false); //是否透明：透明

        textField01.setBounds(200,200,200,25);//外形参数
        faceCombo.setBounds(200,250,150,25);//外形参数
        faceCombo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {//选项框活动监听
              GameActivity.signal=faceCombo.getSelectedIndex();//将选项框所选项的索引赋值到“信号量”上，其将在Game activity的下棋先后手中起作用
            }
        });

        jButton.setBounds(220,400,150,25);
        this.add(faceCombo);//此部分意义为将控件加入到窗口中来
        this.add(jButton);
        this.add(textField01);
     
         
        jButton.addMouseListener(new MouseListener() {//鼠标监听方法
            @Override
            public void mouseClicked(MouseEvent e) {
                iP=textField01.getText();//获取输入框输入的IP
                GameActivity.ip=iP;//将此页面中的IP地址赋值到Game Activity中

                try {

                    new Thread(new GameActivity()).start();//设置新线程

                    dispose();//即释放所有本机屏幕资源
                } catch (SocketException e1) {
                    e1.printStackTrace();//对Socket的异常打印？
                }


            }

            @Override
            public void mousePressed(MouseEvent e) {//按下鼠标

            }

            @Override
            public void mouseReleased(MouseEvent e) {//松开鼠标

            }

            @Override
            public void mouseEntered(MouseEvent e) {//移入控件

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


    }

}
