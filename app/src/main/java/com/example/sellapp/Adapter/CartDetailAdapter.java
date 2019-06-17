package com.example.sellapp.Adapter;

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
import java.util.List;

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.ViewHolder> {

    private List<ListProduct> mListCartDetail;
    private Context mContext;
    private OnCheckChange mCheckChange;
    private boolean isCheckAll;
    private int count = 0, countTemp = 0;
    private int mTotalPrice;

    public void setOnCheckChange(OnCheckChange checkChange) {
        this.mCheckChange = checkChange;
    }

    public CartDetailAdapter(List<ListProduct> ListCartDetail, Context Context, boolean isCheck, int total_price) {
        this.mListCartDetail = ListCartDetail;
        this.mContext = Context;
        this.isCheckAll = isCheck;
        this.mTotalPrice = total_price;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_recycler_view_cart_detail, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ListProduct product = mListCartDetail.get(i);

        holder.mTxtNameItemCart.setText(product.getProductName());
        holder.mTxtPriceCart.setText(FortmartPrice(product.getProductPrice()) + " VND");
        holder.mTxtPriceCart.setTag(product.getProductPrice());

        Bitmap imgItemCart = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.mImgCart.setImageBitmap(imgItemCart);
        holder.mImgRemoveCart.setTag(product.getId());
        holder.mCbCart.setTag(product.getId());

        if (isCheckAll) {
            holder.mCbCart.setChecked(true);
            this.countTemp++;
            this.mTotalPrice += Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
        }
        else {
            holder.mCbCart.setChecked(false);
            this.countTemp++;
        }

        if (this.countTemp == mListCartDetail.size()){
            if (isCheckAll)
                this.mCheckChange.CheckChange(true, this.mTotalPrice);
            else
                this.mCheckChange.CheckChange(false, this.mTotalPrice);
        }

        holder.mImgRemoveCart.setOnClickListener(v -> {
            Connect database = new Connect();
            database.Connection(mContext);
            showDialog(v.getTag().toString(), database, i, holder.mCbCart);
        });

        holder.mCbCart.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                this.count++;
                this.mTotalPrice += Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
                if (this.count == mListCartDetail.size())
                    mCheckChange.CheckChange(true, this.mTotalPrice);
                else mCheckChange.CheckChange(false, this.mTotalPrice);
            }
            else {
                this.mTotalPrice -= Integer.valueOf(holder.mTxtPriceCart.getTag().toString());
                mCheckChange.CheckChange(false, this.mTotalPrice);
                this.count--;
            }
        });
    }

    private void showDialog(String product_id, Connect db, int position, CheckBox cb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Xóa Sản Phẩm");
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không?");

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            boolean check = db.DeleteItemCart(product_id);
            if (check) {
                if (cb.isChecked()) {
                    Log.d("BBB", this.mTotalPrice + "\n");
                    this.mTotalPrice -= mListCartDetail.get(position).getProductPrice();
                    Log.d("BBB", this.mTotalPrice + "\n");
                    mListCartDetail.remove(position);
                    this.count--;
                    if (this.count == mListCartDetail.size())
                        this.mCheckChange.CheckChange(true, this.mTotalPrice);
                    else
                        this.mCheckChange.CheckChange(false, this.mTotalPrice);
                }else {
                    mListCartDetail.remove(position);
                    if (this.count == mListCartDetail.size())
                        this.mCheckChange.CheckChange(true, this.mTotalPrice);
                }
                notifyDataSetChanged();
            }
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
        TextView mTxtNameItemCart, mTxtPriceCart;
        ImageView mImgRemoveCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCbCart = itemView.findViewById(R.id.cb_item_cart);
            mImgCart = itemView.findViewById(R.id.img_item_cart);
            mTxtNameItemCart = itemView.findViewById(R.id.txt_item_cart);
            mTxtPriceCart = itemView.findViewById(R.id.txt_price_item_cart);
            mImgRemoveCart = itemView.findViewById(R.id.img_remove_item_cart);
        }
    }

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}
