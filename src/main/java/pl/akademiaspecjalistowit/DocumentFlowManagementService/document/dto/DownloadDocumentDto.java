package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto;

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
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEventEntity;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentEventAppendingException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DownloadDocumentDto {
    private byte[] file;
    private String fileName;


    public void appendEventsToPDF(List<DocumentEventEntity> events){
        if(file.length == 0 || events.isEmpty()){
            throw new DocumentEventAppendingException("File is empty or there are no events");
        }
        String tempFileName = UUID.randomUUID().toString();
        convertAndSaveToPDF(tempFileName);
        modifyPdf(tempFileName, events);
        convertToByteFieldAndSave(tempFileName);
        deleteTempFiles(tempFileName);
    }

    private void convertAndSaveToPDF(String tempFileName){
        try {
            OutputStream out = new FileOutputStream(tempFileName + ".pdf");
            out.write(file);
            out.close();
        } catch (IOException e) {
            throw new DocumentEventAppendingException("Saving error", e);
        }
    }

    private void modifyPdf(String tempFileName, List<DocumentEventEntity> events){
        final int MAX_LINES = 49;
        try{
            PdfReader reader = new PdfReader(tempFileName + ".pdf");
            PdfWriter writer = new PdfWriter(tempFileName + "modified.pdf");
            PdfFont font = PdfFontFactory.createFont("c:/windows/fonts/times.ttf");
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

    private void addTitleAndPage(Document document, PdfFont font) {
        Paragraph title = new Paragraph("Protokół akcji dokumentu na dzień " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .setFont(font)
                .setFontSize(16)
                .setBold();
        document.showTextAligned(title,36,806, document.getPdfDocument().getNumberOfPages(), TextAlignment.LEFT, VerticalAlignment.TOP, 0);
    }

    private void addEventToDocument(Document document, int yCounter, PdfFont font, String eventText) {
        // page size = rectangle 595 (x), 842 (y)
        // effective x = 486, sign size at font size 14 = 7 -> 69 max signs in line -> false can be more
        // effective y = 49 lines pre page
        int x = 54;
        int y = 806 - yCounter * 16;
        Paragraph paragraph = new Paragraph(eventText);

        paragraph.setFont(font);
        paragraph.setFontSize(14);
        document.showTextAligned(paragraph, x, y, document.getPdfDocument().getNumberOfPages(), TextAlignment.LEFT, VerticalAlignment.TOP, 0);
    }

    private EventTextResult createEventText(DocumentEventEntity event, int eventCounter){
        String eventText = eventCounter + ". " + event.getEventReason() + " - " + event.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        eventText += "\nAutor akcji: " + event.getIssuer();
        eventText += "\nOpis zdarzenia: ";
        int originalSize = event.getEventDescription().length();
        final int CHUNK_SIZE = 69;
        StringBuilder formattedEventText = new StringBuilder();
        for (int i = 0; i < event.getEventDescription().length(); i += CHUNK_SIZE) {
            int endIndex = Math.min(i + CHUNK_SIZE, event.getEventDescription().length());
            formattedEventText.append(event.getEventDescription(), i, endIndex).append("\n");
        }
        int amountOfAddedLines = (formattedEventText.length() - originalSize) + 2; // \n counter
        return new EventTextResult(eventText + formattedEventText.toString(), amountOfAddedLines);
    }

    private void convertToByteFieldAndSave(String tempFileName){
        try {
            InputStream inputStream = new FileInputStream(tempFileName + "modified.pdf");
            file = inputStream.readAllBytes();
            inputStream.close();
        } catch (IOException e) {
            throw new DocumentEventAppendingException("Reading error", e);
        }
    }

    private void deleteTempFiles(String tempFileName){
        File originalTempFile = new File(tempFileName + ".pdf");
        File modifiedTempFile = new File(tempFileName + "modified.pdf");
        boolean isOriginalDeleted = originalTempFile.delete();
        boolean isModifiedDeleted = modifiedTempFile.delete();
        if(!isOriginalDeleted || !isModifiedDeleted){
            throw new DocumentEventAppendingException("Failed to delete temporary files");
        }
    }

    @Getter
    private class EventTextResult{
        private final String formattedEventText;
        private final int amountOfAddedLines;

        public EventTextResult(String formattedEventText, int amountOfAddedLines) {
            this.formattedEventText = formattedEventText;
            this.amountOfAddedLines = amountOfAddedLines;
        }

    }
}
