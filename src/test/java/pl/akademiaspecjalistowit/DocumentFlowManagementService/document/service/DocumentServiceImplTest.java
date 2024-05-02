package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.TestData;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.NewEventInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentNotFoundException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentValidationException;

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

        //given
        DocumentCreationInput documentCreationInput = TestData.preparedTestDocumentCreationInput();
        NewEventInput newEventInput = TestData.preparedTestNewEventInput();

        // when
        UUID expectedResult = documentService.createDocument(documentCreationInput, newEventInput);

        //then
        assertNotNull(expectedResult);
        verify(documentDataService).saveDocument(any(DocumentEntity.class));
    }

    @Test
    void unhappyPath_saveDocument_shouldNotSaveInvalidFile() {
        //given
        DocumentCreationInput documentCreationInput = TestData.preparedTestDocumentCreationInputWithInvalidFile();
        NewEventInput newEventInput = TestData.preparedTestNewEventInput();

        //then:
        assertThrows(DocumentValidationException.class,
                () -> documentService.createDocument(documentCreationInput, newEventInput));
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

}


