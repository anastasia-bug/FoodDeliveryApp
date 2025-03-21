package com.example.javaexam4_5;

import java.util.HashMap;

public class CartManager {
    private static CartManager instance;
    private HashMap<Integer, MenuItem> cartItems;

    public CartManager() {
        cartItems = new HashMap<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public HashMap<Integer, MenuItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(MenuItem item) {
        if (cartItems.containsKey(item.getId())) {
            cartItems.get(item.getId()).setQuantity(cartItems.get(item.getId()).getQuantity() + 1);
        } else {
            item.setQuantity(1);
            cartItems.put(item.getId(), item);
        }
    }

    public void decreaseQuantity(MenuItem item) {
        if (cartItems.containsKey(item.getId())) {
            int newQuantity = cartItems.get(item.getId()).getQuantity() - 1;
            if (newQuantity > 0) {
                cartItems.get(item.getId()).setQuantity(newQuantity);
            }
        }
    }

    public void removeFromCart(MenuItem item) {
        cartItems.remove(item.getId());
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (MenuItem item : cartItems.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public int getItemQuantity(MenuItem item) {
        return cartItems.containsKey(item.getId()) ? cartItems.get(item.getId()).getQuantity() : 0;
    }
}
