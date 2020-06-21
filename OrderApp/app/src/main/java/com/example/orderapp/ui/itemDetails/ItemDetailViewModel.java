package com.example.orderapp.ui.itemDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.CommentModel;
import com.example.orderapp.Model.StuffModel;

public class ItemDetailViewModel extends ViewModel {

    private MutableLiveData<StuffModel> stuffModelMutableLiveData;
    private MutableLiveData<CommentModel> commentModelMutableLiveData;

    public void setCommentModel(CommentModel commentModel){
        if(commentModelMutableLiveData != null){
            commentModelMutableLiveData.setValue(commentModel);
        }
    }

    public MutableLiveData<CommentModel> getCommentModelMutableLiveData() {
        return commentModelMutableLiveData;
    }

    public ItemDetailViewModel() {
        commentModelMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<StuffModel> getStuffModelMutableLiveData() {
        if (stuffModelMutableLiveData == null){
            stuffModelMutableLiveData = new MutableLiveData<>();
        }
        stuffModelMutableLiveData.setValue(Common.selectedStuff);
        return stuffModelMutableLiveData;
    }

    public void setStuffModel(StuffModel stuffModel) {
        if (stuffModelMutableLiveData!=null){
            stuffModelMutableLiveData.setValue(stuffModel);
        }
    }
}
