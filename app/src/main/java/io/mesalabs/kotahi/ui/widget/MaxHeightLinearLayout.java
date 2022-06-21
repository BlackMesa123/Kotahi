package io.mesalabs.kotahi.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;

public class MaxHeightLinearLayout extends LinearLayout {
    private int mMaxHeight = 0;

    public MaxHeightLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public MaxHeightLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                               int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MaxHeightLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                               int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            final int hSize = MeasureSpec.getSize(heightMeasureSpec);
            final int hMode = MeasureSpec.getMode(heightMeasureSpec);

            switch (hMode) {
                case MeasureSpec.AT_MOST:
                    heightMeasureSpec = MeasureSpec
                            .makeMeasureSpec(Math.min(hSize, mMaxHeight), MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    heightMeasureSpec = MeasureSpec
                            .makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.EXACTLY:
                    heightMeasureSpec = MeasureSpec
                            .makeMeasureSpec(Math.min(hSize, mMaxHeight), MeasureSpec.EXACTLY);
                    break;
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setMaxHeight(@Px int maxHeight) {
        mMaxHeight = maxHeight;
        invalidate();
    }
}
