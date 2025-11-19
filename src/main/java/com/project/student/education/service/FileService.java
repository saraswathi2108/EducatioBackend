package com.project.student.education.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    public String uploadImage(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        String filePath = path + File.separator + name;

        File files = new File(path);
        if (!files.exists()) {
            boolean created = files.mkdirs();
            System.out.println("Folder created: " + created + " Path: " + path);
        }

        System.out.println("Uploading file to: " + filePath);

        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (Exception e) {
            System.out.println("UPLOAD ERROR: " + e.getMessage());
            e.printStackTrace();   // THIS will show root cause
            throw e;
        }

        return name;
    }
}