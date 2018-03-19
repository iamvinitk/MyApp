package com.example.admin.myapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapp.R;
import com.example.admin.myapp.activities.MainActivity;
import com.example.admin.myapp.adapters.ProductAdapter;
import com.example.admin.myapp.networking.DataFetch;
import com.example.admin.myapp.objects.Product;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private ArrayList<Product> productsList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private int counter = 0;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_product, container, false);
        counter = 1;
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productAdapter = new ProductAdapter(productsList, getContext());
        load();
        productAdapter.setLoadMoreListener(new ProductAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                    }
                });
            }
        });
        recyclerView.setAdapter(productAdapter);

    }

    private void load() {
        new FetchProductData().execute(getContext(), counter++);
    }

    private void loadMore() {
        new FetchProductData().execute(getContext(), counter++);
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchProductData extends AsyncTask<Object, Object, ArrayList<Product>> {

        @Override
        protected ArrayList<Product> doInBackground(Object[] objects) {
            DataFetch dataFetch = new DataFetch();

            return dataFetch.getProducts((Context) objects[0], (int) objects[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<Product> response) {
            if (response.size() == 0) {
                productAdapter.setMoreDataAvailable(false);
            } else if (response.size() > 0) {
                productsList.addAll(response);
                System.out.print("No. of products:" + productsList.size());
                productAdapter.notifyDataChanged();
            }
        }
    }
}
