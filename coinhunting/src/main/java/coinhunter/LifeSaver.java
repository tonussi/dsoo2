package coinhunter;

public class LifeSaver extends Dog {

    LifeSaver(Integer number, Color label, Graph woods) {
        super(number, label, woods);
        this.setName("lifesaver");
    }

    public void wakeupDogs() {
        if (!woods.sleeping.isEmpty()) {
            for (Dog dog : woods.sleeping) {
                if (dog.state == DogState.SLEEPING_AT_EMPTY_POT) {
                    synchronized (dog) {
                        dog.notify();
                        dog.state = DogState.SEEKING_COINS;
                    }
                }
            }
            woods.sleeping.clear();
        }
    }

    @Override
    public Vertex move() {
        Vertex v;
        if (position == -1) {
            v = woods.getRoot();
            position = v.potNumber;
        } else {
            try {
                sleep(200);
            } catch (InterruptedException ex) {
            }
            position = (position + 1) % 21;
            if (position == 0) {
                position++;
            }
            v = woods.getNodeAt(position);
            position = v.potNumber;
        }
        return v;
    }

    public void fixPot() {
        Vertex v = move();
        synchronized (v) {
            if (v.potCoins == 0) {
                v.potCoins += 1;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            switch (state) {
                case RETURN_TO_MASTER_HUNTER:
                    return;
                case FIXING_POTS:
                    fixPot();
                    break;
                default:
                    break;
            }
            if (state == DogState.RETURN_TO_MASTER_HUNTER) {
                break;
            }
        }
    }
}
