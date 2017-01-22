package com.example.jaehyeong.exercise;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jae Hyeong on 2017-01-22.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    ArrayList<Labels> labels;
    Context mcontext;

    public ItemAdapter(Context context) {
        this.mcontext = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setItems(ArrayList<Labels> labels) {
        this.labels = labels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Labels label = labels.get(position);
        holder.mTitle.setText(label.title);
        Uri uri = Uri.parse(label.user_avatar);
        Context context = holder.mAvatar.getContext();
        Picasso.with(context).load(uri).into(holder.mAvatar);
        Log.d("Adapting", uri.toString());

        holder.userName.setText(label.user_name);
        holder.commentCount.setText(String.valueOf(label.comments) + " comments");
        holder.issueNumber.setText("Issue no. " + String.valueOf(label.issue_number));
    }

    @Override
    public int getItemCount() {
        if(labels == null) return 0;
        return labels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        private TextView mTitle;
        private ImageView mAvatar;
        private TextView userName;
        private TextView commentCount;
        private TextView issueNumber;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);
            mAvatar = (ImageView) v.findViewById(R.id.avatar);
            userName = (TextView) v.findViewById(R.id.user_name);
            commentCount = (TextView) v.findViewById(R.id.comment_count);
            issueNumber = (TextView) v.findViewById(R.id.issue_number);
        }
    }
}
