package wof.utils;

import org.junit.Test;
import wof.exceptions.HeadlineCannotBeEmptyException;

import static junit.framework.TestCase.fail;

public class WallControllerUtilsTest {

    @Test
    public void testInput() {
        checkHeadline("");
        checkHeadline(" ");
        checkHeadline("    ");
        checkHeadline(null);
    }

    private void checkHeadline(String s) {
        try {
            WallControllerUtils.checkHeadline(s);
            fail("check headline has not thrown exception");
        } catch (HeadlineCannotBeEmptyException ignore) {
        }
    }
}
