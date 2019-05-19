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
import com.example.sellapp.Model.CategoryMenuModel.ListCateMenu;
import com.example.sellapp.R;
import com.example.sellapp.Util.ItemClickListener;
import com.example.sellapp.View.ProductByCateMenuActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CateMenuAdapter extends RecyclerView.Adapter<CateMenuAdapter.ViewHolder> {

    private Context mContext;
    private List<ListCateMenu> mListCateMenu;

    public CateMenuAdapter(Context mContext, List<ListCateMenu> mListCateMenu) {
        this.mContext = mContext;
        this.mListCateMenu = mListCateMenu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mLayoutInflate = LayoutInflater.from(viewGroup.getContext());
        View v = mLayoutInflate.inflate(R.layout.custom_cate_menu_layout, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.mTxtCateMenu.setText(mListCateMenu.get(i).getmCateMenuName());
        Picasso.get()
                .load(Config.URL + mListCateMenu.get(i).getmCateMenuPic())
                .placeholder(R.drawable.icon_home)
                .error(R.drawable.circle_bg)
                .into(viewHolder.mImgCateMenu);

        viewHolder.setItemClickListener((view, position) -> {
            Intent mIntent = new Intent(this.mContext, ProductByCateMenuActivity.class);
            mIntent.putExtra("ID", mListCateMenu.get(position).getmId());
            mIntent.putExtra("CateMenuName", mListCateMenu.get(position).getmCateMenuName());
            this.mContext.startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mListCateMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener mItemClickListener;

        ImageView mImgCateMenu;
        TextView mTxtCateMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgCateMenu = itemView.findViewById(R.id.mImgCateMenu);
            mTxtCateMenu = itemView.findViewById(R.id.mTxtCateMenu);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.mItemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
