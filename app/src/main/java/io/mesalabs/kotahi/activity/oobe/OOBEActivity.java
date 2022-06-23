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

package io.mesalabs.kotahi.activity.oobe;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SemView;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dev.rikka.tools.refine.Refine;

import com.samsung.android.view.animation.SineInOut80;
import io.mesalabs.kotahi.R;
import io.mesalabs.kotahi.databinding.ActivityOobeBinding;
import io.mesalabs.oneui.support.utils.BuildUtils;
import io.mesalabs.oneui.support.widget.Toast;

public class OOBEActivity extends AppCompatActivity
        implements NavController.OnDestinationChangedListener {
    private static final int TIME_INTERVAL = 2000;

    private ActivityOobeBinding mBinding;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;

    private long mBackPressed;
    private Toast mToast;

    // TODO tablet support
    //private boolean mIsLandScape;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mBinding = ActivityOobeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // TODO add localized string
        mToast = Toast.makeText(this, "CONFIRM", Toast.LENGTH_SHORT);

        initAppBar();
        initNavHostFragment();
    }

    private void initAppBar() {
        // TODO tablet support
        //mIsLandScape = getResources().getConfiguration().orientation
        //        == Configuration.ORIENTATION_LANDSCAPE;

        if (!BuildUtils.isSemDevice()) {
            mBinding.toolbar.setTitleTextAppearance(this,
                    io.mesalabs.oneui.R.style.TextAppearance_Kotahi_SeslToolbar_Title);
        }

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // TODO tablet support
        // if (!mIsLandScape)
        mBinding.toolbar.setAlpha(0f);
        mBinding.toolbar.setTitle(R.string.app_name);

        if (BuildUtils.isOneUI()) {
            SemView v = Refine.unsafeCast(getWindow().getDecorView());
            v.semSetRoundedCorners(SemView.SEM_ROUNDED_CORNER_NONE);
        }
    }

    private void initNavHostFragment() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();

            NavGraph navGraph = mNavController.getNavInflater()
                    .inflate(R.navigation.oobe_nav_graph);
            navGraph.setStartDestination(R.id.nav_ob_intro_fragment);
            mNavController.setGraph(navGraph);

            mNavController.addOnDestinationChangedListener(this);

            mAppBarConfiguration = new AppBarConfiguration.Builder(navGraph).build();
        }
    }

    @Override
    public void onBackPressed() {
        final long currentTimeMs = System.currentTimeMillis();

        if (mBackPressed + TIME_INTERVAL > currentTimeMs) {
            super.onBackPressed();
            finish();
            mToast.cancel();
        } else {
            mToast.show();
        }

        mBackPressed = currentTimeMs;
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mNavController != null && mAppBarConfiguration != null) {
            return NavigationUI.navigateUp(
                    mNavController, mAppBarConfiguration)
                        || super.onSupportNavigateUp();
        }
        return false;
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller,
                                     @NonNull NavDestination destination,
                                     @Nullable Bundle arguments) {
        // TODO tablet support
        //if (!mIsLandScape)
        if (destination.getId() == R.id.nav_ob_config_fragment
                && mBinding.toolbar.getAlpha() == 0f) {
            mBinding.toolbar.setAlpha(1f);
            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            fadeIn.setDuration(350);
            fadeIn.setStartOffset(250);
            fadeIn.setInterpolator(new SineInOut80());
            fadeIn.setFillAfter(true);
            mBinding.toolbar.startAnimation(fadeIn);
        } else {
            AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
            fadeOut.setDuration(200);
            fadeOut.setStartOffset(100);
            fadeOut.setInterpolator(new LinearInterpolator());
            fadeOut.setFillAfter(true);
            mBinding.toolbar.startAnimation(fadeOut);
        }
    }
}
