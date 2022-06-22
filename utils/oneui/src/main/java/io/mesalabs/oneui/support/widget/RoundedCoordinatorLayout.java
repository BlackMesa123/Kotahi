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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import io.mesalabs.oneui.R;

public class RoundedCoordinatorLayout extends CoordinatorLayout {
    private final Context mContext;
    private final SeslRoundedCorner mRoundedCorner;

    public RoundedCoordinatorLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundedCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.RoundedCoordinatorLayout);
        final int roundedCorners = a.getInt(R.styleable.RoundedCoordinatorLayout_roundedCorners,
                SeslRoundedCorner.ROUNDED_CORNER_ALL);
        a.recycle();

        mRoundedCorner = new SeslRoundedCorner(mContext);
        mRoundedCorner.setRoundedCorners(roundedCorners);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mRoundedCorner.drawRoundedCorner(canvas);
    }

    public void setRoundedCorners(int roundedCorners) {
        mRoundedCorner.setRoundedCorners(roundedCorners);
        invalidate();
    }

    public void setRoundedCornerColor(int roundedCorners, @ColorInt int color) {
        mRoundedCorner.setRoundedCornerColor(roundedCorners, color);
        invalidate();
    }
}
