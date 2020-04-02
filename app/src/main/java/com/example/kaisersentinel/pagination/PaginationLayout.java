package com.example.kaisersentinel.pagination;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class PaginationLayout extends LinearLayout {

    private int mPageActive = 0;
    private HorizontalScrollView mScroll;
    private LinearLayout mBar;

    public PaginationLayout(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);

        // creates the class that will control the gestures and apply it in the
        // scroll
        final GestureDetector mGestureDetector = new GestureDetector(new MySimpleOnGestureListener());

        mScroll = new HorizontalScrollView(context);
        mScroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mScroll.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (mGestureDetector.onTouchEvent(event)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        // creates Previous and Next buttons
        Button btnPrevious = new Button(getContext());
        btnPrevious.setLayoutParams(new LayoutParams(150, LayoutParams.FILL_PARENT));
        btnPrevious.setText("Previous");
        btnPrevious.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                previous();
            }
        });

        Button btnNext = new Button(getContext());
        btnNext.setLayoutParams(new LayoutParams(150, LayoutParams.FILL_PARENT));
        btnNext.setText("Next");
        btnNext.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                next();
            }
        });

        // bar that include the buttons
        mBar = new LinearLayout(getContext());
        mBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        mBar.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        mBar.setBackgroundColor(Color.GRAY);
        mBar.addView(btnPrevious);
        mBar.addView(btnNext);
        mBar.setVisibility(LinearLayout.INVISIBLE);

        // add bar and scroll in the super (LinearLayout)
        super.addView(mBar);
        super.addView(mScroll);
    }

    /**
     * All that the user include is added in the scroll
     */
    @Override
    public void addView(View child) {
        mScroll.addView(child);
    }

    /**
     * Controls if the top bar should appear or not
     */
    @Override
    protected void onSizeChanged(int arg0, int arg1, int arg2, int arg3) {
        super.onSizeChanged(arg0, arg1, arg2, arg3);
        View chield = mScroll.getChildAt(0);
        if (chield != null) {
            if (chield.getMeasuredWidth() > getWidth()) {
                mBar.setVisibility(LinearLayout.VISIBLE);
            } else {
                mBar.setVisibility(LinearLayout.INVISIBLE);
            }
        }
    }

    /**
     * does the effect "back a page"
     */
    public void previous() {
        mPageActive = (mPageActive > 0) ? mPageActive - 1 : 0;
        mScroll.smoothScrollTo(mPageActive * mScroll.getWidth(), 0);
    }

    /**
     * does the effect "forward a page"
     */
    public void next() {
        int pageWidth = mScroll.getWidth();
        int nextPage = (mPageActive + 1) * pageWidth;
        if (nextPage - mScroll.getScrollX() <= pageWidth) {
            mScroll.smoothScrollTo(nextPage, 0);
            mPageActive++;
        } else {
            mScroll.smoothScrollTo(mScroll.getScrollX(), 0);
        }
    }

    /**
     * Private class that controls the gestures, forward or back a page.
     */
    private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1 != null && e2 != null) {
                if (e1.getX() - e2.getX() > 0) {
                    // forward...
                    next();
                    return true;
                } else if (e2.getX() - e1.getX() > 0) {
                    // back...
                    previous();
                    return true;
                }
            }

            return false;
        }
    }
}
