/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI2;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.Camera;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class OpenCV454 extends JFrame{
    
    //camera man hinh
    private  JLabel cameraScreen;
    private JButton btnCapture;
    private VideoCapture capture;
    private Mat image;
    private boolean clicked;
    
    public OpenCV454(){
        // thiet ke ui
        setLayout(null);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);
        
        btnCapture = new JButton("capture");
        btnCapture.setBounds(300, 480, 80,40);
        add(btnCapture);
        
        btnCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              clicked = true;
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (capture!=null) {
                    capture.release();
                }
                System.exit(0);
            }
            
});
        
        
    setSize(new Dimension(640,560));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
}
    
    // tao camera
    public void StartCamera(){
    capture = new VideoCapture(0);
    image = new Mat();
    byte[] imageData;
        ImageIcon icon;
        while (true) {            
            capture.read(image);

            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
            if (clicked) {
                String name = JOptionPane.showInputDialog(this,"dat ten anh di thg loz");
                if (name == null) {
                  name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                }
                // ghi file
                Imgcodecs.imwrite("logos/"+name+".jpg", image);
                clicked = false;
            }
        }
    }
    public static void main(String[] args) {
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
          @Override
            public void run() {
               OpenCV454 openCV454 = new OpenCV454();
             
              new Thread(new Runnable() {
                   @Override
                   public void run() {
                       openCV454.StartCamera();
                  }
              }).start();
           }
        } );
        
      System.out.println("load thanh cong");
    }
    
}
