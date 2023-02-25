package com.example.mongoWeb.servises;

import com.example.mongoWeb.data.PepData;
import com.example.mongoWeb.dto.NameStatisticDto;
import com.example.mongoWeb.dto.PageDto;
import com.example.mongoWeb.dto.PepInfoDto;
import com.example.mongoWeb.dto.PepQueryDto;

import java.util.List;

public interface MongoService {
    void insertData(List<PepData> pepDataList);

    void deleteAll();

    PageDto<PepInfoDto> search(PepQueryDto query);

    List<NameStatisticDto> listOfTenMostPopularNames();
}
