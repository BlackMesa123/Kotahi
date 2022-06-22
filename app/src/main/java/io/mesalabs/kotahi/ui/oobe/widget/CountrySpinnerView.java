package io.mesalabs.kotahi.ui.oobe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.mesalabs.kotahi.R;
import io.mesalabs.kotahi.ui.oobe.adapters.CountryListAdapter;
import io.mesalabs.kotahi.ui.oobe.adapters.CountryListAdapter.OnCountryItemSelected;
import io.mesalabs.kotahi.ui.widget.MaxHeightLinearLayout;

public class CountrySpinnerView extends LinearLayout
        implements OnCountryItemSelected {
    private static final float VIEW_VERTICAL_PADDING = 10.0f;

    private final Context mContext;
    private OnCountryItemSelected mListener;
    private AppCompatTextView mTextView;

    private AlertDialog mDialog;
    private SearchView mCountrySearchView;
    private RecyclerView mCountryListView;
    private CountryListAdapter mListAdapter;

    private int mSelectedPos = 0;

    public interface OnCountryItemSelected {
        void onItemSelected(String countryCode, String phoneNumberFormat);
    }

    public CountrySpinnerView(@NonNull Context context) {
        this(context, null);
    }

    public CountrySpinnerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountrySpinnerView(@NonNull Context context, @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CountrySpinnerView(@NonNull Context context, @Nullable AttributeSet attrs,
                        int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
        initDialog();
        initCountryListView();
        initCountrySearchView();
    }

    private void init() {
        removeAllViews();

        setBackgroundResource(R.drawable.oobe_country_spinner_background);

        setOrientation(HORIZONTAL);
        inflate(mContext, R.layout.view_oobe_country_spinner_view, this);

        mTextView = findViewById(R.id.country_spinner_item_text);
        // TODO add localized string
        mTextView.setText("Country");
        mTextView.setEnabled(false);

        final int verticalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, VIEW_VERTICAL_PADDING,
                getResources().getDisplayMetrics());
        setPadding(0, verticalPadding, 0, verticalPadding);
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // TODO add localized string
        builder.setTitle("Select country");

        LayoutInflater inflater = LayoutInflater.from(mContext);
        MaxHeightLinearLayout view = (MaxHeightLinearLayout) inflater
                .inflate(R.layout.view_oobe_country_spinner_dialog, null);

        view.setMaxHeight((int) (mContext.getResources().getDisplayMetrics().widthPixels * 1.25f));
        mCountrySearchView = view.findViewById(R.id.country_dialog_search_view);
        mCountryListView = view.findViewById(R.id.country_dialog_list_view);
        builder.setView(view);

        // TODO add localized string
        builder.setNegativeButton("Cancel", null);
        builder.setOnDismissListener(dialog -> {
            mCountrySearchView.setQuery("", true);
            mCountrySearchView.clearFocus();
        });

        mDialog = builder.create();
        mDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setOnClickListener(v -> showDialog());
        setOnLongClickListener(v -> {
            showDialog();
            return true;
        });

        // TODO fix typeface in non-Samsung OS
        /*if (!BuildUtils.isSemDevice()) {
            mCountrySearchView.seslGetAutoCompleteView()
                    .setTypeface(TypefaceUtils.ROBOTO_MEDIUM);
        }*/
    }

    private void initCountryListView() {
        if (mCountryListView != null) {
            mCountryListView.setLayoutManager(new LinearLayoutManager(mContext));
            mCountryListView.setAdapter(mListAdapter
                    = new CountryListAdapter(mContext, this));
            mCountryListView.setItemAnimator(null);
            mCountryListView.seslSetLastRoundedCorner(false);
            mCountryListView.seslSetGoToTopEnabled(true);
            mCountryListView.seslSetFastScrollerEnabled(true);
        }
    }

    private void initCountrySearchView() {
        if (mCountrySearchView != null) {
            // TODO add voice search
            //SearchManager manager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
            //SearchableInfo info = manager.getSearchableInfo(new ComponentName(mContext, OOBEActivity.class));
            //mCountrySearchView.setSearchableInfo(info);

            mCountrySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mCountrySearchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mListAdapter.getFilter().filter(newText);
                    return true;
                }
            });
        }
    }

    private void showDialog() {
        ((LinearLayoutManager) mCountryListView.getLayoutManager())
                .scrollToPositionWithOffset(mSelectedPos,0);
        mDialog.show();

        // TODO fix typeface in non-Samsung OS
        /*if (!BuildUtils.isSemDevice()) {
            ((TextView) mDialog.findViewById(androidx.appcompat.R.id.alertTitle))
                    .setTypeface(TypefaceUtils.ROBOTO_MEDIUM);
            mDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                    .setTypeface(TypefaceUtils.ROBOTO_MEDIUM);
        }*/
    }

    public void setOnItemSelectedListener(@Nullable OnCountryItemSelected listener) {
        mListener = listener;
    }

    public void setCountryFromCode(String countryCode) {
        ArrayList<CountryListAdapter.Country> countries
                = mListAdapter.getCountries();

        for (CountryListAdapter.Country c : countries) {
            if (c.code.equals(countryCode)) {
                int index = countries.indexOf(c);
                mListAdapter.setCheckedItem(index);
                return;
            }
        }

        mListAdapter.setCheckedItem(-1);
    }

    public void setCountryFromISO(String countryIso) {
        ArrayList<CountryListAdapter.Country> countries
                = mListAdapter.getCountries();

        for (CountryListAdapter.Country c : countries) {
            if (c.iso.toLowerCase().equals(countryIso)) {
                int index = countries.indexOf(c);
                mListAdapter.setCheckedItem(index);
                return;
            }
        }

        mListAdapter.setCheckedItem(-1);
    }

    @Override
    public void onItemSelected(CountryListAdapter.Country country, int selectedPosition) {
        mSelectedPos = selectedPosition;

        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }

        if (mSelectedPos != -1) {
            mTextView.setText(mListAdapter.getCountryName(country));
            mTextView.setEnabled(true);
            if (mListener != null) {
                mListener.onItemSelected(country.code, country.phoneNumberFormat);
            }
        } else {
            mSelectedPos = 0;
            // TODO add localized string
            mTextView.setText("Country");
            mTextView.setEnabled(false);
        }
    }
}
