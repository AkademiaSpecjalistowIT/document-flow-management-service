package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentEventType;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate creationDate;

    private String issuer;

    private DocumentEventType eventType;

    private String eventReason;

    private String eventDescription;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private DocumentEntity document;

    public DocumentEvent(LocalDate creationDate, String issuer,
                         DocumentEventType eventType, String eventReason,
                         String eventDescription, DocumentEntity document) {
        this.creationDate = creationDate;
        this.issuer = issuer;
        this.eventType = eventType;
        this.eventReason = eventReason;
        this.eventDescription = eventDescription;
        if(document != null){
            document.addEvent(this);
        }
    }

    public void assignDocumentToEvent(DocumentEntity document) {
        this.document = document;
        if(document != null && !document.getEvents().contains(this)){
            document.getEvents().add(this);
        }
    }
}
