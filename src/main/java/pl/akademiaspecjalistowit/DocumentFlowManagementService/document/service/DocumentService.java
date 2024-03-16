package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    List<DocumentDto> getAllDocuments();

    UUID saveDocument(MultipartFile fileRequest);

    byte[] downloadDocument(UUID documentId);
}
