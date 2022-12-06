package io.devpl.codegen.fxui.utils;

public class StringUtils {

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean hasText(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean hasText(final CharSequence... sequence) {
        if (sequence == null) return false;
        for (CharSequence charSequence : sequence) {
            if (!hasText(charSequence)) return false;
        }
        return true;
    }

    /**
     * convert string from slash style to camel style, such as my_course will convert to MyCourse
     * @param str
     * @return
     */
    public static String dbStringToCamelStyle(String str) {
        if (str != null) {
            if (str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toUpperCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }
}
