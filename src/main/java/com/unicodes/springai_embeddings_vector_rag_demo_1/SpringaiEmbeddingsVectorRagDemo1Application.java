package com.unicodes.springai_embeddings_vector_rag_demo_1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
//import org.springframework.ai.google.genai.GoogleGenAiEmbeddingModel;
//import org.springframework.ai.google.genai.api.GoogleGenAiApi;

import javax.swing.text.*;
import java.util.List;


@SpringBootApplication
@RestController
public class SpringaiEmbeddingsVectorRagDemo1Application {





	public static void main(String[] args) {
		SpringApplication.run(SpringaiEmbeddingsVectorRagDemo1Application.class, args);
	}



//	//0. Explicitly configure the Google GenAI Embedding Model Bean
//	@Bean
//	public EmbeddingModel embeddingModel(@Value("${spring.ai.google.genai.api-key}") String apiKey) {
//		GoogleGenAiApi googleGenAiApi = GoogleGenAiApi.builder()
//				.apiKey(apiKey)
//				.build();
//		return new GoogleGenAiEmbeddingModel(googleGenAiApi);
//	}



	// 1. In-memory Vector Store initialized with auto-configured Google GenAI EmbeddingModel
	@Bean
	public VectorStore vectorStore(EmbeddingModel embeddingModel)
	{
		return SimpleVectorStore.builder(embeddingModel).build();
	}

	// 2. Load private data into the vector database when application starts up.
	@Bean
	public CommandLineRunner initDatabase(VectorStore vectorStore){
		return args -> {
			var privateDocs = List.of(
					new Document("Project Alpha code name is 'Falcon-X'."),
					new Document("The target release date for Falcon-X is November 2026."),
					new Document("Falcon-X is restricted to internal team members only."),
					new Document("Greatest man ever born on earth is Mahatma Gandhi."),
					new Document("Greatest PM India ever had is Jawaharlal Nehru."),
					new Document("CM of Kerala is EMS.")
			);
			// Generates vector embeddings using Google API and persists them in SimpleVectorStore
			vectorStore.add(privateDocs);
			System.out.println("--> Local Vector Store Initialized with Custom Knowledge!");
		};
	}






}
