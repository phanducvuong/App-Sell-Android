package com.example.sellapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sellapp.Model.PromoModel.ListPromotion;
import com.example.sellapp.R;
import com.example.sellapp.Util.ItemClickListener;
import com.example.sellapp.Util.OnChangePromo;

import java.util.List;

public class MenuPromoAdapter extends RecyclerView.Adapter<MenuPromoAdapter.ViewHolder> {

    private List<ListPromotion> mListPromo;
    private Context mContext;
    private OnChangePromo mOnChangePromo;

    public void setOnChangePromo(OnChangePromo onChangePromo) {
        this.mOnChangePromo = onChangePromo;
    }

    public MenuPromoAdapter(Context mContext, List<ListPromotion> mListPromo) {
        this.mListPromo = mListPromo;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.custom_promotion_list_name_layout, viewGroup, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.mTxtPromoName.setText(this.mListPromo.get(i).getmName());

        if (i == 0) holder.mTxtPromoName.setTextColor(Color.RED);

        holder.setItemClickListener((view, position) -> this.mOnChangePromo.ChangePromo(position));
    }

    @Override
    public int getItemCount() {
        return this.mListPromo == null ? 0 : this.mListPromo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTxtPromoName;
        ItemClickListener itemClickListener;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtPromoName = itemView.findViewById(R.id.txt_promotion_name);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
