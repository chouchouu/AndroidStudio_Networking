package com.example.asm_fashion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {

    Button btn_signin,btn_signup;
    ImageView img_cloose;
    EditText edt_mail, edt_pas;
    String loginUrl = "http://192.168.1.5:8080/Asm_Fashion/index.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        btn_signin =findViewById(R.id.btn_signin);
        btn_signup=findViewById(R.id.btn_signup);
        edt_mail=  findViewById(R.id.edt_mail);
        img_cloose=findViewById(R.id.img_cloose);
        img_cloose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Signin.this,MainActivity.class);
                startActivity(i);
            }
        });

        edt_pas=findViewById(R.id.edt_pas);



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signin.this,Signup.class);
                startActivity(i);
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringrequest = new StringRequest(
                        Request.Method.POST,loginUrl,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LoginProcessing(response);
                        Log.d("data",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signin.this, "Volley error" + error, Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());

                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("email", edt_mail.getText().toString());
                        param.put("password", edt_pas.getText().toString());
                        param.put("tag", "login");
                        return param;
                    }
                };
                Volley.newRequestQueue(Signin.this).add(stringrequest);
            }
        });

    }
    void LoginProcessing(String response){
        String result="";
        String name = "";
        try {
            JSONObject jsonobject = new JSONObject(response);
            result = jsonobject.getString("thanhcong");
            //doc tat ca du lieu tu json bo vao ArrayList
            if(Integer.parseInt(result)==1)//thanh cong
            {
                JSONObject user = jsonobject.getJSONObject("user");
                Intent intent = new Intent(this,HomeScreen.class);
                intent.putExtra("id",user.getString("id"));
                startActivity(intent);
            }
            else //that bai
            {
                Log.d("login","Lỗi đăng nhập");
                Toast.makeText(this, "Chưa nhập email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}