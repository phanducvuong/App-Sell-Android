package com.example.sellapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellapp.Config;
import com.example.sellapp.Model.CategoryModel.ListCategory;
import com.example.sellapp.R;
import com.example.sellapp.View.FashionByMenuActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecycleViewHolder> {

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    private List<ListCategory> mListCategory;
    private Context context;

    public CategoryAdapter(List<ListCategory> mListCategory, Context context) {
        this.mListCategory = mListCategory;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater mInflate = LayoutInflater.from(viewGroup.getContext());
        View mView = mInflate.inflate(R.layout.custom_category_recylayout, viewGroup, false);

        return new RecycleViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder mHolder, int i) {

        mHolder.mTxtCate.setText(mListCategory.get(i).getmCateName());
        Picasso.get()
                .load(Config.URL + mListCategory.get(i).getmCatePic())
                .placeholder(R.drawable.icon_home)
                .error(R.drawable.circle_bg)
                .into(mHolder.mImgCate);

        mHolder.setItemClickListener((view, position) -> {

            Intent mIntent = new Intent(this.context, FashionByMenuActivity.class);
            mIntent.putExtra("ID", mListCategory.get(position).getId());
            mIntent.putExtra("CateName", mListCategory.get(position).getmCateName());
            this.context.startActivity(mIntent);

        });
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }


    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemClickListener itemClickListener;

        ImageView mImgCate;
        TextView mTxtCate;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgCate = itemView.findViewById(R.id.mImgCate);
            mTxtCate = itemView.findViewById(R.id.mTxtCate);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
