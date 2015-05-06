package ua.kaganovych.persistencesearch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private CustomEditText mSearch;
    private ImageView mHamArrowIcon;
    private RelativeLayout mMainLayout;
    private CustomListView mListView;
    private CustomAdapter mAdapter;
    private ArrayList<Item> mList;
    private ImageView mClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = (CustomEditText) findViewById(R.id.search);
        mHamArrowIcon = (ImageView) findViewById(R.id.hamArrowIcon);
        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mListView = (CustomListView) findViewById(R.id.listView);
        mClear = (ImageView) findViewById(R.id.clear);
        mClear.setVisibility(View.GONE);

        Button open = (Button)findViewById(R.id.buttonOpen);
        Button close = (Button)findViewById(R.id.buttonClose);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });


        mList = new ArrayList<>();
        mList.add(new Item("One"));
        mList.add(new Item("Two"));
        mList.add(new Item("Three"));
        mList.add(new Item("One"));
        mList.add(new Item("Two"));
        mList.add(new Item("Three"));
        mList.add(new Item("One"));
        mList.add(new Item("Two"));
        mList.add(new Item("Three"));
        mList.add(new Item("One"));
        mList.add(new Item("Two"));
        mAdapter = new CustomAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(View.GONE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Item item = mAdapter.getItem(position);
                mSearch.setText(item.suggestion);
                mClear.setVisibility(View.GONE);
                hideKeyboard(view);
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearch.setText("");
                mClear.setVisibility(View.GONE);
            }
        });

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    mClear.setVisibility(View.VISIBLE);
                } else {
                    mClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    mListView.setVisibility(View.VISIBLE);
                }
            }
        });

        mSearch.setCallback(new CustomEditText.onArrowDownListener() {
            @Override
            public boolean onArrowDown() {
                hideKeyboard(mSearch);
                return true;
            }

            @Override
            public boolean onSearchSubmitted() {
                hideKeyboard(mSearch);
                return true;
            }
        });

        mHamArrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mSearch.clearFocus();
        mListView.setVisibility(View.GONE);
    }
}
