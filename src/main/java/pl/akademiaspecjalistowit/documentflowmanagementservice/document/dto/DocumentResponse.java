package pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto;

import lombok.*;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.model.DocumentState;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class DocumentResponse {
    private UUID documentId;
    private LocalDate creationDate;
    private String fileName;
    private String description;
    private String documentType;
    private LocalDate deadline;
    private DocumentState state;
}
