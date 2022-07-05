package com.samsung.android.contacts.presetimage;

public class PresetImageUtil {

    static int[] CONTACT_LIST_PRESET_BACKGROUND = {
            R.drawable.contact_list_preset_01,
            R.drawable.contact_list_preset_02,
            R.drawable.contact_list_preset_03,
            R.drawable.contact_list_preset_04,
            R.drawable.contact_list_preset_05,
            R.drawable.contact_list_preset_06,
            R.drawable.contact_list_preset_07,
            R.drawable.contact_list_preset_08,
            R.drawable.contact_list_preset_09,
            R.drawable.contact_list_preset_10,
            R.drawable.contact_list_preset_11,
            R.drawable.contact_list_preset_12,
            R.drawable.contact_list_preset_13,
            R.drawable.contact_list_preset_14,
            R.drawable.contact_list_preset_15,
            R.drawable.contact_list_preset_16,
            R.drawable.contact_list_preset_17,
            R.drawable.contact_list_preset_18,
            R.drawable.contact_list_preset_19,
            R.drawable.contact_list_preset_20,
            R.drawable.contact_list_preset_21,
            R.drawable.contact_list_preset_22,
            R.drawable.contact_list_preset_23,
            R.drawable.contact_list_preset_24,
            R.drawable.contact_list_preset_25,
            R.drawable.contact_list_preset_26,
            R.drawable.contact_list_preset_27,
            R.drawable.contact_list_preset_28,
            R.drawable.contact_list_preset_29,
            R.drawable.contact_list_preset_30,
            R.drawable.contact_list_preset_31,
            R.drawable.contact_list_preset_32,
            R.drawable.contact_list_preset_33,
            R.drawable.contact_list_preset_34,
            R.drawable.contact_list_preset_35
    };

    static int[] CONTACT_LIST_PRESET_CIRCLE_BACKGROUND = {
            R.drawable.contact_list_preset_crop_01,
            R.drawable.contact_list_preset_crop_02,
            R.drawable.contact_list_preset_crop_03,
            R.drawable.contact_list_preset_crop_04,
            R.drawable.contact_list_preset_crop_05,
            R.drawable.contact_list_preset_crop_06,
            R.drawable.contact_list_preset_crop_07,
            R.drawable.contact_list_preset_crop_08,
            R.drawable.contact_list_preset_crop_09,
            R.drawable.contact_list_preset_crop_10,
            R.drawable.contact_list_preset_crop_11,
            R.drawable.contact_list_preset_crop_12,
            R.drawable.contact_list_preset_crop_13,
            R.drawable.contact_list_preset_crop_14,
            R.drawable.contact_list_preset_crop_15,
            R.drawable.contact_list_preset_crop_16,
            R.drawable.contact_list_preset_crop_17,
            R.drawable.contact_list_preset_crop_18,
            R.drawable.contact_list_preset_crop_19,
            R.drawable.contact_list_preset_crop_20,
            R.drawable.contact_list_preset_crop_21,
            R.drawable.contact_list_preset_crop_22,
            R.drawable.contact_list_preset_crop_23,
            R.drawable.contact_list_preset_crop_24,
            R.drawable.contact_list_preset_crop_25,
            R.drawable.contact_list_preset_crop_26,
            R.drawable.contact_list_preset_crop_27,
            R.drawable.contact_list_preset_crop_28,
            R.drawable.contact_list_preset_crop_29,
            R.drawable.contact_list_preset_crop_30,
            R.drawable.contact_list_preset_crop_31,
            R.drawable.contact_list_preset_crop_32,
            R.drawable.contact_list_preset_crop_33,
            R.drawable.contact_list_preset_crop_34,
            R.drawable.contact_list_preset_crop_35
    };

    private static int getPresetImageIndex(long identifier) {
        if (identifier < 0) {
            return 0;
        }

        final long imageIndex = identifier % CONTACT_LIST_PRESET_BACKGROUND.length;
        return (int) imageIndex;
    }

    private static int getPresetImageIndex(String displayName) {
        if (displayName != null) {
            final int identifier = Math.abs(displayName.hashCode());

            if (identifier >= 0) {
                final long imageIndex = identifier % CONTACT_LIST_PRESET_BACKGROUND.length;
                return (int) imageIndex;
            }
        }

        return 0;
    }

    public static int getPresetImage(String displayName) {
        final int imageIndex = getPresetImageIndex(displayName);
        return CONTACT_LIST_PRESET_BACKGROUND[imageIndex];
    }

    public static int getCirclePresetImage(String displayName) {
        final int imageIndex = getPresetImageIndex(displayName);
        return CONTACT_LIST_PRESET_CIRCLE_BACKGROUND[imageIndex];
    }

    public static int getPresetImage(long userId) {
        final int imageIndex = getPresetImageIndex(userId);
        return CONTACT_LIST_PRESET_BACKGROUND[imageIndex];
    }

    public static int getCirclePresetImage(long identifier) {
        final int imageIndex = getPresetImageIndex(identifier);
        return CONTACT_LIST_PRESET_CIRCLE_BACKGROUND[imageIndex];
    }

    public static int getCirclePresetImage(long contactID, String displayName) {
        return contactID > 0 ? getCirclePresetImage(contactID) : getCirclePresetImage(displayName);
    }

    public static int getPresetImage(long contactID, String displayName) {
        return contactID > 0 ? getPresetImage(contactID) : getPresetImage(displayName);
    }
}
