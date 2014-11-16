package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by feresr on 5/28/14.
 */
public class TablesAdapter extends ArrayAdapter<Table> {

    private Context context;
    private DatabaseHandler db;
    private List<Table> tables;

    private String TAG = this.getClass().getSimpleName();
    public TablesAdapter(Context context, int resource){
        super(context, resource);
        this.context = context;

        db = new DatabaseHandler(getContext());
        DatabaseHandler.registerAdapter(this);
        tables =  db.getTables();
        Log.e("TABLES", tables.toString());
    }


    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public long getItemId(int position) {
        return tables.get(position).getId();
    }

    @Override
    public Table getItem(int position) {
        return tables.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tableNumber;

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_table, null);
        }

        tableNumber = (TextView) v.findViewById(R.id.table_number_txt);

        Table table = getItem(position);

        tableNumber.setText(Integer.toString(table.getNumber()));

        return v;
    }
}
