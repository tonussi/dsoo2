package coinhunter;

import java.util.*;

public class DogGroup {

    Map<Color, Dog> woodsGroup;

    public DogGroup() {
        this.woodsGroup = new HashMap();
    }

    public void addDog(Dog dog) {
        if (woodsGroup.size() < 4) {
            woodsGroup.put(dog.label, dog);
        }
    }

    public void removeDog(Dog dog) {
        woodsGroup.remove(dog.label);
    }

}
