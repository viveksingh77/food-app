package com.vivekishere.foodapp.Utility;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class helper {
    public static final String MEAL_ID ="mealId";
    public static final String MEAL_NAME ="mealName";
    public static final String MEAL_THUMb = "mealThumb";
    public static final String CATEGORY_NAME = "category";
    public static void Toast(Context context , String message){
        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
    }
    public static void LoadImage(Context context , String url , ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }
}
