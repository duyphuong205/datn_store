package com.anime.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
	String uploadFile(MultipartFile file);

	String uploadFileCloudinary(MultipartFile file) throws IOException;
}
