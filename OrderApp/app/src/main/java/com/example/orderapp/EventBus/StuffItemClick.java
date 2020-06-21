package com.example.orderapp.EventBus;

import com.example.orderapp.Model.StuffModel;

public class StuffItemClick {
    private boolean success;
    private StuffModel stuffModel;

    public StuffItemClick(boolean success, StuffModel stuffModel) {
        this.success = success;
        this.stuffModel = stuffModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public StuffModel getStuffModel() {
        return stuffModel;
    }

    public void setStuffModel(StuffModel stuffModel) {
        this.stuffModel = stuffModel;
    }
}
