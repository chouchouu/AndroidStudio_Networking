package com.example.asm_fashion;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.fashionAdapter;
import modal.fashion;

public class FragmentHome extends Fragment {
    ArrayList<fashion> fashions = new ArrayList<>();

    final String fashionUrl = "http://192.168.1.5:8080/Asm_Fashion/getFashion.php";
    fashionAdapter adapter;
    ListView lv;
    List<fashion>searchlist;
    EditText edtSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lv = view.findViewById(R.id.lv);
        edtSearch=view.findViewById(R.id.search);
        fashions=new ArrayList<>();
        adapter=new fashionAdapter(getActivity(),R.layout.item_fashion,fashions);
        lv.setAdapter(adapter);
        getData(fashionUrl);

        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("id");


        //tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchlist = new ArrayList<>();
                if (s.length() == 0) {
                    searchlist = fashions;
                } else {
                    for (fashion item : fashions) {
                        if (item.getTenfs().toLowerCase().contains(s.toString().toLowerCase())
                                || item.getTenfs().toLowerCase().contains(s.toString().toLowerCase())) {
                            searchlist.add(item);
                        }
                    }
                }


                adapter = new fashionAdapter(getContext(),R.layout.item_fashion, searchlist);
                lv.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

return view;
    }
    private void getData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       // Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                    for (int i = 0;i<response.length(); i++){
                        try {
                        JSONObject object = response.getJSONObject(i);
                       fashions.add(new fashion(
                               object.getInt("Id"),
                               object.getString("Ten"),
                               object.getString("Gia"),
                               object.getString("Mota")

                       ));
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
