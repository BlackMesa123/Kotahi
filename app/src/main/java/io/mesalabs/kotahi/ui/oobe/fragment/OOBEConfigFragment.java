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

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.transition.MaterialSharedAxis;
import com.samsung.android.view.animation.SineInOut80;

import io.mesalabs.kotahi.databinding.FragmentOobeConfigBinding;

public class OOBEConfigFragment extends Fragment {
    private Context mContext;
    private FragmentOobeConfigBinding mBinding;

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

        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentOobeConfigBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    private void init() {
        mBinding.oobeConfigCountrySpinner.setOnItemSelectedListener(
                (countryCode, phoneNumberFormat) -> {
                    boolean hasFocus = mBinding.oobeConfigCountryCode.hasFocus();
                    if (hasFocus) {
                        mBinding.oobeConfigCountryCode.clearFocus();
                    }
                    mBinding.oobeConfigCountryCode.setText(countryCode);
                    if (hasFocus) {
                        mBinding.oobeConfigCountryCode.requestFocus();
                    }
                });

        mBinding.oobeConfigCountryCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                if (mBinding.oobeConfigCountryCode.hasFocus()) {
                    mBinding.oobeConfigCountrySpinner.setCountryFromCode(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mBinding.oobeConfigCountrySpinner.setCountryFromISO(tm.getSimCountryIso());

        // TODO add localized string
        mBinding.oobeIntroSubButton.setText("Next");
        mBinding.oobeIntroSubButton.seslSetButtonShapeEnabled(true);
        // TODO do something ffs
        mBinding.oobeIntroSubButton.setOnClickListener(v -> { });
    }
}
