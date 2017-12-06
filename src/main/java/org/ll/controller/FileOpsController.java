package org.ll.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.ll.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/file")
public class FileOpsController {

    private static final Logger log = LoggerFactory.getLogger(FileOpsController.class);

    @Value("${file.tmpFolder}")
    private String tmpFolder;

    @Value("${file.salt}")
    private String salt;

    @PostMapping(value = "/upload.do", produces = "text/plain")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String fileFullPath = file.getOriginalFilename();
        String fileName = fileFullPath.substring(fileFullPath.lastIndexOf(File.separator) + 1);
        String fileID = Util.encodeToHex(fileName.substring(0, fileName.lastIndexOf(".")) + salt);
        Path folder = Paths.get(tmpFolder);
        Path targetFile = folder.resolve(fileID + fileName.substring(fileName.lastIndexOf(".")));
        try {
            if (!Files.exists(targetFile))
                Files.createFile(targetFile);
            Files.write(targetFile, file.getBytes());
        } catch (IOException e) {
            log.error("", e);
            return null;
        }
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return fileID;
    }

    @GetMapping("/download.do")
    public ResponseEntity<Resource> download() {
        Path cerPath = Paths.get("C:\\RTC\\workspace\\worm\\target\\classes", "worm.cer");
        Resource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(cerPath));
            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + cerPath.getFileName().toString() + "\"")
                .contentLength(cerPath.toFile().length()).contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
        } catch (IOException e) {
            log.error("", e);
            return (ResponseEntity<Resource>) ResponseEntity.notFound();
        }
    }

    @GetMapping("/preview.do")
    public ResponseEntity<Resource> preview(@RequestParam String fileID) {
        String contentType = "image/jpeg";
        Path imgPath = Paths.get(tmpFolder, fileID + ".jpg");
        if(!Files.exists(imgPath)){
            contentType = "image/png";
            imgPath = imgPath.resolveSibling(fileID + ".png");
        }
        Resource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(imgPath));
            return ResponseEntity.ok()
                .contentLength(imgPath.toFile().length())
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
        } catch (IOException e) {
            log.error("", e);
            return (ResponseEntity<Resource>) ResponseEntity.notFound();
        }
    }


}
