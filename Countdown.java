import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Countdown {

    static int seconds = 0;
    static long timestamp = 0;

    public static void main(String[] args) {

        if (args.length == 1) {
            seconds = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            seconds = 60 * Integer.parseInt(args[0]) + Integer.parseInt(args[1]);
        } else {
            seconds = 60;
        }

        System.out.printf("Countdown from %d seconds%n", seconds);

        timestamp = System.currentTimeMillis();
        updateCountdown();
    }

    private static void updateCountdown() {

        long currentTime = (System.currentTimeMillis() - timestamp) / 1000;
        String msg = "";
        boolean stop = false;
        if (currentTime > seconds) {
            msg = "--:--";
            stop = true;
        } else {
            long minutes = (seconds - currentTime) / 60;
            long seconds = (Countdown.seconds - currentTime) % 60;

            msg = (minutes < 10 ? "0"+minutes : ""+minutes) +":"+(seconds < 10 ? "0"+seconds : ""+seconds);
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("countdown.txt"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            writer.write(msg);
            writer.close();
        } catch (NoSuchFileException e) {
            String empty = "";
            try {
                Files.write(Paths.get("countdown.txt"), empty.getBytes());
                System.out.println("no countdown.txt found -> therefore created one");
            } catch (IOException ex) {
                System.out.println("first catch");
                ex.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("second catch");
            e.printStackTrace();
        }

        if (stop) {
            return;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateCountdown();
    }
}