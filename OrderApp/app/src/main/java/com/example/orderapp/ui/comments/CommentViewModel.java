package com.example.orderapp.ui.comments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderapp.Model.CommentModel;

import java.util.List;

public class CommentViewModel extends ViewModel {

    private MutableLiveData<List<CommentModel>> mutableLiveDataStuffList;

    public CommentViewModel() {
        mutableLiveDataStuffList = new MutableLiveData<>();
    }

    public MutableLiveData<List<CommentModel>> getMutableLiveDataStuffList() {
        return mutableLiveDataStuffList;
    }

    public void setCommentList(List<CommentModel> commentList){
        mutableLiveDataStuffList.setValue(commentList);
    }
}
