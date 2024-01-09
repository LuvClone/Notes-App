package com.example.finalnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    EditText email, password;
    Button LoginBtn;

    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        LoginBtn = (Button) findViewById(R.id.LoginBtn1);
        DB = new DBHelper(this);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = email.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals("")){
                    Toast.makeText(LogInActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = DB.checkpassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(LogInActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        intent.putExtra("email",user);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(LogInActivity.this, "Wrong email or password!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}