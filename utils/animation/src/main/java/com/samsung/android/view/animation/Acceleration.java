package com.samsung.android.view.animation;

import android.view.animation.PathInterpolator;

public class Acceleration extends PathInterpolator {

    public Acceleration() {
        super(0.4f, 0.0f, 1.0f, 1.0f);
    }

}
