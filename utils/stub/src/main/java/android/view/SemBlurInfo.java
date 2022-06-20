package android.view;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SemBlurInfo implements Parcelable {
    public static final int BLUR_MODE_WINDOW = 0;
    public static final int BLUR_MODE_WINDOW_CAPTURED = 1;
    public static final int BLUR_MODE_CANVAS = 2;

    @IntDef({BLUR_MODE_WINDOW,
            BLUR_MODE_WINDOW_CAPTURED,
            BLUR_MODE_CANVAS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BlurMode {
    }

    public static final Parcelable.Creator<SemBlurInfo> CREATOR
            = new Parcelable.Creator<SemBlurInfo>() {
        @Override
        public SemBlurInfo createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public SemBlurInfo[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

    public SemBlurInfo(@BlurMode int blurMode,
                       Bitmap capturedBitmap,
                       int blurRadius,
                       int backgroundBlurColor,
                       float cornerRadiusTL,
                       float cornerRadiusTR,
                       float cornerRadiusBL,
                       float cornerRadiusBR,
                       int scale) {
        throw new RuntimeException("Stub!");
    }

    protected SemBlurInfo(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        throw new RuntimeException("Stub!");
    }

    public int getBlurMode() {
        throw new RuntimeException("Stub!");
    }

    public int getBlurRadius() {
        throw new RuntimeException("Stub!");
    }

    public int getBackgroundBlurColor() {
        throw new RuntimeException("Stub!");
    }

    public void getBackgroundBlurCornerRadius(float[] outRadius) {
        throw new RuntimeException("Stub!");
    }

    public Bitmap getCapturedBitmap() {
        throw new RuntimeException("Stub!");
    }

    public int getCanvasDownScale() {
        throw new RuntimeException("Stub!");
    }

    public static class Builder {
        public Builder(int blurMode) {
            throw new RuntimeException("Stub!");
        }

        public Builder setBitmap(Bitmap capturedBitmap) {
            throw new RuntimeException("Stub!");
        }

        public Builder setRadius(int blurRadius) {
            throw new RuntimeException("Stub!");
        }

        private Builder hidden_setRadius(int blurRadius) {
            throw new RuntimeException("Stub!");
        }

        public Builder setBackgroundColor(int color) {
            throw new RuntimeException("Stub!");
        }

        private Builder hidden_setBackgroundColor(int color) {
            throw new RuntimeException("Stub!");
        }

        public Builder setBackgroundCornerRadius(float cornerRadius) {
            throw new RuntimeException("Stub!");
        }

        private Builder hidden_setBackgroundCornerRadius(float cornerRadius) {
            throw new RuntimeException("Stub!");
        }

        public Builder setBackgroundCornerRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
            throw new RuntimeException("Stub!");
        }

        public Builder setCanvasScale(int scale) {
            throw new RuntimeException("Stub!");
        }

        public SemBlurInfo build() {
            throw new RuntimeException("Stub!");
        }

        private SemBlurInfo hidden_build() {
            throw new RuntimeException("Stub!");
        }

        private void checkNotUsed() {
            throw new RuntimeException("Stub!");
        }
    }
}
