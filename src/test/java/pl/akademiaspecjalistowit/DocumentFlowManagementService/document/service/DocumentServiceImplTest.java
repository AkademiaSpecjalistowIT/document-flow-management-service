package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.TestData;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentNotFoundException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentValidationException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentState;

import java.util.Calendar;
import java.util.Date;
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
        assertThrows(DocumentValidationException.class, () -> documentService.saveDocument(givenFileRequest));
        verify(documentDataService, never()).saveDocument(any(DocumentEntity.class));
    }

    @Test
    void unhappyPath_downloadDocument_shouldThrowNotFound(){
        //given
        UUID documentId = UUID.randomUUID();
        when(documentDataService.getDocument(documentId)).thenReturn(Optional.empty());
        //when && then
        DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class, () -> documentService.downloadDocument(documentId));
        assertEquals(documentId.toString(), exception.getMessage());
    }

    @Test
    void happyPath_downloadDocument_shouldReturnDownloadDocumentDto(){
        //given
        UUID documentId = UUID.randomUUID();
        DocumentEntity documentEntity = prepareValidDocumentEntity(documentId);
        when(documentDataService.getDocument(documentId)).thenReturn(Optional.of(documentEntity));
        //when
        DownloadDocumentDto downloadDocumentDto = documentService.downloadDocument(documentId);
        //then
        verifyDownloadDocumentDto(downloadDocumentDto, documentEntity);
    }

    private void verifyDownloadDocumentDto(DownloadDocumentDto downloadDocumentDto, DocumentEntity documentEntity){
        assertEquals(downloadDocumentDto.getFile(), documentEntity.getFile());
        assertEquals(downloadDocumentDto.getFileName(), documentEntity.getFileName());
    }

    private DocumentEntity prepareValidDocumentEntity(UUID documentId){
        return new DocumentEntity(
                1L,
                documentId,
                new Date(),
                TestData.preparedValidFileForTestDocumentEntity(),
                "TestFileName",
                "TestDescription",
                "TestDocumentType",
                new Date(2030, Calendar.JUNE,10),
                DocumentState.PROCESSING
        );
    }
}