package com.cong.lunarsurvey.config;

import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai.zhipu")
@Data
public class ZhiPuAiConfig {

    private String apiKey;

    @Bean
    public ClientV4 getClientV4() {
        return new ClientV4.Builder(apiKey).build();
    }
}