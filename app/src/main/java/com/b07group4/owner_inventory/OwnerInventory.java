package com.b07group4.owner_inventory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b07group4.DBHandler.ProductManager;
import com.b07group4.DataModels.Product;
import com.b07group4.R;

import java.util.ArrayList;
import java.util.List;

public class OwnerInventory extends AppCompatActivity {

    private List<Product> products;
    private RecyclerView productsView;
    private ProductAdapter adapter;

    private ProductManager pm;

    private String username, storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_inventory);

        username = getIntent().getStringExtra("OWNER_NAME");
        storeName = getIntent().getStringExtra("STORE_NAME");

        pm = ProductManager.getInstance();
        products = new ArrayList<>();
        products.add(Product.getPlaceholder());

        productsView = findViewById(R.id.productList);
        adapter = new ProductAdapter(products);

        adapter.setOnEdit(pos -> {
            Intent i = new Intent(this, EditProduct.class);
            i.putExtra("OWNER_NAME", username);
            i.putExtra("STORE_NAME", storeName);
            i.putExtra("PRODUCT_ID", products.get(pos).getId());
            Log.d("DBG", "Product id: " + products.get(pos).getId());
            i.putExtra("PRODUCT_NAME", products.get(pos).getName());
            i.putExtra("PRODUCT_BRAND", products.get(pos).getBrand());
            i.putExtra("PRODUCT_PRICE", String.valueOf(products.get(pos).getPrice()));
            i.putExtra("PRODUCT_INFO", products.get(pos).getInfo());
            startActivity(i);
        });

        adapter.setOnDelete(pos -> {
            Log.d("DBG", "Deleteing item at position: " + pos);
        });

        productsView.setAdapter(adapter);
        productsView.setLayoutManager(new LinearLayoutManager(this));
        ((TextView)findViewById(R.id.store_name)).setText(storeName);

        pm.GetAll(l -> {
            products.clear();
            l.forEach(p -> {
                if (p.getOwner_id().equals(username))
                    products.add(p);
            });
            adapter.notifyDataSetChanged();
        });
    }

    public void onClickAdd(View v) {
        Intent intent = new Intent(this, CreateProduct.class);
        intent.putExtra("OWNER_NAME", username);
        intent.putExtra("STORE_NAME", storeName);
        startActivity(intent);
    }
}