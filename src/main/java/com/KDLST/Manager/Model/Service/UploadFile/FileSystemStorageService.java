package com.KDLST.Manager.Model.Service.UploadFile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    

    public FileSystemStorageService() {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        this.rootLocation = Paths.get("D:/newProject/new11/KDLSTManager/src/main/resources/static/images");
    }
    

    @Override
    public void store(MultipartFile file) {
        try {

            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
