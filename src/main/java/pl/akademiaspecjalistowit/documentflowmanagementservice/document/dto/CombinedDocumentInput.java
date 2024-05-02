package pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CombinedDocumentInput {
    private DocumentCreationInput documentInput;
    private NewEventInput eventInput;
}
