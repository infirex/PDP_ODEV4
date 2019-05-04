
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {

    private static ArrayList<String> numbers = new ArrayList<String>();

    public static void dosyaOku() {
        try {
            Scanner dosya = new Scanner(new File("sayilar.txt"));

            while (dosya.hasNextLine()) {
                numbers.add(dosya.nextLine());
            }

            dosya.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static class Calculator implements Runnable {

        private int binler, yüzler, onlar, birler;
        private int basamak;
        public double zaman;

        public Calculator(int basamak) {
            this.basamak = basamak;
        }

        public String result() {
            return binler + "" + yüzler + "" + onlar + "" + birler;
        }

        @Override
        public void run() {
            switch (basamak) {
                case 0:
                    for (String number : numbers) {
                        binler += Integer.valueOf(String.valueOf(number.charAt(basamak)));
                    }
                    break;
                case 1:
                    for (String number : numbers) {
                        yüzler += Integer.valueOf(String.valueOf(number.charAt(basamak)));
                    }
                    break;
                case 2:
                    for (String number : numbers) {
                        onlar += Integer.valueOf(String.valueOf(number.charAt(basamak)));
                    }
                    break;
                case 3:
                    for (String number : numbers) {
                        birler += Integer.valueOf(String.valueOf(number.charAt(basamak)));
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        dosyaOku();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        //Seri hesaplama
        int binler = 0, yüzler = 0, onlar = 0, birler = 0;
        System.out.println("Seri hesaplama başladı");
        long start2 = System.nanoTime();
        
        for (String number : numbers) {
            binler += Integer.valueOf(String.valueOf(number.charAt(0)));
            yüzler += Integer.valueOf(String.valueOf(number.charAt(1)));
            onlar += Integer.valueOf(String.valueOf(number.charAt(2)));
            birler += Integer.valueOf(String.valueOf(number.charAt(3)));
        }
        long finish2 = System.nanoTime();
        double time2 = (finish2 - start2) / 1000000.0;
        System.out.println("Seri hesaplama süresi:" + time2 + " milisaniye");

        //Paralel hesaplama
        System.out.println("Paralel hesaplama başladı");
        long start = System.nanoTime();

        executor.submit(new Calculator(0));
        executor.submit(new Calculator(1));
        executor.submit(new Calculator(2));
        executor.submit(new Calculator(3));

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        long finish = System.nanoTime();
        double time = (finish - start) / 1000000.0;
        System.out.println("Paralel hesaplama süresi:" + time + " milisaniye");

    }
}
