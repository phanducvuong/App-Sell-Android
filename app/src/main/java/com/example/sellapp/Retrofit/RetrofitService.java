package com.example.sellapp.Retrofit;

import com.example.sellapp.Model.CategoryModel.Category;
import com.example.sellapp.Model.ProductModel.Product;
import com.example.sellapp.Model.SlideModel.Slide;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("tat-ca-the-loai")
    Observable<Category> getCategory();

    @GET("slide")
    Observable<Slide> getSlide();

    @GET("san-pham")
    Observable<Product> getProductHighlight();
}
