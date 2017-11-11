package com.abedkiloo.abednego.dobismart.activities;

import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkiloo.abednego.dobismart.R;

import java.util.ArrayList;
import java.util.List;


public class  ArtisanJobFrag extends Fragment {
    RecyclerView jobRecy;
    LinearLayoutManager linearLayoutManager;
    List<JobSet> feedsDataSetList;
    JobAdapter feedsAdapter;
    JobSet feedsDataSet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.jobfrag, null);

        xml(v);

        return v;
    }

    private void xml(View v) {
        jobRecy = v.findViewById(R.id.jobRecycler);

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        jobRecy.setLayoutManager(linearLayoutManager);

        feedsDataSetList = new ArrayList<>();
        feedsAdapter = new JobAdapter(getActivity().getApplicationContext(), feedsDataSetList);
        jobRecy.setAdapter(feedsAdapter);


    }
    private void populate_list() {
        feedsDataSet = new JobSet();
        feedsDataSet.setJobName("Plumbing");
        feedsDataSet.setJobLocation("Garden Estate");
        feedsDataSet.setJobContent("Broken Sink");
        feedsDataSetList.add(feedsDataSet);

        feedsDataSet = new JobSet();
        feedsDataSet.setJobName("Plumbing");
        feedsDataSet.setJobLocation("Garden Estate");
        feedsDataSet.setJobContent("Broken Sink");
        feedsDataSetList.add(feedsDataSet);
    }

}
