package com.anime.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.anime.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3Client;

    private final Cloudinary cloudinary;

    private final String BUCKET_NAME = "datn-anime-store";

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(BUCKET_NAME, fileName, file.getInputStream(), metadata);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        amazonS3Client.setObjectAcl(BUCKET_NAME, fileName, CannedAccessControlList.PublicRead);
        return amazonS3Client.getResourceUrl(BUCKET_NAME, fileName);
    }

    @Override
    public String uploadFileCloudinary(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }


}
