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
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.R;
import com.example.sellapp.Util.ItemClickListener;
import com.example.sellapp.View.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductHighlightAdapter extends RecyclerView.Adapter<ProductHighlightAdapter.RecycleViewHolder> {

    private List<ListProduct> mListProduct;
    private Context mContext;

    public ProductHighlightAdapter(List<ListProduct> mListProduct, Context mContext) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater mLayoutInflate = LayoutInflater.from(mContext);
        View mItemView = mLayoutInflate.inflate(R.layout.custom_recycleview_product_highlight_layout, viewGroup, false);

        return new RecycleViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder mHolder, int i) {

        Picasso.get()
                .load(Config.URL + mListProduct.get(i).getProductPicBig())
                .error(R.drawable.color_cursor)
                .placeholder(R.drawable.icon_home)
                .into(mHolder.mImgProduct);

        mHolder.mTxtProductName.setText(mListProduct.get(i).getProductName());
        mHolder.mTxtProductPrice.setText(FortmartPrice(mListProduct.get(i).getProductPrice()));

        mHolder.setItemClickListener((view, position) -> {
            Intent mIntent = new Intent(this.mContext, ProductDetailActivity.class);
            mIntent.putExtra("ProductID", mListProduct.get(position).getId());
            mIntent.putExtra("ProductName", mListProduct.get(position).getProductName());
            this.mContext.startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImgProduct;
        TextView mTxtProductName;
        TextView mTxtProductPrice;

        private ItemClickListener mItemClickListener;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProduct = itemView.findViewById(R.id.img_product);
            mTxtProductName = itemView.findViewById(R.id.txt_product_name);
            mTxtProductPrice = itemView.findViewById(R.id.txt_product_price);

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

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}
