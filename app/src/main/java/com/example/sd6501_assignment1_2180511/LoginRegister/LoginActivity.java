package com.example.sd6501_assignment1_2180511.LoginRegister;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sd6501_assignment1_2180511.DatabaseHandlerUsers;
import com.example.sd6501_assignment1_2180511.MainActivity;
import com.example.sd6501_assignment1_2180511.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //debugging
    private static final String TAG = "LoginActivity";

    //view variables
    private EditText email, password;
    private Button loginBtn;
    private TextView registerText, incorrectLogin;

    //User database
    private DatabaseHandlerUsers db;

    //Registration
//    HashMap<String, String> logInInfo = new HashMap<>();
    ArrayList<UserClass> users;
    protected static int registrationRequestCode = 1;
    long newUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandlerUsers(LoginActivity.this);

        findViews();

//        get all users in database
        Log.d(TAG, "onCreate: Trying to retrieve users");
        users = db.getAllUsersFromDB();

        sendToRegistration();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Trying to save user details");
                checkLogin(email.getText().toString().trim(), password.getText().toString().trim());
            }
        }); //End log in
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == registrationRequestCode) {

            registerText.setText("");

            newUserID = data.getLongExtra("userID", 0);
            UserClass registeredUser = db.getUserByID(newUserID);
            users.add(registeredUser);

            clearTextFields();
        }
    }

    private void findViews() {
        email = findViewById(R.id.login_et_username);
        password = findViewById(R.id.login_et_password);
        loginBtn = findViewById(R.id.login_btn_signin);
        registerText = findViewById(R.id.login_tv_register);
        incorrectLogin = findViewById(R.id.login_tv_incorrectLogin);
    }

    private void sendToRegistration() {
        SpannableString ss = new SpannableString("No account? REGISTER");
        ClickableSpan cs  = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, registrationRequestCode);
            }
        };
        ss.setSpan(cs, 12, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerText.setText(ss);
        registerText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void checkLogin(String userName, String userPassword) {
        for (UserClass user : users) {
            if((user.getEmail().equals(userName)) && (user.getPassword().equals(userPassword))) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        incorrectLogin.setText("Incorrect login information");
    }

    private void clearTextFields() {
        email.getText().clear();
        password.getText().clear();
        incorrectLogin.setText("");
    }
}