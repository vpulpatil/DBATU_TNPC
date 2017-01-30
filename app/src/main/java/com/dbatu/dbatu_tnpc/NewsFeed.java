package com.dbatu.dbatu_tnpc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Keyur on 10-1-2017.
 */

public class NewsFeed extends Fragment {

    private TextView cardmail;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.news_feed,container,false);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Student");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.newsdetails);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Student, NewsFeedViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Student, NewsFeedViewHolder>(
                Student.class,
                R.layout.news_feed,
                NewsFeedViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(NewsFeedViewHolder viewHolder, Student model, int position) {

                viewHolder.setEmail(model.getEmail());

            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private class  NewsFeedViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public NewsFeedViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            cardmail = (TextView)mView.findViewById(R.id.studentemail);
        }

        public void setEmail(String email){
            cardmail.setText(email);
        }

    }

}
