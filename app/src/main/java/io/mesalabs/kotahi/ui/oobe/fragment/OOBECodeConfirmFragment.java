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

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.transition.MaterialSharedAxis;
import com.samsung.android.view.animation.SineIn50;
import com.samsung.android.view.animation.SineInOut80;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import io.mesalabs.kotahi.KotahiApp;
import io.mesalabs.kotahi.MainActivity;
import io.mesalabs.kotahi.R;
import io.mesalabs.kotahi.databinding.FragmentOobeCodeConfirmBinding;
import io.mesalabs.oneui.support.widget.ProgressDialog;

public class OOBECodeConfirmFragment extends Fragment implements Client.ResultHandler {

    private Context mContext;
    private FragmentOobeCodeConfirmBinding mBinding;

    private ProgressDialog progressDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MaterialSharedAxis enterTransition
                = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        enterTransition.setDuration(600);
        enterTransition.setInterpolator(new SineInOut80());
        setEnterTransition(enterTransition);

        MaterialSharedAxis exitTransition
                = new MaterialSharedAxis(MaterialSharedAxis.X, false);
        exitTransition.setDuration(600);
        setReturnTransition(exitTransition);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentOobeCodeConfirmBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ColorDrawable c = new ColorDrawable(mContext.getColor(io.mesalabs.oneui.R.color.samsung_bg_color));
        view.setForeground(c);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(c, PropertyValuesHolder.ofInt("alpha", 255, 0));
        animator.setTarget(c);
        animator.setStartDelay(300);
        animator.setDuration(300);
        animator.setInterpolator(new SineIn50());
        animator.start();

        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void init() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_CIRCLE);
        progressDialog.setCancelable(false);

        mBinding.oobeCodeConfirmSubButton.setText(R.string.oobe_next);
        mBinding.oobeCodeConfirmSubButton.seslSetButtonShapeEnabled(true);
        mBinding.oobeCodeConfirmSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                KotahiApp.confirmCode(mBinding.oobeCodeConfirmCode.getText().toString(), OOBECodeConfirmFragment.this);
            }
        });

    }


    @Override
    public void onResult(TdApi.Object object) {
        progressDialog.dismiss();
        Log.e("Code Confirm", object.toString());

        switch (object.getConstructor()) {
            case TdApi.Error.CONSTRUCTOR:
                // TODO
                Toast.makeText(mContext, "Auth error", Toast.LENGTH_SHORT).show();
                break;
            case TdApi.Ok.CONSTRUCTOR:
                // TODO

                KotahiApp.registerUser("first", "last", new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        Log.e("User", object.toString());
                        startActivity(new Intent(mContext, MainActivity.class));
                    }
                });

                break;
        }
    }

}
