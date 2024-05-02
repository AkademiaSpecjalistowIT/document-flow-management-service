package pl.akademiaspecjalistowit.DocumentFlowManagementService.document;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.NewEventInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEventEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentEventType;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TestData {
    public static MultipartFile preparedTestPdfFileForUpload() {
        byte[] testFileContent = getTestFileContent("testFile_isPdf.pdf");
        return new MockMultipartFile("file", "testFile_isPdf.pdf",
                "application/pdf", testFileContent);
    }

    public static MultipartFile preparedTestNotPdfFileForUpload() {
        byte[] testFileContent = getTestFileContent("testFile_notPDF.txt");
        return new MockMultipartFile("file", "testFile_notPDF.txt",
                "application/plain", testFileContent);
    }

    public static byte[] preparedValidFileForTestDocumentEntity() {
        return getTestFileContent("testFile_isPdf.pdf");
    }

    public static byte[] preparedValidEmptyFileForTestDocumentEntity(){ return getTestFileContent("testFile_emptyPDF.pdf");}

    public static DocumentEntity preparedValidDocumentEntityWithEmptyPDF(UUID documentId){
        List<DocumentEventEntity> events = new ArrayList<>();
        byte[] file = TestData.preparedValidEmptyFileForTestDocumentEntity();
        DocumentEntity document = new DocumentEntity(
                1L,
                documentId,
                LocalDate.now(),
                file,
                "TestFileName",
                "TestDescription",
                "TestDocumentType",
                LocalDate.of(2030, 6, 10),
                events,
                DocumentState.CREATED
        );
        DocumentEventEntity event = preparedTestDocumentEvent(document);
        events.add(event);
        return document;
    }

    public static DocumentEntity preparedValidDocumentEntity(UUID documentId) {
        List<DocumentEventEntity> events = new ArrayList<>();
        byte[] file = TestData.preparedValidFileForTestDocumentEntity();
        DocumentEntity document = new DocumentEntity(
                1L,
                documentId,
                LocalDate.now(),
                file,
                "TestFileName",
                "TestDescription",
                "TestDocumentType",
                LocalDate.of(2030, 6, 10),
                events,
                DocumentState.CREATED
        );
        DocumentEventEntity event = preparedTestDocumentEvent(document);
        events.add(event);
        return document;
    }

    public static DocumentCreationInput preparedTestDocumentCreationInput() {
        return new DocumentCreationInput("testFile_isPdf",
                "Test description",
                "CV",
                LocalDate.of(2024, 6, 10), preparedTestPdfFileForUpload());
    }

    public static DocumentCreationInput preparedTestDocumentCreationInputWithInvalidFile() {
        return new DocumentCreationInput("testFile_notPDF",
                "Test description",
                "CV",
                LocalDate.of(2024, 6, 10), preparedTestNotPdfFileForUpload());
    }

    private static byte[] getTestFileContent(String fileName) {
        try {
            return Files.readAllBytes(Path.of("src/test/resources/" + fileName));
        } catch (IOException e) {
            return null;
        }
    }

    private static DocumentEventEntity preparedTestDocumentEvent(DocumentEntity document) {
        return new DocumentEventEntity(
                LocalDate.now(),
                "username",
                DocumentEventType.CREATED,
                "Test reason",
                "Test description",
                document);
    }

    public static NewEventInput preparedTestNewEventInput(){
        return new NewEventInput(LocalDate.now(),
                "system",
                DocumentEventType.CREATED,
                "New document created",
                "A new document has been uploaded to the system");
    }

    public static String prepareAppendedEventText(){
        String date = LocalDate.now().toString();
        return " Protokół akcji dokumentu na dzień " + date + "\n" +
                "1. Test reason - " + date + " \n" +
                "Autor akcji: username \n" +
                "Opis zdarzenia: Test description \n" +
                "2. Test reason - " + date + " \n" +
                "Autor akcji: username \n" +
                "Opis zdarzenia: Test description ";
    }
}
