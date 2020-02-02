import com.github.sarxos.webcam.Webcam;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static javafx.application.Application.launch;

public class Main extends Application {
    static Image card;
    static Webcam webcam;
    public static void main(String[] args) throws IOException {

     /*   Dimension dim[]=webcam.getViewSizes();
        int i;
        for(Dimension d: dim)
        {
            System.out.println(d.toString());
        }*/
        //ImageIO.write(webcam.getImage(),"JPG", new File("out.jpg"));

        //System.out.println("Saved");
        launch(args);
    }
    public void start (Stage primary) throws InterruptedException, SocketException {


        Group root=new Group();
        Label label = new javafx.scene.control.Label();
        label.setGraphic(new ImageView(card));
        root.getChildren().add(label);
        Scene scene=new Scene(root,640,480);

        primary.setScene(scene);
        primary.show();
        int k=50;
        DatagramSocket ds=new DatagramSocket(5000);
        Timeline time = new Timeline(
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        BufferedImage buf=null;
                        try {

                            byte []data=new byte[30000];
                            DatagramPacket dp=new DatagramPacket(data,30000);
                            ds.receive(dp);
                            buf=ImageIO.read(new ByteArrayInputStream(data));






                        } catch ( IOException e) {
                            e.printStackTrace();

                        }
                        card = SwingFXUtils.toFXImage(buf, null );
                        label.setGraphic(new ImageView(card));
                    }
                })
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.playFromStart();
        /*while(k!=0)
        {
            k--;
            System.out.println(k);
            BufferedImage buf =webcam.getImage();
            card = SwingFXUtils.toFXImage(buf, null );
            Label label1 = new Label();
            label1.setGraphic(new ImageView(card));
            scene.setRoot(label1);
            Thread.sleep(200);
        }
        webcam.close();*/

    }
}