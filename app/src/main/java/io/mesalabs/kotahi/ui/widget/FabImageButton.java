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

package io.mesalabs.kotahi.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.TooltipCompat;

import io.mesalabs.kotahi.R;
import io.mesalabs.oneui.support.utils.BuildUtils;

public class FabImageButton extends AppCompatImageButton {
    private static final float DEFAULT_ELEVATION_DP = 4.0f;

    public FabImageButton(@NonNull Context context) {
        this(context, null);
    }

    public FabImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FabImageButton(@NonNull Context context, @Nullable AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.FabImageButton, defStyleAttr, R.style.Widget_Kotahi_ImageButton_FAB);

        setMinimumWidth(a.getDimensionPixelSize(
                R.styleable.FabImageButton_android_minWidth, -1));
        setMinimumHeight(a.getDimensionPixelSize(
                R.styleable.FabImageButton_android_minHeight, -1));

        Drawable bg = context.getDrawable(a.getResourceId(
                R.styleable.FabImageButton_android_background, R.drawable.widget_fab_background));
        setBackground(bg);

        setImageDrawable(a.getDrawable(R.styleable.FabImageButton_android_src));

        setScaleType(ScaleType.CENTER);

        setElevation(a.getDimension(R.styleable.FabImageButton_android_elevation,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        DEFAULT_ELEVATION_DP, context.getResources().getDisplayMetrics())));

        setTooltipText(a.getText(R.styleable.FabImageButton_tooltipText));

        a.recycle();


        // DEBUG
        setClickable(true);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            Context c = getContext();
            TypedValue outValue = new TypedValue();
            c.getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, outValue, true);
            if (outValue.resourceId > 0) {
                drawable.setTint(c.getColor(outValue.resourceId));
            } else {
                drawable.setTint(outValue.data);
            }
        }

        super.setImageDrawable(drawable);
    }

    @Override
    public void setTooltipText(@Nullable CharSequence tooltipText) {
        if (Build.VERSION.SDK_INT >= 26 && BuildUtils.isOneUI()) {
            super.setTooltipText(tooltipText);
        } else {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
    }
}
