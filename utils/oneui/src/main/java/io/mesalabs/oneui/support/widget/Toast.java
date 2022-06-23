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

package io.mesalabs.oneui.support.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;

import io.mesalabs.oneui.R;

public class Toast extends android.widget.Toast {
    public Toast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast result = new Toast(context);

        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.oui_transient_notification, null);
        TextView tv = v.findViewById(android.R.id.message);
        tv.setText(text);

        result.setView(v);
        result.setDuration(duration);

        return result;
    }

    public static Toast makeText(Context context, @StringRes int resId, int duration) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }
}
