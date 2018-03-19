package com.example.admin.myapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.myapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.admin.myapp.utils.Constants.AUTHORIZATION;
import static com.example.admin.myapp.utils.Constants.BASE_URL;
import static com.example.admin.myapp.utils.Constants.PREFS_NAME;
import static com.example.admin.myapp.utils.Constants.PRODUCT_DETAILS;
import static com.example.admin.myapp.utils.Constants.PRODUCT_ID;
import static com.example.admin.myapp.utils.Constants.TOKEN;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        String productId;
        if (getIntent() != null) {
            productId = getIntent().getStringExtra(PRODUCT_ID);
            productDetail(productId, this);
        }

    }

    private void productDetail(String productId, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + PRODUCT_DETAILS + "?q=" + productId;


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            TextView productName, price, rating, seller, desc;
                            ImageView thumbnail;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("result_code") == 1) {
                                thumbnail = findViewById(R.id.aid_image);
                                productName = findViewById(R.id.aid_product_name);
                                price = findViewById(R.id.aid_product_price);
                                rating = findViewById(R.id.aid_ratings);
                                seller = findViewById(R.id.aid_seller_name);
                                desc = findViewById(R.id.aid_description);
                                productName.setText(jsonObject.getString("product_name"));
                                price.setText("Rs. " + jsonObject.getString("product_price"));
                                rating.setText(jsonObject.getString("rating") + " *");
                                seller.setText(jsonObject.getString("seller"));
                                desc.setText(jsonObject.getString("details"));
                                thumbnail.setImageURI(Uri.parse(jsonObject.getString("image_url")));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Display Error Toast
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {

            public Map<String, String> getHeaders() {
                SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                params.put(AUTHORIZATION, "Token " + prefs.getString(TOKEN, ""));
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
