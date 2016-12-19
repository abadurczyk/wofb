package wof.utils;

import org.apache.commons.lang3.StringUtils;
import wof.exceptions.HeadlineCannotBeEmptyException;

public class WallControllerUtils {

    public static void checkHeadline(String headline) {

        if (headline == null || StringUtils.isWhitespace(headline)) {
            throw new HeadlineCannotBeEmptyException();
        }
    }
}
