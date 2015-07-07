package com.tesis.restapp.restapp.activities.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Category;

import java.util.List;

/**
 * Created by feresr on 6/12/14.
 */
public class CategoriesAdapter extends ArrayAdapter<Category> {
    private LayoutInflater inflater;
    private Context context;
    private DatabaseHandler db;
    private List<Category> categories;
    int[] images = new int[]{R.drawable.bw_cafeteria, R.drawable.bw_comida, R.drawable.bw_sandwich, R.drawable.bw_cerveza, R.drawable.bw_wine, R.drawable.bw_juice, R.drawable.bw_dessert};

    public CategoriesAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        db = new DatabaseHandler(getContext());
        categories = db.getCategories();
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView description;
        LinearLayout layout;
        View v = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_category, null);

        }


        description = (TextView) v
                .findViewById(R.id.category_description_txt);
        layout = (LinearLayout) v
                .findViewById(R.id.category_layout);

        description.setText(getItem(position).getName());


        layout.setBackgroundResource(images[position % images.length]);


        return v;
    }
}
