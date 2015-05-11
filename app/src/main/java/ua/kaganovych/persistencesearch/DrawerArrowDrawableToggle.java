package ua.kaganovych.persistencesearch;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Property;

public class DrawerArrowDrawableToggle extends DrawerArrowDrawable {

    public static final Property<DrawerArrowDrawableToggle, Float> PROGRESS = new Property<DrawerArrowDrawableToggle, Float>(Float.TYPE, "progress") {
        @Override
        public Float get(DrawerArrowDrawableToggle drawerArrowDrawableToggle) {
            return drawerArrowDrawableToggle.getPosition();
        }

        @Override
        public void set(DrawerArrowDrawableToggle object, Float value) {
            object.setPosition(value);
        }
    };

    private final Activity mActivity;

    public DrawerArrowDrawableToggle(Activity activity, Context themedContext) {
        super(themedContext);
        this.mActivity = activity;
    }

    public void setPosition(float position) {
        if(position == 1.0F) {
            this.setVerticalMirror(true);
        } else if(position == 0.0F) {
            this.setVerticalMirror(false);
        }

        super.setProgress(position);
    }

    @Override
    boolean isLayoutRtl() {
        return ViewCompat.getLayoutDirection(this.mActivity.getWindow().getDecorView()) == 1;
    }

    public float getPosition() {
        return super.getProgress();
    }
}
