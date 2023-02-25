package com.example.mongoWeb.servises;

import com.example.mongoWeb.data.PepData;
import com.example.mongoWeb.repository.ZipRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    @Autowired
    private MongoService mongoService;
    @Override
    public void unZip(MultipartFile multipartFile) {
        final String outDirectory = "F:\\Prodjects\\mongoWeb\\mongoWeb\\src\\main\\resources\\";

        if (multipartFile.isEmpty()) {
            System.out.println("File cannot be read");
            return;
        }

        File file = null;
        try {
            file = multipartToFile(multipartFile, multipartFile.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            ZipFile zip = new ZipFile(file);
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                //System.out.println(entry.getName());

                if (entry.isDirectory()) {
                    new File(file.getParent(), entry.getName()).mkdirs();
                } else {
                    write(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(
                                    new File(outDirectory, entry.getName()))));
                }
            }

            zip.close();
////            Enumeration entries = zip.entries();
//
//            ZipInputStream zip = new ZipInputStream(file.getInputStream());
//            ZipEntry entry = zip.getNextEntry();
//
//            while (entry != null) {
//                System.out.println(entry.getName());
//
//                if (entry.isDirectory()) {
//                    new File(file.getOriginalFilename(), entry.getName()).mkdirs();
//                } else {
//                    write(,
//                            new BufferedOutputStream(new FileOutputStream(
//                                    new File(outDirectory, entry.getName()))));
//                }
//                zip.getNextEntry();
//            }
//            zip.closeEntry();
//            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mongoService.insertData(convertJsonToData("pep.json"));
    }

    private static List<PepData> convertJsonToData(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PepData> listPepData;
        try {
            listPepData = objectMapper.readValue(Paths.get("src/main/resources/" + fileName).toFile(), new TypeReference<List<PepData>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(listPepData.size());
//        for (PepData pepData : listPepData) {
//            System.out.println(pepData);
//        }
        return listPepData;
    }


    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }

    private static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}
