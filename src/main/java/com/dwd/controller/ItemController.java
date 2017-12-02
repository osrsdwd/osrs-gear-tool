package com.dwd.controller;

import com.dwd.model.osrsapi.Item;
import com.dwd.persistence.ItemRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getItems")
public class ItemController {

    private static final Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemRepository itemRepository;
    /**
     * Get every item from the api and save to the database
     *
     * @return a set containing all the retrieved items
     */
    @RequestMapping("/all")

    public List<Item> getAll(){

        return itemRepository.findAll();
    }
}
