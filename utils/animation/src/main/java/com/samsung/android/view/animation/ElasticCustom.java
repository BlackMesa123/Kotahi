package com.samsung.android.view.animation;

import android.view.animation.Interpolator;

public class ElasticCustom implements Interpolator {
    private float amplitude = 1.0f;
    private float period = 0.2f;

    public ElasticCustom() {
    }

    public ElasticCustom(float amplitude, float period) {
        this.amplitude = amplitude;
        this.period = period;
    }

    public void setAmplitude(float value) {
        amplitude = value;
    }

    public void setPeriod(float value) {
        period = value;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public float getPeriod() {
        return period;
    }

    @Override
    public float getInterpolation(float t) {
        return out(t, amplitude, period);
    }

    private float out(float t, float a, float p) {
        if (t == 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }

        if (p == 0.0f) {
            p = 0.3f;
        }

        float s;
        if (a == 0.0f || a < 1.0f) {
            s = p / 4.0f;
            a = 1.0f;
        } else {
            s = (float) (p / (2 * Math.PI) * Math.asin(1.0f / a));
        }

        return (float) (a * Math.pow(2.0d, (-10.0f * t)) * Math.sin((t - s) * (2 * Math.PI)) / p + 1.0d);
    }
}
