package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DownloadDocumentDto {
    private byte[] file;
    private String fileName;
}
