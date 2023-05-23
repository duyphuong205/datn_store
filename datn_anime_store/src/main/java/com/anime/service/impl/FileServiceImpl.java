package com.anime.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class FileServiceImpl implements FileService {

//	private final AmazonS3Client amazonS3Client;
//
//	private final String BUCKET_NAME = "";
//
//	@Override
//	public String uploadFile(MultipartFile file) {
//		String fileName = file.getOriginalFilename();
//		ObjectMetadata metadata = new ObjectMetadata();
//		metadata.setContentLength(file.getSize());
//		metadata.setContentType(file.getContentType());
//		try {
//			amazonS3Client.putObject(BUCKET_NAME, fileName, file.getInputStream(), metadata);
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//
//		amazonS3Client.setObjectAcl(BUCKET_NAME, fileName, CannedAccessControlList.PublicRead);
//		return amazonS3Client.getResourceUrl(BUCKET_NAME, fileName);
//	}

}
