package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.repository.DocumentRepository;

import java.util.List;

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

}
