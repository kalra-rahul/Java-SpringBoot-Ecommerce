package com.example.demo.controller;

import com.example.demo.modal.ItemList;
import com.example.demo.service.ItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemListController {

    @Autowired
    private ItemListService itemListService;

    @GetMapping("/item-list")
    public List<ItemList> getItemList(){
        return itemListService.getItemList();
    }

    @PostMapping("/save-item-list")
    public String saveItemList(@RequestBody ItemList itemListData){
        return itemListService.saveItemList(itemListData);
    }

    @PutMapping("/save-item-list")
    public String updateItemList(@RequestBody ItemList itemListData){
        return itemListService.saveItemList(itemListData);
    }
}
