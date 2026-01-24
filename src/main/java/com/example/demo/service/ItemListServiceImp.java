package com.example.demo.service;

import com.example.demo.modal.ItemList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemListServiceImp implements ItemListService{
    Integer id = 1;
    List<ItemList> itemList = new ArrayList<>();

    @Override
    public List<ItemList> getItemList() {
        return itemList;
    }

    @Override
    public String saveItemList(ItemList itemListData) {
        itemListData.setListId(String.valueOf(++id));
        itemList.add(itemListData);
        return "List added successfully";
    }
}
