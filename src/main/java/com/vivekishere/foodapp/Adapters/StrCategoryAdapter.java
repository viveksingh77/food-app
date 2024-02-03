package com.vivekishere.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.databinding.StrcategoryBinding;

import java.util.List;

public class StrCategoryAdapter extends RecyclerView.Adapter<StrCategoryAdapter.ViewHolder> {
    Context context;
    List<MealsItem> mealsItemList;

    public StrCategoryAdapter(Context context, List<MealsItem> mealsItemList) {
        this.context = context;
        this.mealsItemList = mealsItemList;
    }

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void OnClick(MealsItem mealsItem);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.strcategory , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        helper.LoadImage(context , mealsItemList.get(position).getStrMealThumb() , holder.binding.categorySrc);
        holder.binding.tvCategory.setText(mealsItemList.get(position).getStrMeal());
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.OnClick(mealsItemList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mealsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        StrcategoryBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StrcategoryBinding.bind(itemView);
        }
    }
}
