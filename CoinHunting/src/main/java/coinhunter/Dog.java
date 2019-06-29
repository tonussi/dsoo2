package coinhunter;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dog extends Thread {

    Graph woods;
    Integer number;
    Color label;
    Integer coins;
    Integer position;
    DogState state;
    Integer UNIT_OF_COINS = 20;

    private static final Logger LOGGER = Logger.getLogger(Dog.class.getName());

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Dog(Integer number, Color label, Graph woods) {
        super();
        this.number = number;
        this.label = label;
        this.woods = woods;
        this.coins = 0;
        this.position = -1;
        this.state = DogState.READY_AND_WAITING;
        this.setName("dog(" + this.label.toString().toLowerCase() + "," + this.number + ")");
    }

    @Override
    public void run() {
        while (true) {
//            LOGGER.info(toString());

            switch (state) {
                case RETURN_TO_MASTER_HUNTER:
                    return;
                case READY_AND_WAITING:
                    try {
                        sleep(200);
                    } catch (InterruptedException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                    break;
                case SLEEPING_AT_EMPTY_POT:
                    try {
                        sleep(60);
                    } catch (InterruptedException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                    break;
                case FOUND_UNIT_OF_COINS:
                    break;
                case SEEKING_COINS:
                    seeking();
                    break;
                case TRAVELLING_BETWEEN_POTS:
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                    break;
                default:
                    break;
            }

            if (state == DogState.RETURN_TO_MASTER_HUNTER) {
                break;
            }
        }
    }

    public Vertex move() {
        Vertex v;
        if (position == -1) {
            v = woods.getRoot();
            position = v.potNumber;
        } else {
            try {
                this.state = DogState.TRAVELLING_BETWEEN_POTS;
                sleep(100);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }

            // A movimentacao dos cachorros pelas arestas eh livre.
            v = woods.getNextRandomAdjVertex(position);
            position = v.potNumber;
        }

        this.state = DogState.SEEKING_COINS;
        return v;
    }

    public void seeking() {
        Vertex v = move();
        // Retirei o synchronized do metodo e coloquei em lugares especificos
        // logo abaixo. Para que um cachorro nao durma com um mutex preso para
        // ele, assim o funcionamento fica mais condizente.

        synchronized (v) {
            if (v.potCoins > 1) {
                v.potCoins--;
                coins++;
                return;
            }

            if (v.potCoins == 0) {
                woods.sleeping.add(this);
                this.state = DogState.SLEEPING_AT_EMPTY_POT;
            }

            if (coins >= UNIT_OF_COINS) {
                this.state = DogState.FOUND_UNIT_OF_COINS;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.number);
        hash = 59 * hash + Objects.hashCode(this.label);
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
        final Dog other = (Dog) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (this.label != other.label) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\n\tDog{"
                + "\n\t\tnumber=" + number
                + "\n\t\tlabel=" + label
                + "\n\t\tcoins=" + coins
                + "\n\t\tposition=" + position
                + "\n\t\tstate=" + state
                + "\n\t\tUNIT_OF_COINS=" + UNIT_OF_COINS
                + "\n\t}";
    }

}
