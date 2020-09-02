package com.example.asm_fashion;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modal.fashion;

import static android.app.Activity.RESULT_OK;

public class FragmentAdd extends Fragment {
    ArrayList<fashion> fashions = new ArrayList<>();
    Button btnAdd;
    TextView tv_choose, btn_choose;
    final String fashionUrl = "http://10.82.71.217:8080/Asm_Fashion/CreateFahion.php";
    String url = "http://10.82.71.217:8080/Asm_Fashion/upload.php";
    public static final String UPLOAD_KEY = "image";
    private Bitmap bitmap;
    private Uri filePath;
    private ImageView img_upload;
    private int PICK_IMAGE_REQUEST = 1;
    EditText edt_name, edt_price, edt_de;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        btnAdd = view.findViewById(R.id.btnAdd);
        edt_name = view.findViewById(R.id.edt_name);
        btn_choose = view.findViewById(R.id.btn_choose);
        edt_price = view.findViewById(R.id.edt_price);
        edt_de = view.findViewById(R.id.edt_de);
        tv_choose = view.findViewById(R.id.tv_upload);
        img_upload = view.findViewById(R.id.img_upload);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, fashionUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       ProductProgessing(response);
                        Log.d("data", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Volley error" + error, Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                }
                )
                {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("tenfs", edt_name.getText().toString());
                        param.put("giafs", edt_price.getText().toString());
                        param.put("motafs", edt_de.getText().toString());
                        return param;
                    }
                };
                Volley.newRequestQueue(getActivity()).add(stringRequest);
            }
        });

        //Upload anh
        tv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });


        return view;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              loading.dismiss();

                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                //data.put("image",get(filePath));

                String result = rh.postRequest(url,data);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    //Lay
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                img_upload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ProductProgessing(String response) {
        String result = "";
        try {
            JSONObject jsonobject = new JSONObject(response);
            result = jsonobject.getString("thanhcong");

            if (Integer.parseInt(result) == 1)//thanh cong
            {
                Intent i =new Intent(getActivity(),HomeScreen.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();

            } else //that bai
            {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
