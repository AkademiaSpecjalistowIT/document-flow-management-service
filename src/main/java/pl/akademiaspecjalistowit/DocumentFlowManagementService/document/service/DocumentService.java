package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentResponse;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    List<DocumentResponse> getAllDocuments();

    UUID saveDocument(MultipartFile fileRequest);

    DownloadDocumentDto downloadDocument(UUID documentId);
}
