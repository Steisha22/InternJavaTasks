package com.example.kafka.repository;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.kafka.data.MailData;
import com.example.kafka.data.MailStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MailRepository {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private final String indexName = "mails";

    public String createOrUpdateDocument(MailData mail) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(mail.getId())
                .document(mail)
        );
        if(response.result().name().equals("Created")){
            return response.id();
        }else if(response.result().name().equals("Updated")){
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }


    public List<MailData> searchErrorMails() {
        Criteria criteria = new Criteria(MailData.Fields.status)
                .is(MailStatus.ERROR);
        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<MailData> searchHits = elasticsearchOperations.search(searchQuery, MailData.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}

