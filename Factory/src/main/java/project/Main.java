package project;

import project.model.Faction;
import project.model.Factory;
import project.model.PartType;
import project.model.SharedState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    List<PartType> producedParts = Collections.synchronizedList(new ArrayList<>());
    Object lock = new Object();
    SharedState state = new SharedState();
    Factory factory = new Factory(producedParts, lock, state);
    Faction world = new Faction("World", producedParts, lock, state);
    Faction wednesday = new Faction("Wednesday", producedParts, lock, state);
    factory.start();
    world.start();
    wednesday.start();
    try {
      factory.join();
      world.join();
      wednesday.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Simulation finished.");
  }
}


