package com.talha.app.util;

public class StringUtil {
    public static String capitalize(String s)
    {
        s = s.trim();

        if (s.isEmpty())
            return "";

        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    public static int getCount(String s1, String s2)
    {
        int count = 0;

        for (int index = -1; (index = s1.indexOf(s2, index + 1))!= -1; ++count)
            ;

        return count;
    }

    public static boolean isBlank(String s)
    {
        return s.trim().isEmpty();
    }

    public static boolean isPalindrome(String s)
    {
        s = removeWS(s);

        return reverse(s).equals(s);
    }

    public static boolean isPangram(String s, String alphabet)
    {
        int count = 0;
        int len = alphabet.length();

        for (int i = 0; i < len; ++i)
            if (s.contains(alphabet.charAt(i) + ""))
                ++count;

        return count == len;
    }

    public static boolean isPangramEN(String s)
    {
        s = s.toLowerCase();

        int count = 0;

        for (char ch = 'a'; ch <= 'z'; ++ch)
            if (s.contains(ch + ""))
                ++count;

        return count == 26;
    }

    public static boolean isPangramTR(String s)
    {
        String alphabet = "abcçdefgğhıijklmnoöprsştuüvyz";

        return isPangram(s.toLowerCase(), alphabet);
    }

    public static String padLeft(String s, int len, char ch)
    {
        if (len <= s.length())
            return s;

        return repeat(len - s.length(), ch) + s;
    }

    public static String padLeft(String s, int len)
    {
        return padLeft(s, len, ' ');
    }

    public static String padRight(String s, int len, char ch)
    {
        if (len <= s.length())
            return s;

        return s + repeat(len - s.length(), ch);
    }

    public static String padRight(String s, int len)
    {
        return padRight(s, len, ' ');
    }

    public static String removeWS(String s)
    {
        String str = "";

        int len = s.length();

        for (int i = 0; i < len; ++i) {
            char ch = s.charAt(i);

            if (!Character.isWhitespace(ch))
                str += ch;
        }
        return str;
    }

    public static String repeat(int n, char ch)
    {
        if (n <= 0)
            return "";

        String str = "";

        while (n-- > 0)
            str += ch;

        return str;
    }

    public static String reverse(String s)
    {
        String str = "";

        for (int i = s.length() - 1; i >= 0; --i)
            str += s.charAt(i);

        return str;
    }

    public static String trimEnd(String s)
    {
        int i = s.length() - 1;

        for (; i >= 0 && Character.isWhitespace(s.charAt(i)); --i)
            ;

        return s.substring(0, i + 1);
    }

    public static String trimStart(String s)
    {
        int i = 0;

        for (; i < s.length() && Character.isWhitespace(s.charAt(i)); ++i)
            ;

        return s.substring(i);
    }

}