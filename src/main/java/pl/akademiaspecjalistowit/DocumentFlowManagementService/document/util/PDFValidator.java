package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import org.springframework.web.multipart.MultipartFile;


public class PDFValidator {

    public static boolean isPDF(MultipartFile file) {
        String fileContentType = file.getContentType();
        String requiredFileFormat = "application/pdf";
        if (!requiredFileFormat.equals(fileContentType)) {
            throw new RuntimeException("Only PDF files are allowed.");
        } else {
            return true;
        }
    }
}
