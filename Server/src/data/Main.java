package data;

import callpackets.VideoPacket;
import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageConsumer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        Webcam webcam;

        webcam= Webcam.getWebcams().get(0);
        System.out.println(Webcam.getWebcams());


        if (webcam != null) {
            System.out.println("Webcam: " + webcam.getName());
        } else {
            System.out.println("No webcam detected");
        }
        webcam.setViewSize(new Dimension(640 , 480));
        webcam.open();
        BufferedImage ima;

        Thread thread = new Thread(new AudioCall("anandkunal241"));
        thread.start();
        boolean flag =true;
       while(true){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ima=webcam.getImage() ;

           if(flag){
               flag=false;
               ImageIO.write(webcam.getImage(),"JPG", new File("out.jpg"));
               System.out.println("Saved");
           }
            ByteArrayOutputStream baos=new ByteArrayOutputStream(200000);

            try {
                System.out.println(ima);
                ImageIO.write(ima,"jpg",baos);
                baos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte []data=baos.toByteArray();

            VideoPacket videoPacket = new VideoPacket(data, timestamp,"me","me",false);

            try {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();

                ObjectOutputStream oos = new ObjectOutputStream(baos2);
                oos.writeObject(videoPacket);
                byte[] pack = baos2.toByteArray();
                DatagramSocket ds = new DatagramSocket();

                InetAddress ip=InetAddress.getByName("localhost");
                System.out.println(ip);
                DatagramPacket dp=new DatagramPacket(pack,pack.length,ip,5000);
                ds.send(dp);

            }
            catch(IOException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

       }

}






}
 /*   final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
    final ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(o);
final byte[] data = baos.toByteArray();

final DatagramPacket packet = new DatagramPacket(data, data.length);
// Send the packet*/