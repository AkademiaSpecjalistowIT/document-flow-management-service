package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentState;

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

    private DocumentState state;

    public DocumentEntity(byte[] file) {
        this.documentId = UUID.randomUUID();
        this.creationDate = new Date();
        this.file = file;
    }
}

