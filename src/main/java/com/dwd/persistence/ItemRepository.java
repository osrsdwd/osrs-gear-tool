package com.dwd.persistence;

import com.dwd.model.osrsapi.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findItemByName(String name);
}
