package com.smartfan.mobility.telemetry.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BehaviorEventMongoRepository extends MongoRepository<BehaviorEventDocument, String> {
}
