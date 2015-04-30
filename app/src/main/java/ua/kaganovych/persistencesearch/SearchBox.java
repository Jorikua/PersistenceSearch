package ua.kaganovych.persistencesearch;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class SearchBox extends CardView {
    public SearchBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.activity_main, this);
    }
}
