/*
 * Kotahi
 * Copyright © 2022 BlackMesa123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package io.mesalabs.oneui.support.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.SemConfiguration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dev.rikka.tools.refine.Refine;
import io.mesalabs.oneui.support.utils.BuildUtils;

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    // Activity Flags
    private boolean mIsInPopOver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mIsInPopOver = isInPopOver(getResources().getConfiguration());
        setFinishOnTouchOutside(mIsInPopOver);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mIsInPopOver = isInPopOver(newConfig);
        setFinishOnTouchOutside(mIsInPopOver);
    }

    private boolean isInPopOver(@NonNull Configuration newConfig) {
        if (BuildUtils.isOneUI()) {
            try {
                SemConfiguration c = Refine.unsafeCast(newConfig);
                return c.semIsPopOver();
            } catch (NoSuchMethodError e) {
                String c = newConfig.toString();
                if (c.contains("PopOver=")) {
                    return c.contains("PopOver=on");
                }
                return c.contains("PopOver");
            }
        }

        return false;
    }
}
