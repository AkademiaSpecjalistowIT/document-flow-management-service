package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentNotFoundException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentProcessingException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.mapper.DocumentMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity.createNewDocument;

@Service
@AllArgsConstructor
class DocumentServiceImpl implements DocumentService {
    private final DocumentDataService documentDataService;

    @Override
    public List<DocumentResponse> getAllDocuments() {
        return documentDataService.getAllDocuments()
                .stream()
                .map(DocumentMapper::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DownloadDocumentDto downloadDocument(UUID documentId) {
        DocumentEntity document = documentDataService.getDocument(documentId).orElseThrow(
                () -> new DocumentNotFoundException(documentId.toString())
        );
        return DocumentMapper.downloadDtoFromEntity(document);
    }

    @Override
    @Transactional
    public UUID saveDocument(DocumentCreationInput input) {
        try {
            DocumentEntity documentEntity = createNewDocument(input.getFile(), input.getFileName(),
                    input.getDescription(), input.getDocumentType(), input.getDeadline());
            documentDataService.saveDocument(documentEntity);
            return documentEntity.getDocumentId();
        } catch (IOException e) {
            throw new DocumentProcessingException();
        }
    }
}
