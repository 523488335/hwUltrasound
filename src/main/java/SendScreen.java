//import java.awt.Dimension;
//import java.awt.Rectangle;
//import java.awt.Robot;
//import java.awt.Toolkit;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Date;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//import javax.imageio.ImageIO;
//
//public class SendScreen extends javax.swing.JFrame {
//
//    private static final long serialVersionUID = -552475961408456287L;
//    public static int SERVERPORT=12122;
//    private ServerSocket serverSocket;
//    private Robot robot;
//    public  Dimension screen;
//    public Rectangle rect ;
//    private Socket socket;
//
//    public static void main(String[] args) {
//        new SendScreen();
//        }
//    public SendScreen(int SERVERPORT) {
//        try {
//            serverSocket = new ServerSocket(SERVERPORT);
//            //设置超时时间
//            serverSocket.setSoTimeout(864000);
//            robot = new Robot();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        screen = Toolkit.getDefaultToolkit().getScreenSize();  //获取主屏幕的大小
//
//        rect = new Rectangle(screen);                          //构造屏幕大小的矩形
//
//        Thread t = new  reThread();
//        t.start();
//
//    }
//    public SendScreen() {
//        this(SERVERPORT);
//    }
//
//    class reThread extends Thread {
//
//
//        public void run(){
//            ZipOutputStream zip = null;
//            OutputStream out = null;
//            ZipEntry zipEntry = null;
//            try {
//                socket = serverSocket.accept();
//                out  = new DataOutputStream(socket.getOutputStream());
//                zip = new ZipOutputStream(out);
//                zip.setLevel(11);     //设置压缩级别,java共8个还是11个压缩级别？
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            while(true) {
//            try{
//                long startMili=System.currentTimeMillis();
//                System.out.println("开始 "+startMili);
//                System.out.println(rect);
//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                ImageIO.write(robot.createScreenCapture(rect), "png", output);
//                zipEntry = new ZipEntry(new Date().getTime() + "");
//                zip.putNextEntry(zipEntry);
//                zip.write(output.toByteArray());
//                //sleep(40);
//                long endMili=System.currentTimeMillis();
//                System.out.println(endMili - startMili + "ms");
//            }catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//                }
//            }
//        }
//    }
//}