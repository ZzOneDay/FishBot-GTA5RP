package home.fishing.core;

import org.springframework.stereotype.Service;

@Service
public class RandomGenerator {
    public int generateValue(int min, int max) {
        return min + (int) ((max - min) * Math.random());
    }
}
