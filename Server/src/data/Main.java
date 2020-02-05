package data;

import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageConsumer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) {
        Webcam webcam;
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640 , 480));
        webcam.open();
        BufferedImage ima;

        Thread thread = new Thread(new AudioCall("anandkunal241"));
        thread.start();

        while(true){
            ima=webcam.getImage() ;
            //Image card = SwingFXUtils.toFXImage(ima, null );
            ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
            try {
                ImageIO.write(ima,"jpg",baos);
                baos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //byte[] data = baos.toByteArray();
            byte []data=baos.toByteArray();




            try {

                DatagramSocket ds = new DatagramSocket();

                InetAddress ip=InetAddress.getByName("localhost");
                DatagramPacket dp=new DatagramPacket(data,data.length,ip,5000);
                ds.send(dp);
                ds.close();


            }
            catch(IOException e){
                e.printStackTrace();
            }

        }}






}
 /*   final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
    final ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(o);
final byte[] data = baos.toByteArray();

final DatagramPacket packet = new DatagramPacket(data, data.length);
// Send the packet*/