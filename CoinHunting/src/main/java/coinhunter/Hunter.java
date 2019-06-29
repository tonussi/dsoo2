package coinhunter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hunter extends Thread {

    Color label;
    Set<Dog> dogs;
    Integer number;
    Integer coins;
    HunterState state;
    Integer UNIT_OF_COINS = 20;

    private static final Logger LOGGER = Logger.getLogger(Hunter.class.getName());

    public Hunter(Integer number, Color label, Integer numberOfDogs, Graph woods) {
        super();
        this.number = number;
        this.coins = 0;
        this.dogs = new HashSet<>();
        this.label = label;
        this.state = HunterState.COLLECTING_COINS;

        for (int i = 1; i <= numberOfDogs; i++) {
            Dog dog = new Dog(i, label, woods);
            this.dogs.add(dog);
        }

        this.setName("hunter_" + this.label.toString().toLowerCase() + "_" + this.number);
    }

    @Override
    public void run() {
        while (true) {

            switch (state) {
                case END_HUNTING:
                    return;
                case COLLECTING_COINS:
                    for (Dog dog : dogs) {
                        if (dog.coins >= UNIT_OF_COINS) {
                            synchronized (dog) {
                                dog.coins -= UNIT_OF_COINS;
                                this.coins += UNIT_OF_COINS;
                            }
                        }
                    }
                    try {
                        sleep(6);
                    } catch (InterruptedException ex) {
                        return;
                    }
                    break;
                default:
                    break;
            }

            if (state == HunterState.END_HUNTING) {
                break;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.number);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hunter other = (Hunter) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        return true;
    }

    public int sumCoins() {
        int sum = 0;
        for (Dog dog : dogs) {
            sum += dog.coins;
        }
        return coins + sum;
    }

    @Override
    public String toString() {
        return "Hunter{"
                + "\n\tlabel=" + label
                + "\n\t" + dogs.toString()
                + "\n\tnumber=" + number
                + "\n\tCOINS=" + coins
                + "\n\ttotal coins=" + sumCoins()
                + "\n\tstate=" + state
                + "\n\tUNIT_OF_COINS=" + UNIT_OF_COINS
                + '}';
    }

}
