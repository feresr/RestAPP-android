package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Table;

import java.util.List;

/**
 * Adapter for available tables
 */
public class TablesAdapter extends ArrayAdapter<Table> {

    private Context context;
    private DatabaseHandler db;
    private List<Table> tables;

    public TablesAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;

        db = new DatabaseHandler(getContext());
        DatabaseHandler.registerAdapter(this);
        tables = db.getTables();
        Log.e("TABLES", tables.toString());
    }


    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public void notifyDataSetChanged() {
        tables = db.getTables();
        super.notifyDataSetChanged();
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
        TextView description;

        View v = convertView;

        //TODO: peraps I can delete this.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_table, null);
        }

        tableNumber = (TextView) v.findViewById(R.id.table_number_txt);

        Table table = getItem(position);

        tableNumber.setText(Integer.toString(table.getNumber()));


        description = (TextView) v.findViewById(R.id.description_txt);

        if (table.getDescription() != null) {
            description.setVisibility(View.VISIBLE);
            description.setText(table.getDescription());
        } else {
            description.setVisibility(View.GONE);
        }
        return v;
    }
}
