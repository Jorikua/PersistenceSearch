package ua.kaganovych.persistencesearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private EditText mSearch;
    private ImageView mSearchIcon;
    private RelativeLayout mMainLayout;
    private ListView mListView;
    private CustomAdapter mAdapter;
    private ArrayList<Item> mList;
    private ImageView mClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = (EditText) findViewById(R.id.search);
        mSearchIcon = (ImageView) findViewById(R.id.hamArrowIcon);
        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mListView = (ListView) findViewById(R.id.listView);
        mClear = (ImageView) findViewById(R.id.clear);
        mClear.setVisibility(View.GONE);

        mList = new ArrayList<>();
        mList.add(new Item("One"));
        mList.add(new Item("Two"));
        mList.add(new Item("Three"));
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

//        mMainLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                hideKeyboard(view);
//                return false;
//            }
//        });

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

        mSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });

        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    mSearch.clearFocus();
//                }
                return true;
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mSearch.clearFocus();
        mListView.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            mSearch.clearFocus();
            mListView.setVisibility(View.GONE);
        }
        return super.onKeyDown(keyCode, event);
    }

}
