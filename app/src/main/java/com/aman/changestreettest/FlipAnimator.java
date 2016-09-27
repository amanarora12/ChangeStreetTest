package com.aman.changestreettest;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 9/23/16.
 */

public class FlipAnimator {
    private final int DURATION=185;

    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private List<String> mListItemNames;
    private List<View> views;
    private LayoutInflater inflater;
    private FlipAnimatorListener listener;
    private int mHeight;
    private int mWidth;
    public FlipAnimator(Context context,DrawerLayout drawerLayout,List<String> listItemNames){
        mContext=context;
        mDrawerLayout=drawerLayout;
        mListItemNames=listItemNames;
        inflater=LayoutInflater.from(mContext);
        views=new ArrayList<>();
        listener= (FlipAnimatorListener) mContext;
    }

    public void showMenu(){
        setViewsClickable(false);
        views.clear();
        final int listSize=mListItemNames.size();
        for(int i=0;i<listSize;i++){
            View menuItem=inflater.inflate(R.layout.menu_item,null);
            menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideMenu();
                }
            });
            ((TextView)menuItem.findViewById(R.id.menu_item_text)).setText(mListItemNames.get(i));
            menuItem.setVisibility(View.GONE);
            menuItem.setEnabled(false);
            views.add(menuItem);
            listener.addViewToListContainer(menuItem);
            final double position=i;
            double delay=3 * DURATION * (position/listSize);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(position<views.size()){
                        animateShowMenuItem((int) position);
                    }
                    if(position==views.size()-1){
                        setViewsClickable(true);
                    }
                }
            }, (long) delay);
        }
    }

    public void hideMenu(){
        setViewsClickable(false);
        int size=mListItemNames.size();
        for(int i=size;i>=0;i--){
            final double position=i;
            double delay=2.5 * DURATION * (position/size);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(position<views.size()){
                        animateHideMenuItem((int) position);
                    }
                }
            }, (long) delay);
        }
    }

    private void animateShowMenuItem(int pos){
        final View view = views.get(pos);
        view.setVisibility(View.VISIBLE);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeight=view.getHeight();
                mWidth=view.getWidth();
                //Log.e("AMAN",view.getHeight()+" "+view.getWidth());
            }
        });
        //view.setPivotY(mHeight);
        //view.setPivotX(mWidth);
        FlipAnimation flip=new FlipAnimation(90,0,mWidth,mHeight/2);
        flip.setDuration(DURATION);
        flip.setFillAfter(true);
        flip.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(flip);

        //Log.e("AMAN",view.getHeight()+" "+view.getWidth());
//
//        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotationY", 90.0f, 0.0f);
//        animation.setDuration(300);
//        //animation.setRepeatCount(ObjectAnimator.INFINITE);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animation.start();

    }

    private void animateHideMenuItem(final int pos){
        final View view =views.get(pos);
        FlipAnimation flip=new FlipAnimation(0,90,315f,268/2);
        //Log.e("NAMA",view.getWidth()+" "+view.getHeight());
        flip.setDuration(DURATION);
        flip.setFillAfter(true);
        flip.setInterpolator(new AccelerateDecelerateInterpolator());
        flip.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);
                if(pos==views.size()-1){
                    listener.enableHomeButton();
                    mDrawerLayout.closeDrawers();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(flip);
    }

    private void setViewsClickable(boolean clickable){
        for(View view:views){
            view.setEnabled(clickable);
        }
    }

    public interface FlipAnimatorListener{
        void addViewToListContainer(View view);
        void disableHomeButton();
        void enableHomeButton();
    }
}
