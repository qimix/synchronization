package ru.qimix;

import java.util.*;

public class Main {
    public static Map<Integer, Integer> sizeToFreq = Collections.synchronizedMap(new HashMap<>());
    public static List<String> routeStore = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new RouteMaker("RLRFR", 100).start();
        }
    }

    static class RouteMaker extends Thread {
        String letters;
        Integer length;

        public RouteMaker(String letters, int length) {
            this.letters = letters;
            this.length = length;
        }

        @Override
        public void run() {
            generateRoute(letters, length);
        }

        public String generateRoute(String letters, int length) {
            Random random = new Random();
            StringBuilder route = new StringBuilder();
            for (int i = 0; i < length; i++) {
                route.append(letters.charAt(random.nextInt(letters.length())));
            }
            synchronized (routeStore) {
                routeStore.add(route.toString());
            }
            return route.toString();
        }
    }
}