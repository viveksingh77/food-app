package com.vivekishere.foodapp.Retrofit;

import com.vivekishere.foodapp.Pojo.CategoryList;
import com.vivekishere.foodapp.Pojo.MealsByCategoryList;
import com.vivekishere.foodapp.Pojo.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("random.php")
    Call<MealResponse>getRandomFood();
    @GET("lookup.php?")
    Call<MealResponse> getMealInfo(@Query("i")String id);

    @GET("filter.php?")
    Call<MealsByCategoryList> getPopularItems(@Query("c") String CategoryName);
    @GET("categories.php")
    Call<CategoryList> getCategoryList();

    @GET("filter.php?")
    Call<MealsByCategoryList> getMealsByCategory(@Query("c") String CategoryName);
    @GET("search.php")
    Call<MealResponse> SearchMeals(@Query("s")String query);
}
