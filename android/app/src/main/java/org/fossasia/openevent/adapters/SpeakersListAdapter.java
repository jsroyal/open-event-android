package org.fossasia.openevent.adapters;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
=======
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
>>>>>>> upstream/master
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import org.fossasia.openevent.R;
import org.fossasia.openevent.activities.SpeakerDetailsActivity;
import org.fossasia.openevent.api.Urls;
import org.fossasia.openevent.data.Speaker;
import org.fossasia.openevent.dbutils.DbSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static org.fossasia.openevent.utils.SortOrder.sortOrderSpeaker;

/**
 * User: MananWason
 * Date: 11-06-2015
 */
<<<<<<< HEAD
public class SpeakersListAdapter extends BaseRVAdapter<Speaker, SpeakersListAdapter.RecyclerViewHolder> {
=======
public class SpeakersListAdapter extends BaseRVAdapter<Speaker, ViewHolder.Viewholder> implements FastScrollRecyclerView.SectionedAdapter{
>>>>>>> upstream/master

    private Activity activity;

    @SuppressWarnings("all")
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            DbSingleton instance = DbSingleton.getInstance();
            List<Speaker> trackList = instance.getSpeakerList(sortOrderSpeaker(activity));
            final ArrayList<Speaker> filteredSpeakerList = new ArrayList<>();
            String query = constraint.toString().toLowerCase(Locale.getDefault());
            for (Speaker speaker : trackList) {
                final String nameText = speaker.getName().toLowerCase(Locale.getDefault());
                final String organisationText = speaker.getOrganisation().toLowerCase(Locale.getDefault());
                final String countryText = speaker.getCountry().toLowerCase(Locale.getDefault());
                if (nameText.contains(query) ||
                        organisationText.contains(query) ||
                        countryText.contains(query)) {
                    filteredSpeakerList.add(speaker);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredSpeakerList;
            filterResults.count = filteredSpeakerList.size();
            Timber.d("Speaker filtering done total results %d", filterResults.count);
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            animateTo((List<Speaker>) results.values);
        }
    };

    public SpeakersListAdapter(List<Speaker> speakers, Activity activity) {
        super(speakers);
        this.activity = activity;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_speaker, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
<<<<<<< HEAD
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Speaker current = getItem(position);

        String photoUri = Urls.getBaseUrl() + current.getPhoto();
        Uri uri = Uri.parse(photoUri);

        Picasso.with(holder.speakerImage.getContext())
                .load(uri)
                .placeholder(R.drawable.ic_account_circle_grey_24dp)
                .into(holder.speakerImage);

        holder.speakerName.setText(TextUtils.isEmpty(current.getName()) ? "" : current.getName());
        holder.speakerDesignation.setText(String.format("%s%s", current.getPosition(), current.getOrganisation()));
        holder.speakerCountry.setText(String.format("%s", current.getCountry()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speakerName = current.getName();
                Intent intent = new Intent(activity, SpeakerDetailsActivity.class);
                intent.putExtra(Speaker.SPEAKER, speakerName);
                activity.startActivity(intent);
            }
        });
    }
=======
    public ViewHolder.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_speaker, parent, false);
        ViewHolder.Viewholder viewholder = new ViewHolder.Viewholder(view);

        viewholder.setImgView1((ImageView) view.findViewById(R.id.speaker_image));
        viewholder.setTxtView1((TextView) view.findViewById(R.id.speaker_name));
        viewholder.setTxtView2((TextView) view.findViewById(R.id.speaker_info));
        viewholder.setTxtView3((TextView) view.findViewById(R.id.speaker_info_country));
>>>>>>> upstream/master

    public void refresh() {
        clear();
        animateTo(DbSingleton.getInstance().getSpeakerList(sortOrderSpeaker(activity)));
    }

    protected class RecyclerViewHolder extends RecyclerView.ViewHolder {

<<<<<<< HEAD
        @BindView(R.id.speakers_list_image)
        ImageView speakerImage;

        @BindView(R.id.speakers_list_name)
        TextView speakerName;

        @BindView(R.id.speakers_list_designation)
        TextView speakerDesignation;
=======
        StringBuilder photoUri = new StringBuilder();
        photoUri.append(Urls.getBaseUrl()).append(current.getPhoto());
        Uri uri = Uri.parse(photoUri.toString());
        Picasso.with(holder.getImgView1().getContext()).load(uri)
                .placeholder(R.drawable.ic_account_circle_grey_24dp).transform(new CircleTransform()).into(holder.getImgView1());

        holder.getTxtView2().setText(String.format("%s%s", current.getPosition(), current.getOrganisation()));
        holder.getTxtView1().setText(TextUtils.isEmpty(current.getName()) ? "" : current.getName());
        holder.getTxtView3().setText(String.format("%s",current.getCountry()));

>>>>>>> upstream/master

        @BindView(R.id.speakers_list_country)
        TextView speakerCountry;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
<<<<<<< HEAD
}
=======

    @NonNull
    @Override
    public String getSectionName(int position) {
        Speaker sp=getItem(position);
        SharedPreferences prefsSort;
        prefsSort = PreferenceManager.getDefaultSharedPreferences(activity);
        switch (prefsSort.getInt("sortType", 0)) {
            case 0:
                return ""+sp.getName().charAt(0);
            case 1:
                return ""+sp.getOrganisation().charAt(0);
            case 2:
                return ""+sp.getCountry().charAt(0);
            default:
                return ""+sp.getName().charAt(0);
        }
    }
}
>>>>>>> upstream/master
