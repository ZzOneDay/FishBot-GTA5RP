package home.developer.core;

import home.developer.configuration.CoreConfiguration;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class RandomGeneratorTest extends TestCase {

    @Autowired
    RandomGenerator randomGenerator;

    @Test
    public void testRandomGenerator() {
        int valueMin = 10;
        int valueMax = 1000;

        int random1 = randomGenerator.generateValue(valueMin, valueMax);
        int random2 = randomGenerator.generateValue(valueMin, valueMax);

        assertTrue(random1 > valueMin && random1 < valueMax);
        assertTrue(random2 > valueMin && random2 < valueMax);
        assertTrue(random1 != random2);
    }
}