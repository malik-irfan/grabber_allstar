package com.im.ai.grabber.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ALMCompanyAssistService {


    @Value("classpath:/prompt/assist_company_employee.st")
    private Resource assistTemplate;
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;


    public String custExpEmpLeaves(String message){
        List<Document> documents = vectorStore.similaritySearch(message);
        System.out.println(documents.size()+"  is the size of documents..................");
        String collect = documents.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));


        Message systemMessage = new SystemPromptTemplate(assistTemplate).createMessage(Map.of("documents", collect));
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        String resp = chatClient.prompt(prompt).call().content();
        System.out.println(resp);
        return resp;

    }
}
