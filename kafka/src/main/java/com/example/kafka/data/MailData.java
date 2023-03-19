package com.example.kafka.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldNameConstants
@Document(indexName="mails")
public class MailData {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private MailStatus status;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String consumerEmail;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String errorMessage = "";

    @Field(type = FieldType.Keyword)
    private List<String> transactionIds;

    public boolean hasTransactionId(String transactionId) {
        return transactionIds != null && transactionIds.contains(transactionId);
    }

    public void addTransactionId(String transactionId) {
        if (this.transactionIds == null) {
            this.transactionIds = new ArrayList<>(2);
        }
        this.transactionIds.add(transactionId);
    }
}
