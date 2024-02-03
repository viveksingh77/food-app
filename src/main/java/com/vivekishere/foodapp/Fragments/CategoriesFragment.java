package com.vivekishere.foodapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.vivekishere.foodapp.Activites.CategoryActivity;
import com.vivekishere.foodapp.Adapters.CategoriesAdapter;
import com.vivekishere.foodapp.Pojo.CategoriesItem;
import com.vivekishere.foodapp.Pojo.CategoryList;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.ViewModal.HomeViewModal;
import com.vivekishere.foodapp.databinding.FragmentCategoriesBinding;

public class CategoriesFragment extends Fragment {
    FragmentCategoriesBinding binding ;
    CategoriesAdapter categoriesAdapter;
    private HomeViewModal homeViewModal;

    public CategoriesFragment() {
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
        // Inflate the layout for this fragment
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        binding = FragmentCategoriesBinding.inflate(inflater , container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModal.getCategories();
        ObserveCategoryLiveData();
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


    private void ObserveCategoryLiveData() {
        homeViewModal.ObserveCategories().observe(getViewLifecycleOwner(), new Observer<CategoryList>() {
            @Override
            public void onChanged(CategoryList categoryList) {
                categoriesAdapter = new CategoriesAdapter(getContext() , categoryList.getCategories());
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 2 , LinearLayoutManager.VERTICAL , false));
                binding.recyclerView.setAdapter(categoriesAdapter);
                OnCategoryItemClick();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getActivity().getWindow().setStatusBarColor(getContext().getResources().getColor(R.color.black_shade_3 , null));
    }
}