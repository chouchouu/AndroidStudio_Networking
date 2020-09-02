package com.example.asm_fashion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLanguage;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import modal.fashion;

public class Update_Fashion extends AppCompatActivity {
    EditText ed_name,ed_price,ed_de;
    Button btnedit;
    int id=0;
    String url="http://10.82.71.217:8080/Asm_Fashion/updateFashion.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__fashion);
        ed_name=findViewById(R.id.ed_name);
        ed_price=findViewById(R.id.ed_price);
        ed_de=findViewById(R.id.ed_de);
        btnedit=findViewById(R.id.btnedit);
        Intent intent=getIntent();
        fashion fs = (fashion) intent.getSerializableExtra("data");
        id=fs.getId();
        Toast.makeText(this,fs.getTenfs(),Toast.LENGTH_SHORT).show();
        ed_name.setText(fs.getTenfs());
        ed_price.setText(fs.getGiafs());
        ed_de.setText(fs.getMotafs());
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ten = ed_name.getText().toString().trim();
                String gia = ed_price.getText().toString().trim();
                String mota = ed_de.getText().toString().trim();
                if (ten.matches("") || gia.equals("")|| mota.length()==0) {
                    Toast.makeText(Update_Fashion.this, "Khong bo trong thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    UpdateFashion(url);
                    Intent i =new Intent(Update_Fashion.this,HomeScreen.class);
                    startActivity(i);
                }
            }
        });

    }
    private void UpdateFashion(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(Update_Fashion.this,"Cap nhat thanh cong",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Update_Fashion.this,"Loi",Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Fashion.this,"Loi, thu lai",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idFS",String.valueOf(id));
                params.put("tenFS",ed_name.getText().toString().trim());
                params.put("giaFS",ed_price.getText().toString().trim());
                params.put("motaFS",ed_de.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}