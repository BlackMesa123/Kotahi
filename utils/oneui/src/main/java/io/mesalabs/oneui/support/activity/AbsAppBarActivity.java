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

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import io.mesalabs.oneui.R;
import io.mesalabs.oneui.support.base.BaseActivity;

/**
 * Part of the code has been kanged from:
 * https://github.com/OneUIProject/oneui-design/blob/main/lib/src/main/java/dev/oneuiproject/oneui/layout/ToolbarLayout.java
 */
abstract class AbsAppBarActivity extends BaseActivity {
    protected final String TAG = getClass().getSimpleName();
    // AppBar Flags
    private boolean mExpandable = true;
    private boolean mExpanded = true;
    // Toolbar
    protected Drawable mNavigationIcon;
    private CharSequence mTitleCollapsed;
    private CharSequence mTitleExpanded;
    private CharSequence mSubtitleCollapsed;
    private CharSequence mSubtitleExpanded;

    abstract void onLayoutCreate();

    @NonNull
    public abstract AppBarLayout getAppBarLayout();
    @NonNull
    public abstract CollapsingToolbarLayout getCollapsingToolbarLayout();
    @NonNull
    public abstract Toolbar getToolbar();
    @NonNull
    public abstract ViewGroup getContentContainer();
    @NonNull
    public abstract ViewGroup getFooterContainer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLayoutCreate();
        initToolbar();
        resetAppBar();
        refreshLayout(getResources().getConfiguration());
    }

    private void initToolbar() {
        Toolbar toolbar = getToolbar();

        CollapsingToolbarLayout.LayoutParams lp = new CollapsingToolbarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        lp.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);

        getCollapsingToolbarLayout().addView(toolbar, lp);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void resetAppBar() {
        AppBarLayout abl = getAppBarLayout();
        if (mExpandable) {
            abl.setEnabled(true);
            abl.seslSetCustomHeightProportion(false, 0.3f);
        } else {
            abl.setEnabled(false);
            abl.seslSetCustomHeight(getResources().getDimensionPixelSize(
                            androidx.appcompat.R.dimen.sesl_action_bar_height_with_padding));
        }
    }

    private void refreshLayout(@NonNull Configuration newConfig) {
        final boolean isLandscape
                = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        setAppBarExpanded(!isLandscape & mExpanded);

        setContentSideMargin(newConfig, getContentContainer());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetAppBar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        setContentView(inflater.inflate(
                layoutResID, getContentContainer(), false));
    }

    @Override
    public void setContentView(@NonNull View view) {
        getContentContainer().addView(view, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(@NonNull View view,
                               @NonNull ViewGroup.LayoutParams params) {
        getContentContainer().addView(view, params);
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        setAppBarTitle(getString(titleId));
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        setAppBarTitle(title);
    }

    /*
     * Calls Super setContentView.
     */
    protected void setRootView(@NonNull View view) {
        super.setContentView(view);
    }

    protected void setRootView(@NonNull View view,
                               @NonNull ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    /*
     * AppBarLayout expandable/expanded methods.
     */
    public void setAppBarExpandable(boolean expandable) {
        if (mExpandable != expandable) {
            mExpandable = expandable;
            resetAppBar();
        }
    }

    public boolean isAppBarExpandable() {
        return mExpandable;
    }

    public void setAppBarExpanded(boolean expanded) {
        setAppBarExpanded(expanded, ViewCompat.isLaidOut(getAppBarLayout()));
    }

    public void setAppBarExpanded(boolean expanded, boolean animate) {
        if (mExpandable) {
            mExpanded = expanded;
            getAppBarLayout().setExpanded(expanded, animate);
        } else {
            Log.d(TAG, "setExpanded: appBar is not expandable");
        }
    }

    public boolean isAppBarExpanded() {
        return mExpandable && !getAppBarLayout().seslIsCollapsed();
    }

    /*
     * Toolbar NavButton methods.
     */
    public void defaultHomeAsUp() {
        if (mNavigationIcon == null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setNavigationButtonIcon(R.drawable.tw_ic_ab_back_mtrl);
            setNavigationButtonOnClickListener(v -> onBackPressed());
        }
    }

    public void setNavigationButtonIcon(@DrawableRes int resId) {
        setNavigationButtonIcon(mContext.getDrawable(resId));
    }

    public void setNavigationButtonIcon(@Nullable Drawable icon) {
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(
                androidx.appcompat.R.attr.actionMenuTextColor, outValue, true);

        if (outValue.resourceId > 0 && icon != null) {
            DrawableCompat.setTintList(icon, mContext.getColorStateList(outValue.resourceId));
        }

        getToolbar().setNavigationIcon(mNavigationIcon = icon);
    }

    public void setNavigationButtonTooltip(@Nullable CharSequence tooltipText) {
        getToolbar().setNavigationContentDescription(tooltipText);
    }

    public void setNavigationButtonOnClickListener(@Nullable View.OnClickListener listener) {
        getToolbar().setNavigationOnClickListener(listener);
    }

    /*
     * AppBar Title methods.
     */
    public void setAppBarTitle(@Nullable CharSequence title) {
        setAppBarTitle(title, title);
    }

    public void setAppBarTitle(@Nullable CharSequence expandedTitle,
                         @Nullable CharSequence collapsedTitle) {
        getToolbar().setTitle(mTitleCollapsed = collapsedTitle);
        getCollapsingToolbarLayout().setTitle(mTitleExpanded = expandedTitle);
    }

    public void setAppBarExpandedSubtitle(@Nullable CharSequence expandedSubtitle) {
        getCollapsingToolbarLayout().seslSetSubtitle(mSubtitleExpanded = expandedSubtitle);
    }

    public void setAppBarCollapsedSubtitle(@Nullable CharSequence collapsedSubtitle) {
        getToolbar().setSubtitle(mSubtitleCollapsed = collapsedSubtitle);
    }

    /*
     * SeslCollapsingToolbar custom views.
     */
    public void setAppBarCustomTitleView(@NonNull View view) {
        setAppBarCustomTitleView(view,
                new CollapsingToolbarLayout.LayoutParams(view.getLayoutParams()));
    }

    public void setAppBarCustomTitleView(@NonNull View view,
                                   @Nullable CollapsingToolbarLayout.LayoutParams params) {
        if (params == null) {
            params = new CollapsingToolbarLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.seslSetIsTitleCustom(true);
        getCollapsingToolbarLayout().seslSetCustomTitleView(view, params);
    }

    public void setAppBarCustomSubtitle(@NonNull View view) {
        getCollapsingToolbarLayout().seslSetCustomSubtitle(view);
    }

    /*
     * Footer methods.
     */
    public void addFooterView(@NonNull View view) {
        getFooterContainer().addView(view, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addFooterView(@NonNull View view,
                               @NonNull ViewGroup.LayoutParams params) {
        getFooterContainer().addView(view, params);
    }

    /*
     * Misc
     */
    private void setContentSideMargin(@NonNull Configuration config, @NonNull ViewGroup layout) {
        if (!isDestroyed() && !isFinishing()) {
            findViewById(android.R.id.content).post(() -> {
                if (config.screenWidthDp >= 589) {
                    layout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                }

                final int m = getSideMargin(config);
                layout.setPadding(m, 0, m, 0);
            });
        }
    }

    private int getSideMargin(@NonNull Configuration config) {
        final int width = findViewById(android.R.id.content).getWidth();
        return (int) (width * getMarginRatio(config.screenWidthDp, config.screenHeightDp));
    }

    private float getMarginRatio(int screenWidthDp, int screenHeightDp) {
        if (screenWidthDp < 589) {
            return 0.0f;
        }
        if (screenHeightDp > 411 && screenWidthDp <= 959) {
            return 0.05f;
        }
        if (screenWidthDp >= 960 && screenHeightDp <= 1919) {
            return 0.125f;
        }
        if (screenWidthDp >= 1920) {
            return 0.25f;
        }

        return 0.0f;
    }
}
