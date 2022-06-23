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

import android.view.LayoutInflater;
import android.view.SemView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import dev.rikka.tools.refine.Refine;
import io.mesalabs.oneui.R;
import io.mesalabs.oneui.databinding.OuiLayoutAppBarUiBinding;
import io.mesalabs.oneui.support.utils.BuildUtils;

public class AppBarActivity extends AbsAppBarActivity {
    // Views
    private OuiLayoutAppBarUiBinding mAppBarContent;
    private Toolbar mToolbar;
    private LinearLayout mFooterContainer;

    void onLayoutCreate() {
        LinearLayout rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);

        mAppBarContent = OuiLayoutAppBarUiBinding.inflate(
                LayoutInflater.from(this), rootView, false);
        LinearLayout.LayoutParams appBarLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        appBarLp.weight = 1.0f;
        if (BuildUtils.isOneUI()) {
            mAppBarContent.coordinatorLayout.setRoundedCorners(0);
        }
        rootView.addView(mAppBarContent.getRoot(), appBarLp);

        mFooterContainer = new LinearLayout(this);
        mFooterContainer.setOrientation(LinearLayout.VERTICAL);
        mFooterContainer.setBackgroundResource(R.color.samsung_bg_color);
        LinearLayout.LayoutParams footerLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.addView(mFooterContainer, footerLp);

        setRootView(rootView);
    }

    @NonNull
    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return mAppBarContent.coordinatorLayout;
    }

    @NonNull
    @Override
    public AppBarLayout getAppBarLayout() {
        return mAppBarContent.appBarLayout;
    }

    @NonNull
    @Override
    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return mAppBarContent.collapsingToolbarLayout;
    }

    @NonNull
    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = new Toolbar(this);
        }
        return mToolbar;
    }

    @NonNull
    @Override
    public ViewGroup getContentContainer() {
        return mAppBarContent.contentContainer;
    }

    @NonNull
    @Override
    public ViewGroup getFooterContainer() {
        return mFooterContainer;
    }

    /*
     * Footer methods.
     */
    public void addFooterView(@NonNull View view) {
        super.addFooterView(view);
        if (BuildUtils.isOneUI()) {
            Refine.<SemView>unsafeCast(
                    getWindow().getDecorView()).semSetRoundedCorners(0);
            mAppBarContent.getRoot().setRoundedCorners(
                    SeslRoundedCorner.ROUNDED_CORNER_BOTTOM_LEFT
                            | SeslRoundedCorner.ROUNDED_CORNER_BOTTOM_RIGHT);
        }
    }

    public void addFooterView(@NonNull View view,
                              @NonNull ViewGroup.LayoutParams params) {
        super.addFooterView(view, params);
        if (BuildUtils.isOneUI()) {
            Refine.<SemView>unsafeCast(
                    getWindow().getDecorView()).semSetRoundedCorners(0);
            mAppBarContent.getRoot().setRoundedCorners(
                    SeslRoundedCorner.ROUNDED_CORNER_BOTTOM_LEFT
                            | SeslRoundedCorner.ROUNDED_CORNER_BOTTOM_RIGHT);
        }
    }
}
