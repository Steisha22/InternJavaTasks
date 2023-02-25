package com.example.mongoWeb.servises;

import com.example.mongoWeb.data.PepData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void unZip (MultipartFile file);
}
