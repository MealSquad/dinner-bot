import com.github.wthompson40.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;

@RunWith(JUnit4.class)
public class MainTest {

    @Test
    public void testGetToken() {
        assertFalse(Main.readProperties("token").isEmpty());
    }
}
