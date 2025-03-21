package com.example.javaexam4_5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemChangeListener {
    private RecyclerView recyclerView;
    private TextView totalPrice;
    private TextView emptyText;
    private CartAdapter adapter;
    private List<MenuItem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_cart);
        totalPrice = view.findViewById(R.id.total_price);
        emptyText = view.findViewById(R.id.empty_text);
        Button orderButton = view.findViewById(R.id.order_button);

        cartItems = new ArrayList<>(CartManager.getInstance().getCartItems().values());
        adapter = new CartAdapter(cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        updateTotalPrice();
        return view;
    }

    @Override
    public void onIncreaseQuantity(MenuItem item) {
        CartManager.getInstance().addToCart(item);
        adapter.notifyDataSetChanged();
        updateTotalPrice();
    }

    @Override
    public void onDecreaseQuantity(MenuItem item) {
        CartManager.getInstance().decreaseQuantity(item);
        adapter.notifyDataSetChanged();
        updateTotalPrice();
    }

@Override
public void onRemoveItem(MenuItem item) {

    int position = cartItems.indexOf(item);

    // Если товар найден в списке
    if (position != -1) {
        CartManager.getInstance().removeFromCart(item);
        cartItems.remove(position);

        adapter.notifyItemRemoved(position);

        updateTotalPrice();
    }
}

    private void updateTotalPrice() {
        totalPrice.setText("Итого: " + CartManager.getInstance().getTotalPrice() + " ₽");
        if (CartManager.getInstance().getCartItems().size() > 0) {
            emptyText.setVisibility(View.GONE);
        }
        else {
            emptyText.setVisibility(View.VISIBLE);
        }
    }

}
