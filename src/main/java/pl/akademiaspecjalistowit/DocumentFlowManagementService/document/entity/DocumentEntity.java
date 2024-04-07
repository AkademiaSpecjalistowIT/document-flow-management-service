package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception.DocumentValidationException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentState;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID documentId;

    private LocalDate creationDate;

    @Lob
    private byte[] file;

    private String fileName;

    private String description;

    private String documentType;

    private LocalDate deadline;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentEventEntity> events = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DocumentState state;


    public DocumentEntity(byte[] file,
                          String fileName,
                          String description,
                          String documentType,
                          LocalDate deadline) {
        this.documentId = UUID.randomUUID();
        this.creationDate = LocalDate.now();
        this.file = file;
        this.fileName = fileName;
        this.description = description;
        this.documentType = documentType;
        this.deadline = deadline;
        this.state = DocumentState.CREATED;
    }

    public static DocumentEntity createNewDocument(MultipartFile file,
                                                   String fileName,
                                                   String description,
                                                   String documentType,
                                                   LocalDate deadline) throws IOException {
        validateDocument(file);
        return new DocumentEntity(file.getBytes(),
                fileName,
                description,
                documentType,
                deadline);

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

    public void addEvent(DocumentEventEntity event) {
        events.add(event);
        event.assignDocumentToEvent(this);
    }
}

