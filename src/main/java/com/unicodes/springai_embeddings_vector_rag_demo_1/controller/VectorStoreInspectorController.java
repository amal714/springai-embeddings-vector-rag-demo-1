package com.unicodes.springai_embeddings_vector_rag_demo_1.controller;


import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VectorStoreInspectorController {


    private final VectorStore vectorStore;

    public VectorStoreInspectorController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }


    @GetMapping("/vectorstore/inspect")
    public List<Document> inspectVectorStore(){
        //Retrieves top matching documents stored in the vector store.
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("*") // Match all documents
                        .topK(10) // Limit to 10 results
                        .similarityThreshold(0.0) // Return all results regardless of score
                        .build()
        );
    }


}
