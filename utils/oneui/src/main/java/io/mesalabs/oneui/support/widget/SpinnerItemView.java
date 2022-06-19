package io.mesalabs.oneui.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SeslCheckedTextView;
import androidx.core.content.ContextCompat;

import io.mesalabs.oneui.R;
import io.mesalabs.oneui.support.utils.TypefaceUtils;

public class SpinnerItemView extends SeslCheckedTextView {

    public SpinnerItemView(@NonNull Context context) {
        this(context, null);
    }

    public SpinnerItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SpinnerItemView(@NonNull Context context, @Nullable AttributeSet attrs,
                           int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if (checked) {
            setTypeface(TypefaceUtils.getBoldSupportTypeface());

            Context context = getContext();
            if (context != null && getCurrentTextColor() == Color.MAGENTA) {
                ColorStateList textColor = ContextCompat.getColorStateList(context,
                        R.color.oneui_spinner_dropdown_text_color);
                if (textColor != null) {
                    setTextColor(textColor);
                }
            }
        } else {
            setTypeface(TypefaceUtils.getNormalSupportTypeface());
        }
    }

}
