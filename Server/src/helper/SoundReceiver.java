package helper;

import callpackets.AudioPacket;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class SoundReceiver implements Runnable{

    @Override
    public void run(){
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
            SourceDataLine speakers;
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();


            DatagramSocket serverSocket = new DatagramSocket(6678);



            while (true) {

                byte[] buffer = new byte[10400];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(response);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                AudioPacket audioPacket = (AudioPacket) objectInputStream.readObject();

                speakers.write(audioPacket.getData(), 0, audioPacket.getData().length);
            }



        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }catch(LineUnavailableException ex){
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}