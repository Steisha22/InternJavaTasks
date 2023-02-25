package com.example.mongoWeb.repository;

import com.example.mongoWeb.data.PepData;
import com.example.mongoWeb.dto.NameStatisticDto;
import com.example.mongoWeb.dto.PepQueryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ZipRepositoryCustom {
    Page<PepData> search(PepQueryDto query);

    List<NameStatisticDto> listOfTenMostPopularNames();
}
