package org.fossasia.openevent.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.fossasia.openevent.OpenEventApp;
import org.fossasia.openevent.R;
import org.fossasia.openevent.adapters.TracksListAdapter;
import org.fossasia.openevent.data.Track;
import org.fossasia.openevent.dbutils.DataDownloadManager;
import org.fossasia.openevent.dbutils.DbSingleton;
import org.fossasia.openevent.events.RefreshUiEvent;
import org.fossasia.openevent.events.TracksDownloadEvent;
import org.fossasia.openevent.utils.NetworkUtils;
import org.fossasia.openevent.utils.ShowNotificationSnackBar;

import java.util.List;

import butterknife.BindView;
<<<<<<< HEAD
=======
import butterknife.ButterKnife;
import butterknife.Unbinder;
>>>>>>> upstream/master

/**
 * User: MananWason
 * Date: 05-06-2015
 */
public class TracksFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    final private String SEARCH = "searchText";

    private TracksListAdapter tracksListAdapter;

    @BindView(R.id.tracks_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_no_tracks) TextView noTracksView;
    @BindView(R.id.list_tracks) RecyclerView tracksRecyclerView;
    @BindView(R.id.tracks_frame) View windowFrame;

<<<<<<< HEAD
=======
    private Unbinder unbinder;

>>>>>>> upstream/master
    private String searchText = "";

    private SearchView searchView;

<<<<<<< HEAD
    private DbSingleton dbSingleton;

    private Snackbar snackbar;

    private Toolbar toolbar;
    private AppBarLayout.LayoutParams layoutParams;
    private int SCROLL_OFF = 0;
=======

>>>>>>> upstream/master

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
<<<<<<< HEAD

        View view = super.onCreateView(inflater, container, savedInstanceState);

        OpenEventApp.getEventBus().register(this);
        dbSingleton = DbSingleton.getInstance();
        List<Track> mTracks = dbSingleton.getTrackList();
        setVisibility();
=======
        View view = inflater.inflate(R.layout.list_tracks, container, false);
        unbinder = ButterKnife.bind(this,view);

        OpenEventApp.getEventBus().register(this);
        DbSingleton dbSingleton = DbSingleton.getInstance();
        List<Track> mTracks = dbSingleton.getTrackList();
        tracksListAdapter = new TracksListAdapter(mTracks);
        tracksRecyclerView.setAdapter(tracksListAdapter);
        setVisibility(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(ConstantStrings.IS_DOWNLOAD_DONE, true));
        tracksListAdapter.setOnClickListener(new TracksListAdapter.SetOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Track model = tracksListAdapter.getItem(position);
                String trackTitle = model.getName();
                Intent intent = new Intent(getContext(), TracksActivity.class);
                intent.putExtra(ConstantStrings.TRACK, trackTitle);
                startActivity(intent);
            }
        });
>>>>>>> upstream/master
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


        //setting the grid layout to cut-off white space in tablet view
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = (int) (width/200.00);

        tracksRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),spanCount);
        tracksRecyclerView.setLayoutManager(gridLayoutManager);
        tracksListAdapter = new TracksListAdapter(getContext(), mTracks);
        tracksRecyclerView.setAdapter(tracksListAdapter);
        tracksRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridLayoutManager.getChildCount() - 1) {
                    layoutParams.setScrollFlags(SCROLL_OFF);
                    toolbar.setLayoutParams(layoutParams);
                }
                tracksRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        if (savedInstanceState != null && savedInstanceState.getString(SEARCH) != null) {
            searchText = savedInstanceState.getString(SEARCH);
        }
        return view;
    }

    public void setVisibility() {
        if (!dbSingleton.getTrackList().isEmpty()) {
            noTracksView.setVisibility(View.GONE);
            tracksRecyclerView.setVisibility(View.VISIBLE);
        } else {
            noTracksView.setVisibility(View.VISIBLE);
            tracksRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
<<<<<<< HEAD
    protected int getLayoutResource() {
        return R.layout.list_tracks;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OpenEventApp.getEventBus().unregister(this);
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        toolbar.setLayoutParams(layoutParams);
=======
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
>>>>>>> upstream/master
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (isAdded()) {
            if (searchView != null) {
                bundle.putString(SEARCH, searchText);
            }
        }
        super.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
<<<<<<< HEAD
=======
        switch (item.getItemId()) {
            case R.id.share_tracks_url:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Urls.WEB_APP_URL_BASIC + Urls.TRACKS);
                intent.putExtra(Intent.EXTRA_SUBJECT, R.string.share_links);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_links)));
                return true;
        }
>>>>>>> upstream/master
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_tracks, menu);
        MenuItem item = menu.findItem(R.id.action_search_tracks);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        if (searchText != null) {
            searchView.setQuery(searchText, false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (!TextUtils.isEmpty(query)) {
            searchText = query;
            tracksListAdapter.getFilter().filter(searchText);
        } else {
            tracksListAdapter.refresh();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Subscribe
    public void RefreshData(RefreshUiEvent event) {
        setVisibility();
        if (searchText.length() == 0) {
            tracksListAdapter.refresh();
        }
    }

    @Subscribe
    public void onTrackDownloadDone(TracksDownloadEvent event) {
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
        if (event.isState()) {
            tracksListAdapter.refresh();

        } else {
            if (getActivity() != null) {
                Snackbar.make(windowFrame, getActivity().getString(R.string.refresh_failed), Snackbar.LENGTH_LONG).setAction(R.string.retry_download, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refresh();
                    }
                }).show();
<<<<<<< HEAD
            }
        }
    }

    private void refresh() {
        if (NetworkUtils.haveNetworkConnection(getActivity())) {
            if (NetworkUtils.isActiveInternetPresent()) {
                //Internet is working
                DataDownloadManager.getInstance().downloadTracks();
            } else {
                //set is refreshing false as let user to login
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                //Device is connected to WI-FI or Mobile Data but Internet is not working
                ShowNotificationSnackBar showNotificationSnackBar = new ShowNotificationSnackBar(getContext(),getView(),swipeRefreshLayout) {
                    @Override
                    public void refreshClicked() {
                        refresh();
                    }
                };
                //show snackbar will be useful if user have blocked notification for this app
                snackbar = showNotificationSnackBar.showSnackBar();
                //show notification
                showNotificationSnackBar.buildNotification();
            }
        } else {
            if (snackbar!=null && snackbar.isShown()) {
                snackbar.dismiss();
=======
>>>>>>> upstream/master
            }
            OpenEventApp.getEventBus().post(new TracksDownloadEvent(false));
        }
        setVisibility();
    }

    private void refresh() {
        if (NetworkUtils.haveNetworkConnection(getActivity())) {
            DataDownloadManager.getInstance().downloadTracks();
            setVisibility(true);
        } else {
            OpenEventApp.getEventBus().post(new TracksDownloadEvent(false));
            setVisibility(false);
        }
    }

}
