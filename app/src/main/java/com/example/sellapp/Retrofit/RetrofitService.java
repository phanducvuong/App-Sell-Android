package com.example.sellapp.Retrofit;

import android.content.Intent;

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

    @GET("slide")
    Observable<Slide> getSlide();

    @GET("san-pham")
    Observable<Product> getProductHighlight();

    @GET("san-pham-theo-the-loai/{cate_id}")
    Observable<Product> getProductByCateId(@Path("cate_id") String cate_id);
}
