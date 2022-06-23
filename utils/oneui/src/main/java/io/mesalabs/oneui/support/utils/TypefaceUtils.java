/*
 * Kotahi
 * Copyright © 2022 BlackMesa123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
