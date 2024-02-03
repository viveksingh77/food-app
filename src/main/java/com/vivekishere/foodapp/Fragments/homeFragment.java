package com.vivekishere.foodapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivekishere.foodapp.Activites.CategoryActivity;
import com.vivekishere.foodapp.Activites.MealActivity;
import com.vivekishere.foodapp.Adapters.CategoriesAdapter;
import com.vivekishere.foodapp.Adapters.PopularAdapter;
import com.vivekishere.foodapp.Pojo.CategoriesItem;
import com.vivekishere.foodapp.Pojo.CategoryList;
import com.vivekishere.foodapp.Pojo.MealsByCategoryList;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Retrofit.RetrofitInstance;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.ViewModal.HomeViewModal;
import com.vivekishere.foodapp.databinding.FragmentHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment {
    FragmentHomeBinding binding;
    private HomeViewModal homeViewModal;
     MealsItem mealsItemH;
     PopularAdapter popularAdapter ;
     CategoriesAdapter categoriesAdapter;


    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModal = ViewModelProviders.of(this).get(HomeViewModal.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater , container , false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //for Random item
        homeViewModal.getRandomMeal();
        observeRandomMeal();
        onRandomMealClick();
        //for popularItems
        homeViewModal.getPopularItem();
        ObservePopularliveData();
        //for categories
        homeViewModal.getCategories();
       ObserveCategoriesLiveData();
       OnSearchIconClick();
    }

    private void OnSearchIconClick() {
        binding.searchButton.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_homeAF_to_searchFragment);
        });
    }

    private void OnCategoryItemClick() {
       categoriesAdapter.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
           @Override
           public void OnClick(CategoriesItem categoriesItem) {
               Intent intent = new Intent(getContext() , CategoryActivity.class);
               intent.putExtra(helper.CATEGORY_NAME , categoriesItem.getStrCategory());
               startActivity(intent);
           }
       });
    }

    private void CategoryItemRecyclerView() {
        binding.rcCategories.setLayoutManager(new GridLayoutManager(getContext(),3 , LinearLayoutManager.VERTICAL , false));
        binding.rcCategories.setAdapter(categoriesAdapter);
    }
    private void ObserveCategoriesLiveData() {
        homeViewModal.ObserveCategories().observe(getViewLifecycleOwner(), new Observer<CategoryList>() {
            @Override
            public void onChanged(CategoryList categoryList) {
                categoriesAdapter = new CategoriesAdapter(getContext() , categoryList.getCategories());
                CategoryItemRecyclerView();
                OnCategoryItemClick();
            }
        });
    }
    private void OnPopularItemClick() {
       popularAdapter.setOnPopularMealClickListener(new PopularAdapter.OnPopularMealClickListener() {
           @Override
           public void OnPopularClick(MealsItem mealsItem) {
               Intent intent = new Intent(getContext() , MealActivity.class);
               intent.putExtra(helper.MEAL_ID , mealsItem.getIdMeal());
               intent.putExtra(helper.MEAL_NAME, mealsItem.getStrMeal());
               intent.putExtra(helper.MEAL_THUMb , mealsItem.getStrMealThumb());
               startActivity(intent);

           }
       });
    }
    private void OnPopularLongClick(){
        popularAdapter.setOnLongMealCLickListener(new PopularAdapter.OnLongMealCLickListener() {
            @Override
            public void OnLongClick(MealsItem mealsItem) {
                Bundle bundle = new Bundle();
                bundle.putString(helper.MEAL_ID ,mealsItem.getIdMeal());
                LongTouchBS longTouchBS = new LongTouchBS();
                longTouchBS.setArguments(bundle);
                longTouchBS.show(getActivity().getSupportFragmentManager(), null);
            }
        });
    }
    private void PopularItemRecyclerView() {
        binding.recyclerViewP.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));
        binding.recyclerViewP.setAdapter(popularAdapter);
    }
    private void ObservePopularliveData() {
        homeViewModal.ObserveCategoryList().observe(getViewLifecycleOwner(), new Observer<MealsByCategoryList>() {
            @Override
            public void onChanged(MealsByCategoryList mealsByCategoryList) {
               popularAdapter = new PopularAdapter(getContext() , mealsByCategoryList.getMeals());
               popularAdapter.notifyDataSetChanged();
                PopularItemRecyclerView();
                OnPopularItemClick();
                OnPopularLongClick();
            }
        });
    }
    private void onRandomMealClick() {
        binding.cardRandomImg.setOnClickListener(view -> {
            Intent intent = new Intent(getContext() , MealActivity.class);
            intent.putExtra(helper.MEAL_ID , mealsItemH.getIdMeal());
            intent.putExtra(helper.MEAL_NAME, mealsItemH.getStrMeal());
            intent.putExtra(helper.MEAL_THUMb , mealsItemH.getStrMealThumb());
            startActivity(intent);
        });
    }
    private void observeRandomMeal() {
        homeViewModal.observeRandomMeal().observe(getViewLifecycleOwner(), mealsItem -> {
            helper.LoadImage(getContext() , mealsItem.getStrMealThumb() , binding.cardRandomImg);
            mealsItemH = mealsItem;
        });
}}