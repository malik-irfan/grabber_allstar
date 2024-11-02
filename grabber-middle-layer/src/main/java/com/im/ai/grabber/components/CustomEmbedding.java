package com.im.ai.grabber.components;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomEmbedding {

    @Autowired
    private VectorStore vectorStore;

    public void processData(){
        List<Document> documents = List.of(
                new Document("Tom is a programmer working for Acme Programming.", Map.of("meta1", "meta1")),
                new Document("Tom is under paid."),
                new Document("Bob is a programmer working for Acme Programming.", Map.of("meta2", "meta2")),
                new Document("Bob is paid more than Tom.", Map.of("meta3", "meta3")),
                new Document("Acme Programming is a consulting company that employs programmers.", Map.of("meta4", "meta4")));

        // Add the documents to PGVector
        vectorStore.add(documents);
        System.out.println("embedding done!!! ---------------");
    }
}
