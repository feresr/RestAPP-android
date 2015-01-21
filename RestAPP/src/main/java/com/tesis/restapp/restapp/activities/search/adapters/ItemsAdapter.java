package com.tesis.restapp.restapp.activities.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for items in a category.
 */
public class ItemsAdapter extends ArrayAdapter<Item> {

    private List<Item> items;

    public ItemsAdapter(Context context, int resource) {
        super(context, resource);
        items = new ArrayList<Item>();
    }

    public ItemsAdapter(Context context, int resource, int categoryId) {
        super(context, resource);
        DatabaseHandler db = new DatabaseHandler(context);
        items = db.getItemsInCategory(categoryId);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView description;
        TextView price;
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) super.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_item, null);

        }

        description = (TextView) v
                .findViewById(R.id.item_description_txt);
        price = (TextView) v.findViewById(R.id.item_price_txt);

        Item item = getItem(position);
        if (item != null) {
            description.setText(item.getName());
            price.setText(String.valueOf(item.getPrice()));
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                List<Item> filteredResults = db.getItemsByName(constraint);

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Item>) results.values;
                ItemsAdapter.this.notifyDataSetChanged();
            }
        };
    }
}
