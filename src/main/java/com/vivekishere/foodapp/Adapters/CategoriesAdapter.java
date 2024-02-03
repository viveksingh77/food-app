package com.vivekishere.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekishere.foodapp.Pojo.CategoriesItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.databinding.CategoryItemBinding;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewModal> {
    Context context;
    List<CategoriesItem> categoriesItems;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void OnClick(CategoriesItem categoriesItem);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CategoriesAdapter(Context context, List<CategoriesItem> categoriesItems) {
        this.context = context;
        this.categoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewModal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewModal(LayoutInflater.from(context).inflate(R.layout.category_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewModal holder, int position) {
        helper.LoadImage(context, categoriesItems.get(position).getStrCategoryThumb(),holder.binding.categoryImg);
        holder.binding.tvCategory.setText(categoriesItems.get(position).getStrCategory());
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.OnClick(categoriesItems.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categoriesItems.size();
    }

    public class ViewModal extends RecyclerView.ViewHolder {
        CategoryItemBinding binding ;
        public ViewModal(@NonNull View itemView) {
            super(itemView);
            binding = CategoryItemBinding.bind(itemView);
        }
    }
}
