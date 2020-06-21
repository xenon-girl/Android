package com.example.orderapp.ui.stuffList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.StuffModel;

import java.util.List;

public class StuffListViewModel extends ViewModel {

    private MutableLiveData<List<StuffModel>> stuffModelMutableLiveData;

    public StuffListViewModel() {

    }

    public MutableLiveData<List<StuffModel>> getStuffModelMutableLiveData() {
        if (stuffModelMutableLiveData == null){
            stuffModelMutableLiveData = new MutableLiveData<>();
        }
        stuffModelMutableLiveData.setValue(Common.categorySelected.getItems());
        return stuffModelMutableLiveData;
    }
}