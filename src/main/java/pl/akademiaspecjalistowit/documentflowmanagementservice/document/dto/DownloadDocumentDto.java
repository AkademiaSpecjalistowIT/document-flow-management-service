package pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto;

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
import lombok.Setter;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.entity.DocumentEventEntity;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.exception.DocumentEventAppendingException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DownloadDocumentDto {
    @Setter
    private byte[] file;
    private String fileName;
}
