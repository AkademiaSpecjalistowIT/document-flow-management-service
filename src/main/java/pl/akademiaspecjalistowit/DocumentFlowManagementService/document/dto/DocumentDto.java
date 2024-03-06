package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class DocumentDto {
    private UUID documentId;
    private Date creationDate;
    private byte[] file;
}
