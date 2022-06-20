package android.view;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(View.class)
public class SemView {
    public static final int SEM_ROUNDED_CORNER_NONE = 0;
    public static final int SEM_ROUNDED_CORNER_ALL = 15;
    public static final int SEM_ROUNDED_CORNER_TOP_LEFT = 1;
    public static final int SEM_ROUNDED_CORNER_TOP_RIGHT = 2;
    public static final int SEM_ROUNDED_CORNER_BOTTOM_LEFT = 4;
    public static final int SEM_ROUNDED_CORNER_BOTTOM_RIGHT = 8;

    public void semSetDisplayCutoutBackgroundColor(final int color) {
        throw new RuntimeException("Stub!");
    }

    public void semSetRoundedCorners(int corners) {
        throw new RuntimeException("Stub!");
    }

    public int semGetRoundedCorners() {
        throw new RuntimeException("Stub!");
    }

    public void semSetRoundedCornerColor(int corners, int color) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public int semGetRoundedCornerColor(int corner) {
        throw new RuntimeException("Stub!");
    }

    public void semSetVerticalScrollBarPadding(boolean flag) {
        throw new RuntimeException("Stub!");
    }

    public void semSetVerticalScrollBarPaddingPosition(int paddingValue) {
        throw new RuntimeException("Stub!");
    }

    public void semSetHoverPopupType(int type) {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public void semSetBlurEnabled(boolean enabled) {
        throw new RuntimeException("Stub!");
    }

    public void semSetBlurRadius(int blurRadius) {
        throw new RuntimeException("Stub!");
    }

    public void semSetBackgroundBlurCornerRadius(float cornerRadius) {
        throw new RuntimeException("Stub!");
    }

    public void semSetBackgroundBlurColor(int color) {
        throw new RuntimeException("Stub!");
    }

    public void semSetBlurInfo(SemBlurInfo blurInfo) {
        throw new RuntimeException("Stub!");
    }
}
