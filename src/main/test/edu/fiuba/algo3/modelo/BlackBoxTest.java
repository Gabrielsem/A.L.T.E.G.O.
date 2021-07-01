package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.BlackBox;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackBoxTest {

    @Test
    public void test01_NumTest() {
        BlackBox bb = new BlackBox(5);

        assertEquals( bb.getNum(2),10);
    }
}
