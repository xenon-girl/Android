package com.example.orderapp.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderapp.CallBack.IBestDealCallbackListener;
import com.example.orderapp.CallBack.IPopularCallbackListener;
import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.BestDealModel;
import com.example.orderapp.Model.PopularCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IPopularCallbackListener, IBestDealCallbackListener {

//    private MutableLiveData<String> mText;

    private MutableLiveData<List<PopularCategoryModel>> popularList;
    private MutableLiveData<String> messageError;
    private IPopularCallbackListener popularCallbackListener;

    // for best deals
    private MutableLiveData<List<BestDealModel>> bestDealList;
    private IBestDealCallbackListener bestDealCallbackListener;

    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");

        popularCallbackListener = this;
        bestDealCallbackListener = this;
    }

    //Getter function best deals
    public MutableLiveData<List<BestDealModel>> getBestDealList() {

        if (bestDealList == null){
            bestDealList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();

            loadBestDealList();
        }
        return bestDealList;
    }

    private void loadBestDealList() {
        final List<BestDealModel> tempList = new ArrayList<>();
        final DatabaseReference bestDealRef = FirebaseDatabase.getInstance().getReference(Common.BEST_DEALS_REF);
        bestDealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapShot: dataSnapshot.getChildren()) {
                    BestDealModel model = itemSnapShot.getValue(BestDealModel.class);
                    tempList.add(model);
                }
                bestDealCallbackListener.onBestDealLoadSuccess(tempList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                bestDealCallbackListener.onBestDealLoadFailed(databaseError.getMessage());
            }
        });
    }

    // Getter function Popular Categories
    public MutableLiveData<List<PopularCategoryModel>> getPopularList() {
        if (popularList == null){
            popularList = new MutableLiveData<>();
            messageError = new MutableLiveData<>();

            loadPopularList();
        }
        return popularList;
    }

    private void loadPopularList() {
        final List<PopularCategoryModel> tempList = new ArrayList<>();
        final DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference(Common.POPULAR_CATEGORY_REF);
        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapShot: dataSnapshot.getChildren()) {
                    PopularCategoryModel model = itemSnapShot.getValue(PopularCategoryModel.class);
                    tempList.add(model);
                }
                popularCallbackListener.onPopularLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularCallbackListener.onPopularLoadFailed(databaseError.getMessage());
            }
        });

    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels) {

        popularList.setValue(popularCategoryModels);
    }

    @Override
    public void onPopularLoadFailed(String message) {

        messageError.setValue(message);
    }

    @Override
    public void onBestDealLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealList.setValue(bestDealModels);
    }

    @Override
    public void onBestDealLoadFailed(String message) {
        messageError.setValue(message);
    }


}