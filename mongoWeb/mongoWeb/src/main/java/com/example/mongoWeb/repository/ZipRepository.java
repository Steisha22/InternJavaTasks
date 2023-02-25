package com.example.mongoWeb.repository;

import com.example.mongoWeb.data.PepData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ZipRepository extends MongoRepository<PepData, String>, ZipRepositoryCustom {

    @Override
    <S extends PepData> List<S> saveAll(Iterable<S> entities);

    @Override
    void deleteAll();
}
