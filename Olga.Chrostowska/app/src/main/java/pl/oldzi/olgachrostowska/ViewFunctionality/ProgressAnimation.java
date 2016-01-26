package pl.oldzi.olgachrostowska.ViewFunctionality;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressAnimation {
    private AnimatorSet progressAnimatorSet;
    private ObjectAnimator fadeOut;
    private ProgressBar progressCircle;

    public ProgressAnimation(ProgressBar progressBar) {
        this.progressCircle = progressBar;
        fadeOut = ObjectAnimator.ofFloat(progressCircle, "alpha", 1f, .1f);
        fadeOut.setDuration(1000);
        progressAnimatorSet = new AnimatorSet();
        progressAnimatorSet.play(fadeOut);
    }
    public void resetAnimation() {
        progressCircle.setAlpha(1f);
        progressCircle.setVisibility(View.VISIBLE);
    }
    public void animate() {
        progressAnimatorSet.start();
        progressAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
