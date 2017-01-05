package wof.utils;

import static wof.entities.Category.MAX_CATEGORY_NAME_LENGTH;

public class CategoryControllerUtils {

    public static boolean isCategoryNameSupported(String categoryName) {
        if (categoryName.isEmpty()) {
            return false;
        }
        if (categoryName.length() >= MAX_CATEGORY_NAME_LENGTH) {
            return false;
        }
        return true;
    }
}
