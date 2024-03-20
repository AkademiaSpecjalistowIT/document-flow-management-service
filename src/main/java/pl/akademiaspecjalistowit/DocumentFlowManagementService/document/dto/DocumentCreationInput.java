package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto;

import lombok.AccessLevel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentCreationInput {
    private String fileName;
    private String description;
    private String documentType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    private MultipartFile file;
}
