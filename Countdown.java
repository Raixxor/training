import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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


        BufferedWriter writer = null;

        try {
            writer = Files.newBufferedWriter(Paths.get("countdown.txt"), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            writer.write(msg);
            writer.close();
        } catch (IOException e) {
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