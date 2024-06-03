package com.KDLST.Manager.Model.Service.UploadFile;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void store(MultipartFile file);

    void init();
}
