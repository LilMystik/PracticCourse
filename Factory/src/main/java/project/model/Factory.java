package project.model;

import java.util.List;
import java.util.Random;

public class Factory extends Thread {
  private final List<PartType> producedParts;
  private final Object lock;
  private final SharedState state;
  private final Random random = new Random();

  public Factory(List<PartType> producedParts, Object lock, SharedState state) {
    this.producedParts = producedParts;
    this.lock = lock;
    this.state = state;
  }

  @Override
  public void run() {
    while (state.day <= 100) {
      synchronized (lock) {
        producedParts.clear();
        int partsToday = random.nextInt(11);
        for (int i = 0; i < partsToday; i++) {
          PartType part = PartType.values()[random.nextInt(4)];
          producedParts.add(part);
        }
        System.out.println("Day " + state.day + ": Factory produced " + producedParts);
        state.partsReady = true;
        state.factionsDone = 0;
        lock.notifyAll();
        try {
          while (state.factionsDone < 2) {
            lock.wait();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        state.partsReady = false;
        state.day++;
      }
    }
    synchronized (lock) {
      state.day = 101;
      state.partsReady = false;
      lock.notifyAll();
    }
  }
}

