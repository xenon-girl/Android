package com.example.orderapp.Model;

import java.util.List;

public class CategoryModel {
    private String item_id, name, image;
    List<StuffModel> items;

    public CategoryModel() {

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<StuffModel> getItems() {
        return items;
    }

    public void setItems(List<StuffModel> items) {
        this.items = items;
    }

}
