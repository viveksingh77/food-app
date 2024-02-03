package com.vivekishere.foodapp.ViewModal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vivekishere.foodapp.Pojo.MealsByCategoryList;
import com.vivekishere.foodapp.Retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModal extends ViewModel {
    private static final String TAG = "CategoryViewModal";
    private MutableLiveData<MealsByCategoryList> mutableLiveData = new MutableLiveData<>();
    public LiveData<MealsByCategoryList> ObserveCategoryLiveData() {
        return mutableLiveData;
    }

    public void getMealByCategory(String CategoryName){
        RetrofitInstance.getInstance()
                .retrofitApi()
                .getMealsByCategory(CategoryName)
                .enqueue(new Callback<MealsByCategoryList>() {
                    @Override
                    public void onResponse(Call<MealsByCategoryList> call, Response<MealsByCategoryList> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            mutableLiveData.setValue(response.body());
                        }else {
                            Log.d(TAG, "onResponse: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsByCategoryList> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }

}
