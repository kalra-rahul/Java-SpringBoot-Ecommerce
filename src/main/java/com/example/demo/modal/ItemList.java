package com.example.demo.modal;

public class ItemList{
    private String listId;
    private String ListName;

    public ItemList(String size, String itemName) {
        listId = size;
        ListName = itemName;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }

}
