package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asm_fashion.FragmentHome;
import com.example.asm_fashion.HomeScreen;
import com.example.asm_fashion.R;
import com.example.asm_fashion.Update_Fashion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modal.fashion;

public class fashionAdapter extends BaseAdapter {
    String deleteUrl = "http://10.82.71.217:8080/Asm_Fashion/deleteFashion.php";
    Context context;
    int layout;
     List<fashion> fashions;

    public fashionAdapter(Context context, int layout, List<fashion> fashions) {
        this.context = context;
        this.layout = layout;
        this.fashions = fashions;
    }




    @Override
    public int getCount() {
        return fashions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class  ViewHolder{
        TextView txt_name,txt_price,txt_de,imgde,imged;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txt_name =view.findViewById(R.id.txt_name);
            holder.txt_price=view.findViewById(R.id.txt_price);
            holder.txt_de=view.findViewById(R.id.txt_de);
            holder.imgde =view.findViewById(R.id.img_bin);
            holder.imged=view.findViewById(R.id.img_edit);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final fashion fs = fashions.get(i);
        holder.txt_name.setText(fs.getTenfs());
        holder.txt_price.setText("$"+""+fs.getGiafs());
        holder.txt_de.setText(fs.getMotafs());
        holder.imged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context, Update_Fashion.class);
                i.putExtra("data",fs);
                context.startActivity(i);
            }
        });
        holder.imgde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        xoaFashion(fs.getTenfs(), fs.getId());
            }
        });
        return view;
    }
    private void xoaFashion(String ten, final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Xoa nha!!!");
        dialogXoa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteFs(id);
                Intent intent =new Intent(context, HomeScreen.class);
                context.startActivity(intent);
            }
        });
        dialogXoa.setNegativeButton("Wait, no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }
    public void deleteFs(final int idfs){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if (response.trim().equals("success")){
                        Toast.makeText(context,"Xoa thanh cong",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"Loi",Toast.LENGTH_SHORT).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Loi, thu lai",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idfashion",String.valueOf(idfs));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
