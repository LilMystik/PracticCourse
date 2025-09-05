package project.model;

import java.util.*;

public class Faction extends Thread {
  private final String name;
  private final List<PartType> producedParts;
  private final Object lock;
  private final SharedState state;
  private final Map<PartType, Integer> inventory = new EnumMap<>(PartType.class);
  private final Random random = new Random();

  private int lastProcessedDay = 0;

  public Faction(String name, List<PartType> producedParts, Object lock, SharedState state) {
    this.name = name;
    this.producedParts = producedParts;
    this.lock = lock;
    this.state = state;
    for (PartType type : PartType.values()) {
      inventory.put(type, 0);
    }
  }

  public int getRobotCount() {
    return Math.min(
            Math.min(inventory.get(PartType.HEAD), inventory.get(PartType.TORSO)),
            Math.min(inventory.get(PartType.HAND) / 2, inventory.get(PartType.FEET) / 2));
  }

  @Override
  public void run() {
    while (true) {
      synchronized (lock) {
        try {
          // Wait until parts are ready and it's a new day for this faction
          while (!state.partsReady || state.day == lastProcessedDay) {
            if (state.day > 100) return;
            lock.wait();
          }
          int maxToTake= random.nextInt(6);
          int taken = 0;
          while (taken < maxToTake && !producedParts.isEmpty()) {
            int index = random.nextInt(producedParts.size());
            PartType part = producedParts.remove(index);
            inventory.put(part, inventory.get(part) + 1);
            taken++;
          }
          System.out.println(name + " collected " + taken + " parts on Day " + state.day);
          lastProcessedDay = state.day;
          state.factionsDone++;
          if (state.factionsDone == 2) {
            lock.notifyAll(); // Wake the factory
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    int robots = getRobotCount();
    System.out.println(name + " completed " + robots + " robots!");
  }
}}

