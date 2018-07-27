package org.ll.service;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Value("${file.tmpFolder}")
    private String folder;
    
    public File getImage(String imageID){
        return Paths.get(folder, imageID + ".jpg").toFile();
    }
}
