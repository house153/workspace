package com.house.variety.util;



import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.text.StrBuilder;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final String NULLSTR = "";
    private static final char SEPARATOR = '_';
    private static boolean separatorBeforeDigit = false;
    private static boolean separatorAfterDigit = true;

    public StringUtils() {
    }

    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || objects.length == 0;
    }

    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return "";
        } else {
            if (start < 0) {
                start += str.length();
            }

            if (start < 0) {
                start = 0;
            }

            return start > str.length() ? "" : str.substring(start);
        }
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return "";
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    public static String format(String template, Object... params) {
        return !isEmpty(params) && !isEmpty(template) ? StrFormatter.format(template, params) : template;
    }

    public static String uncapitalize(String str) {
        int strLen;
        return str != null && (strLen = str.length()) != 0 ? (new StrBuilder(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString() : str;
    }

    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                boolean nextUpperCase = true;
                if (i < s.length() - 1) {
                    nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
                }

                if (i > 0 && Character.isUpperCase(c)) {
                    if (!upperCase || !nextUpperCase) {
                        sb.append('_');
                    }

                    upperCase = true;
                } else {
                    upperCase = false;
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            String[] var2 = strs;
            int var3 = strs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && !name.isEmpty()) {
            if (!name.contains("_")) {
                return name.substring(0, 1).toUpperCase() + name.substring(1);
            } else {
                String[] camels = name.split("_");
                String[] var3 = camels;
                int var4 = camels.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String camel = var3[var5];
                    if (!camel.isEmpty()) {
                        result.append(camel.substring(0, 1).toUpperCase());
                        result.append(camel.substring(1).toLowerCase());
                    }
                }

                return result.toString();
            }
        } else {
            return "";
        }
    }

    public static String removePrefixAfterPrefixToLower(String rawString, int index) {
        return prefixToLower(rawString.substring(index, rawString.length()), 1);
    }

    public static String prefixToLower(String rawString, int index) {
        String beforeChar = rawString.substring(0, index).toLowerCase();
        String afterChar = rawString.substring(index, rawString.length());
        return beforeChar + afterChar;
    }

    public static String firstCharToLower(String rawString) {
        return prefixToLower(rawString, 1);
    }

    public static String camelToHyphen(String input) {
        return wordsToHyphenCase(wordsAndHyphenAndCamelToConstantCase(input));
    }

    private static String wordsAndHyphenAndCamelToConstantCase(String input) {
        boolean betweenUpperCases = false;
        boolean containsLowerCase = containsLowerCase(input);
        StringBuilder buf = new StringBuilder();
        char previousChar = ' ';
        char[] chars = input.toCharArray();

        for(int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            boolean isUpperCaseAndPreviousIsUpperCase = Character.isUpperCase(previousChar) && Character.isUpperCase(c);
            boolean isUpperCaseAndPreviousIsLowerCase = Character.isLowerCase(previousChar) && Character.isUpperCase(c);
            boolean previousIsWhitespace = Character.isWhitespace(previousChar);
            boolean lastOneIsNotUnderscore = buf.length() > 0 && buf.charAt(buf.length() - 1) != '_';
            boolean isNotUnderscore = c != '_';
            if (!lastOneIsNotUnderscore || !isUpperCaseAndPreviousIsLowerCase && !previousIsWhitespace && (!betweenUpperCases || !containsLowerCase || !isUpperCaseAndPreviousIsUpperCase)) {
                if (separatorAfterDigit && Character.isDigit(previousChar) && Character.isLetter(c) || separatorBeforeDigit && Character.isDigit(c) && Character.isLetter(previousChar)) {
                    buf.append('_');
                }
            } else {
                buf.append("_");
            }

            if (shouldReplace(c) && lastOneIsNotUnderscore) {
                buf.append('_');
            } else if (!Character.isWhitespace(c) && (isNotUnderscore || lastOneIsNotUnderscore)) {
                buf.append(Character.toUpperCase(c));
            }

            previousChar = c;
        }

        if (Character.isWhitespace(previousChar)) {
            buf.append("_");
        }

        return buf.toString();
    }

    public static boolean containsLowerCase(String s) {
        char[] var1 = s.toCharArray();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            char c = var1[var3];
            if (Character.isLowerCase(c)) {
                return true;
            }
        }

        return false;
    }

    private static boolean shouldReplace(char c) {
        return c == '.' || c == '_' || c == '-';
    }

    private static String wordsToHyphenCase(String s) {
        StringBuilder buf = new StringBuilder();
        char lastChar = 'a';
        char[] var3 = s.toCharArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            if (Character.isWhitespace(lastChar) && !Character.isWhitespace(c) && '-' != c && buf.length() > 0 && buf.charAt(buf.length() - 1) != '-') {
                buf.append("-");
            }

            if ('_' == c) {
                buf.append('-');
            } else if ('.' == c) {
                buf.append('-');
            } else if (!Character.isWhitespace(c)) {
                buf.append(Character.toLowerCase(c));
            }

            lastChar = c;
        }

        if (Character.isWhitespace(lastChar)) {
            buf.append("-");
        }

        return buf.toString();
    }
}

