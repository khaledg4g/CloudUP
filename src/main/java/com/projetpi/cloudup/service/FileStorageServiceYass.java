package com.projetpi.cloudup.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;
import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class FileStorageServiceYass {
    @Value("${application.file.upload.photo-output-path}")
    private String fileUploadPath;
    public String saveFile(@NonNull MultipartFile file,@NonNull Long idUser) {
        final String fileUploadSuhPath = "users" + separator + idUser;

        return uploadFile(file,fileUploadSuhPath);
    }

    private String uploadFile(MultipartFile file, String fileUploadSuhPath) {
            final String finalUploadPath = fileUploadPath + separator + fileUploadSuhPath;
            File targetFolder = new File(finalUploadPath);

            if (!targetFolder.exists()) {
                boolean folderCreated = targetFolder.mkdirs();
                if (!folderCreated) {
                    log.warn("Failed to create the target folder: " + targetFolder);
                    return null;
                }
            }
        final String fileExtension = getFileExtension(file.getOriginalFilename());

        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, file.getBytes());
            log.info("File saved to: " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }


    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
