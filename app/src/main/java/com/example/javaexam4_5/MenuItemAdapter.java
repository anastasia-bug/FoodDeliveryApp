package com.example.javaexam4_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder> {

    private Context context;
    private List<MenuItem> menuItemList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }

    public MenuItemAdapter(Context context, List<MenuItem> menuItemList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.menuItemList = menuItemList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItemList.get(position);

        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView imageView;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            priceTextView = itemView.findViewById(R.id.item_price);
            imageView = itemView.findViewById(R.id.item_image);
        }

        public void bind(final MenuItem item, final OnItemClickListener onItemClickListener) {

            nameTextView.setText(item.getName());
            priceTextView.setText(String.format("%.2f руб.", item.getPrice()));

            int imageResource = item.getDrawableIdByName();
            if (imageResource != 0) {
                imageView.setImageResource(imageResource);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }

    }
}
