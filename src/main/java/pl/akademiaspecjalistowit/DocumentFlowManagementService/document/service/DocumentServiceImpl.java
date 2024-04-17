package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.NewEventInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEventEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentNotFoundException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentProcessingException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.mapper.DocumentMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity.createNewDocument;
import static pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEventEntity.createEvent;

@Service
@AllArgsConstructor
class DocumentServiceImpl implements DocumentService {
    private final DocumentDataService documentDataService;
    private final DocumentEventService documentEventService;

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
        DownloadDocumentDto DocumentDto = DocumentMapper.downloadDtoFromEntity(document);
        DocumentDto = documentEventService.appendEventsToPDF(DocumentDto, document.getEvents());
        return DocumentDto;
    }

    @Override
    @Transactional
    public UUID createDocument(DocumentCreationInput documentInput, NewEventInput eventInput) {
        try {
            DocumentEntity documentEntity = createNewDocument(documentInput.getFile(),
                    documentInput.getFileName(),
                    documentInput.getDescription(),
                    documentInput.getDocumentType(),
                    documentInput.getDeadline());
            DocumentEventEntity event = createEvent(eventInput.getIssuer(),
                    eventInput.getEventType(),
                    eventInput.getEventReason(),
                    eventInput.getEventDescription(),
                    documentEntity);
            documentEntity.addEvent(event);
            documentDataService.saveDocument(documentEntity);
            return documentEntity.getDocumentId();
        } catch (IOException e) {
            throw new DocumentProcessingException();
        }
    }
}
