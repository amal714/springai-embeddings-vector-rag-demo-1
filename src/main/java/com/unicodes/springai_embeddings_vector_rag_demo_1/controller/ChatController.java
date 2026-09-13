package com.unicodes.springai_embeddings_vector_rag_demo_1.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {


    private ChatClient chatClient;

    // Inject ChatClient.Builder and VectorStore through Constructor Injection
    public ChatController(ChatClient.Builder chatClientBuilder, VectorStore vectorStore){
        this.chatClient = chatClientBuilder
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }

    // 3. Combined RAG Controller Endpoint
    @GetMapping("/ask")
    public String askAI(@RequestParam String question){

        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }


}
