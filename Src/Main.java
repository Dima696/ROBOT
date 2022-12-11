import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap <>();
    public static void main(String[] args) {
       Runnable task = () -> {
            String route = generateRoute("RLRFR", 100);
            int frequency = (int) route.chars()
                    .filter(ch -> ch == 'R').count();

            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(frequency)) {
                    sizeToFreq.put(frequency, sizeToFreq.get(frequency) + 1);
                } else {
                    sizeToFreq.put(frequency, 1);
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            Thread thread2 = new Thread(task);
            thread2.start();
            thread2.join();
        }


        Map.Entry<Integer, Integer> mapAmount = sizeToFreq
                .entrySet()
                .stream()
                .max( Map.Entry.comparingByValue() )
                .get();
        System.out.println( "Самое частое количество повторений " + mapAmount.getKey()
                + " (встретилось " + mapAmount.getValue() + " раз)" );
        System.out.println( "Другие размеры: " );

        sizeToFreq
                .entrySet()
                .stream()
                .sorted( Map.Entry.comparingByValue() )
                .forEach( e -> System.out.println( " - " + e.getKey() + " (" + e.getValue() + " раз)" ) );
    }
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}
