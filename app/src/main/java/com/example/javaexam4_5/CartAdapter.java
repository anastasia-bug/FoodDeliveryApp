package com.example.javaexam4_5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<MenuItem> cartItems;
    private OnCartItemChangeListener listener;

    public CartAdapter(List<MenuItem> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        MenuItem item = cartItems.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice() + " ₽");
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        int imageResource = item.getDrawableIdByName();
        if (imageResource != 0) {
            holder.imageView.setImageResource(imageResource);
        }

        holder.increaseButton.setOnClickListener(v -> {
            listener.onIncreaseQuantity(item);
        });

        holder.decreaseButton.setOnClickListener(v -> {
            listener.onDecreaseQuantity(item);
        });

        holder.removeButton.setOnClickListener(v -> {
            listener.onRemoveItem(item);
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView imageView;
        ImageButton increaseButton, decreaseButton, removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            quantity = itemView.findViewById(R.id.item_quantity);
            imageView = itemView.findViewById(R.id.item_image);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }

    public interface OnCartItemChangeListener {
        void onIncreaseQuantity(MenuItem item);
        void onDecreaseQuantity(MenuItem item);
        void onRemoveItem(MenuItem item);
    }
}

