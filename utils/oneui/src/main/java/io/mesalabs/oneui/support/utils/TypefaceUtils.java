package io.mesalabs.oneui.support.utils;

import android.graphics.Typeface;

public class TypefaceUtils {
    private static final String SEC_ROBOTO = "sec-roboto-light";
    private static final String ROBOTO_REGULAR = "sans-serif";
    private static final String ROBOTO_MEDIUM = "sans-serif-medium";

    public static Typeface getNormalSupportTypeface() {
        if (BuildUtils.isSemDevice()) {
            return Typeface.create(SEC_ROBOTO, Typeface.NORMAL);
        } else {
            return Typeface.create(ROBOTO_REGULAR, Typeface.NORMAL);
        }
    }

    public static Typeface getBoldSupportTypeface() {
        if (BuildUtils.isSemDevice()) {
            return Typeface.create(SEC_ROBOTO, Typeface.BOLD);
        } else {
            return Typeface.create(ROBOTO_MEDIUM, Typeface.NORMAL);
        }
    }
}
