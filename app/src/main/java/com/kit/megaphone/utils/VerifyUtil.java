package com.kit.megaphone.utils;

import android.widget.EditText;

public class VerifyUtil {

    public static boolean verifyString(String str) {
        if (str == null || str.length() == 0) return false;
        return true;
    }

    public static boolean verifyStrings(String... ss) {
        for (int i = 0; i < ss.length; i++) {
            if (!verifyString(ss[i])) return false;
        }
        return true;
    }

    public static boolean verifyStringsFromEditText(EditText... es) {
        for (int i = 0; i < es.length; i++) {
            if (!verifyString(es[i].getText().toString())) return false;
        }
        return true;
    }
}
