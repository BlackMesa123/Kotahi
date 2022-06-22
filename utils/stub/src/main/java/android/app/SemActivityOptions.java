package android.app;

import android.graphics.Point;
import android.os.Bundle;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(ActivityOptions.class)
public class SemActivityOptions {
    public static final int SEM_POP_OVER_ANCHOR_POSITION_HORIZONTAL_LEFT = 16;
    public static final int SEM_POP_OVER_ANCHOR_POSITION_HORIZONTAL_RIGHT = 32;
    public static final int SEM_POP_OVER_ANCHOR_POSITION_HORIZONTAL_CENTER = 64;
    public static final int SEM_POP_OVER_ANCHOR_POSITION_VERTICAL_TOP = 1;
    public static final int SEM_POP_OVER_ANCHOR_POSITION_VERTICAL_BOTTOM = 2;
    public static final int SEM_POP_OVER_ANCHOR_POSITION_VERTICAL_CENTER = 4;

    public static final int SEM_POP_OVER_POSITION_HORIZONTAL_LEFT = 16;
    public static final int SEM_POP_OVER_POSITION_HORIZONTAL_RIGHT = 32;
    public static final int SEM_POP_OVER_POSITION_HORIZONTAL_CENTER = 64;
    public static final int SEM_POP_OVER_POSITION_VERTICAL_TOP = 1;
    public static final int SEM_POP_OVER_POSITION_VERTICAL_BOTTOM = 2;
    public static final int SEM_POP_OVER_POSITION_VERTICAL_CENTER = 4;

    public static SemActivityOptions makeBasic() {
        throw new RuntimeException("Stub!");
    }

    public Bundle toBundle() {
        throw new RuntimeException("Stub!");
    }

    public SemActivityOptions semSetPopOverOptions(int[] widthDp, int[] heightDp,
                                                   Point[] marginDp, int[] position) {
        throw new RuntimeException("Stub!");
    }

    public SemActivityOptions semSetChooserPopOverPosition(int position) {
        throw new RuntimeException("Stub!");
    }
}
