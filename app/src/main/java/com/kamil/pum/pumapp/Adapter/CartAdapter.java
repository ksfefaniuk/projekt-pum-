package com.kamil.pum.pumapp.Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kamil.pum.pumapp.Model.ProductImage;
import com.kamil.pum.pumapp.R;
import java.util.ArrayList;
import static com.kamil.pum.pumapp.View.CartActivity.grandTotal;
import static com.kamil.pum.pumapp.View.CartActivity.grandTotalplus;
import static com.kamil.pum.pumapp.View.CartActivity.temparraylist;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<ProductImage> cartModelArrayList;
    Context context;

    public CartAdapter(ArrayList<ProductImage> cartModelArrayList, Context context) {
        this.context = (Context) context;
        this.cartModelArrayList = cartModelArrayList;
    }


    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {

        holder.productCartPrice.setText(String.valueOf(cartModelArrayList.get(position).getTotalCash()));
        holder.productCartCode.setText(cartModelArrayList.get(position).getProductName());
        holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bag);
        requestOptions.error(R.drawable.bag);
        Log.d("imageurl", String.valueOf(cartModelArrayList.get(position).getProductImage()));
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cartModelArrayList.get(position).getProductImage()).into(holder.productCartImage);


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cartModelArrayList.size() == 1) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());
                    grandTotalplus = 0;
                    grandTotal.setText("cena:"+ String.valueOf(grandTotalplus)+"zł");
                }

                if (cartModelArrayList.size() > 0) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());
                    grandTotalplus = 0;
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
                    }

                    Log.d("cena", String.valueOf(grandTotalplus));
                    grandTotal.setText("cena:"+ String.valueOf(grandTotalplus)+"zł");

                } else {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ilosc i cena
        holder.cartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("cena", String.valueOf(cartModelArrayList.get(position).getStocks()));

                grandTotalplus = 0;
                holder.cartDecrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getProductQuantity());
                Log.d("ilosc", String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

                holder.cartIncrement.setEnabled(true);
                cartUpdateCounter += 1;

                cartModelArrayList.get(position).setProductQuantity((cartUpdateCounter));
                int cash = (Integer.parseInt(cartModelArrayList.get(position).getProductPrice()) * (cartModelArrayList.get(position).getProductQuantity()));

                holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

                cartModelArrayList.get(position).setTotalCash(cash);
                holder.productCartPrice.setText(String.valueOf(cash));


                for (int i = 0; i < temparraylist.size(); i++) {
                    grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
                }
                Log.d("suma", String.valueOf(grandTotalplus));
                grandTotal.setText("cena:"+ String.valueOf(grandTotalplus)+"zł");

            }

        });

        // odejmowanie ilosci i sumy
        holder.cartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grandTotalplus = 0;
                holder.cartIncrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getProductQuantity());
                Log.d("udejmowanie", String.valueOf(cartModelArrayList.get(position).getProductQuantity()));


                if (cartUpdateCounter == 1) {
                    holder.cartDecrement.setEnabled(false);
                    Toast.makeText(context, "Nie można kupić mniej niż jednego", Toast.LENGTH_SHORT).show();
                } else {
                    holder.cartDecrement.setEnabled(true);
                    cartUpdateCounter -= 1;
                    cartModelArrayList.get(position).setProductQuantity((cartUpdateCounter));
                    holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));
                    int cash = (Integer.parseInt(cartModelArrayList.get(position).getProductPrice()) * (cartModelArrayList.get(position).getProductQuantity()));

                    cartModelArrayList.get(position).setTotalCash(cash);
                    holder.productCartPrice.setText(String.valueOf(cash));
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
                    }

                    Log.d("Suma", String.valueOf(grandTotalplus));
                    grandTotal.setText("cena:"+ String.valueOf(grandTotalplus)+"zł");

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("koszyksize", String.valueOf(cartModelArrayList.size()));
        return cartModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productCartImage, cartIncrement, cartDecrement, deleteItem;
        TextView productCartCode, productCartPrice, productCartQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            productCartImage = itemView.findViewById(R.id.list_image_cart);
            deleteItem = itemView.findViewById(R.id.delete_item_from_cart);
            productCartCode = itemView.findViewById(R.id.product_cart_code);
            productCartPrice = itemView.findViewById(R.id.product_cart_price);
            productCartQuantity = itemView.findViewById(R.id.cart_product_quantity_tv);
            cartDecrement = itemView.findViewById(R.id.cart_decrement);
            cartIncrement = itemView.findViewById(R.id.cart_increment);


        }
    }


}
