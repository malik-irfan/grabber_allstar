package com.im.ai.grabber.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AllStarService {

    private final ChatClient chatClient;
    @Autowired
    VectorStore vectorStore;


    @Value("classpath:/prompt/all_star_assist_services.st")
    private Resource allStarServicesAssist;

    public AllStarService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    public String getServicesDetail(String query){
        // Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.query(query).withTopK(5));
        System.out.println("Retrieved Doc lists: "+results.size());
        String collect = results.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));

        PromptTemplate promptTemplate = new PromptTemplate(allStarServicesAssist);
        Prompt prompt = promptTemplate.create(Map.of("input", query, "content", collect));

        String resp = chatClient.prompt(prompt).call().content();
        System.out.println(resp);
        return resp;

    }
}
