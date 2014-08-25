package com.tesis.restapp.restapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.tesis.restapp.restapp.activities.main.adapters.ItemsInOrderAdapter;
import com.tesis.restapp.restapp.activities.main.adapters.OrdersAdapter;
import com.tesis.restapp.restapp.activities.main.adapters.TablesAdapter;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.models.Category;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;


import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static OrdersAdapter ordersAdapter;
    private static ItemsInOrderAdapter itemsInOrderAdapter;
    public static TablesAdapter tablesAdapter;

    public static void registerAdapter(OrdersAdapter adapter) {

        ordersAdapter = adapter;

    }
    public static void registerAdapter(ItemsInOrderAdapter adapter){

        itemsInOrderAdapter = adapter;

    }

    public static void registerAdapter(TablesAdapter adapter){

        tablesAdapter = adapter;

    }

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "restApp";

    // Contacts table name
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_TABLES = "tables";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_ORDER_ITEM = "order_item";

    // Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SEATS = "seats";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_TABLE_ID = "table_id";
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_ITEM_ID = "item_id";


    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_TAKEN = "taken";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_TOTAL = "total";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT"
                + ")";

        db.execSQL(CREATE_CATEGORIES_TABLE);

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_PRICE + " REAL,"
                + KEY_CATEGORY_ID + " INTEGER,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT,"
                + " FOREIGN KEY(" + KEY_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + KEY_ID + ")"
                + ")";

        db.execSQL(CREATE_ITEMS_TABLE);


        String CREATE_TABLES_TABLE = "CREATE TABLE " + TABLE_TABLES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NUMBER + " INTEGER,"
                + KEY_SEATS + " INTEGER,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_TAKEN + " INTEGER,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT"
                + ")";

        db.execSQL(CREATE_TABLES_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TABLE_ID + " INTEGER,"
                + KEY_TOTAL + " REAL,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT,"
                + " FOREIGN KEY(" + KEY_TABLE_ID + ") REFERENCES " + TABLE_TABLES + "(" + KEY_ID + ")"
                + ")";

        db.execSQL(CREATE_ORDERS_TABLE);

        String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE " + TABLE_ORDER_ITEM + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ORDER_ID + " INTEGER,"
                + KEY_ITEM_ID + " INTEGER,"
                + KEY_QUANTITY + " INTEGER,"
                + KEY_PRICE + " REAL,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT,"
                + " FOREIGN KEY(" + KEY_ITEM_ID + ") REFERENCES " + TABLE_ITEMS + "(" + KEY_ID + "), "
                + " FOREIGN KEY(" + KEY_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + KEY_ID + ") "
                + ")";

        db.execSQL(CREATE_ORDER_ITEM_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

        // Create tables again
        onCreate(db);

    }

    // Getting All Contacts
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<Order>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS
                + " JOIN " + TABLE_TABLES + " ON " + TABLE_ORDERS + "." + KEY_TABLE_ID + "=" + TABLE_TABLES + "." + KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                Order order = new Order();
                order.setId(cursor.getInt(0));

                Table table = new Table();
                table.setId(cursor.getInt(4));

                table.setNumber(cursor.getInt(5));

                table.setSeats(cursor.getInt(6));
                table.setDescription(cursor.getString(7));
                table.setTaken(cursor.getInt(8) == 1);

                order.setTable(table);

                String selectItemsQuery = "SELECT * FROM " + TABLE_ITEMS
                        + " JOIN " + TABLE_ORDER_ITEM + " ON " + TABLE_ITEMS + "." + KEY_ID + "=" + TABLE_ORDER_ITEM + "." + KEY_ITEM_ID
                        + " AND " + TABLE_ORDER_ITEM + "." + KEY_ORDER_ID + " = " + order.getId();

                Cursor itemCursor = db.rawQuery(selectItemsQuery, null);
                ArrayList<Item> items = new ArrayList<Item>();
                if (itemCursor.moveToFirst()) {
                    do {

                        Item item = new Item();
                        item.setId(itemCursor.getInt(0));
                        item.setName(itemCursor.getString(1));
                        item.setDescription(itemCursor.getString(2));
                        item.setPrice(itemCursor.getDouble(3));
                        Category category = new Category();

                        String selectCategory = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + TABLE_CATEGORIES + "." + KEY_ID + " = " + itemCursor.getInt(4);
                        Cursor categoryCursor = db.rawQuery(selectCategory, null);
                        if (categoryCursor.moveToFirst()) {
                            category.setId(categoryCursor.getInt(0));
                            category.setName(categoryCursor.getString(1));
                        }
                        item.setCategory(category);

                        items.add(item);
                    } while (itemCursor.moveToNext());
                }
                order.setItems(items);

                orderList.add(order);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return orderList;
    }

    public List<Table> getTables() {
        List<Table> tableList = new ArrayList<Table>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TABLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Table table = new Table();
                table.setId(cursor.getInt(0));
                table.setNumber(cursor.getInt(1));
                table.setSeats(cursor.getInt(2));
                table.setDescription(cursor.getString(3));
                table.setTaken(cursor.getInt(4) == 1);


                tableList.add(table);

            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return tableList;
    }

    public List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));

                categoryList.add(category);

            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return categoryList;
    }

    public Item getItemById(int itemID) {
        Item item = new Item();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + TABLE_ITEMS + "." + KEY_ID + "=" + itemID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {


            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setDescription(cursor.getString(2));
            item.setPrice(cursor.getDouble(3));
            Category category = new Category();

            String selectCategory = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + TABLE_CATEGORIES + "." + KEY_ID + " = " + cursor.getInt(4);
            Cursor categoryCursor = db.rawQuery(selectCategory, null);
            if (categoryCursor.moveToFirst()) {
                category.setId(categoryCursor.getInt(0));
                category.setName(categoryCursor.getString(1));
            }
            item.setCategory(category);
        }
        db.close();
        // return contact list
        Log.e("e",item.toString());
        return item;
    }

    public List<Item> getItemsInCategory(int categoryID) {
        ArrayList<Item> items = new ArrayList<Item>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + TABLE_ITEMS + "." + KEY_CATEGORY_ID + "=" + categoryID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setPrice(cursor.getDouble(3));
                Category category = new Category();

                String selectCategory = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + TABLE_CATEGORIES + "." + KEY_ID + " = " + categoryID;
                Cursor categoryCursor = db.rawQuery(selectCategory, null);
                if (categoryCursor.moveToFirst()) {
                    category.setId(categoryCursor.getInt(0));
                    category.setName(categoryCursor.getString(1));
                }
                item.setCategory(category);

                items.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return items;

    }

    public Order getOrderById(int id) {
        // Select All Query
        Order order = new Order();

        String selectQuery = "SELECT * FROM " + TABLE_ORDERS
                + " JOIN " + TABLE_TABLES + " ON " + TABLE_ORDERS + "." + KEY_TABLE_ID + "=" + TABLE_TABLES + "." + KEY_ID + " WHERE " + TABLE_ORDERS + "." + KEY_ID + "=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            order.setId(cursor.getInt(0));

            Table table = new Table();
            table.setId(cursor.getInt(4));

            table.setNumber(cursor.getInt(5));

            table.setSeats(cursor.getInt(6));
            table.setDescription(cursor.getString(7));
            table.setTaken(cursor.getInt(8) == 1);

            order.setTable(table);

            String selectItemsQuery = "SELECT * FROM " + TABLE_ITEMS
                    + " JOIN " + TABLE_ORDER_ITEM + " ON " + TABLE_ITEMS + "." + KEY_ID + "=" + TABLE_ORDER_ITEM + "." + KEY_ITEM_ID
                    + " AND " + TABLE_ORDER_ITEM + "." + KEY_ORDER_ID + " = " + order.getId();

            Cursor itemCursor = db.rawQuery(selectItemsQuery, null);
            ArrayList<Item> items = new ArrayList<Item>();
            if (itemCursor.moveToFirst()) {
                do {

                    Item item = new Item();
                    item.setId(itemCursor.getInt(0));
                    item.setName(itemCursor.getString(1));
                    item.setDescription(itemCursor.getString(2));
                    item.setPrice(itemCursor.getDouble(3));
                    item.setCategory(new Category());

                    items.add(item);
                } while (itemCursor.moveToNext());
            }
            order.setItems(items);


        }
        db.close();
        // return contact list
        return order;
    }

    public void addItems(List<ItemRow> items) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, null, null);
        for (ItemRow item : items) {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, item.getId()); // Contact Name
            values.put(KEY_NAME, item.getName()); // Contact Phone Number
            values.put(KEY_DESCRIPTION, item.getDescription()); // Contact Phone Number
            values.put(KEY_PRICE, item.getPrice()); // Contact Phone Number
            values.put(KEY_CATEGORY_ID, item.getCategory_id());
            values.put(KEY_CREATED_AT, item.getCreated_at());
            values.put(KEY_UPDATED_AT, item.getUpdated_at());

            // Inserting Row
            db.insert(TABLE_ITEMS, null, values);

        }

        db.close(); // Closing database connection
    }

    public void addTables(List<TableRow> tables) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TABLES, null, null);
        for (TableRow table : tables) {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, table.getId()); // Contact Name
            values.put(KEY_NUMBER, table.getNumber()); // Contact Phone Number
            values.put(KEY_SEATS, table.getSeats()); // Contact Phone Number
            values.put(KEY_DESCRIPTION, table.getDescription()); // Contact Phone Number
            values.put(KEY_TAKEN, table.isTaken());
            values.put(KEY_CREATED_AT, table.getCreated_at());
            values.put(KEY_UPDATED_AT, table.getUpdated_at());

            // Inserting Row
            db.insert(TABLE_TABLES, null, values);

        }

        db.close(); // Closing database connection
    }

    public void addCategories(List<CategoryRow> categories) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, null, null);
        for (CategoryRow category : categories) {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, category.getId()); // Contact Name
            values.put(KEY_NAME, category.getName()); // Contact Phone Number
            values.put(KEY_CREATED_AT, category.getCreated_at());
            values.put(KEY_UPDATED_AT, category.getUpdated_at());

            // Inserting Row
            db.insert(TABLE_CATEGORIES, null, values);

        }

        db.close(); // Closing database connection
    }

    public void addOrders(List<OrderRow> orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, null, null);
        for (OrderRow order : orders) {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, order.getId()); // Contact Name
            values.put(KEY_TABLE_ID, order.getTable_id()); // Contact Phone Number
            values.put(KEY_CREATED_AT, order.getCreated_at());
            values.put(KEY_UPDATED_AT, order.getUpdated_at());

            // Inserting Row
            db.insert(TABLE_ORDERS, null, values);

        }
        ordersAdapter.notifyDataSetChanged();
        db.close(); // Closing database connection
    }

    public void addOrderItems(List<Order_itemRow> orderItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER_ITEM, null, null);
        for (Order_itemRow orderItem : orderItems) {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, orderItem.getId()); // Contact Name
            values.put(KEY_ORDER_ID, orderItem.getOrder_id()); // Contact Phone Number
            values.put(KEY_ITEM_ID, orderItem.getItem_id());
            values.put(KEY_QUANTITY, orderItem.getQuantity());
            values.put(KEY_PRICE, orderItem.getPrice());
            values.put(KEY_CREATED_AT, orderItem.getCreated_at());
            values.put(KEY_UPDATED_AT, orderItem.getUpdated_at());

            // Inserting Row
            db.insert(TABLE_ORDER_ITEM, null, values);

        }

        db.close(); // Closing database connection

    }

    public void addItemToOrder(Context context, final Order order, final Item item){
        final SQLiteDatabase db = this.getWritableDatabase();
        final Toast errorToast = Toast.makeText(context, "Problema en el servidor", Toast.LENGTH_SHORT);
        ApiClient.getRestAppApiClient().addItemToOrder(order.getId(), item.getId(), 1, new Callback<com.tesis.restapp.restapp.database.Response>() {
            @Override
            public void success(com.tesis.restapp.restapp.database.Response data, retrofit.client.Response response) {

                if(data.wasSuccessful()) {
                    ContentValues values = new ContentValues();

                    values.put(KEY_ID, data.getId());
                    values.put(KEY_ORDER_ID, order.getId());
                    values.put(KEY_ITEM_ID, item.getId());
                    values.put(KEY_QUANTITY, 1);
                    values.put(KEY_PRICE, item.getPrice());
                    // Inserting Row
                    db.insert(TABLE_ORDER_ITEM, null, values);

                    itemsInOrderAdapter.notifyDataSetChanged();
                }else{
                    Log.e("DATABASE HANDLER",data.getErrors());
                }
                db.close(); // Closing database connection
            }

            @Override
            public void failure(RetrofitError error) {
                errorToast.show();
                db.close();
            }
        });
    }

    public void removeItemFromOrder(Context context, final Order order, final Item item){
        final SQLiteDatabase db = this.getWritableDatabase();
        final Toast errorToast = Toast.makeText(context, "Problema en el servidor", Toast.LENGTH_SHORT);
        String selectQuery = "SELECT * FROM " + TABLE_ORDER_ITEM
                + " WHERE " + TABLE_ORDER_ITEM + "." + KEY_ITEM_ID + "=" + item.getId() + " AND " + TABLE_ORDER_ITEM + "." + KEY_ORDER_ID + " =" + order.getId();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            ApiClient.getRestAppApiClient().removeItemFromOrder(cursor.getInt(0), new Callback<Order_itemRow>() {
                @Override
                public void success(Order_itemRow order_itemRow, Response response) {

                    db.delete(TABLE_ORDER_ITEM, "id = ?", new String[] { String.valueOf(order_itemRow.getId())});

                    itemsInOrderAdapter.notifyDataSetChanged();
                    db.close(); // Closing database connection
                }

                @Override
                public void failure(RetrofitError error) {
                    errorToast.show();
                    db.close();
                }
            });


         }



    }

    public void removeOrder(OrderRow order){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, "id = ?", new String[] { String.valueOf(order.getId())});
        ordersAdapter.notifyDataSetChanged();
        db.close();
    }

    public void addOrder(OrderRow order) {
        SQLiteDatabase db = this.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(KEY_ID, order.getId()); // Contact Name
            values.put(KEY_TABLE_ID, order.getTable_id()); // Contact Phone Number
            values.put(KEY_CREATED_AT, order.getCreated_at());
            values.put(KEY_UPDATED_AT, order.getUpdated_at());

            // Inserting Row
            db.insert(TABLE_ORDERS, null, values);


        ordersAdapter.notifyDataSetChanged();
        db.close(); // Closing database connection
    }

    public void updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Item> items = order.getItems();

        db.delete(TABLE_ORDER_ITEM, KEY_ORDER_ID + " = ?",
                new String[] { String.valueOf(order.getId()) });
        for(Item item : items){
            ContentValues values = new ContentValues();
            //values.put(KEY_ID, item.getId());
            values.put(KEY_ORDER_ID, order.getId());
            values.put(KEY_ITEM_ID, item.getId());
            values.put(KEY_QUANTITY, 1);
            values.put(KEY_PRICE, item.getPrice());
            // Inserting Row
            db.insert(TABLE_ORDER_ITEM, null, values);
        }
        itemsInOrderAdapter.notifyDataSetChanged();
        db.close(); // Closing database connection

    }

}
