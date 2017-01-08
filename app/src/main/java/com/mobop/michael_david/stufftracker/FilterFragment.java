package com.mobop.michael_david.stufftracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterFragment extends Fragment  {

    public static final int FRAGMENT_ID  = 2;

    EditText nameTextView;
    EditText brandTextView;
    EditText modelTextView;
    EditText nfcIdTextView;

    OnFragmentInteractionListener mListener;
    ArrayList<CheckBox> categoriesCheckboxes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.filter_menu_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        nameTextView = (EditText)view.findViewById(R.id.item_name_filter);
        brandTextView = (EditText)view.findViewById(R.id.item_brand_filter);
        modelTextView = (EditText)view.findViewById(R.id.item_model_filter);
        nfcIdTextView = (EditText)view.findViewById(R.id.item_nfc_id_filter);

        nameTextView.getBackground().clearColorFilter();
        brandTextView.getBackground().clearColorFilter();
        modelTextView.getBackground().clearColorFilter();
        nfcIdTextView.getBackground().clearColorFilter();

        // Retrieve constant categories
        ArrayList<String> categories = new ArrayList<>(Arrays.asList((getResources().getStringArray(R.array.categories_names))));


        LinearLayout categoriesCheckboxesLayout=(LinearLayout)view.findViewById(R.id.categoriesCheckboxesLayout);

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        categoriesCheckboxes = new ArrayList<CheckBox>();

        // Dynamicly create checkboxes
        for (String categorie: categories) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.gravity = Gravity.RIGHT;
            CheckBox checkBox = new CheckBox(getActivity().getApplicationContext());
            checkBox.setLayoutParams(params);
            checkBox.setPadding(80,0,0,0);
            checkBox.setText(categorie);
            checkBox.setTextColor(getResources().getColor(R.color.colorTextGrey));
            checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            categoriesCheckboxes.add(checkBox);

            categoriesCheckboxesLayout.addView(checkBox);
        }

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_validate_filter:
                // TODO : Test if current filter is correct
                mListener.onFragmentQuit(FRAGMENT_ID, 0);

                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity = (Activity) context;
            try {
                mListener = (OnFragmentInteractionListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnFragmentInteractionListener"); }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public FilterStuffItems getFilterStuffItems() {

        ArrayList<String> categoriesChecked = new ArrayList<String>();
        for (CheckBox checkBox: categoriesCheckboxes) {

            // Find which textbox is selected
            if(checkBox.isChecked()){
                categoriesChecked.add(checkBox.getText().toString());
            }
        }

        return new FilterStuffItems(
                nameTextView.getText().toString(),
                brandTextView.getText().toString(),
                modelTextView.getText().toString(),
                nfcIdTextView.getText().toString(),
                categoriesChecked);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

}
