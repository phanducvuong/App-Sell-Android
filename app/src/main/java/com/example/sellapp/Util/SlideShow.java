package com.example.sellapp.Util;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.sellapp.Config;
import com.example.sellapp.Model.SlideModel.ListSlide;
import com.example.sellapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SlideShow {

    private Animation mIn, mOut;
    private List<ListSlide> mListSlide;
    private Context mContext;
    private ViewFlipper mViewFlipper;

    public SlideShow(List<ListSlide> mListSlide, Context mContext, ViewFlipper mViewFlipper) {
        this.mListSlide = mListSlide;
        this.mContext = mContext;
        this.mViewFlipper = mViewFlipper;
        mIn = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right);
        mOut = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left);
    }

    public void showSlide() {

        for(int i = 0; i < mListSlide.size(); i++) {

            ImageView mImageSlide = new ImageView(mContext);
            mImageSlide.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Picasso.get()
                    .load(Config.URL + mListSlide.get(i).getmSlideImg())
                    .into(mImageSlide);

            mViewFlipper.addView(mImageSlide);
            mViewFlipper.startFlipping();
            mViewFlipper.setInAnimation(mIn);
            mViewFlipper.setOutAnimation(mOut);
            mViewFlipper.setFlipInterval(3000);
            mViewFlipper.setAutoStart(true);
        }
    }
}
