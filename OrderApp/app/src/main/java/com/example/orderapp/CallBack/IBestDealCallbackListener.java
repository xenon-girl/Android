package com.example.orderapp.CallBack;

import com.example.orderapp.Model.BestDealModel;
import com.example.orderapp.Model.PopularCategoryModel;

import java.util.List;

public interface IBestDealCallbackListener {
    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}
