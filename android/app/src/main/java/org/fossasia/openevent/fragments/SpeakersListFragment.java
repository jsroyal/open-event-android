package org.fossasia.openevent.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java

=======
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java
import com.squareup.otto.Subscribe;

import org.fossasia.openevent.OpenEventApp;
import org.fossasia.openevent.R;
import org.fossasia.openevent.adapters.SpeakersListAdapter;
import org.fossasia.openevent.api.Urls;
import org.fossasia.openevent.data.Speaker;
import org.fossasia.openevent.dbutils.DataDownloadManager;
import org.fossasia.openevent.dbutils.DbSingleton;
import org.fossasia.openevent.events.SpeakerDownloadEvent;
import org.fossasia.openevent.utils.NetworkUtils;
import org.fossasia.openevent.utils.ShowNotificationSnackBar;
import org.fossasia.openevent.views.MarginDecoration;

import java.util.List;

import butterknife.BindView;
<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java
=======
import butterknife.ButterKnife;
import butterknife.Unbinder;
>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java
import timber.log.Timber;

import static org.fossasia.openevent.utils.SortOrder.sortOrderSpeaker;

public class SpeakersListFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    private static final String PREF_SORT = "sortType";

    final private String SEARCH = "searchText";

    private SharedPreferences prefsSort;

    @BindView(R.id.speaker_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_no_speakers)  TextView noSpeakersView;
<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java
    @BindView(R.id.rv_speakers) RecyclerView speakersRecyclerView;
=======
    @BindView(R.id.rv_speakers) FastScrollRecyclerView speakersRecyclerView;

    private Unbinder unbinder;
>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java

    private SpeakersListAdapter speakersListAdapter;

    private String searchText = "";

    private SearchView searchView;

    private int sortType;

    private Snackbar snackbar;


    private StaggeredGridLayoutManager gridLayoutManager;
    private Toolbar toolbar;
    private AppBarLayout.LayoutParams layoutParams;
    private int SCROLL_OFF = 0;
    private int spanCount = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java

        View view = super.onCreateView(inflater, container, savedInstanceState);

        OpenEventApp.getEventBus().register(this);

=======
        View view = inflater.inflate(R.layout.list_speakers, container, false);
        unbinder = ButterKnife.bind(this,view);

        OpenEventApp.getEventBus().register(this);

        speakersRecyclerView.setThumbColor(getResources().getColor(R.color.color_primary));
        speakersRecyclerView.setPopupBgColor(getResources().getColor(R.color.color_primary));

>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java
        final DbSingleton dbSingleton = DbSingleton.getInstance();
        final List<Speaker> mSpeakers = dbSingleton.getSpeakerList(sortOrderSpeaker(getActivity()));
        prefsSort = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortType = prefsSort.getInt(PREF_SORT, 0);

        speakersListAdapter = new SpeakersListAdapter(mSpeakers, getActivity());
        speakersRecyclerView.addItemDecoration(new MarginDecoration(getContext()));
        speakersRecyclerView.setHasFixedSize(true);
        speakersRecyclerView.setAdapter(speakersListAdapter);
        gridLayoutManager = new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);
        speakersRecyclerView.setLayoutManager(gridLayoutManager);
        speakersRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                if (mSpeakers.size() - 1 - gridLayoutManager.findLastCompletelyVisibleItemPositions(new int[spanCount])[0] < spanCount ) {
                    layoutParams.setScrollFlags(SCROLL_OFF);
                    toolbar.setLayoutParams(layoutParams);
                }
                speakersRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java
=======

>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        if (savedInstanceState != null && savedInstanceState.getString(SEARCH) != null) {
            searchText = savedInstanceState.getString(SEARCH);
        }
        if (!mSpeakers.isEmpty()) {
            noSpeakersView.setVisibility(View.GONE);
            speakersRecyclerView.setVisibility(View.VISIBLE);
        } else {
            noSpeakersView.setVisibility(View.VISIBLE);
            speakersRecyclerView.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java
    protected int getLayoutResource() {
        return R.layout.list_speakers;
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
>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java
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
        switch (item.getItemId()) {
            case R.id.share_speakers_url:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Urls.WEB_APP_URL_BASIC + Urls.SPEAKERS);
                intent.putExtra(Intent.EXTRA_SUBJECT, R.string.share_links);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_links)));
                break;
            case R.id.action_sort:

                final AlertDialog.Builder dialogSort = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.dialog_sort_title)
                        .setSingleChoiceItems(R.array.speaker_sort, sortType, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sortType = which;
                                SharedPreferences.Editor editor = prefsSort.edit();
                                editor.putInt(PREF_SORT, which);
                                editor.apply();
                                speakersListAdapter.refresh();
                                dialog.dismiss();
                            }
                        });

                dialogSort.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_speakers, menu);
        MenuItem item = menu.findItem(R.id.action_search_speakers);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setQuery(searchText, false);
    }

    @Subscribe
    public void speakerDownloadDone(SpeakerDownloadEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        if (event.isState()) {
            speakersListAdapter.refresh();
            Timber.i("Speaker download completed");
        } else {
            if (getActivity() != null) {
                Snackbar.make(getView(), getActivity().getString(R.string.refresh_failed), Snackbar.LENGTH_LONG).setAction(R.string.retry_download, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refresh();
                    }
                }).show();
            }
            Timber.i("Speaker download failed.");
        }
    }

    private void refresh() {
        if (NetworkUtils.haveNetworkConnection(getActivity())) {
<<<<<<< HEAD:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakersListFragment.java
            if (NetworkUtils.isActiveInternetPresent()) {
                //Internet is working
                DataDownloadManager.getInstance().downloadSpeakers();
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
=======
            DataDownloadManager.getInstance().downloadSpeakers();
>>>>>>> upstream/master:android/app/src/main/java/org/fossasia/openevent/fragments/SpeakerFragment.java
        } else {
            OpenEventApp.getEventBus().post(new SpeakerDownloadEvent(false));
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        searchText = query;
        if (!TextUtils.isEmpty(query)) {
            speakersListAdapter.getFilter().filter(query);
        } else {
            speakersListAdapter.refresh();
        }
        return true;
    }

}
