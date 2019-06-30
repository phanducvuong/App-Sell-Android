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

import com.example.sellapp.Config;
import com.example.sellapp.Model.PromoModel.ListProductPromo;
import com.example.sellapp.R;
import com.example.sellapp.Util.ItemClickListener;
import com.example.sellapp.View.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductPromoAdapter extends RecyclerView.Adapter<ProductPromoAdapter.ViewHolder> {

    private List<ListProductPromo> mListPromoProduct;
    private Context mContext;
    private int mPercent;

    public ProductPromoAdapter(Context mContext, List<ListProductPromo> mListPromoProduct, int percent) {
        this.mListPromoProduct = mListPromoProduct;
        this.mContext = mContext;
        this.mPercent = percent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.custom_product_promotion, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Picasso.get()
                .load(Config.URL + mListPromoProduct.get(i).getProductPicBig())
                .placeholder(R.drawable.icon_home)
                .error(R.drawable.circle_bg)
                .into(holder.mImgProductPromo);

        holder.mTxtProductPromoName.setText(mListPromoProduct.get(i).getProductName());
        holder.mTxtProductPrice.setText(FortmartPrice(mListPromoProduct.get(i).getProductPrice()));

        int temp = PricePromo(mListPromoProduct.get(i).getProductPrice(), this.mPercent);
        holder.mTxtProductPricePromo.setText(FortmartPrice(temp));
        holder.mTxtPercent.setText("Sale " + mPercent + "%");

        holder.SetOnClickListener(((view, position) -> {
            Intent intent = new Intent(this.mContext, ProductDetailActivity.class);
            intent.putExtra("ProductID", mListPromoProduct.get(position).getmId());
            intent.putExtra("Percent", String.valueOf(this.mPercent));
            this.mContext.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return this.mListPromoProduct != null ? this.mListPromoProduct.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImgProductPromo;
        TextView mTxtProductPromoName, mTxtProductPrice, mTxtProductPricePromo, mTxtPercent;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProductPromo = itemView.findViewById(R.id.img_product_promo);
            mTxtProductPromoName = itemView.findViewById(R.id.txt_product_name_promo);
            mTxtProductPrice = itemView.findViewById(R.id.txt_product_price_old);
            mTxtProductPricePromo = itemView.findViewById(R.id.txt_product_price_promo);
            mTxtPercent = itemView.findViewById(R.id.txt_percent);

            itemView.setOnClickListener(this);
        }

        public void SetOnClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    private int PricePromo(Integer oldPrice, Integer percent) {
        return (oldPrice * (100 - percent) / 100);
    }

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}
