package io.mesalabs.kotahi.ui.oobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.mesalabs.kotahi.R;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder>
        implements SectionIndexer, Filterable {
    private static final int VIEW_TYPE_COUNTRY = 0;
    private static final int VIEW_TYPE_NO_ITEM = 1;

    private final Context mContext;
    private final OnCountryItemSelected mListener;

    private final List<String> mSections = new ArrayList<>();
    private final List<Integer> mPositionForSection = new ArrayList<>();
    private final List<Integer> mSectionForPosition = new ArrayList<>();

    private final ArrayList<Country> mCountries = new ArrayList<>();
    private ArrayList<Country> mFilteredCountries;
    private Country mSelectedCountry = null;
    private int mSelectedPos = -1;

    public interface OnCountryItemSelected {
        void onItemSelected(@Nullable Country country, int selectedPosition);
    }

    public class Country {
        public String name;
        public String code;
        public String phoneNumberFormat;

        Country() {
            super();
        }

        @NonNull
        @Override
        public String toString() {
            return "name=" + name
                    + "; code=" + code
                    + "; phoneNumberFormat=" + phoneNumberFormat;
        }
    }

    public CountryListAdapter(@NonNull Context context,
                              @Nullable OnCountryItemSelected callback) {
        mContext = context;
        mListener = callback;
        initCountriesList();
        initSectionIndexer();
    }

    private void initCountriesList() {
        try {
            InputStream stream = mContext.getResources()
                    .getAssets().open("countries.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] args = line.split(";");
                Country c = new Country();
                c.name = args[2];
                c.code = args[0];
                if (args.length > 3) {
                    c.phoneNumberFormat = args[3];
                }
                mCountries.add(c);
            }

            reader.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(mCountries, (c1, c2) -> c1.name.compareTo(c2.name));
        mFilteredCountries = new ArrayList<>(mCountries);
    }

    private void initSectionIndexer() {
        mSections.clear();
        mPositionForSection.clear();
        mSectionForPosition.clear();

        for (int i = 0; i < mFilteredCountries.size(); i++) {
            Country c = mFilteredCountries.get(i);

            String letter = c.name.substring(0, 1);
            if (i == 0 || !mSections
                    .get(mSections.size() - 1).equals(letter)) {
                mSections.add(letter);
                mPositionForSection.add(i);
            }
            mSectionForPosition.add(mSections.size() - 1);
        }
    }

    @Nullable
    public ArrayList<Country> getCountries() {
        return mCountries;
    }

    @NonNull
    public String getCountryName(Country country) {
        if (country != null) {
            return country.name + " (" + getCountryPhoneCode(country) + ")";
        } else {
            return "";
        }
    }

    @NonNull
    private String getCountryPhoneCode(Country country) {
        if (country != null) {
            return "+" + country.code;
        } else {
            return "";
        }
    }

    /*@NonNull
    public String getCountryPhoneNumberFormatting(Country country) {
        if (country != null) {
            return country.phoneNumberFormat;
        } else {
            return "";
        }
    }*/

    public void setCheckedItem(int position) {
        final int prevPos = mSelectedPos;

        if (position != -1) {
            mSelectedCountry = mFilteredCountries.get(position);
            mSelectedPos = mCountries.indexOf(mSelectedCountry);
        } else {
            mSelectedCountry = null;
            mSelectedPos = -1;
        }

        if (prevPos != -1) {
            notifyItemChanged(prevPos);
        }

        if (mListener != null) {
            mListener.onItemSelected(mSelectedCountry, mSelectedPos);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        final boolean isCountryItem = viewType == VIEW_TYPE_COUNTRY;
        final int layoutResId;
        if (isCountryItem) {
            layoutResId = R.layout.view_oobe_country_spinner_list_item;
        } else {
            layoutResId = R.layout.view_oobe_country_spinner_list_no_item;
        }

        View view = inflater.inflate(layoutResId, parent, false);
        return new ViewHolder(view, isCountryItem);
    }

    @Override
    public int getItemCount() {
        if (!mFilteredCountries.isEmpty()) {
            return mFilteredCountries.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return mFilteredCountries.isEmpty()
                ? VIEW_TYPE_NO_ITEM
                : VIEW_TYPE_COUNTRY;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.isCountryItem) {
            Country c = mFilteredCountries.get(position);

            AppCompatRadioButton itemView = (AppCompatRadioButton) holder.itemView;
            itemView.setText(getCountryName(c));
            itemView.setChecked(c.equals(mSelectedCountry));
            itemView.setOnClickListener(v -> setCheckedItem(holder.getBindingAdapterPosition()));
        } else {
            AppCompatTextView itemView = (AppCompatTextView) holder.itemView;
            // TODO add localized string
            itemView.setText("No results");
        }
    }

    @Override
    public Object[] getSections() {
        return mSections.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (sectionIndex >= mPositionForSection.size()) {
            return 0;
        }
        return mPositionForSection.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position >= mSectionForPosition.size()) {
            return 0;
        }
        return mSectionForPosition.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final String searchText = constraint.toString().toLowerCase();

                if (!searchText.isEmpty()) {
                    mFilteredCountries.clear();
                    for (Country c : mCountries) {
                        final String countryName = c.name.toLowerCase();
                        if (countryName.contains(searchText)) {
                            mFilteredCountries.add(c);
                        }
                    }
                } else {
                    mFilteredCountries = new ArrayList<>(mCountries);
                }

                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
                initSectionIndexer();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        boolean isCountryItem;

        public ViewHolder(@NonNull View itemView, boolean isCountryItem) {
            super(itemView);
            this.isCountryItem = isCountryItem;
        }
    }
}
