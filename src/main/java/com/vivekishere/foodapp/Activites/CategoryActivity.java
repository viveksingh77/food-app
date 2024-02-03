package com.vivekishere.foodapp.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.vivekishere.foodapp.Adapters.StrCategoryAdapter;
import com.vivekishere.foodapp.Pojo.MealsByCategoryList;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.ViewModal.CategoryViewModal;
import com.vivekishere.foodapp.databinding.ActivityCategoryBinding;
import com.vivekishere.foodapp.databinding.StrcategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    CategoryViewModal categoryViewModal;
    StrCategoryAdapter strCategoryAdapter;
    String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categoryViewModal = ViewModelProviders.of(this).get(CategoryViewModal.class);
         categoryName = getIntent().getStringExtra(helper.CATEGORY_NAME);
        categoryViewModal.getMealByCategory(categoryName);
        ObserveCategoryData();
    }

    private void ObserveCategoryData() {
        categoryViewModal.ObserveCategoryLiveData().observe(this, new Observer<MealsByCategoryList>() {
            @Override
            public void onChanged(MealsByCategoryList mealsByCategoryList) {
                strCategoryAdapter = new StrCategoryAdapter(CategoryActivity.this , mealsByCategoryList.getMeals());
                binding.tvCategory.setText(categoryName+"("+strCategoryAdapter.getItemCount()+")");
                strCategoryAdapter.notifyDataSetChanged();
                OnItemClick();
                showRecyclerView();
            }
        });
    }
    private void showRecyclerView(){
        binding.recyclerView.setAdapter(strCategoryAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this , 2 , LinearLayoutManager.VERTICAL , false));
    }
    private void OnItemClick(){
        strCategoryAdapter .setOnItemClickListener(new StrCategoryAdapter.OnItemClickListener() {
            @Override
            public void OnClick(MealsItem mealsItem) {
                Intent intent = new Intent(CategoryActivity.this , MealActivity.class);
                intent.putExtra(helper.MEAL_ID , mealsItem.getIdMeal());
                intent.putExtra(helper.MEAL_NAME , mealsItem.getStrMeal());
                intent.putExtra(helper.MEAL_THUMb , mealsItem.getStrMealThumb());
                startActivity(intent);
            }
        });
    }


}