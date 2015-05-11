package ua.kaganovych.persistencesearch;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class CustomEditText extends EditText {

    public interface onEventListener {
        boolean onArrowDown();
        boolean onSearchSubmitted();
    }

    private onEventListener mCallback;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCallback(onEventListener mCallback) {
        this.mCallback = mCallback;
    }


    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            KeyEvent.DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getRepeatCount() == 0) {
                    state.startTracking(event, this);
                } else if (event.getAction() == KeyEvent.ACTION_UP
                        && !event.isCanceled() && state.isTracking(event)) {
                    if (mCallback != null) {
                        return mCallback.onArrowDown() || super.dispatchKeyEventPreIme(event);
                    }
                }
            }
        }

        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public void onEditorAction(int actionCode) {
        if (actionCode == EditorInfo.IME_ACTION_SEARCH || actionCode == EditorInfo.IME_ACTION_DONE
                || actionCode == EditorInfo.IME_ACTION_GO || actionCode == EditorInfo.IME_ACTION_NONE) {
            if (!TextUtils.isEmpty(getText().toString())) {
                mCallback.onSearchSubmitted();
            }
        }
    }
}
