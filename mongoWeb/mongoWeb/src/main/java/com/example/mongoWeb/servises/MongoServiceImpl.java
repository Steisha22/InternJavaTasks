package com.example.mongoWeb.servises;

import com.example.mongoWeb.data.PepData;
import com.example.mongoWeb.dto.NameStatisticDto;
import com.example.mongoWeb.dto.PageDto;
import com.example.mongoWeb.dto.PepInfoDto;
import com.example.mongoWeb.dto.PepQueryDto;
import com.example.mongoWeb.repository.ZipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{

    @Autowired
    private ZipRepository zipRepository;

    @Override
    public void insertData(List<PepData> pepDataList) {
        zipRepository.saveAll(pepDataList);
    }

    @Override
    public void deleteAll() {
        zipRepository.deleteAll();
    }

    @Override
    public PageDto<PepInfoDto> search(PepQueryDto query) {
        Page<PepData> page = zipRepository.search(query);
        return PageDto.fromPage(page, this::toInfoDto);
    }

    @Override
    public List<NameStatisticDto> listOfTenMostPopularNames() {
        return zipRepository.listOfTenMostPopularNames();
    }

    private PepInfoDto toInfoDto(PepData data) {
        return PepInfoDto.builder()
                .id(data.getId())
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .patronymic(data.getPatronymic())
                .isPep(data.isPep())
                .build();
    }
}
