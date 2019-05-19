package com.example.sellapp.Adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductByCateIDAdapter extends RecyclerView.Adapter<ProductByCateIDAdapter.ViewHolder> {

    private Context mContext;
    private List<ListProduct> mListProduct;

    public ProductByCateIDAdapter(Context mContext, List<ListProduct> mListProduct) {
        this.mContext = mContext;
        this.mListProduct = mListProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_product_by_cate_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder mHolder, int i) {

        Picasso.get()
                .load(Config.URL + mListProduct.get(i).getProductPicBig())
                .error(R.drawable.color_cursor)
                .placeholder(R.drawable.icon_home)
                .into(mHolder.mImgProduct);

        mHolder.mTxtProductName.setText(mListProduct.get(i).getProductName());
        mHolder.mTxtProductPrice.setText(FortmartPrice(mListProduct.get(i).getProductPrice()));

        mHolder.SetItemClickListener((view, position) -> Toast.makeText(mContext, mListProduct.get(position).getProductName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return this.mListProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImgProduct;
        TextView mTxtProductName;
        TextView mTxtProductPrice;

        ItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgProduct = itemView.findViewById(R.id.img_product);
            mTxtProductName = itemView.findViewById(R.id.txt_product_name);
            mTxtProductPrice = itemView.findViewById(R.id.txt_product_price);

            itemView.setOnClickListener(this);
        }

        public void SetItemClickListener(ItemClickListener itemClickListener) {
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
