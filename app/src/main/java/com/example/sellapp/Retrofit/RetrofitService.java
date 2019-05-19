package com.example.sellapp.Retrofit;

import android.content.Intent;

import com.example.sellapp.Model.CategoryMenuModel.CategoryMenu;
import com.example.sellapp.Model.CategoryModel.Category;
import com.example.sellapp.Model.ProductModel.Product;
import com.example.sellapp.Model.SlideModel.Slide;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("tat-ca-the-loai")
    Observable<Category> getCategory();

    @GET("the-loai-danh-muc/{cate_id}")
    Observable<CategoryMenu> getCateMenu(@Path("cate_id") String cate_id);

    @GET("slide")
    Observable<Slide> getSlide();

    @GET("san-pham")
    Observable<Product> getProductHighlight();

    @GET("san-pham-theo-the-loai/{cate_id}")
    Observable<Product> getProductByCateMenuId(@Path("cate_id") String cate_id);

    @GET("san-pham-theo-the-loai-asc/{cate_id}")
    Observable<Product> getProductAsc(@Path("cate_id") String cate_id);

    @GET("san-pham-theo-the-loai-des/{cate_id}")
    Observable<Product> getProductDes(@Path("cate_id") String cate_id);

    @GET("san-pham-moi-nhat/{cate_id}")
    Observable<Product> getProductNewest(@Path("cate_id") String cate_id);

    @GET("san-pham-theo-danh-muc/{menu_id}")
    Observable<Product> getProductByMenuId(@Path("menu_id") String menu_id);
}
