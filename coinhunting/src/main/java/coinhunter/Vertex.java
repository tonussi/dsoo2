package coinhunter;

import java.util.Objects;

public class Vertex {

    Integer potNumber;
    Integer potCoins;

    public Vertex(Integer potNumber) {
        this.potNumber = potNumber;
        this.potCoins = 4;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.potNumber);
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
        final Vertex other = (Vertex) obj;
        if (!Objects.equals(this.potNumber, other.potNumber)) {
            return false;
        }
        return true;
    }

}
