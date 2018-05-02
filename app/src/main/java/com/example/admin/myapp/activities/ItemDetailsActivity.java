package com.example.admin.myapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.myapp.R;
import com.example.admin.myapp.objects.Product;
import com.example.admin.myapp.utils.StoreUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.admin.myapp.utils.Constants.AUTHORIZATION;
import static com.example.admin.myapp.utils.Constants.BASE_URL;
import static com.example.admin.myapp.utils.Constants.PREFS_NAME;
import static com.example.admin.myapp.utils.Constants.PRODUCT_DETAILS;
import static com.example.admin.myapp.utils.Constants.PRODUCT_ID;
import static com.example.admin.myapp.utils.Constants.PRODUCT_NAME;
import static com.example.admin.myapp.utils.Constants.PRODUCT_PRICE;
import static com.example.admin.myapp.utils.Constants.PRODUCT_URL;
import static com.example.admin.myapp.utils.Constants.TOKEN;

public class ItemDetailsActivity extends AppCompatActivity {
    Boolean wishlisted = false;
    String productId, productName, productPrice, productUrl;
    TextView textproductName, price, rating, seller, desc, addToCart, buynow;
    ImageView thumbnail, wishlist;
    StoreUtils storeUtils;
    Product product;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        LinearLayout action1, action3;
        if (getIntent() != null) {
            productId = getIntent().getStringExtra(PRODUCT_ID);
            productName = getIntent().getStringExtra(PRODUCT_NAME);
            productPrice = getIntent().getStringExtra(PRODUCT_PRICE);
            productUrl = getIntent().getStringExtra(PRODUCT_URL);

            thumbnail = findViewById(R.id.aid_image);
            textproductName = findViewById(R.id.aid_product_name);
            price = findViewById(R.id.aid_product_price);
            rating = findViewById(R.id.aid_ratings);
            seller = findViewById(R.id.aid_seller_name);
            desc = findViewById(R.id.aid_description);
            addToCart = findViewById(R.id.text_action_bottom1);
            buynow = findViewById(R.id.text_action_bottom2);
            wishlist = findViewById(R.id.aid_wishlist);
            action3 = findViewById(R.id.aid_layout_action3);
            storeUtils = new StoreUtils();
            product = new Product(productName, productId, productPrice, productUrl);

            textproductName.setText(productName);
            price.setText(productPrice);
            Picasso.with(getApplicationContext()).load(productUrl).into(thumbnail);
            Random r = new Random();
            rating.setText(String.valueOf(r.nextInt(6)) + "*");
            seller.setText("AZ Retailer");
            desc.setText(productName);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeUtils.addCartListImageUri(product);
                    Toast.makeText(ItemDetailsActivity.this, "Item added to cart.", Toast.LENGTH_SHORT).show();
                }
            });
            buynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ItemDetailsActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            action3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wishlisted) {
                        wishlisted = false;
                        wishlist.setImageResource(R.drawable.ic_action_fav_bdr);
                        Toast.makeText(getApplicationContext(), "Removed from Wishlist", Toast.LENGTH_SHORT).show();
                        storeUtils.removeWishlistImageUri(index);
                        index = 0;

                    } else {
                        wishlisted = true;
                        Toast.makeText(getApplicationContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();
                        wishlist.setImageResource(R.drawable.ic_action_fav);
                        storeUtils.addWishlistImageUri(product);
                        index = StoreUtils.wishlist.size()-1;
                    }
                }
            });
            //productDetail(productId, this);

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
