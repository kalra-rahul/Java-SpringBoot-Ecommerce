package com.example.demo.service;

import com.example.demo.modal.ItemList;

import java.util.List;

public interface ItemListService {
    public List<ItemList> getItemList();
    public String saveItemList(ItemList itemListData);
}
