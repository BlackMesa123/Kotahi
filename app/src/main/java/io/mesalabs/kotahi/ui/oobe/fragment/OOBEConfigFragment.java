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

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.transition.MaterialSharedAxis;
import com.samsung.android.view.animation.SineInOut80;

import java.util.ArrayList;
import java.util.List;

import io.mesalabs.kotahi.R;
import io.mesalabs.kotahi.databinding.FragmentOobeConfigBinding;

public class OOBEConfigFragment extends Fragment {
    private static final int REQUEST_PHONE_PERMISSION = 1011;

    private Context mContext;
    private FragmentOobeConfigBinding mBinding;

    private ActivityResultLauncher<IntentSenderRequest> numberPickerResultLauncher;
    private IntentSenderRequest numberPickerSenderRequest;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                if (mBinding.oobeConfigCountryCode.hasFocus()) {
                    mBinding.oobeConfigCountrySpinner.setCountryFromCode(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mBinding.oobeConfigCountrySpinner.setCountryFromISO(tm.getSimCountryIso());

        // TODO add localized string
        mBinding.oobeIntroSubButton.setText("Next");
        mBinding.oobeIntroSubButton.seslSetButtonShapeEnabled(true);
        mBinding.oobeIntroSubButton.setOnClickListener(v -> {
            confirmNumberDialog("+" + mBinding.oobeConfigCountryCode.getText() + mBinding.oobeConfigPhoneNumber.getText());
        });

        initNumberListView();
    }


    private void confirmNumberDialog(String number) {
        AlertDialog confirmDialog = new AlertDialog.Builder(mContext)
                .setTitle("Confirm number")
                .setMessage(number)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Next", (dialog, which) -> {
                    // TODO go to the code confirmation page
                    Toast.makeText(mContext, number, Toast.LENGTH_SHORT).show();
                })
                .create();
        confirmDialog.show();
    }

    private void initNumberListView() {
        mBinding.oobeConfigNumberList.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.oobeConfigNumberList.addItemDecoration(new ItemDecoration(mContext));
        mBinding.oobeConfigNumberList.setItemAnimator(null);
        mBinding.oobeConfigNumberList.seslSetLastRoundedCorner(false);

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(mContext).addApi(Auth.CREDENTIALS_API).build();
        mGoogleApiClient.connect();
        numberPickerResultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                confirmNumberDialog(((Credential) result.getData().getParcelableExtra(Credential.EXTRA_KEY)).getId());
            }
        });
        numberPickerSenderRequest = new IntentSenderRequest.Builder(Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()).getIntentSender()).build();

        listPhoneNumbers();
    }

    @SuppressLint("MissingPermission")
    private void listPhoneNumbers() {
        List<String> numbers = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS}, REQUEST_PHONE_PERMISSION);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService(Context.TELEPHONY_SERVICE);
            List<SubscriptionInfo> subscriptionList = SubscriptionManager.from(this.mContext).getActiveSubscriptionInfoList();

            if (subscriptionList != null) {
                for (SubscriptionInfo subscriptionInfo : subscriptionList)
                    numbers.add(telephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getLine1Number());
            }
        }

        numbers.add("Pick from GMS");
        mBinding.oobeConfigNumberList.setAdapter(new NumberAdapter(numbers));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("request", "response");
        if (requestCode == REQUEST_PHONE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            listPhoneNumbers();
    }

    public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {
        private List<String> mNumbers;

        public NumberAdapter(List<String> numbers) {
            this.mNumbers = numbers;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NumberAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_oobe_number_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mNumbers.get(position));
            holder.textView.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos == getItemCount() - 1) {
                    numberPickerResultLauncher.launch(numberPickerSenderRequest);
                } else {
                    confirmNumberDialog(mNumbers.get(pos));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mNumbers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.oobe_config_list_text_view);
            }
        }
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        private final Drawable mDivider;

        public ItemDecoration(@NonNull Context context) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, outValue, true);

            mDivider = context.getDrawable(outValue.data == 0
                    ? androidx.appcompat.R.drawable.sesl_list_divider_dark
                    : androidx.appcompat.R.drawable.sesl_list_divider_light);
        }

        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(c, parent, state);

            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                final int top = child.getBottom()
                        + ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).bottomMargin;
                final int bottom = mDivider.getIntrinsicHeight() + top;

                mDivider.setBounds(parent.getLeft(), top, parent.getRight(), bottom);
                mDivider.draw(c);
            }
        }
    }

}
