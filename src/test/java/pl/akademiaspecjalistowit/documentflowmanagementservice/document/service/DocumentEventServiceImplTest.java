package pl.akademiaspecjalistowit.documentflowmanagementservice.document.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.TestData;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.entity.DocumentEntity;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.exception.DocumentEventAppendingException;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.mapper.DocumentMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentEventServiceImplTest {
    private final DocumentEventService documentEventService = new DocumentEventServiceImpl();
    private final String TEMP_FILE_NAME = "eventAppendTest.pdf";

    private final String EVENT_TEXT = TestData.prepareAppendedEventText();

    @Test
    void happyPath_ShouldAppendEvents(){
        //given
        UUID documentId = UUID.randomUUID();
        DocumentEntity document = TestData.preparedValidDocumentEntityWithEmptyPDF(documentId);
        //when
        DownloadDocumentDto result = documentEventService.appendEventsToPDF(DocumentMapper.downloadDtoFromEntity(document), document.getEvents());
        //then
        prepareForValidation(result);
    }


    private void prepareForValidation(DownloadDocumentDto result){
        byte[] emptyTestFile = TestData.preparedValidEmptyFileForTestDocumentEntity();
        assertNotEquals(result.getFile(), emptyTestFile);
        //save to pdf
        convertAndSaveToPDF(result.getFile());
        //load with lib
        String appendedText = loadAllTextFromPdf();
        //validate
        validateAppendResult(appendedText);
        //delete temp files
        deleteTempFiles();
    }

    private String loadAllTextFromPdf(){
        try {
            PdfReader reader = new PdfReader(TEMP_FILE_NAME);
            PdfDocument pdfDoc = new PdfDocument(reader);
            StringBuilder text = new StringBuilder();
            int totalPages = pdfDoc.getNumberOfPages();
            for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
                String pageText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(pageNum));
                text.append(pageText);
            }
            pdfDoc.close();
            reader.close();
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void validateAppendResult(String appendedText){
        assertEquals(EVENT_TEXT, appendedText);
    }

    private void convertAndSaveToPDF(byte[] file){
        try {
            OutputStream out = new FileOutputStream(TEMP_FILE_NAME);
            out.write(file);
            out.close();
        } catch (IOException e) {
            throw new DocumentEventAppendingException("Saving error while converting to PDF", e);
        }
    }

    private void deleteTempFiles(){
        File originalTempFile = new File(TEMP_FILE_NAME);
        boolean isOriginalDeleted = originalTempFile.delete();
        if(!isOriginalDeleted){
            throw new DocumentEventAppendingException("Failed to delete temporary files");
        }
    }
}
