package com.aman.changestreettest;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by aman on 9/23/16.
 */

public class FlipAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private Camera mCamera;

    public FlipAnimation(float fromDegrees,float toDegrees,float centerX, float centerY){
        mFromDegrees=fromDegrees;
        mToDegrees=toDegrees;
        mCenterX=centerX;
        mCenterY=centerY;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera=new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees=mFromDegrees;
        final float centerX=mCenterX;
        final float centerY=mCenterY;
        final Camera camera=mCamera;
        final Matrix matrix=t.getMatrix();
        float rotationDegree=fromDegrees+((mToDegrees-fromDegrees)*interpolatedTime);

        camera.save();
        camera.setLocation(0,0,-17f);
        camera.rotateY(rotationDegree);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
    }
}
