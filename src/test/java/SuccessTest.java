import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SuccessTest {

    @Test
    public void successTrue(){
        assertTrue(true);
    }

    @Test
    public void successFalse(){
        assertFalse(false);
    }

    @Test
    public void successEquals(){
        assertEquals(10, 5*2);
    }
}
