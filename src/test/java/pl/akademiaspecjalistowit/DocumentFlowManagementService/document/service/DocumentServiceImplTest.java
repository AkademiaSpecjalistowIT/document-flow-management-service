package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.TestData;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {
    @Mock
    private DocumentDataService documentDataService;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    void happyPath_saveDocument_shouldSaveDocumentSuccessfully() {
        //prepare mock file
        MultipartFile givenFileRequest = TestData.preparedTestPdfFileForUpload();

        //when:
        UUID expectedResult = documentService.saveDocument(givenFileRequest);

        //then:
        assertNotNull(expectedResult);
        verify(documentDataService).saveDocument(any(DocumentEntity.class));
    }

    @Test
    void unhappyPath_saveDocument_shouldNotSaveInvalidFile() {
        ////prepare mock file
        MultipartFile givenFileRequest = TestData.preparedTestNotPdfFileForUpload();

        //then:
        assertThrows(RuntimeException.class, () -> documentService.saveDocument(givenFileRequest));
        verify(documentDataService, never()).saveDocument(any(DocumentEntity.class));
    }

    @Test
    void unhappyPath_getDocument_shouldThrowNotFound(){
        //given
        UUID documentId = UUID.randomUUID();
        when(documentDataService.getDocument(documentId)).thenReturn(Optional.empty());
        //when && then
        DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class, () -> documentService.downloadDocument(documentId));
        assertEquals("Document with id %s not found".formatted(documentId), exception.getMessage());
    }

}