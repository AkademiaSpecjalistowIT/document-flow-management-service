package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentValidationException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentState;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID documentId;

    private Date creationDate;

    @Lob
    private byte[] file;

    private String fileName;

    private String description;

    private String documentType;

    private Date deadline;

    private DocumentState state;


    public DocumentEntity(byte[] file,
                          String fileName,
                          String description,
                          String documentType,
                          Date deadline) {
        this.documentId = UUID.randomUUID();
        this.creationDate = new Date();
        this.file = file;
        this.fileName = fileName;
        this.description = description;
        this.documentType = documentType;
        this.deadline = deadline;
        this.state = DocumentState.ACCEPTED;
    }

    public static DocumentEntity createNewDocument(MultipartFile file,
                                                   String fileName,
                                                   String description,
                                                   String documentType,
                                                   Date deadline) throws IOException {
        validateDocument(file);
        return new DocumentEntity(file.getBytes(), fileName, description, documentType, deadline);

    }

    private static void validateDocument(MultipartFile file) {
        if (!isPDF(file)) {
            throw new DocumentValidationException("Only a PDF files are allowed");
        }
    }

    private static boolean isPDF(MultipartFile file) {
        String fileContentType = file.getContentType();
        return "application/pdf".equals(fileContentType);
    }
}

