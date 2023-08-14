package com.anime.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudConfig {
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drfe81hce",
                "api_key", "819473826951997",
                "api_secret", "Ke59Zje_tnKQLDiGnmDEuXAilGU",
                "secure", true));
        return cloudinary;
    }
}
