package com.smallnew.xiuxiu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class XiuActivity extends AppCompatActivity {
    private ImageView mBtnXiu;
    private RelativeLayout rlXiu;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(this, R.raw.xiu);
        setContentView(R.layout.activity_xiu);
        mBtnXiu = (ImageView) this.findViewById(R.id.btn_xiu);
        rlXiu = (RelativeLayout) this.findViewById(R.id.rl_xiu);
        mBtnXiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mp.isPlaying()) {
                    mp.start();
                }
                waveAnim(XiuActivity.this, rlXiu);
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (mp != null)
            mp.release();
        super.onDestroy();
    }

    private void waveAnim(Context paramContext, ViewGroup paramViewGroup) {
        if ((paramContext == null) || (paramViewGroup == null))
            return;
        ImageView localImageView = new ImageView(paramContext);
        localImageView.setImageResource(R.drawable.wave_xiu);
        RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        localLayoutParams.width = this.mBtnXiu.getWidth();
        localLayoutParams.height = this.mBtnXiu.getHeight();
        localImageView.setLayoutParams(localLayoutParams);
        localImageView.setX(this.mBtnXiu.getX());
        localImageView.setY(this.mBtnXiu.getY());
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(localImageView, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("alpha", new float[]{1.0F, 0.0F}), PropertyValuesHolder.ofFloat("scaleX", new float[]{1.2F, 4.0F}), PropertyValuesHolder.ofFloat("scaleY", new float[]{1.2F, 4.0F})});
        localObjectAnimator.setDuration(1000L);
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.playTogether(new Animator[]{localObjectAnimator});
        localAnimatorSet.addListener(new XiuActivity.WaveAnimatarListener(localImageView, paramViewGroup));
        paramViewGroup.addView(localImageView, 0);
        localImageView.setVisibility(0);
        localAnimatorSet.setInterpolator(new LinearInterpolator());
        localAnimatorSet.start();
    }

    private static class WaveAnimatarListener implements Animator.AnimatorListener {
        private View mAniView;
        private ViewGroup mViewGroup;

        public WaveAnimatarListener(View view, ViewGroup viewGroup) {
            mAniView = view;
            mViewGroup = viewGroup;
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mAniView != null && mViewGroup != null) {

                mViewGroup.post(new Runnable() {
                    @Override
                    public void run() {
                        mViewGroup.removeView(mAniView);
                    }
                });
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
