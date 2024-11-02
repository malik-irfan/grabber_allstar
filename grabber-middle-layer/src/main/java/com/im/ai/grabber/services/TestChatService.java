package com.im.ai.grabber.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
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
public class TestChatService {

    private final ChatClient chatClient;

    @Autowired
    VectorStore vectorStore;

    @Value("classpath:/prompt/acme_programming.st")
    private Resource acmeProgrammingPromptTemplate;


    @Value("classpath:/prompt/spring_temp.st")
    private Resource assistTemplate;

    public TestChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    public String springChat(String message){
        // Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.query(message).withTopK(5));

        System.out.println(results.size()+"  is the size of documents..................");
        String collect = results.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));


       // Message systemMessage = new SystemPromptTemplate(assistTemplate).createMessage(Map.of("input", message, "content", collect));
        //UserMessage userMessage = new UserMessage("Spring");
        //Prompt prompt = new Prompt(List.of(systemMessage));

        PromptTemplate promptTemplate = new PromptTemplate(assistTemplate);

        Prompt prompt = promptTemplate.create(Map.of("input", message, "content", collect));

        String resp = chatClient.prompt(prompt).call().content();
        System.out.println(resp);
        return resp;

    }

    public String chatwithme(String query){
        String myContent = """
        Tom is a programmer working for Acme Programming.

        Tom is under paid.
        
        Bob is a programmer working for Acme Programming.
        
        Bob is paid more than Tom.
        
        Acme Programming is a consulting company that employs programmers.
        
        """;


       /* String message = """
            You are an AI assistant that can answer your questions. Use the content provided. If you don't know
            the answer, don't make suggestions just say "I don't know the answer".
            
            content: {content}

            question: {input}
            """;*/

        //PromptTemplate promptTemplate = new PromptTemplate(message);
        PromptTemplate promptTemplate = new PromptTemplate(acmeProgrammingPromptTemplate);

        Prompt prompt = promptTemplate.create(Map.of("input", query, "content", myContent));

        return chatClient.prompt(prompt).call().content();
    }
}
