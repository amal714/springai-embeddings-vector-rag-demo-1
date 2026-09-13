# ⚡ Spring AI Embeddings & Vector RAG Demo

[![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring--AI-1.0-blue?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-ai)
[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Google GenAI](https://img.shields.io/badge/Google--GenAI-Gemini--Flash-4285F4?style=for-the-badge&logo=google&logoColor=white)](https://ai.google.dev/)

> A lightweight, production-ready implementation of **Retrieval-Augmented Generation (RAG)** built with **Spring AI**, **Google GenAI (Gemini)**, and an **In-Memory Vector Store**.

---

## 📌 Overview

This project demonstrates how to ground Large Language Models (LLMs) with private, domain-specific data using Spring AI. By combining in-memory vector storage, text embeddings, and intelligent request advisors, the application enables the LLM to accurately answer questions about internal knowledge without fine-tuning or model retraining.

---

## 🏗 System Architecture

The workflow consists of two main phases: **Document Ingestion** (Application Startup) and the **Retrieval-Augmented Query Pipeline** (Runtime API Requests).

```mermaid
flowchart TD
    %% Custom Styling
    classDef storage fill:#2d3748,stroke:#4a5568,stroke-width:2px,color:#fff;
    classDef process fill:#1a365d,stroke:#2b6cb0,stroke-width:2px,color:#fff;
    classDef external fill:#2c5282,stroke:#4299e1,stroke-width:2px,color:#fff;
    classDef client fill:#276749,stroke:#38a169,stroke-width:2px,color:#fff;

    subgraph PHASE1 [" Phase 1: Ingestion Pipeline Startup "]
        direction LR
        A[📄 Raw Private Documents] -->|CommandLineRunner| B[🤖 Embedding Model Google GenAI]
        B -->|768-dim Embeddings| C[(🧠 SimpleVectorStore In-Memory)]
    end

    subgraph PHASE2 [" Phase 2: RAG Query Pipeline Runtime "]
        direction TB
        Client[👤 Client GET /ask] :::client -->|1. Prompt Question| Controller[🎮 ChatController] :::process
        
        subgraph ADVISOR [" ChatClient Advisor Pipeline "]
            Controller -->|2. Intercept Query| QAAdvisor[🧩 QuestionAnswerAdvisor] :::process
            QAAdvisor -->|3. Similarity Search| VectorDB[(🧠 SimpleVectorStore)] :::storage
            VectorDB -->|4. Top Matching Docs| QAAdvisor
            QAAdvisor -->|5. Augment System Prompt| LLM[🤖 Google Gemini API] :::external
        end

        LLM -->|6. Grounded Answer| Controller
        Controller -->|7. JSON / Plain Text Response| Client
    end

    class C,VectorDB storage;
    class A,B,Controller,QAAdvisor process;
    class LLM external;