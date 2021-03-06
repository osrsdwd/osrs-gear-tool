package com.dwd.persistence;

import com.dwd.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findByName(String name);
    List<Item> findByNameIn(List<String> names);
}
