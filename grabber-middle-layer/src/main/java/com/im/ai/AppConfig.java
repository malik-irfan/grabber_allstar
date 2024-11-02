package com.im.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public ChatClient ollamaChatClient(ChatClient.Builder builder){
        return builder.build();
    }

}
