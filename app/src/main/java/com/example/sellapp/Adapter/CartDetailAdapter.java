package com.example.sellapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellapp.Model.CartModel.Connect;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.R;
import com.example.sellapp.Util.ItemClickListener;
import com.example.sellapp.Util.OnCheckChange;
import com.example.sellapp.View.CartDetailActivity;
import com.example.sellapp.View.HomeActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.ViewHolder> {

    private List<ListProduct> mListCartDetail;
    private Context mContext;
    private OnCheckChange mCheckChange;
    private boolean isCheckAll;
    private int count = 0;
    private int mTotalPrice;
    private Connect db;
    private List<String> mListProduct;

    public void setOnCheckChange(OnCheckChange checkChange) {
        this.mCheckChange = checkChange;
    }

    public CartDetailAdapter(List<ListProduct> ListCartDetail, Context Context, boolean isCheck, int total_price, Connect _db, List<String> list_product) {
        this.mListCartDetail = ListCartDetail;
        this.mContext = Context;
        this.isCheckAll = isCheck;
        this.mTotalPrice = total_price;
        this.db = _db;
        this.mListProduct = list_product;
        this.mListProduct = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_recycler_view_cart_detail, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ListProduct product = mListCartDetail.get(i);

        holder.mTxtNameItemCart.setText(product.getProductName());
        holder.mTxtNameItemCart.setTag(i);
        holder.mTxtPriceCart.setText(FortmartPrice(product.getProductPrice()) + " VND");
        holder.mTxtPriceCart.setTag(product.getProductPrice());

        Bitmap imgItemCart = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.mImgCart.setImageBitmap(imgItemCart);
        holder.mImgRemoveCart.setTag(product.getId());
        holder.mCbCart.setTag(product.getId());
        holder.mTxtAmount.setText(String.valueOf(product.getAmount()));
        holder.mTxtAmount.setTag(product.getId());
        holder.mImgIconMinus.setTag(product.getId());
        holder.mImgIconPlus.setTag(product.getId());

        if (isCheckAll){
            this.count++;
            holder.mCbCart.setChecked(true);
            int tempPrice = Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
            int tempAmount = Integer.valueOf(holder.mTxtAmount.getText().toString());
            this.mTotalPrice += (tempPrice * tempAmount);
            this.mListProduct.add(mListCartDetail.get(i).getId());
            if (this.count == mListCartDetail.size())
                mCheckChange.CheckChange(true, this.mTotalPrice, this.mListProduct);
        }
        else {
            this.mTotalPrice = 0;
            this.mListProduct = new ArrayList<>();
            holder.mCbCart.setChecked(false);
            mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
        }

        holder.mImgRemoveCart.setOnClickListener(v -> showDialog(v.getTag().toString(), db, i, holder.mCbCart, holder.mTxtAmount));

        holder.mCbCart.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                this.count++;
                int tempPrice = Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
                int tempAmount = Integer.valueOf(holder.mTxtAmount.getText().toString());
                this.mTotalPrice += (tempPrice * tempAmount);
                this.mListProduct.add(holder.mCbCart.getTag().toString());
                if (this.count == mListCartDetail.size())
                    mCheckChange.CheckChange(true, this.mTotalPrice, this.mListProduct);
                else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
            }
            else {
                int tempPrice = Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
                int tempAmount = Integer.valueOf(holder.mTxtAmount.getText().toString());
                this.mTotalPrice -= (tempPrice * tempAmount);
                this.mListProduct.remove(holder.mCbCart.getTag());
                mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
                this.count--;
            }
        });

        holder.mImgIconMinus.setOnClickListener(v -> {
            int tempAmount = Integer.valueOf(holder.mTxtAmount.getText().toString());
            int tempPrice = Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
            tempAmount--;
            if (tempAmount > 0 ){
                holder.mTxtAmount.setText(String.valueOf(tempAmount));
                if (holder.mCbCart.isChecked()) {
                    this.mTotalPrice -= tempPrice;
                    if (this.count == mListCartDetail.size())
                        mCheckChange.CheckChange(true, this.mTotalPrice, this.mListProduct);
                    else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
                }else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
                db.Connection(mContext);
                db.updateAmountInCart(holder.mImgIconMinus.getTag().toString(), tempAmount);
            }
        });
        holder.mImgIconPlus.setOnClickListener(v -> {
            int tempAmount = Integer.valueOf(holder.mTxtAmount.getText().toString());
            int tempPrice = Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
            tempAmount++;
            holder.mTxtAmount.setText(String.valueOf(tempAmount));
            if (holder.mCbCart.isChecked()) {
                this.mTotalPrice += tempPrice;
                if (this.count == mListCartDetail.size())
                    mCheckChange.CheckChange(true, this.mTotalPrice, this.mListProduct);
                else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
            } else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
            db.updateAmountInCart(holder.mImgIconPlus.getTag().toString(), tempAmount);
        });
    }

    private void showDialog(String product_id, Connect db, int position, CheckBox cb, TextView txt_amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Xóa Sản Phẩm");
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không?");

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            if (cb.isChecked()) {
                int tempPrice = mListCartDetail.get(position).getProductPrice();
                int tempAmount = Integer.valueOf(txt_amount.getText().toString());
                this.mTotalPrice -= (tempPrice * tempAmount);
                this.mListProduct.remove(position);
                if (this.count == mListCartDetail.size()) {
                    this.count = mListCartDetail.size() - 1;
                    mCheckChange.CheckChange(true, this.mTotalPrice, this.mListProduct);
                }
                else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
                mListCartDetail.remove(position);
                this.count--;
            }else {
                mListCartDetail.remove(position);
                if (this.count == mListCartDetail.size()) mCheckChange.CheckChange(true, this.mTotalPrice, this.mListProduct);
                else mCheckChange.CheckChange(false, this.mTotalPrice, this.mListProduct);
            }
            notifyItemRemoved(position);
            db.DeleteItemCart(product_id);
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mListCartDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox mCbCart;
        ImageView mImgCart;
        TextView mTxtNameItemCart, mTxtPriceCart, mTxtAmount;
        ImageView mImgRemoveCart;
        ImageView mImgIconMinus, mImgIconPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCbCart = itemView.findViewById(R.id.cb_item_cart);
            mImgCart = itemView.findViewById(R.id.img_item_cart);
            mTxtNameItemCart = itemView.findViewById(R.id.txt_item_cart);
            mTxtPriceCart = itemView.findViewById(R.id.txt_price_item_cart);
            mImgRemoveCart = itemView.findViewById(R.id.img_remove_item_cart);
            mTxtAmount = itemView.findViewById(R.id.txt_amount_cart_detail);
            mImgIconMinus = itemView.findViewById(R.id.img_minus_cart_detail);
            mImgIconPlus = itemView.findViewById(R.id.img_plus_cart_detail);
        }
    }

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}
