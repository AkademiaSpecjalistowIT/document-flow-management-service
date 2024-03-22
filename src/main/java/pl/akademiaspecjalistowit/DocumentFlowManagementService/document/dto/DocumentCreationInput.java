package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentCreationInput {
    private String fileName;
    private String description;
    private String documentType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private MultipartFile file;
}
