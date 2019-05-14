package com.example.sd6501_assignment1_2180511.LoginRegister;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.SpannableString;
        import android.text.Spanned;
        import android.text.method.LinkMovementMethod;
        import android.text.style.ClickableSpan;
        import android.util.Log;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.sd6501_assignment1_2180511.DatabaseHandlerUsers;
        import com.example.sd6501_assignment1_2180511.R;

        import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    //debug
    private static final String TAG = "RegistrationActivity";

    //view variables
    private  EditText email, password, confirmPassword;
    private TextView returnToLogIn;
    private Button register;

    //data for log in activity
    HashMap<String, String> userDetails = new HashMap<>();
    protected int loginResultCode = 1;
    DatabaseHandlerUsers db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = new DatabaseHandlerUsers(this);
        findViews();

        setSpannableLogInString();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });
    }

    private void findViews() {
        email = findViewById(R.id.registration_et_username);
        password = findViewById(R.id.registration_et_password);
        confirmPassword = findViewById(R.id.registration_et_confirmPassword);
        returnToLogIn = findViewById(R.id.registration_tv_returnToLogin);
        register = findViewById(R.id.registration_btn_register);
    }

    private boolean checkUsername() {
        String userInput = email.getText().toString().trim();
        if (userInput.isEmpty()) {
            email.setError("Required");
            return false;
        } else if (email.length() > 20 ){
            email.setError("Username must be under 20 characters");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public boolean checkEmailIsValid() {
        String input = email.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            email.setError("Must be a valid email");
            return false;
        } else if(input.isEmpty()) {
            email.setError("Required");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean checkPassword() {
        String pword = password.getText().toString().trim();
        String confirmPWord = confirmPassword.getText().toString().trim();

        if (pword.isEmpty()) {
            password.setError("Required");
            return false;
        } else if (!pword.equals(confirmPWord)) {
            password.setError("Password must match");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean checkRetryPassword() {
        String pword = password.getText().toString().trim();
        String confirmPWord = confirmPassword.getText().toString().trim();

        if(confirmPWord.isEmpty()) {
            confirmPassword.setError("Required");
            return false;
        } else if(!confirmPWord.equals(pword)) {
            confirmPassword.setError("Password must match");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private void setSpannableLogInString() {
        SpannableString ss = new SpannableString("already have an account? SIGN IN");
        ClickableSpan cs  = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(cs, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        returnToLogIn.setText(ss);
        returnToLogIn.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void checkInput() {
        if(!checkEmailIsValid() | !checkPassword() | !checkRetryPassword() ) {
            return;
        }

        userDetails.put(email.getText().toString().trim(), confirmPassword.getText().toString().trim());
        Log.d(TAG, "checkInput: Creating a new user");

        long userID = db.insertUser("sample",
                                    confirmPassword.getText().toString().trim(),
                                    email.getText().toString().trim());
        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();

        Intent loginActivity = new Intent();
        loginActivity.putExtra("userID", userID);
        setResult(loginResultCode, loginActivity);
        finish();
    }
}