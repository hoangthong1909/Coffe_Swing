/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author PC
 */
public class XImage {
    public static Image getAppIcon(){ //Lấy ảnh làm icon 
        URL url =  XImage.class.getResource("File anh o day....");
       return new ImageIcon(url).getImage();
    }
    public static void save(File src){ 
        File dst = new File("logos",src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs(); // tạo thư mục logo nếu chưa tồn tại
        }
        try {
            Path from = Paths.get(src.getAbsolutePath()); //lay duong dan
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from,to,StandardCopyOption.REPLACE_EXISTING); // copy file vào thư mục logo
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static ImageIcon read(String fileName){
        File path = new File("logos",fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(180, 180,Image.SCALE_DEFAULT));
    }
}
