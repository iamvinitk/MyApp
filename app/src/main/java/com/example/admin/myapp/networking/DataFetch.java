package com.example.admin.myapp.networking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.myapp.objects.Product;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.myapp.utils.Constants.AUTHORIZATION;
import static com.example.admin.myapp.utils.Constants.BASE_URL;
import static com.example.admin.myapp.utils.Constants.GET_PRODUCTS_LIST;
import static com.example.admin.myapp.utils.Constants.PLACE_PRINT_ORDER;
import static com.example.admin.myapp.utils.Constants.PREFS_NAME;
import static com.example.admin.myapp.utils.Constants.TOKEN;

public class DataFetch {


//
//    public ArrayList<Sellers> getSellers(final int pageNumber, final Context context) {
//        ArrayList<Sellers> sellers = new ArrayList<>();
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        String url = BASE_URL + GET_SELLERS_LIST + "?page=" + pageNumber;
//        try {
//            response = Jsoup.connect(url).ignoreContentType(true).header(AUTHORIZATION, "Token " + prefs.getString(TOKEN, "")).execute().body();
//            System.out.println(response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            int resultCode = jsonObject.getInt("result_code");
//            if (resultCode == 1) {
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject seller = jsonArray.getJSONObject(i);
//                    JSONObject userName = seller.getJSONObject("user_name");
//                    sellers.add(new Sellers(seller.getString("seller_name"), String.valueOf(seller.getInt("ratings")), seller.getString("address"), seller.getString("seller_image"), userName.getString("username")));
//                }
//            }
//            System.out.println("*******");
//            System.out.println(sellers.size());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // Add the request to the RequestQueue.
//        System.out.println("Returning Seller Data" + sellers.size());
//        return sellers;
//    }


    public ArrayList<Product> getProducts(Context context, int pageNumber) {
        ArrayList<Product> productsArrayList = new ArrayList<>();
        String url;
        String response = "";
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        url = BASE_URL + GET_PRODUCTS_LIST + "?page=" + pageNumber;

        try {
            System.out.print(url);
            response = Jsoup.connect(url).ignoreContentType(true).header(AUTHORIZATION, "Token " + prefs.getString(TOKEN, "")).execute().body();
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            int resultCode = jsonObject.getInt("result_code");
            if (resultCode == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject product = jsonArray.getJSONObject(i);
                    productsArrayList.add(new Product(
                            product.getString("product_name"),
                            String.valueOf(product.getInt("product_id")),
                            product.getString("price"),
                            product.getString("url")
                    ));
                }
            }
            System.out.print(productsArrayList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add the request to the RequestQueue.
        System.out.println("Returning Product Data" + productsArrayList.size());
        return productsArrayList;
    }

    public void uploadFile(String file, String noc, Context context) {
        new FetchData().execute(file, context, noc);
    }

    private int uploadTask(Object filename, Object context, String noc) {
        try {
            System.out.print("\n*Starting Upload*\n");
            Context context1 = (Context) context;
            SharedPreferences prefs = context1.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            File file = new File(String.valueOf(filename));
            FileInputStream fs1 = new FileInputStream(file);
            System.out.print("\n*Read File*\n");

            String response = Jsoup.connect(BASE_URL + PLACE_PRINT_ORDER)
                    .ignoreContentType(true)
                    .data("file", "filename", fs1)
                    .data("noc", noc)
                    .header(AUTHORIZATION, "Token " + prefs.getString(TOKEN, ""))
                    .method(Connection.Method.POST)
                    .execute().body();
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt("result_code");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static class FetchData extends AsyncTask<Object, Object, Integer> {
        Context context;
        @Override
        protected Integer doInBackground(Object... objects) {
            DataFetch dataFetch = new DataFetch();
            context = (Context) objects[1];
            return dataFetch.uploadTask(objects[0], objects[1], (String) objects[2]);
        }

        @Override
        protected void onPostExecute(Integer response) {
            if (response == 1) {
                Toast.makeText(context, "File uploaded Successfully", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(context, "File didn't get Uploaded", Toast.LENGTH_SHORT).show();
        }
    }
}

