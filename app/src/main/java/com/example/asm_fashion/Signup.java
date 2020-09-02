package com.example.asm_fashion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Signup extends AppCompatActivity {

    TextView login;
    ImageView img_exit;
    EditText edt_name,edt_email, edt_pass,edt_repass;
    Button btn_cre;
    String registerUrl ="http://192.168.1.5:8080/Asm_Fashion/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login=findViewById(R.id.login);
        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.ed_email);
        edt_pass=findViewById(R.id.ed_pass);
        edt_repass=findViewById(R.id.edt_repass);
        btn_cre=findViewById(R.id.btnsignup);
        img_exit=findViewById(R.id.img_exit);
        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Signup.this,MainActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i =new Intent(Signup.this,Signin.class);
                startActivity(i);
            }
        });
        btn_cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,registerUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        RegisterProcessing(response);
                        Log.d("data",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signup.this, "Volley error", Toast.LENGTH_SHORT).show();
                        Log.d("loi",error.toString());
                    }
                }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();

                        param.put("name",edt_name.getText().toString());
                        param.put("email",edt_email.getText().toString());
                        param.put("password",edt_pass.getText().toString());
                        param.put("tag","register");

                        return param;
                    }
                };
                Volley.newRequestQueue(Signup.this).add(stringRequest);
            }
        });
    }

    private void RegisterProcessing(String response) {
        String result = "";
        try {
            JSONObject jsonobject = new JSONObject(response);
            result = jsonobject.getString("thanhcong");

            if(Integer.parseInt(result)==1)//thanh cong
            {
                Intent i =new Intent(Signup.this,Signin.class);
                startActivity(i);
                Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();

            }
            else //that bai
            {
                Toast.makeText(this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}