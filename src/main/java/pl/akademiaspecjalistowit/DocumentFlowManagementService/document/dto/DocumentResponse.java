package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto;

import lombok.*;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.model.DocumentState;

import java.util.Date;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class DocumentResponse {
    private UUID documentId;
    private Date creationDate;
    private String fileName;
    private String description;
    private String documentType;
    private Date deadline;
    private DocumentState state;
}
