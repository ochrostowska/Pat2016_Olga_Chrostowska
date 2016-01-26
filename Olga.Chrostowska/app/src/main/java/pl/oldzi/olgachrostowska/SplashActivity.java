package pl.oldzi.olgachrostowska;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    private static final int SPLASH_TIME = 5000;

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
                Boolean showLogScreen = sharedPreferences.getBoolean("showLogScreen", true);
                Intent intent;
                if(!isOnline()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
                    alertDialog.setTitle("No connection");
                    alertDialog.setMessage("Do you want to go to wifi settings?");
                    alertDialog.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS), 0);
                                }
                            });
                    alertDialog.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                    Toast.makeText(SplashActivity.this, "No wifi", Toast.LENGTH_LONG).show();
                }
                else {
                    if(showLogScreen) {
                        intent = new Intent(SplashActivity.this, LogActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_TIME);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
