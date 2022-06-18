package com.samsung.android.view.animation;

import android.view.animation.PathInterpolator;

public class Deceleration extends PathInterpolator {

    public Deceleration() {
        super(0.0f, 0.0f, 0.2f, 1.0f);
    }

}
