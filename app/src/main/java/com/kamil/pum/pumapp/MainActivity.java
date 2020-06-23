package com.kamil.pum.pumapp;
import com.kamil.pum.pumapp.Adapter.ProductAdapter;
import com.kamil.pum.pumapp.Model.ProductModel;
import com.kamil.pum.pumapp.View.CartActivity;
import com.kamil.pum.pumapp.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductAdapter.CallBackUs, ProductAdapter.HomeCallBack {

    public static ArrayList<ProductModel> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    ProductAdapter productAdapter;
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProduct();
        productAdapter = new ProductAdapter(arrayList, this, this);
        productRecyclerView = findViewById(R.id.product_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(gridLayoutManager);
        productRecyclerView.setAdapter(productAdapter);

    }


    private void addProduct() {

        ProductModel productModel2 = new ProductModel("Sajgonki", "20", "10", R.drawable.springrolls);
        arrayList.add(productModel2);
        ProductModel productModel3 = new ProductModel("Burger", "20", "20", R.drawable.burger);
        arrayList.add(productModel3);
        ProductModel productModel12 = new ProductModel("Kurczak", "15", "10", R.drawable.chicken);
        arrayList.add(productModel12);
        ProductModel productModel23 = new ProductModel("MC Zestaw", "6", "10", R.drawable.colddrink);
        arrayList.add(productModel23);
        ProductModel productModel4 = new ProductModel("Pierożki", "24", "20", R.drawable.momos);
        arrayList.add(productModel4);
        ProductModel productModel14 = new ProductModel("Noodle z mięsem", "21", "10", R.drawable.noodles);
        arrayList.add(productModel14);
        ProductModel productModel25 = new ProductModel("Pizza", "32", "10", R.drawable.pizza);
        arrayList.add(productModel25);
        ProductModel productModel5 = new ProductModel("Tortilla + 2 dipy", "16", "20", R.drawable.roll);
        arrayList.add(productModel5);
        ProductModel productModel16 = new ProductModel("Tosty", "10", "10", R.drawable.sandwich);
        arrayList.add(productModel16);

    }

    @Override
    public void addCartItemView() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.cart_action:
                if (cart_count < 1) {
                    Toast.makeText(this, "Nie ma produktów w koszyku!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, CartActivity.class));
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }
}
