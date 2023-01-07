import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class GameActivity extends JFrame implements Runnable{

	//判断数据
    public static int signal=0;//默认我方先行
    public static  String ip;
    private  JButton[]buttons=new JButton[9];
    //棋盘、棋手设计
    private int[]boom=new int[9];
    private int judge=2;
    private ImageIcon a;
    private ImageIcon b;
    //构建客户端、服务端
    //构建端口
    private DatagramSocket client;
    private int serverPort=6666;
    private int clientPort=8888;
    private int outPort=9900;
    private DatagramSocket server;
    //传输数据
    public GameActivity() throws SocketException {
    	//棋手下棋
        a=new ImageIcon("image/img2.PNG");
        b=new ImageIcon("image/img3.PNG");
        if(signal==1){
            serverPort=7777;
            clientPort=9900;
            outPort=8888;
            //棋手交换
            ImageIcon c=a;
            a=b;
            b=c;
        }
        //棋盘窗口
        this.setVisible(true);
        this.setLayout(null);//清空布局管理器
        this.setSize(800,800);
        this.setButtons();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗口关闭
    }
    public void setButtons(){
    	//棋盘布置
        for(int i=0;i<9;i++){
            buttons[i]=new JButton();
        }
        buttons[0].setBounds(100,150,150,150);
        buttons[1].setBounds(260,150,150,150);
        buttons[2].setBounds(420,150,150,150);
        buttons[3].setBounds(100,310,150,150);
        buttons[4].setBounds(260,310,150,150);
        buttons[5].setBounds(420,310,150,150);
        buttons[6].setBounds(100,470,150,150);
        buttons[7].setBounds(260,470,150,150);
        buttons[8].setBounds(420,470,150,150);
        //鼠标监听
        for(int i=0;i<9;i++){
            int j=i;
            buttons[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                	//下棋
                    if(myJudge()&&boom[j]==0){
                        buttons[j].setIcon(a);
                        boom[j]=1;

                        //发送数据
                        try {
                            server=new DatagramSocket(serverPort);

                            //准备数据
                            String msg;
                            if(win()){
                              msg=j+","+1;//胜利
                            }else if(noWin()){
                                msg=j+","+2;//和棋
                            }else//继续下棋
                            {
                                msg=j+","+0;
                            }

                            byte[] data =msg.getBytes();
                            //打包（发送地点 及其端口）
                            DatagramPacket packet=new DatagramPacket(data,data.length,new InetSocketAddress(ip,outPort));
                            //发送
                            server.send(packet);
                            System.out.println("圆发送成功");
                            //圆获胜
                            if(win()){
                                JOptionPane.showConfirmDialog(null, "你赢了！", null, JOptionPane.CLOSED_OPTION);
                                boom=new int[9];
                                for(JButton jButton:buttons){
                                    jButton.setIcon(null);
                                }
                                repaint();

                            }
                            server.close();
                        }catch(Exception ec) //catch执行脚本，并捕获错误
                        {
                            System.out.println("圆异常");
                        }
                        judge++;
                        //和棋
                        if(noWin()){
                            JOptionPane.showConfirmDialog(null, "和棋", null, JOptionPane.CLOSED_OPTION);
                            judge=2;
                            boom=new int[9];
                            for(JButton jButton:buttons){
                                jButton.setIcon(null);
                            }
                        }

                    }

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
            this.add(buttons[i]);

        }

    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) //捕获错误
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(myJudge()) {
                continue;
            }
            //接受数据
            try {
                client=new DatagramSocket(clientPort);
                //准备接收容器
                byte[] container=new byte[1024];
                //封装成包DatagramPacket
                DatagramPacket packet=new DatagramPacket(container,container.length);
                //接收
                client.receive(packet);
                byte[] data=packet.getData();
                int len=packet.getLength();
                String str=new String(data,0,len);
                //基本类型转化为Integer对象
                Integer integer=Integer.valueOf(str.split(",")[0]);//数据分割
                Integer integer1=Integer.valueOf(str.split(",")[1]);
                
                //×下棋
                buttons[integer].setIcon(b);//图标×
                System.out.println(integer1);
                judge++;
                repaint();
                client.close();
                if(integer1==1){
                    JOptionPane.showConfirmDialog(null, "你输了！", null, JOptionPane.CLOSED_OPTION);
                    boom=new int[9];
                    for(JButton jButton:buttons){
                        jButton.setIcon(null);
                        judge=2;
                    }
                    repaint();
                }else if(integer1==2){
                    JOptionPane.showConfirmDialog(null, "和棋", null, JOptionPane.CLOSED_OPTION);
                    boom=new int[9];
                    for(JButton jButton:buttons){
                        jButton.setIcon(null);
                        judge=2;
                    }
                    repaint();
                }
            }catch(Exception ec) {
                System.out.println("异常");
            }
        }

    }
    //判断胜负
    public boolean win() {
        if(boom[0]+boom[1]+boom[2]==3||
           boom[3]+boom[4]+boom[5]==3||
           boom[6]+boom[7]+boom[8]==3)
            return true;
        if(boom[0]+boom[3]+boom[6]==3||
           boom[1]+boom[4]+boom[7]==3||
           boom[2]+boom[5]+boom[8]==3)
            return true;
        if(boom[0]+boom[4]+boom[8]==3||
           boom[2]+boom[4]+boom[6]==3)
            return true;

       return false;
    }
    //判断和棋，是否还有地方可以落子
    public  boolean noWin() {
        for (int i : boom) //禁止重新赋值
        {
            if (i == 0) {
                return false;
            }
        }
        return true;//和棋
    }
    //判断是否继续下棋
    public boolean myJudge(){
        if(signal==0)//我方下棋
        {
            if(judge%2==0){
                return true;
            }else{
                return false;
            }
        }else{
            if(judge%2!=0){
                return true;
            }else{
                return false;
            }
        }
    }

}
