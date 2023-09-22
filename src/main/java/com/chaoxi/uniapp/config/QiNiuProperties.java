package com.chaoxi.uniapp.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "qi-niu-cloud")
@EnableConfigurationProperties
public class QiNiuProperties {

    private String accessKey;
    private String accessSecret;
    private String bucketName;
    private String hostName;
}
