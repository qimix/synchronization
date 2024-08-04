package ru.qimix;

import java.util.*;

public class Main {
    protected static final Map<Integer, Integer> sizeToFreq = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new RouteMaker("RLRFR", 100).start();
        }
        sizeToFreq.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
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
            int coutR = 0;

            for (int i = 0; i < length; i++) {
                Character letter = letters.charAt(random.nextInt(letters.length()));
                route.append(letter);
                if (letter.equals('R')) {
                    coutR++;
                } else {
                    if (coutR > 0) {
                        synchronized (sizeToFreq) {
                            if (!sizeToFreq.containsKey(coutR)) {
                                sizeToFreq.put(coutR, 1);
                            } else {
                                int tmpFreq = sizeToFreq.get(coutR);
                                sizeToFreq.put(coutR, tmpFreq + 1);
                            }
                        }
                    }
                    coutR = 0;
                }
            }
            return route.toString();
        }
    }
}