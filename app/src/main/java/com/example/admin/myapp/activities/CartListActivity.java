package com.example.admin.myapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapp.R;
import com.example.admin.myapp.objects.Product;
import com.example.admin.myapp.utils.StoreUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CartListActivity extends AppCompatActivity {
    private static Context mContext;
    static TextView total, payment;
    static int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mContext = CartListActivity.this;
        total = findViewById(R.id.acl_text_action_bottom1);
        payment = findViewById(R.id.acl_text_action_bottom2);
        final StoreUtils imageUrlUtils = new StoreUtils();
        ArrayList<Product> cartlistImageUri = imageUrlUtils.getCartListImageUri();
        for (Product p : cartlistImageUri) {
            sum += Integer.valueOf(p.getmProductPrice().substring(1));
        }
        total.setText("₹" + String.valueOf(sum));
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        });
        //Show cart layout based on items
        setCartLayout();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Product> mCartlistImageUri;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove, mLayoutEdit;
            TextView name, price;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                name = itemView.findViewById(R.id.lci_name);
                price = itemView.findViewById(R.id.lci_price);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Product> wishlistImageUri) {
            mCartlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
//            if (holder.mImageView.getController() != null) {
//                holder.mImageView.getController().onDetach();
//            }
//            if (holder.mImageView.getTopLevelDrawable() != null) {
//                holder.mImageView.getTopLevelDrawable().setCallback(null);
////                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
//            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Product product = mCartlistImageUri.get(position);

            try {
                holder.name.setText(product.getmProductName());
                holder.price.setText(product.getmProductPrice());
                Picasso.with(mContext).load(product.getmImageUrl()).into(holder.mImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StoreUtils imageUrlUtils = new StoreUtils();
                    imageUrlUtils.removeCartListImageUri(position);
                    sum = 0;
                    for (Product p : imageUrlUtils.getCartListImageUri()) {
                        sum += Integer.valueOf(p.getmProductPrice().substring(1));
                    }
                    total.setText("₹" + String.valueOf(sum));
                    notifyDataSetChanged();
                    //Decrease notification count

                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCartlistImageUri.size();
        }
    }

    protected void setCartLayout() {
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        layoutCartItems.setVisibility(View.VISIBLE);
        layoutCartPayments.setVisibility(View.VISIBLE);
    }
}

