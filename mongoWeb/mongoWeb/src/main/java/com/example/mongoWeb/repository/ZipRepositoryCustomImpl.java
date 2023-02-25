package com.example.mongoWeb.repository;

import com.example.mongoWeb.data.PepData;
import com.example.mongoWeb.dto.NameStatisticDto;
import com.example.mongoWeb.dto.PepQueryDto;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class ZipRepositoryCustomImpl implements ZipRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<PepData> search(PepQueryDto query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPage(),
                query.getSize(),
                Sort.by(Sort.Direction.ASC, PepData.Fields.id)
        );
        Query mongoQuery = new Query().with(pageRequest);
        if (StringUtils.isNotBlank(query.getFirstName())) {
            mongoQuery.addCriteria(where(PepData.Fields.firstName).is(query.getFirstName()));
        }
        if (StringUtils.isNotBlank(query.getLastName())) {
            mongoQuery.addCriteria(where(PepData.Fields.lastName).is(query.getLastName()));
        }
        if (StringUtils.isNotBlank(query.getPatronymic())) {
            mongoQuery.addCriteria(where(PepData.Fields.patronymic).is(query.getPatronymic()));
        }

        final List<PepData> users = mongoTemplate.find(mongoQuery, PepData.class);

        return PageableExecutionUtils.getPage(
                users,
                pageRequest,
                () -> mongoTemplate.count((Query.of(mongoQuery).limit(-1).skip(-1)), PepData.class)
        );
    }

    @Override
    public List<NameStatisticDto> listOfTenMostPopularNames() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("isPep").is(true)),
                Aggregation.group("firstName").count().as("count").first("firstName").as("firstName"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(10)
        );
        return mongoTemplate.aggregate(
                aggregation, "pepData", NameStatisticDto.class).getMappedResults();
    }
}
