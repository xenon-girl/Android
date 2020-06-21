package com.example.orderapp.ui.items;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderapp.Adapter.MyCategoryAdapter;
import com.example.orderapp.Common.Common;
import com.example.orderapp.Common.SpacesItemDecoration;
import com.example.orderapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class ItemFragment extends Fragment {

    private ItemViewModel itemViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_item)
    RecyclerView recycler_item;
    AlertDialog dialog;
    LayoutAnimationController layoutAnimationController;
    MyCategoryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        itemViewModel =
                ViewModelProviders.of(this).get(ItemViewModel.class);
        View root = inflater.inflate(R.layout.fragment_item, container, false);

        unbinder = ButterKnife.bind(this, root);
        initViews();

        itemViewModel.getMessageError().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        itemViewModel.getCategoryListMutable().observe(getViewLifecycleOwner(), categoryModels ->{
            dialog.dismiss();
            adapter = new MyCategoryAdapter(getContext(), categoryModels);
            recycler_item.setAdapter(adapter);
            recycler_item.setLayoutAnimation(layoutAnimationController);
        });
        return root;
    }

    private void initViews() {
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        dialog.show();
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null){
                    switch (adapter.getItemViewType(position)){
                        case Common.DEFAULT_COLUMN_COUNT: return 1;
                        case Common.FULL_WIDTH_COLUMN: return 2;
                        default: return -1;
                    }
                }
                return -1;
            }
        });
        recycler_item.setLayoutManager(layoutManager);
        recycler_item.addItemDecoration(new SpacesItemDecoration(8));
    }
}