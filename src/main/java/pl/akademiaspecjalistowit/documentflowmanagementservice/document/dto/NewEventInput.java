package pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto;

import lombok.*;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.model.DocumentEventType;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewEventInput {
    private LocalDate creationDate = LocalDate.now();

    private String issuer;

    private DocumentEventType eventType;

    private String eventReason;

    private String eventDescription;
}
