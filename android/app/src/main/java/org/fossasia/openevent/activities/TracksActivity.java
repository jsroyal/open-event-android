package org.fossasia.openevent.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.fossasia.openevent.R;
import org.fossasia.openevent.adapters.SessionsListAdapter;
import org.fossasia.openevent.dbutils.DbSingleton;
import org.fossasia.openevent.utils.ConstantStrings;

import butterknife.BindView;
<<<<<<< HEAD
=======
import butterknife.ButterKnife;
import butterknife.OnClick;
>>>>>>> upstream/master

/**
 * User: MananWason
 * Date: 14-06-2015
 */
public class TracksActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    final private String SEARCH = "org.fossasia.openevent.searchText";

    private SessionsListAdapter sessionsListAdapter;

    private String track;

    private String searchText = "";

    private SearchView searchView;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView sessionsRecyclerView;
<<<<<<< HEAD
    @BindView(R.id.txt_no_sessions) TextView noSessionsView;
=======
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.txt_no_sessions) TextView noSessionsView;
    @BindView(R.id.backdrop) ImageView backdrop1;

    @OnClick(R.id.share_fab)
    public void share(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, Urls.WEB_APP_URL_BASIC + Urls.SESSIONS);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getString(R.string.share_links)));
    }
>>>>>>> upstream/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD

=======
        setContentView(R.layout.activity_tracks);

        ButterKnife.bind(this);

        DbSingleton dbSingleton = DbSingleton.getInstance();
        track = getIntent().getStringExtra(ConstantStrings.TRACK);
>>>>>>> upstream/master
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
<<<<<<< HEAD
=======
        loadImage();
        collapsingToolbar.setTitle(track);
        sessionsListAdapter = new SessionsListAdapter(dbSingleton.getSessionbyTracksname(track));
        sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sessionsRecyclerView.setAdapter(sessionsListAdapter);
        sessionsListAdapter.setOnClickListener(new SessionsListAdapter.SetOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Session model = sessionsListAdapter.getItem(position);
                String sessionName = model.getTitle();
                Intent intent = new Intent(getApplicationContext(), SessionDetailActivity.class);
                intent.putExtra(ConstantStrings.SESSION, sessionName);
                intent.putExtra(ConstantStrings.TRACK, track);
                startActivity(intent);
            }
        });
        sessionsRecyclerView.setItemAnimator(new DefaultItemAnimator());

>>>>>>> upstream/master
        if (savedInstanceState != null && savedInstanceState.getString(SEARCH) != null) {
            searchText = savedInstanceState.getString(SEARCH);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        DbSingleton dbSingleton = DbSingleton.getInstance();
        track = getIntent().getStringExtra(ConstantStrings.TRACK);
        if (!TextUtils.isEmpty(track))
            toolbar.setTitle(track);

        //setting the grid layout to cut-off white space in tablet view
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = (int) (width/250.00);

        sessionsRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,spanCount);
        sessionsRecyclerView.setLayoutManager(gridLayoutManager);
        sessionsListAdapter = new SessionsListAdapter(this, dbSingleton.getSessionbyTracksname(track));
        sessionsRecyclerView.setAdapter(sessionsListAdapter);
        sessionsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (sessionsListAdapter.getItemCount() != 0) {
            noSessionsView.setVisibility(View.GONE);
            sessionsRecyclerView.setVisibility(View.VISIBLE);
        } else {
            noSessionsView.setVisibility(View.VISIBLE);
            sessionsRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_tracks;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (searchView != null) {
            bundle.putString(SEARCH, searchText);
        }
        super.onSaveInstanceState(bundle);
    }

<<<<<<< HEAD
=======
    private void loadImage() {
        DbSingleton dbSingleton = DbSingleton.getInstance();
        Track current = dbSingleton.getTrackbyName(track);

        if (current.getImage().length() != 0) {
            Picasso.with(getApplicationContext()).load(current.getImage()).into(backdrop1);

        }
    }

>>>>>>> upstream/master
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_sessions:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_tracks, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search_tracks).getActionView();
        searchView.setOnQueryTextListener(this);
        if (searchText != null) {
            searchView.setQuery(searchText, false);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (!TextUtils.isEmpty(query)) {
            sessionsListAdapter.setTrackName(track);
            sessionsListAdapter.getFilter().filter(query);
        } else {
            sessionsListAdapter.setTrackName(track);
            sessionsListAdapter.refresh();
        }
        searchText = query;
        return true;
    }
}