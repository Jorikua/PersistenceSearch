package ua.kaganovych.persistencesearch;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
    private ImageView mClearIcon;
    private View mDimView;
    private DrawerArrowDrawableToggle mToggle;

    public static final int ANIMATION_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDimView = findViewById(R.id.dimView);
        mSearch = (CustomEditText) findViewById(R.id.search);
        mHamArrowIcon = (ImageView) findViewById(R.id.hamArrowIcon);
        mMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mListView = (CustomListView) findViewById(R.id.listView);
        mClearIcon = (ImageView) findViewById(R.id.clear);
        mClearIcon.setVisibility(View.GONE);


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
                mClearIcon.setVisibility(View.GONE);
                hideKeyboard(view);
            }
        });


        mToggle = new DrawerArrowDrawableToggle(this, this);
        mHamArrowIcon.setImageDrawable(mToggle);


        mHamArrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mToggle.getPosition() == 1) {
                    hideKeyboard(view);
                }
            }
        });

        mClearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearch.setText("");
                mClearIcon.setVisibility(View.GONE);
            }
        });

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    mClearIcon.setVisibility(View.VISIBLE);
                } else {
                    mClearIcon.setVisibility(View.GONE);
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
                    mListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListView.setVisibility(View.VISIBLE);
                            animateToggle(true);
                            dimBackground(true);
                        }
                    }, 64);
                }
            }
        });

        mSearch.setCallback(new CustomEditText.onEventListener() {
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
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mSearch.clearFocus();
        mListView.setVisibility(View.GONE);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                dimBackground(false);
            }
        }, 70);
        animateToggle(false);
    }

    public void dimBackground(final boolean show) {
        mDimView.setEnabled(show);
        ViewCompat.animate(mDimView)
                .alpha(show ? 1 : 0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        mDimView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        mDimView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                })
                .withLayer()
                .start();
    }

    public void animateToggle(boolean show) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mToggle, DrawerArrowDrawableToggle.PROGRESS, show ? 0 : 1, show ? 1 : 0);
        animator.setDuration(200)
                .start();
    }
}
