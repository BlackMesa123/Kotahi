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

import android.app.SearchManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import io.mesalabs.oneui.R;

public class SearchActivity extends AppBarActivity {
    private Toolbar mToolbar;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppBarExpanded(false, false);
        initSearchView();
    }

    private void initSearchView() {
        if (mSearchView != null) {
            mSearchView.setIconifiedByDefault(false);
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
    }

    @NonNull
    @Override
    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = new Toolbar(this);
            mToolbar.setContentInsetsAbsolute(0, 0);
            mSearchView = new SearchView(mContext);
            mToolbar.addView(mSearchView);
        }
        return mToolbar;
    }

    @NonNull
    public SearchView getSearchView() {
        return mSearchView;
    }

    /*
     * SearchView NavButton methods.
     */
    @Override
    public void defaultHomeAsUp() {
        mSearchView.seslSetUpButtonVisibility(View.VISIBLE);
        setNavigationButtonOnClickListener(v -> onBackPressed());
    }

    @Override
    public void setNavigationButtonIcon(@Nullable Drawable icon) {
        if (icon != null) {
            TypedValue outValue = new TypedValue();
            getTheme().resolveAttribute(
                    androidx.appcompat.R.attr.actionMenuTextColor, outValue, true);

            if (outValue.resourceId > 0) {
                DrawableCompat.setTintList(icon, mContext.getColorStateList(outValue.resourceId));
            }

            mSearchView.seslSetUpButtonVisibility(View.VISIBLE);
            mSearchView.seslSetUpButtonIcon(icon);
        } else {
            mSearchView.seslSetUpButtonVisibility(View.GONE);
        }
    }

    @Override
    public void setNavigationButtonTooltip(@Nullable CharSequence tooltipText) {
        TooltipCompat.setTooltipText(mSearchView.seslGetUpButton(), tooltipText);
    }

    @Override
    public void setNavigationButtonOnClickListener(@Nullable View.OnClickListener listener) {
        mSearchView.seslSetOnUpButtonClickListener(listener);
    }

    /*
     * SearchView style methods.
     */
    public void showSearchViewRoundBg(boolean showBg) {
        if (mSearchView != null) {
            mSearchView.setBackground(showBg
                    ? mContext.getDrawable(R.drawable.oneui_search_view_round_background)
                    : null);
        }
    }
}
