package com.example.asm_fashion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

        Button btn_signin,btn_signup;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                btn_signin=findViewById(R.id.btn_đn);
                btn_signup=findViewById(R.id.btn_đk);

                btn_signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent i =new Intent(MainActivity.this,Signin.class);
                                startActivity(i);
                        }
                });
                btn_signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent i =new Intent(MainActivity.this,Signup.class);
                                startActivity(i);
                        }
                });
        }
        }