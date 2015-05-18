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
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private SearchEditText mSearch;
    private ImageView mHamArrowIcon;
    private TipsListView mTipsListView;
    private TipsAdapter mTipsAdapter;
    private ArrayList<Tips> mTipsList;
    private ImageView mClearIcon;
    private View mDimView;
    private DrawerArrowDrawableToggle mToggle;
    private ContactAdapter mContactAdapter;
    private ListView mContactListView;
    private ArrayList<Contact> mContactList;

    public static final int ANIMATION_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactListView = (ListView)findViewById(R.id.contactList);
        mDimView = findViewById(R.id.dimView);
        mSearch = (SearchEditText) findViewById(R.id.search);
        mHamArrowIcon = (ImageView) findViewById(R.id.hamArrowIcon);
        mTipsListView = (TipsListView) findViewById(R.id.listView);
        mClearIcon = (ImageView) findViewById(R.id.clear);
        mClearIcon.setVisibility(View.GONE);

        mTipsList = new ArrayList<>();
        mTipsList.add(new Tips("One"));
        mTipsList.add(new Tips("Two"));
        mTipsList.add(new Tips("Three"));
        mTipsList.add(new Tips("One"));
        mTipsList.add(new Tips("Two"));
        mTipsList.add(new Tips("Three"));
        mTipsList.add(new Tips("One"));
        mTipsList.add(new Tips("Two"));
        mTipsList.add(new Tips("Three"));
        mTipsList.add(new Tips("One"));
        mTipsList.add(new Tips("Two"));
        mTipsAdapter = new TipsAdapter(this, mTipsList);
        mTipsListView.setAdapter(mTipsAdapter);
        mTipsListView.setVisibility(View.GONE);

        mTipsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Tips item = mTipsAdapter.getItem(position);
                mSearch.setText(item.suggestion);
                mClearIcon.setVisibility(View.GONE);
                hideKeyboard(view);
            }
        });

        String[] names = new String[] {"Vasya", "Petya", "Kolya", "Serega", "Chernii", "Pepel", "Seva", "Shurik", "Uba",
                "Ches", "Kirill", "Toster", "Chipsi", "Karavan", "Omlet", "James", "Bond", "Iron Man", "Hulk", "Thor"};
        String[] cities = new String[] {"Dnepr", "Kiev", "Sloch", "Morjva", "Mars", "Tam", "London", "Capital", "Motherland", "Nowhere",
                "Somewhere", "Odindva", "Paris", "Valava", "Prussia", "Bratislava", "Kukuruza", "Vienna", "Berlin", "Lvov"};

        mContactList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            mContactList.add(new Contact(names[i], cities[i]));
        }

        mContactAdapter = new ContactAdapter(this, mContactList);
        mContactListView.setAdapter(mContactAdapter);


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
                    mTipsListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTipsListView.setVisibility(View.VISIBLE);
                            animateToggle(true);
                            dimBackground(true);
                        }
                    }, 64);
                }
            }
        });

        mSearch.setCallback(new SearchEditText.onEventListener() {
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
        mTipsListView.setVisibility(View.GONE);
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
