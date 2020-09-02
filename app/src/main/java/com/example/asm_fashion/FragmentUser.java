package com.example.asm_fashion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import modal.user;

public class FragmentUser extends Fragment {
    String userUrl =  "http://10.82.71.217:8080/Asm_Fashion/get_user.php";
    String updateUrl =  "http://10.82.71.217:8080/Asm_Fashion/updatePass.php";
    TextView txtuser,txtpassword;
    EditText edtPass;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        txtuser=view.findViewById(R.id.txtuser);
        txtpassword=view.findViewById(R.id.txtpassword);
        edtPass = view.findViewById(R.id.edtchangepass);
        btn = view.findViewById(R.id.btn);
        Intent intent = getActivity().getIntent();
        final String id = intent.getStringExtra("id");


       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i =new Intent(getActivity(),Signin.class);
               startActivity(i);
               RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
               StringRequest stringRequest1 = new StringRequest(Request.Method.POST, updateUrl, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       if (!response.isEmpty()){
                           try {
                               JSONObject jsonobject = new JSONObject(response);
                               String pass = jsonobject.getString("password");
                               edtPass.setText(pass);

                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                       }else {

                       }

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.d("gggg",error.getMessage());

                   }
               }
               ){
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String, String> params = new HashMap<>();
                       params.put("id",String.valueOf(id));
                       params.put("password",edtPass.getText().toString().trim());
                       return params;
                   }
               };

               requestQueue1.add(stringRequest1);


           }
       });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, userUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        String username = jsonobject.getString("name");
                        String name = jsonobject.getString("email");
                        String pass = jsonobject.getString("password");

                        txtuser.setText(name);
                        edtPass.setText(pass);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gggg",error.getMessage());

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(id));
                Log.d("loi",params.get("id"));

                return params;
            }
        };

        requestQueue.add(stringRequest);




        return view;
    }
}
