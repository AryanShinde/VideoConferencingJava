public class Tester {
    public static void main(String []args)
    {
        Thread listener = new Thread(new SoundReceiver());
        listener.start();

        /*Thread thread = new Thread(new AudioCall("anandkunal241"));
        thread.start();*/
    }
}
