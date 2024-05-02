package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEventEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentEventAppendingException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DocumentEventServiceImpl implements DocumentEventService{
    private final int TITLE_FONT_SIZE = 16;
    private final int FONT_SIZE = 14;
    private final int LINE_SIZE = 16;
    private final String PDF_SUFFIX = ".pdf";
    private final String MODIFIED_PDF_SUFFIX = "modified.pdf";
    private final int MAX_LINES = 49;
    private final String FONTS_TIMES_TTF = "src/main/resources/fonts/times.ttf";
    private final int STARTING_X = 54;
    private final int STARTING_Y = 806;
    private final int CHUNK_SIZE = 69;
    private final int TITLE_X = 36;
    private final int TITLE_Y = 806;

    @Getter
    private class EventTextResult{
        private final String formattedEventText;
        private final int amountOfAddedLines;

        public EventTextResult(String formattedEventText, int amountOfAddedLines) {
            this.formattedEventText = formattedEventText;
            this.amountOfAddedLines = amountOfAddedLines;
        }

    }

    @Override
    public DownloadDocumentDto appendEventsToPDF(DownloadDocumentDto documentDto, List<DocumentEventEntity> events) {
        if (documentDto.getFile().length == 0 || events.isEmpty()){
            throw new DocumentEventAppendingException("File is empty or there are no events");
        }
        DownloadDocumentDto outputDto = new DownloadDocumentDto(documentDto.getFile(), documentDto.getFileName());
        String tempFileName = UUID.randomUUID().toString();
        convertAndSaveToPDF(tempFileName, documentDto.getFile());
        modifyPdf(tempFileName, events);
        outputDto.setFile(convertToByteFieldAndSave(tempFileName));
        deleteTempFiles(tempFileName);
        return outputDto;
    }


    private void convertAndSaveToPDF(String tempFileName, byte[] file){
        try {
            OutputStream out = new FileOutputStream(tempFileName + PDF_SUFFIX);
            out.write(file);
            out.close();
        } catch (IOException e) {
            throw new DocumentEventAppendingException("Saving error while converting to PDF", e);
        }
    }

    private void modifyPdf(String tempFileName, List<DocumentEventEntity> events){
        try{
            PdfReader reader = new PdfReader(tempFileName + PDF_SUFFIX);
            PdfWriter writer = new PdfWriter(tempFileName + MODIFIED_PDF_SUFFIX);
            PdfFont font = PdfFontFactory.createFont(FONTS_TIMES_TTF);
            PdfDocument pdfDocument = new PdfDocument(reader,writer);
            pdfDocument.addNewPage();
            Document modifiableDocument = new Document(pdfDocument);
            addTitleAndPage(modifiableDocument, font);
            int yCounter = 1; // space for title
            int eventCounter = 1;
            for (int i = 0; i < events.size(); i++){
                // check the number of lines needed and compare to available then if not enough add page
                EventTextResult eventText = createEventText(events.get(i), eventCounter);
                if(yCounter + eventText.amountOfAddedLines >= MAX_LINES){
                    pdfDocument.addNewPage();
                    yCounter = 0;
                }
                addEventToDocument(modifiableDocument, yCounter, font, eventText.formattedEventText);
                yCounter += eventText.amountOfAddedLines;
                eventCounter++;
            }
            modifiableDocument.close();
            pdfDocument.close();
        } catch (IOException e) {
            throw new DocumentEventAppendingException("Appending error", e);
        }
    }

    private void addEventToDocument(Document document, int yCounter, PdfFont font, String eventText) {
        // page size = rectangle 595 (x), 842 (y)
        // effective x = 486, sign size at font size 14 = 7 -> 69 max signs in line -> false can be more
        // effective y = 49 lines pre page
        int y = STARTING_Y - yCounter * LINE_SIZE;
        Paragraph paragraph = new Paragraph(eventText);

        paragraph.setFont(font);
        paragraph.setFontSize(FONT_SIZE);
        document.showTextAligned(paragraph, STARTING_X, y, document.getPdfDocument().getNumberOfPages(), TextAlignment.LEFT, VerticalAlignment.TOP, 0);
    }

    private EventTextResult createEventText(DocumentEventEntity event, int eventCounter){
        String eventText = eventCounter + ". " + event.getEventReason() + " - " + event.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        eventText += "\nAutor akcji: " + event.getIssuer();
        eventText += "\nOpis zdarzenia: ";
        int originalSize = event.getEventDescription().length();
        StringBuilder formattedEventText = new StringBuilder();
        for (int i = 0; i < event.getEventDescription().length(); i += CHUNK_SIZE) {
            int endIndex = Math.min(i + CHUNK_SIZE, event.getEventDescription().length());
            formattedEventText.append(event.getEventDescription(), i, endIndex).append("\n");
        }
        int amountOfAddedLines = (formattedEventText.length() - originalSize) + 2; // \n counter
        return new EventTextResult(eventText + formattedEventText, amountOfAddedLines);
    }
    private void addTitleAndPage(Document document, PdfFont font) {
        Paragraph title = new Paragraph("Protokół akcji dokumentu na dzień " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .setFont(font)
                .setFontSize(TITLE_FONT_SIZE)
                .setBold();
        document.showTextAligned(title,TITLE_X,TITLE_Y, document.getPdfDocument().getNumberOfPages(), TextAlignment.LEFT, VerticalAlignment.TOP, 0);
    }

    private byte[] convertToByteFieldAndSave(String tempFileName){
        try {
            InputStream inputStream = new FileInputStream(tempFileName + MODIFIED_PDF_SUFFIX);
            byte[] file = inputStream.readAllBytes();
            inputStream.close();
            return file;
        } catch (IOException e) {
            throw new DocumentEventAppendingException("Reading error", e);
        }
    }

    private void deleteTempFiles(String tempFileName){
        File originalTempFile = new File(tempFileName + PDF_SUFFIX);
        File modifiedTempFile = new File(tempFileName + MODIFIED_PDF_SUFFIX);
        boolean isOriginalDeleted = originalTempFile.delete();
        boolean isModifiedDeleted = modifiedTempFile.delete();
        if(!isOriginalDeleted || !isModifiedDeleted){
            throw new DocumentEventAppendingException("Failed to delete temporary files");
        }
    }
}
