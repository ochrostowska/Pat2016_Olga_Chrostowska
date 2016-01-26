package pl.oldzi.olgachrostowska;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.logBut);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMail() && isPass()) {
                   Toast.makeText(LogActivity.this, "You're logged in", Toast.LENGTH_SHORT).show();
                   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                   sharedPreferences.edit().putBoolean("showLogScreen", false).apply();
                   Intent intent = new Intent(LogActivity.this, MainActivity.class);
                   startActivity(intent);
                   finish();
               } else {
                   TextView passFlake = (TextView) findViewById(R.id.passFlake);
                   TextView emailFlake = (TextView) findViewById(R.id.emailFlake);
                   passFlake.setVisibility(View.INVISIBLE);
                   emailFlake.setVisibility(View.INVISIBLE);
                   Toast.makeText(LogActivity.this, "You've entered invalid data", Toast.LENGTH_SHORT).show();
                   if (!isPass() && !isMail()) {
                       emailFlake.setVisibility(View.VISIBLE);
                       passFlake.setVisibility(View.VISIBLE);
                   } else if (!isPass()) {
                       passFlake.setVisibility(View.VISIBLE);
                   } else if (!isMail()) {
                       emailFlake.setVisibility(View.VISIBLE);
                   }
               }
            }
        });
    }
    public boolean isMail() {
        EditText emailET = (EditText) findViewById(R.id.emailEditText);
        String userEmailInput = emailET.getText().toString().trim();
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]+$");
        // zaczyna się od [a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-] potem mamy @, potem [a-zA-Z0-9.-], potem ".", potem [a-zA-Z0-9]
        Matcher emailMatcher = emailPattern.matcher(userEmailInput);
        return emailMatcher.matches();
    }
    public boolean isPass() {
        EditText passwordET = (EditText) findViewById(R.id.passwordEditText);
        String userPassInput = passwordET.getText().toString().trim();
        Pattern passPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[^\\s]{8,}$");
        // minimum raz [0-9], minimum raz [a-z], minimum raz [A-Z], od 8 znaków
        Matcher passMatcher = passPattern.matcher(userPassInput);
        return passMatcher.matches();
    }
}