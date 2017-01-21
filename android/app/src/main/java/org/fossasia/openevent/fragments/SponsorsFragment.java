package org.fossasia.openevent.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.fossasia.openevent.OpenEventApp;
import org.fossasia.openevent.R;
import org.fossasia.openevent.adapters.SponsorsListAdapter;
import org.fossasia.openevent.dbutils.DataDownloadManager;
import org.fossasia.openevent.dbutils.DbSingleton;
import org.fossasia.openevent.events.SponsorDownloadEvent;
import org.fossasia.openevent.utils.NetworkUtils;
import org.fossasia.openevent.utils.ShowNotificationSnackBar;

import butterknife.BindView;
<<<<<<< HEAD
=======
import butterknife.ButterKnife;
import butterknife.Unbinder;
>>>>>>> upstream/master
import timber.log.Timber;

/**
 * Created by MananWason on 05-06-2015.
 */
public class SponsorsFragment extends BaseFragment {

    private SponsorsListAdapter sponsorsListAdapter;

<<<<<<< HEAD
    @BindView(R.id.txt_no_sponsors)
    TextView noSponsorsView;
    @BindView(R.id.sponsor_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_sponsors)
    RecyclerView sponsorsRecyclerView;

    private Snackbar snackbar;

    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private AppBarLayout.LayoutParams layoutParams;
    private int SCROLL_OFF = 0;
=======
    @BindView(R.id.txt_no_sponsors) TextView noSponsorsView;
    @BindView(R.id.sponsor_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_sponsors) RecyclerView sponsorsRecyclerView;

    private Unbinder unbinder;
>>>>>>> upstream/master

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
<<<<<<< HEAD

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        OpenEventApp.getEventBus().register(this);
=======
        final View view = inflater.inflate(R.layout.list_sponsors, container, false);
        unbinder = ButterKnife.bind(this,view);

        Bus bus = OpenEventApp.getEventBus();
        bus.register(this);
>>>>>>> upstream/master
        final DbSingleton dbSingleton = DbSingleton.getInstance();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        sponsorsListAdapter = new SponsorsListAdapter(getContext(), dbSingleton.getSponsorList());
        sponsorsRecyclerView.setAdapter(sponsorsListAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        sponsorsRecyclerView.setLayoutManager(linearLayoutManager);
        sponsorsRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getChildCount() - 1) {
                    layoutParams.setScrollFlags(SCROLL_OFF);
                    toolbar.setLayoutParams(layoutParams);
                }
                sponsorsRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });


        if (sponsorsListAdapter.getItemCount() != 0) {
            noSponsorsView.setVisibility(View.GONE);
            sponsorsRecyclerView.setVisibility(View.VISIBLE);
        } else {
            noSponsorsView.setVisibility(View.VISIBLE);
            sponsorsRecyclerView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
<<<<<<< HEAD
    protected int getLayoutResource() {
        return R.layout.list_sponsors;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OpenEventApp.getEventBus().unregister(this);
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        toolbar.setLayoutParams(layoutParams);
    }
=======
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

>>>>>>> upstream/master

    @Subscribe
    public void sponsorDownloadDone(SponsorDownloadEvent event) {

        swipeRefreshLayout.setRefreshing(false);
        if (event.isState()) {
            sponsorsListAdapter.refresh();
            Timber.d("Refresh done");

        } else {
            if (getActivity() != null) {
                Snackbar.make(getView(), getActivity().getString(R.string.refresh_failed), Snackbar.LENGTH_LONG).setAction(R.string.retry_download, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refresh();
                    }
                }).show();
            }
            Timber.d("Refresh not done");

        }
    }

    private void refresh() {
        if (NetworkUtils.haveNetworkConnection(getActivity())) {
<<<<<<< HEAD
            if (NetworkUtils.isActiveInternetPresent()) {
                //Internet is working
                DataDownloadManager.getInstance().downloadSponsors();
            } else {
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
            Snackbar.make(getView(), getActivity().getString(R.string.refresh_failed), Snackbar.LENGTH_LONG).setAction(R.string.retry_download, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refresh();
                }
            }).show();
=======
            DataDownloadManager.getInstance().downloadSponsors();
        } else {
>>>>>>> upstream/master
            OpenEventApp.getEventBus().post(new SponsorDownloadEvent(true));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
