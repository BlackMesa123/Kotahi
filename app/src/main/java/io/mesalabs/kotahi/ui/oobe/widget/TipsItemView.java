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

package io.mesalabs.kotahi.ui.oobe.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import io.mesalabs.kotahi.R;
import io.mesalabs.oneui.support.utils.TypefaceUtils;

public class TipsItemView extends LinearLayout {
    private static final String TAG = TipsItemView.class.getSimpleName();
    private static final float VIEW_HORIZONTAL_PADDING = 32.0f;
    private static final float VIEW_HORIZONTAL_PADDING_ICON = 24.0f;

    private FrameLayout mIconContainer;
    private AppCompatImageView mIcon;
    private AppCompatTextView mTitleTextView;
    private AppCompatTextView mSummaryTextView;

    public TipsItemView(@NonNull Context context) {
        this(context, null);
    }

    public TipsItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipsItemView(@NonNull Context context, @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TipsItemView(@NonNull Context context, @Nullable AttributeSet attrs,
                        int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        removeAllViews();

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(
                androidx.appcompat.R.attr.listChoiceBackgroundIndicator,
                outValue, true);
        if (outValue.resourceId > 0) {
            setBackgroundResource(outValue.resourceId);
        } else {
            Log.w(TAG, "Couldn't retrieve listChoiceBackgroundIndicator!");
        }

        setOrientation(HORIZONTAL);
        inflate(context, R.layout.view_oobe_tips_item, this);

        mIconContainer = findViewById(R.id.tips_item_icon_container);
        mIcon = findViewById(R.id.tips_item_icon);
        mTitleTextView = findViewById(R.id.tips_item_title_text);
        mSummaryTextView = findViewById(R.id.tips_item_summary_text);

        mTitleTextView.setTypeface(TypefaceUtils.getBoldSupportTypeface());

        final int horizontalPadding = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, VIEW_HORIZONTAL_PADDING,
                getResources().getDisplayMetrics());
        setPadding(horizontalPadding, 0, horizontalPadding, 0);
    }

    public void setIcon(@DrawableRes int resId) {
        setIcon(getContext().getDrawable(resId));
    }

    public void setIcon(@Nullable Drawable icon) {
        final boolean hasIcon = icon != null;
        mIconContainer.setVisibility(hasIcon ? VISIBLE : GONE);
        mIcon.setImageDrawable(icon);

        final float paddingSize = hasIcon
                ? VIEW_HORIZONTAL_PADDING_ICON : VIEW_HORIZONTAL_PADDING;
        final int horizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingSize, getResources().getDisplayMetrics());
        setPadding(horizontalPadding, 0, horizontalPadding, 0);
    }

    public void setTitleText(@Nullable CharSequence titleText) {
        mTitleTextView.setText(titleText);
    }

    public void setTitleColor(@ColorInt int color) {
        mTitleTextView.setTextColor(color);
    }

    public void setSummaryText(@Nullable CharSequence summaryText) {
        final boolean isTextEmpty = summaryText == null
                || summaryText.length() == 0;
        mSummaryTextView.setVisibility(isTextEmpty ? GONE : VISIBLE);
        mSummaryTextView.setText(summaryText);
    }
}
