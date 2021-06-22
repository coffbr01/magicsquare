package me.bcoffield.magicsquare;

import me.bcoffield.magicsquare.lucas.LucasRandomC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  private static final int threads = 8;
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(threads);
    for (int i = 0; i < threads; i++) {
      executor.execute(() -> new LucasRandomC().run());
    }
    executor.shutdown();
  }
}
