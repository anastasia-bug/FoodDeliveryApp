package com.example.javaexam4_5;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuDetailFragment extends Fragment {

    private ImageView itemImage;
    private TextView itemName;
    private TextView itemWeightCalories;
    private TextView itemPrice;
    private TextView itemDescription;
    private TextView itemQuantity;

    private Button btnAdd;
    private Button btnRemove;
    private LinearLayout quantityLayout;
    private ImageButton btnIncrease;
    private ImageButton btnDecrease;

    private CartManager cartManager;
    private MenuItem menuItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_detail, container, false);

        itemImage = rootView.findViewById(R.id.item_image);
        itemName = rootView.findViewById(R.id.item_name);
        itemWeightCalories = rootView.findViewById(R.id.item_weight_calories);
        itemPrice = rootView.findViewById(R.id.item_price);
        itemDescription = rootView.findViewById(R.id.item_description);

        btnAdd = rootView.findViewById(R.id.btn_add);
        btnRemove = rootView.findViewById(R.id.btn_remove);
        quantityLayout = rootView.findViewById(R.id.quantity_layout);
        itemQuantity = rootView.findViewById(R.id.item_quantity);
        btnIncrease = rootView.findViewById(R.id.btn_increase);
        btnDecrease = rootView.findViewById(R.id.btn_decrease);

        ImageButton backButton = rootView.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        cartManager = CartManager.getInstance();

        menuItem = getArguments().getParcelable("menu_item");

        if (menuItem != null) {
            int imageResource = menuItem.getDrawableIdByName();
            if (imageResource != 0) {
                itemImage.setImageResource(imageResource);
            }

            itemName.setText(menuItem.getName());
            itemWeightCalories.setText(menuItem.getWeight() + " • " + menuItem.getCalories() + " ккал");
            itemPrice.setText(String.format("%.2f руб.", menuItem.getPrice()));
            itemDescription.setText(menuItem.getDescription());

            int quantityInCart = cartManager.getItemQuantity(menuItem);
            updateUI(quantityInCart);
        }

        btnAdd.setOnClickListener(v -> {
            cartManager.addToCart(menuItem);
            updateUI(cartManager.getItemQuantity(menuItem));
        });

        btnRemove.setOnClickListener(v -> {
            cartManager.removeFromCart(menuItem);
            updateUI(0);
        });

        btnIncrease.setOnClickListener(v -> {
            cartManager.addToCart(menuItem);
            updateUI(cartManager.getItemQuantity(menuItem));
        });

        btnDecrease.setOnClickListener(v -> {
            cartManager.decreaseQuantity(menuItem);
            updateUI(cartManager.getItemQuantity(menuItem));
        });

        return rootView;
    }

    private void updateUI(int quantity) {
        if (quantity > 0) {
            btnAdd.setVisibility(View.GONE);
            btnRemove.setVisibility(View.VISIBLE);
            quantityLayout.setVisibility(View.VISIBLE);
            itemQuantity.setText(String.valueOf(quantity));
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnRemove.setVisibility(View.GONE);
            quantityLayout.setVisibility(View.GONE);
        }
    }
}
