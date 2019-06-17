package com.example.sellapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sellapp.Model.CommentModel.ListComment;
import com.example.sellapp.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<ListComment> mListComment;
    private boolean isCheck;

    public CommentAdapter(Context mContext, List<ListComment> mListComment, boolean isCheck) {
        this.mContext = mContext;
        this.mListComment = mListComment;
        this.isCheck = isCheck;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.custom_layout_recycler_comment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTxtTitleComment.setText(mListComment.get(i).getmTitle());
        viewHolder.mTxtContentComment.setText(mListComment.get(i).getmContent());
        viewHolder.mTxtPostedComment.setText("Được đánh giá bởi " + mListComment.get(i).getmDeviceName() + "\nNgày " + mListComment.get(i).getmDateComment());
        viewHolder.mRtbComment.setRating(Float.valueOf(mListComment.get(i).getmNumberStar()));
    }

    @Override
    public int getItemCount() {
        if (isCheck) {
            return mListComment.size();
        } else if (mListComment.size() > 0) {
            return 3;
        }else return mListComment.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtTitleComment, mTxtPostedComment, mTxtContentComment;
        RatingBar mRtbComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtTitleComment = itemView.findViewById(R.id.txt_title_commentR);
            mTxtPostedComment = itemView.findViewById(R.id.txt_posted_commentR);
            mTxtContentComment = itemView.findViewById(R.id.txt_content_commentR);
            mRtbComment = itemView.findViewById(R.id.rtb_commentR);
        }
    }
}
