package com.dwd.persistence;

import com.dwd.model.build.Build;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildRepository extends MongoRepository<Build, Long>{
    Build findByBuildName(String name);
}
