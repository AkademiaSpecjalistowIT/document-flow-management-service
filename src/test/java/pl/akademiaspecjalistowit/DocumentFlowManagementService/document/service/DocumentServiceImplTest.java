package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.repository.DocumentRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DocumentServiceImplTest {
    @Mock
    private DocumentRepository documentRepository;
    @InjectMocks
    private DocumentServiceImpl documentServiceImpl;

    @Test
    void happyPath_saveDocument_shouldSaveDocumentSuccessfully() {
        //prepare mock file
        MultipartFile givenFile = mock(MultipartFile.class);
        when(givenFile.getContentType()).thenReturn("application/pdf");
    }

    @Test
    void unhappyPath_saveDocument_shouldBeATransactionalRollback() {
        //ToDo: integration test
    }


}