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

package io.mesalabs.oneui.support.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.SemView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import dev.rikka.tools.refine.Refine;
import io.mesalabs.oneui.R;
import io.mesalabs.oneui.databinding.OuiLayoutDrawerUiBinding;
import io.mesalabs.oneui.support.utils.BuildUtils;

/**
 * Part of the code has been kanged from:
 * https://github.com/OneUIProject/oneui-design/blob/main/lib/src/main/java/dev/oneuiproject/oneui/layout/DrawerLayout.java
 */
public class DrawerActivity extends AbsAppBarActivity {
    private static final float DRAWER_CORNER_RADIUS = 15.f;
    // Flags
    private boolean mIsRtl = false;
    // Views
    private OuiLayoutDrawerUiBinding mBinding;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsRtl = getResources().getConfiguration()
                .getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        initDrawer();
    }

    @Override
    void onLayoutCreate() {
        mBinding = OuiLayoutDrawerUiBinding.inflate(getLayoutInflater());
        setRootView(mBinding.getRoot());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mIsRtl = newConfig.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        setDrawerWidth();
        openDrawer(false, false);
    }

    private void initDrawer() {
        if (BuildUtils.isOneUI()) {
            Refine.<SemView>unsafeCast(
                    getWindow().getDecorView()).semSetRoundedCorners(0);
        }

        super.setNavigationButtonIcon(mContext.getDrawable(R.drawable.tw_ic_ab_drawer_mtrl));
        // TODO add localized string
        super.setNavigationButtonTooltip("Open drawer sar");
        super.setNavigationButtonOnClickListener(v -> openDrawer(true, true));

        mBinding.drawerLayout.setScrimColor(mContext.getColor(
                R.color.oui_drawerlayout_drawer_dim_color));
        mBinding.drawerLayout.setDrawerElevation(0.0f);
        mBinding.drawerLayout.addDrawerListener(
                new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        super.onDrawerSlide(drawerView, slideOffset);

                        Window window = getWindow();

                        float slideX = drawerView.getWidth() * slideOffset;
                        if (mIsRtl)
                            slideX *= -1;
                        mBinding.appBarContainer.setTranslationX(slideX);

                        final float[] hsv = new float[3];
                        Color.colorToHSV(mContext.getColor(
                                R.color.samsung_bg_color), hsv);
                        hsv[2] *= 1f - (slideOffset * 0.2f);

                        final int systemBarsColor = Color.HSVToColor(hsv);
                        window.setStatusBarColor(systemBarsColor);
                        window.setNavigationBarColor(systemBarsColor);
                    }
                });

        setDrawerWidth();
        setDrawerCornerRadius(DRAWER_CORNER_RADIUS);
    }

    @NonNull
    @Override
    public AppBarLayout getAppBarLayout() {
        return mBinding.appBarContent.appBarLayout;
    }

    @NonNull
    @Override
    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return mBinding.appBarContent.collapsingToolbarLayout;
    }

    @NonNull
    @Override
    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = new Toolbar(this);
        }
        return mToolbar;
    }

    @NonNull
    @Override
    public ViewGroup getContentContainer() {
        return mBinding.appBarContent.contentContainer;
    }

    @NonNull
    @Override
    public ViewGroup getFooterContainer() {
        return mBinding.footerContainer;
    }

    /*
     * Toolbar NavButton methods.
     */
    @Override
    public void defaultHomeAsUp() {
        Log.e(TAG, "defaultHomeAsUp: not supported in DrawerLayout");
    }

    @Override
    public void setNavigationButtonIcon(@DrawableRes int resId) {
        Log.e(TAG, "setNavigationButtonIcon: not supported in DrawerLayout");
    }

    @Override
    public void setNavigationButtonIcon(@Nullable Drawable icon) {
        Log.e(TAG, "setNavigationButtonIcon: not supported in DrawerLayout");
    }

    @Override
    public void setNavigationButtonTooltip(@Nullable CharSequence tooltipText) {
        Log.e(TAG, "setNavigationButtonTooltip: not supported in DrawerLayout");
    }

    @Override
    public void setNavigationButtonOnClickListener(@Nullable View.OnClickListener listener) {
        Log.e(TAG, "setNavigationButtonOnClickListener: not supported in DrawerLayout");
    }

    /*
     * Drawer methods.
     */
    private void setDrawerWidth() {
        ViewGroup.LayoutParams lp = mBinding.drawerContentContainer.getLayoutParams();

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        final int displayWidth = size.x;
        final float density = getResources().getDisplayMetrics().density;
        final float dpi = (float) displayWidth / density;

        double widthRate;
        if (dpi >= 1920.0F) {
            widthRate = 0.22D;
        } else if (dpi >= 960.0F && dpi < 1920.0F) {
            widthRate = 0.2734D;
        } else if (dpi >= 600.0F && dpi < 960.0F) {
            widthRate = 0.46D;
        } else if (dpi >= 480.0F && dpi < 600.0F) {
            widthRate = 0.5983D;
        } else {
            widthRate = 0.844D;
        }

        lp.width = (int) ((double) displayWidth * widthRate);
    }

    private void setDrawerCornerRadius(@Dimension float dp) {
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        setDrawerCornerRadius(px);
    }

    private void setDrawerCornerRadius(@Px int px) {
        mBinding.drawerContentContainer.setOutlineProvider(new DrawerOutlineProvider(px));
        mBinding.drawerContentContainer.setClipToOutline(true);
    }

    public void openDrawer(boolean open, boolean animate) {
        if (open) {
            mBinding.drawerLayout.openDrawer(
                    mBinding.drawerContentContainer, animate);
        } else {
            mBinding.drawerLayout.closeDrawer(
                    mBinding.drawerContentContainer, animate);
        }
    }


    private class DrawerOutlineProvider extends ViewOutlineProvider {
        private final int mCornerRadius;

        public DrawerOutlineProvider(@Px int cornerRadius) {
            mCornerRadius = cornerRadius;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(
                    mIsRtl
                            ? 0
                            : -mCornerRadius,
                    0,
                    mIsRtl
                            ? view.getWidth() + mCornerRadius
                            : view.getWidth(), view.getHeight(),
                    mCornerRadius);
        }
    }
}
