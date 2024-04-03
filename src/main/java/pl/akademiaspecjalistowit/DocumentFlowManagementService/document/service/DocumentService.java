package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.NewEventInput;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    List<DocumentResponse> getAllDocuments();

    UUID createDocument(DocumentCreationInput input, NewEventInput eventInput);

    DownloadDocumentDto downloadDocument(UUID documentId);
}
