package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.mapper.DocumentMapper;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util.PDFValidator.isPDF;

@Service
@AllArgsConstructor
class DocumentServiceImpl implements DocumentService {
    private final DocumentDataService documentDataService;

    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentDataService.getAllDocuments()
                .stream()
                .map(DocumentMapper::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UUID saveDocument(MultipartFile fileRequest) {
            validateDocument(fileRequest);
            DocumentEntity documentEntity = generateDocumentEntity(fileRequest);
            documentDataService.saveDocument(documentEntity);
            return documentEntity.getDocumentId();
    }

    private DocumentEntity generateDocumentEntity(MultipartFile file) {
        UUID documentId = UUID.randomUUID();
        Date date = new Date();
        try {
            byte[] docFile = file.getBytes();
            return new DocumentEntity(docFile);
        } catch (IOException e) {
            throw new RuntimeException("Error processing document file", e);
        }
    }

    private void validateDocument(MultipartFile file) {
        if (!isPDF(file)) {
            throw new RuntimeException("Unsupported file type, only PDF files are allowed");
        }
    }
}
