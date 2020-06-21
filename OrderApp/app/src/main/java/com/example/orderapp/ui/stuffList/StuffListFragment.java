package com.example.orderapp.ui.stuffList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderapp.Adapter.MyCategoryAdapter;
import com.example.orderapp.Adapter.MyStuffListAdapter;
import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.StuffModel;
import com.example.orderapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StuffListFragment extends Fragment {

    private StuffListViewModel stuffListViewModel;
    Unbinder unbinder;
    @BindView(R.id.recycler_stuff_list)
    RecyclerView recycler_stuff_list;

    LayoutAnimationController layoutAnimationController;
    MyStuffListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        stuffListViewModel =
                ViewModelProviders.of(this).get(StuffListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stuff_list, container, false);

        unbinder = ButterKnife.bind(this, root);
        initViews();
        stuffListViewModel.getStuffModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<StuffModel>>() {
            @Override
            public void onChanged(List<StuffModel> stuffModels) {
                adapter = new MyStuffListAdapter(getContext(), stuffModels);
                recycler_stuff_list.setAdapter(adapter);
                recycler_stuff_list.setLayoutAnimation(layoutAnimationController);
            }
        });
        return root;
    }

    private void initViews() {

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(Common.categorySelected.getName());

        recycler_stuff_list.setHasFixedSize(true);
        recycler_stuff_list.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
    }
}