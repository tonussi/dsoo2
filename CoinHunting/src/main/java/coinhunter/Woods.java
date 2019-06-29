package coinhunter;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Woods extends Thread {

    Graph woods;
    DogGroup group;
    Hunter yellow;
    Hunter green;
    Hunter blue;
    Dog lifesaver;
    volatile boolean winner;
    List<Hunter> hunters;
    private static final Logger LOGGER = Logger.getLogger(Woods.class.getName());

    public Woods() {
        super();
        winner = false;
        woods = new Graph();
        group = new DogGroup();
        hunters = new ArrayList<>();
        lifesaver = new LifeSaver(0x505, Color.RED, woods);
        this.setName("woods");
        this.setPriority(MAX_PRIORITY);
    }

    public void createMap() {
        for (int i = 1; i <= 20; i++) {
            woods.addVertex(i);
        }

        woods.addEdge(1, 2);
        woods.addEdge(1, 15); // c

        woods.addEdge(2, 1);
        woods.addEdge(2, 3);
        woods.addEdge(2, 4);
        woods.addEdge(2, 5); // c

        woods.addEdge(3, 2);
        woods.addEdge(3, 9); // c

        woods.addEdge(4, 2);
        woods.addEdge(4, 9);
        woods.addEdge(4, 10); // c

        woods.addEdge(5, 2);
        woods.addEdge(5, 6); // c

        woods.addEdge(6, 5);
        woods.addEdge(6, 7);
        woods.addEdge(6, 8); // c

        woods.addEdge(7, 6); // c

        woods.addEdge(8, 6); // c

        woods.addEdge(9, 3);
        woods.addEdge(9, 4);
        woods.addEdge(9, 15);
        woods.addEdge(9, 18); // c

        woods.addEdge(10, 4);
        woods.addEdge(10, 12); // c

        woods.addEdge(11, 12);
        woods.addEdge(11, 14);
        woods.addEdge(11, 17); // c

        woods.addEdge(12, 10);
        woods.addEdge(12, 11);
        woods.addEdge(12, 13); // c

        woods.addEdge(13, 12); // c

        woods.addEdge(14, 11);
        woods.addEdge(14, 16); // c

        woods.addEdge(15, 1);
        woods.addEdge(15, 9); // c

        woods.addEdge(16, 14);
        woods.addEdge(16, 17);
        woods.addEdge(16, 18);
        woods.addEdge(16, 20); // c

        woods.addEdge(17, 11);
        woods.addEdge(17, 16); // c

        woods.addEdge(18, 9);
        woods.addEdge(18, 16);
        woods.addEdge(18, 19); // c

        woods.addEdge(19, 18);
        woods.addEdge(19, 20); // c

        woods.addEdge(20, 16);
        woods.addEdge(20, 19); // c
    }

    class SortById implements Comparator<Hunter> {

        @Override
        public int compare(Hunter a, Hunter b) {
            return a.number - b.number;
        }
    }

    class SortByCoins implements Comparator<Hunter> {

        @Override
        public int compare(Hunter a, Hunter b) {
            return (int) (a.sumCoins() - b.sumCoins());
        }
    }

    public void createHunters() {
        yellow = new Hunter(1, Color.YELLOW, 2, woods);
        green = new Hunter(2, Color.GREEN, 2, woods);
        blue = new Hunter(3, Color.BLUE, 2, woods);

        hunters.add(yellow);
        hunters.add(green);
        hunters.add(blue);

        Collections.sort(hunters, new SortById());
    }

    public void addDogsToQueue() {
        if (group.woodsGroup.size() < 4) {
            for (Hunter hunter : hunters) {
                Set<Dog> dogs = hunter.dogs;

                for (Dog dog : dogs) {
                    if (dog.position == -1 && dog.state == DogState.READY_AND_WAITING) {
                        group.addDog(dog);
                    }
                }
            }
        }

        for (Hunter hunter : hunters) {
            hunter.start();
        }
    }

    @Override
    public void run() {
        while (!winner) {

            for (Hunter hunter : hunters) {

                if (hunter.sumCoins() >= 50) {
                    winner = true;
                }

                for (Dog beagle : hunter.dogs) {
                    if (beagle.state == DogState.FOUND_UNIT_OF_COINS) {
                        group.removeDog(beagle);
                    }
                    if (group.woodsGroup.size() < 3) {
                        for (Dog dog : hunter.dogs) {
                            if (dog.state == DogState.READY_AND_WAITING && !dog.number.equals(beagle.number)) {
                                dog.state = DogState.SEEKING_COINS;
                                group.addDog(dog);
                                if (!dog.isAlive()) {
                                    dog.start();
                                }
                            }
                        }
                    }
                    if (beagle.state == DogState.FOUND_UNIT_OF_COINS) {
                        beagle.state = DogState.READY_AND_WAITING;
                    }
                }

            }

            if (winner) {
                break;
            }

        }

        for (Hunter hunter : hunters) {
            hunter.state = HunterState.END_HUNTING;
            for (Dog dog : hunter.dogs) {
                dog.stop();
                dog.state = DogState.RETURN_TO_MASTER_HUNTER;
            }
        }

        lifesaver.state = DogState.RETURN_TO_MASTER_HUNTER;

        Collections.sort(hunters, new SortByCoins());
        Collections.reverse(hunters);

        LOGGER.info("WINNERS / RANKING:");
        for (int i = 0; i < hunters.size(); i++) {
            LOGGER.log(Level.INFO, "{0}\u00ba:", i+1);
            LOGGER.log(Level.INFO, hunters.get(i).toString());
        }

    }

    public void allowDogsToEnter() {
        for (Hunter hunter : hunters) {
            for (Dog dog : hunter.dogs) {
                if (group.woodsGroup.containsValue(dog)) {
                    dog.state = DogState.SEEKING_COINS;
                    if (!dog.isAlive()) {
                        dog.start();
                    }
                }
            }
        }
    }

    public void allowLifeSaverToEnter() {
        lifesaver.state = DogState.FIXING_POTS;
        lifesaver.start();
    }

    void init() {
        createMap();
        createHunters();
        addDogsToQueue();
        allowDogsToEnter();
        allowLifeSaverToEnter();
    }

}
