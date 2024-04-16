package pl.akademiaspecjalistowit.documentflowmanagementservice.document.service;

import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.NewEventInput;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    List<DocumentResponse> getAllDocuments();

    UUID createDocument(DocumentCreationInput input, NewEventInput eventInput);

    DownloadDocumentDto downloadDocument(UUID documentId);
}
