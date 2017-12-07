package com.dwd.persistence;

import com.dwd.model.Build;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildRepository extends MongoRepository<Build, Long>{
    Build findByBuildName(String name);
}
