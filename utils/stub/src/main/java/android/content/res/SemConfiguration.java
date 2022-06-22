package android.content.res;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(Configuration.class)
public class SemConfiguration {
    public static final int SEM_BUTTON_SHAPE_UNDEFINED = -1;
    public static final int SEM_BUTTON_SHAPE_DISABLED = 0;
    public static final int SEM_BUTTON_SHAPE_ENABLED = 1;

    public static final int SEM_DESKTOP_MODE_DISABLED = 0;
    public static final int SEM_DESKTOP_MODE_ENABLED = 1;

    public static final int SEM_DISPLAY_DEVICE_TYPE_MAIN = 0;
    public static final int SEM_DISPLAY_DEVICE_TYPE_SUB = 5;

    public static final int SEM_MOBILE_KEYBOARD_COVERED_NO = 0;
    public static final int SEM_MOBILE_KEYBOARD_COVERED_YES = 1;

    public int semButtonShapeEnabled;
    public int semDesktopModeEnabled;
    public int semDisplayDeviceType;
    public int semMobileKeyboardCovered;

    public boolean semIsPopOver() {
        throw new RuntimeException("Stub!");
    }
}
