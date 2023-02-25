package com.example.mongoWeb.controller;

import com.example.mongoWeb.dto.NameStatisticDto;
import com.example.mongoWeb.dto.PageDto;
import com.example.mongoWeb.dto.PepQueryDto;
import com.example.mongoWeb.dto.PepInfoDto;
import com.example.mongoWeb.servises.FileServiceImpl;
import com.example.mongoWeb.servises.MongoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mongoWeb")
@RequiredArgsConstructor
public class MongoController {
    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private MongoServiceImpl mongoService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        mongoService.deleteAll();
        fileService.unZip(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/_search")
    public PageDto<PepInfoDto> search(@RequestBody PepQueryDto query) {
        return mongoService.search(query);
    }

    @GetMapping("/popularNames")
    public List<NameStatisticDto> listOfTenMostPopularNames() {
        return mongoService.listOfTenMostPopularNames();
    }
}
