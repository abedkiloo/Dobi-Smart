package com.abedkiloo.abednego.dobismart.activities;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkiloo.abednego.dobismart.R;

import java.util.List;

/**
 * Created by abedkiloo on 8/29/17.
 */

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<JobSet> feedsDataSetList;
    Context context;


    public JobAdapter(Context g_context, List<JobSet> g_feedsDataSetslist) {
        this.feedsDataSetList = g_feedsDataSetslist;
        this.context = g_context;


    }

    @Override
    public FeedsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recy_answer_view, null);

        FeedsView feedsView = new FeedsView(v);

        return feedsView;


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        JobSet feedsDataSet = feedsDataSetList.get(position);
        FeedsView feed_image_view = (FeedsView) holder;
        feed_image_view.jobName.setText(feedsDataSet.getJobContent());
        feed_image_view.jobLoc.setText(feedsDataSet.getJobLocation());
        feed_image_view.jobDesc.setText(feedsDataSet.getJobName());

    }

    @Override
    public int getItemCount() {
        return feedsDataSetList.size();
    }


    public class FeedsView extends RecyclerView.ViewHolder {
        AppCompatTextView jobName, jobDesc, jobLoc;

        public FeedsView(View itemView) {
            super(itemView);
            jobName = itemView.findViewById(R.id.jobName);
            jobDesc = itemView.findViewById(R.id.jobDesc);
            jobLoc = itemView.findViewById(R.id.jobLoc);


        }
    }
}
