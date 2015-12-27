package pl.oldzi.olgachrostowska;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SplashActivity.this, "Activity stopped", Toast.LENGTH_LONG).show();
                handler.removeCallbacksAndMessages(null);
            }
        });
        ImageView snowflake = (ImageView) findViewById(R.id.imageView);
        RotateAnimation rotate = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setDuration(3500);
        snowflake.startAnimation(rotate);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String activityToLaunch = sharedPreferences.getString(getString(R.string.activity_to_launch), "LOG_ACTIVITY");
                Intent intent;
                switch(activityToLaunch){
                    case "MAIN_ACTIVITY": intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case "LOG_ACTIVITY": intent = new Intent(SplashActivity.this, LogActivity.class);
                        startActivity(intent);
                        break;
                    default: break;}

                finish();
            }
        }, SPLASH_TIME);

    }

}
