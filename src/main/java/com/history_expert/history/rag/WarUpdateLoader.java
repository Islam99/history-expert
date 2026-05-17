package com.history_expert.history.rag;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class WarUpdateLoader {
  private final VectorStore vectorStore;

  @Value("classpath:/US_ConflictWithIran.pdf")
  Resource dataFile;

  public WarUpdateLoader(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @PostConstruct
  public void loadPDF() {
    TikaDocumentReader tikaDoucmentReader = new TikaDocumentReader(dataFile);
    List<Document> docs = tikaDoucmentReader.get();
    TextSplitter textSplitter =
        TokenTextSplitter.builder().withChunkSize(200).withMaxNumChunks(400).build();
    vectorStore.add(textSplitter.split(docs));
  }
}
