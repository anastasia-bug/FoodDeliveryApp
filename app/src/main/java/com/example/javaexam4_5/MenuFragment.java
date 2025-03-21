package com.example.javaexam4_5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private MenuItemAdapter menuAdapter;
    private List<MenuItem> menuItemList;
    private DatabaseHelper databaseHelper;
    private int category;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        if (getArguments() != null) {
            category = getArguments().getInt("category");
        }


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());
        menuItemList = new ArrayList<>();

        menuAdapter = new MenuItemAdapter(getContext(), menuItemList, new MenuItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
            MenuDetailFragment fragment = new MenuDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable("menu_item", item);
            fragment.setArguments(args);
                requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            }
        });
        recyclerView.setAdapter(menuAdapter);

        loadMenuItems();

        return view;
    }

    private void loadMenuItems() {
        menuItemList.clear();

        List<MenuItem> items;

        if (category == 0)
        {
            items = databaseHelper.getAllMenuItems();
        }
        else {
            items = databaseHelper.getMenuItemsByCategory(category);
        }

        Log.d("DB_CHECK", "Найдено " + items.size() + " элементов");
        Log.d("CATEGORY_CHECK", "Выбрана категория: " + category);

        menuItemList.addAll(items);

    }
}