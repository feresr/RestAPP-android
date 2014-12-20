package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.TablesAdapter;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.BusProvider;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.api.TablesUpdatedEvent;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Table;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Presents the user with a list of tables to choose from
 */
public class TablesFragment extends ListFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private MainHandler activity;
    private TablesAdapter adapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.activity = (MainHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainHandler");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the list fragment's content view by calling the super method
        final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);

        // Now create a SwipeRefreshLayout to wrap the fragment's content view
        mSwipeRefreshLayout = new ListFragmentSwipeRefreshLayout(container.getContext());

        // Add the list fragment's content view to the SwipeRefreshLayout, making sure that it fills
        // the SwipeRefreshLayout
        mSwipeRefreshLayout.addView(listFragmentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Make sure that the SwipeRefreshLayout will fill the fragment
        mSwipeRefreshLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        mSwipeRefreshLayout.setOnRefreshListener(this);


        // Now return the SwipeRefreshLayout as this fragment's content view
        return mSwipeRefreshLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TablesAdapter(getActivity(), R.layout.fragment_tables);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        setEmptyText("No hay mesas disponibles");
        //Prevents refreshing table on rotation
        if (savedInstanceState == null) {
            refreshTables();
        }

    }

    private void refreshTables() {
        setListShown(false);
        final Bus bus = BusProvider.getInstance();
        final DatabaseHandler db = new DatabaseHandler(getActivity());
        RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient(getActivity());
        apiInterface.retrieveTablesAsync(new Callback<List<Table>>() {
            @Override
            public void success(List<Table> tables, Response response) {
                db.addTables(tables);
                bus.post(new TablesUpdatedEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                switch (error.getKind()){
                    case NETWORK:
                        break;
                    case HTTP:
                        break;
                    case CONVERSION:
                        //TODO: handle errors.
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        activity.onTableSelected((Table) parent.getAdapter().getItem(position));
    }

    @Subscribe
    public void onTablesUpdated(TablesUpdatedEvent e) {
        adapter.notifyDataSetChanged();
        setListShown(true);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);

    }

    @Override
    public void onRefresh() {
        refreshTables();
    }

    private class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {

        public ListFragmentSwipeRefreshLayout(Context context) {
            super(context);
        }

        /**
         * As mentioned above, we need to override this method to properly signal when a
         * 'swipe-to-refresh' is possible.
         *
         * @return true if the {@link android.widget.ListView} is visible and can scroll up.
         */
        @Override
        public boolean canChildScrollUp() {
            final ListView listView = getListView();
            if (listView.getVisibility() == View.VISIBLE) {
                return canListViewScrollUp(listView);
            } else {
                return false;
            }
        }

    }

    /**
     * Utility method to check whether a {@link ListView} can scroll up from it's current position.
     * Handles platform version differences, providing backwards compatible functionality where
     * needed.
     */
    private static boolean canListViewScrollUp(ListView listView) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            // For ICS and above we can call canScrollVertically() to determine this
            return ViewCompat.canScrollVertically(listView, -1);
        } else {
            // Pre-ICS we need to manually check the first visible item and the child view's top
            // value
            return listView.getChildCount() > 0 &&
                    (listView.getFirstVisiblePosition() > 0
                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
        }
    }

}
