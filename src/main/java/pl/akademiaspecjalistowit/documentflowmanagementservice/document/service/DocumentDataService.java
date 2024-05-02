package pl.akademiaspecjalistowit.documentflowmanagementservice.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.repository.DocumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class DocumentDataService {
    private final DocumentRepository documentRepository;

    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
    }

    public void saveDocument(DocumentEntity documentEntity) {
        documentRepository.save(documentEntity);
    }

    public Optional<DocumentEntity> getDocument(UUID documentId) {
        return documentRepository.findByDocumentId(documentId);
    }
}
