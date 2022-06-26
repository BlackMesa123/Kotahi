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

package io.mesalabs.kotahi.ui.oobe.fragment;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.transition.MaterialSharedAxis;

import com.samsung.android.view.animation.SineInOut80;

import io.mesalabs.kotahi.R;
import io.mesalabs.kotahi.databinding.FragmentOobeIntroBinding;
import io.mesalabs.kotahi.ui.oobe.widget.TipsItemView;
import io.mesalabs.kotahi.ui.utils.WebUtils;
import io.mesalabs.oneui.support.utils.TypefaceUtils;

public class OOBEIntroFragment extends Fragment {
    private Context mContext;
    private FragmentOobeIntroBinding mBinding;

    // TODO tablet support
    //private boolean mIsLandScape;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO tablet support
        //mIsLandScape = getResources().getConfiguration().orientation
        //        == Configuration.ORIENTATION_LANDSCAPE;

        MaterialSharedAxis enterTransition
                = new MaterialSharedAxis(MaterialSharedAxis.X, false);
        enterTransition.setDuration(600);
        enterTransition.setInterpolator(new SineInOut80());
        setReenterTransition(enterTransition);

        MaterialSharedAxis exitTransition
                = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        exitTransition.setDuration(600);
        setExitTransition(exitTransition);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentOobeIntroBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO tablet support
        /*if (mIsLandScape) {
            mBinding.oobeIntroHeader.setVisibility(View.GONE);

            mBinding.oobeIntroScrollView.setPadding(0, 0, 0, 0);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mBinding.oobeIntroScrollView.getLayoutParams();
            lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.matchConstraintPercentHeight = 1;
            mBinding.oobeIntroScrollView.setLayoutParams(lp);
        } else {
            mBinding.oobeIntroHeader.setVisibility(View.VISIBLE);
        }*/

        initTipsItems();
        initToSView();
        initFooterButton();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    // FIXME
    private void initTipsItems() {
        LinearLayout.LayoutParams defaultLp = new LinearLayout
                .LayoutParams(MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        String[] titles = {
                "!Early build",
                "Beautiful",
                "Powerful"
        };

        String[] summaries = {
                "This app is currently in W.I.P. and not stable!",
                "The world's fastest messaging app, now with a revamped user interface.",
                "Kotahi uses the Telegram's API to provide free unlimited cloud storage for chats and media."
        };

        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String summary = summaries[i];

            TipsItemView item = new TipsItemView(mContext);
            if (title.startsWith("!")) {
                item.setTitleColor(mContext.getColor(R.color.oobe_intro_tips_warning_text_color));
                title = title.substring(1);
            }
            item.setTitleText(title);
            item.setSummaryText(summary);
            mBinding.oobeIntroTipsContainer.addView(item, defaultLp);
        }
    }

    // FIXME
    private void initToSView() {
        // TODO add localized string
        String tos = "Terms of Service";
        String tosText = "By clicking Continue, you agree to the " + tos + ".";

        SpannableString tosLink = new SpannableString(tosText);
        tosLink.setSpan(new ToSSpanText(), tosText.indexOf(tos), tosText.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mBinding.oobeIntroFooterTosText.setText(tosLink);
        mBinding.oobeIntroFooterTosText.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.oobeIntroFooterTosText.setHighlightColor(Color.TRANSPARENT);
    }

    // FIXME
    private void initFooterButton() {
        if (getResources().getConfiguration().screenWidthDp < 360) {
            mBinding.oobeIntroFooterButton.getLayoutParams().width = MATCH_PARENT;
        }

        // TODO add localized string
        mBinding.oobeIntroFooterButton.setText("Continue");
        mBinding.oobeIntroFooterButton.setTypeface(TypefaceUtils.getBoldSupportTypeface());
        mBinding.oobeIntroFooterButton.setOnClickListener(v -> {
            mBinding.oobeIntroFooterTosText.setEnabled(false);
            mBinding.oobeIntroFooterButton.setText("");
            mBinding.oobeIntroFooterButton.setVisibility(View.INVISIBLE);
            mBinding.oobeIntroFooterButtonProgress.setVisibility(View.VISIBLE);
            mBinding.oobeIntroFooterButtonProgress.post(this::navigateToNextFragment);
        });
    }

    private void navigateToNextFragment() {
        NavController controller = NavHostFragment.findNavController(OOBEIntroFragment.this);
        controller.navigate(R.id.action_nav_to_obConfigFragment);
    }

    private class ToSSpanText extends ClickableSpan {
        private static final String TOS_LINK = "https://telegram.org/tos";

        @Override
        public void onClick(View widget) {
            widget.playSoundEffect(SoundEffectConstants.CLICK);
            WebUtils.openWebPage(mContext, TOS_LINK);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setTypeface(TypefaceUtils.getBoldSupportTypeface());
        }
    }
}
