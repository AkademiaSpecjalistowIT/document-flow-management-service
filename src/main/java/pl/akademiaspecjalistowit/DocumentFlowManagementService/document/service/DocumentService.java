package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    List<DocumentDto> getAllDocuments();

    UUID saveDocument(MultipartFile fileRequest);

    DownloadDocumentDto downloadDocument(UUID documentId);
}
