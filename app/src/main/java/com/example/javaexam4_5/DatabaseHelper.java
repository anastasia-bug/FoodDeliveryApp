package com.example.javaexam4_5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "food_delivery.db";
    private static final int DB_VERSION = 3;

    // Таблица категорий
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    // Таблица позиций меню
    private static final String TABLE_MENU_ITEMS = "menu_items";
    private static final String COLUMN_ITEM_ID = "id";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_CATEGORY = "category_id";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_WEIGHT = "weight";
    private static final String COLUMN_ITEM_CALORIES = "calories";
    private static final String COLUMN_ITEM_IMAGE = "imagePath";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " ("
                + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";

        String createMenuItemsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_MENU_ITEMS + " ("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + COLUMN_ITEM_CATEGORY + " INTEGER, "
                + COLUMN_ITEM_DESCRIPTION + " TEXT, "
                + COLUMN_ITEM_PRICE + " REAL NOT NULL, "
                + COLUMN_ITEM_WEIGHT + " TEXT, "
                + COLUMN_ITEM_CALORIES + " INTEGER, "
                + COLUMN_ITEM_IMAGE + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_ITEM_CATEGORY + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + "));";

        db.execSQL(createCategoriesTable);
        db.execSQL(createMenuItemsTable);

        insertData(db);
    }

    private void insertCategory(SQLiteDatabase db, String name) {
        String query = "INSERT INTO " + TABLE_CATEGORIES + " (" + COLUMN_CATEGORY_NAME + ") VALUES (?);";
        db.execSQL(query, new Object[]{name});
    }

    private void insertMenuItem(SQLiteDatabase db, String name, int category, String description,
                                double price, String weight, int calories, String image) {
        String query = "INSERT INTO " + TABLE_MENU_ITEMS + " (" +
                COLUMN_ITEM_NAME + ", " + COLUMN_ITEM_CATEGORY + ", " +
                COLUMN_ITEM_DESCRIPTION + ", " + COLUMN_ITEM_PRICE + ", " +
                COLUMN_ITEM_WEIGHT + ", " + COLUMN_ITEM_CALORIES +", " +
                COLUMN_ITEM_IMAGE + ") VALUES (?, ?, ?, ?, ?, ?, ?);";
        db.execSQL(query, new Object[]{name, category, description, price, weight, calories, image});
    }

    private void insertData(SQLiteDatabase db) {
        insertCategory( db,"Пицца");
         insertCategory( db,"Паста");
         insertCategory( db,"Напитки");

        // Пицца
        insertMenuItem(db, "Маргарита", 1, "Томатный соус, моцарелла, базилик", 450.0, "350 г", 850, "pizza_margarita");
          insertMenuItem(db, "Пепперони", 1, "Томатный соус, моцарелла, пепперони", 500.0, "400 г", 950, "pizza_pepperoni");
          insertMenuItem(db, "Четыре сыра", 1, "Моцарелла, пармезан, горгонзола, чеддер", 600.0, "450 г", 1200, "pizza_four_cheese");
          insertMenuItem(db, "Гавайская", 1, "Томатный соус, моцарелла, бекон, ананас", 550.0, "420 г", 1000, "pizza_hawaiian");
          insertMenuItem(db, "Мясная", 1, "Говядина, бекон, курица, моцарелла", 650.0, "500 г", 1300, "pizza_meat");
          insertMenuItem(db, "Вегетарианская", 1, "Овощи, моцарелла, томатный соус", 480.0, "380 г", 900, "pizza_veggie");

        // Паста
          insertMenuItem(db, "Карбонара", 2, "Паста, сливочный соус, бекон", 500.0, "400 г", 950, "pasta_carbonara");
          insertMenuItem(db, "Болоньезе", 2, "Паста, мясной соус", 520.0, "420 г", 980, "pasta_bolognese");
          insertMenuItem(db, "Фетучини с грибами", 2, "Паста, грибы, сливочный соус", 550.0, "450 г", 920, "pasta_mushroom_fettuccine");
          insertMenuItem(db, "Песто", 2, "Паста, соус песто, пармезан", 510.0, "400 г", 890, "pasta_pesto");
          insertMenuItem(db, "Альфредо", 2, "Паста, курица, сливочный соус", 530.0, "420 г", 960, "pasta_alfredo");
          insertMenuItem(db, "Арабьята", 2, "Паста, острый томатный соус", 480.0, "390 г", 870, "pasta_arrabbiata");

        // Напитки
          insertMenuItem(db, "Кола", 3, "Газированный напиток", 150.0, "500 мл", 200, "drink_cola");
          insertMenuItem(db, "Апельсиновый сок", 3, "Свежевыжатый сок", 250.0, "300 мл", 180, "drink_orange_juice");
          insertMenuItem(db, "Чай черный", 3, "Чайный лист, кипяток", 100.0, "250 мл", 0, "drink_black_tea");
          insertMenuItem(db, "Чай зеленый", 3, "Чайный лист, кипяток", 100.0, "250 мл", 0, "drink_green_tea");
          insertMenuItem(db, "Американо", 3, "Эспрессо, вода", 200.0, "250 мл", 10, "drink_americano");
          insertMenuItem(db, "Капучино", 3, "Эспрессо, молоко, пенка", 250.0, "300 мл", 150, "drink_cappuccino");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
            onCreate(db);
        }
    }

    public List<MenuItem> getMenuItemsByCategory(int categoryId) {
        List<MenuItem> menuItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MENU_ITEMS+ " WHERE " + COLUMN_ITEM_CATEGORY + " = ? ORDER BY " + COLUMN_ITEM_NAME + " ASC",
                new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                MenuItem menuItem = new MenuItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ITEM_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_WEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_CALORIES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_IMAGE))
                );
                menuItems.add(menuItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return menuItems;
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MENU_ITEMS + " ORDER BY " + COLUMN_ITEM_NAME + " ASC",
                new String[]{});

        if (cursor.moveToFirst()) {
            do {
                MenuItem menuItem = new MenuItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ITEM_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_WEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_CALORIES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_IMAGE))
                );
                menuItems.add(menuItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return menuItems;
    }

}
