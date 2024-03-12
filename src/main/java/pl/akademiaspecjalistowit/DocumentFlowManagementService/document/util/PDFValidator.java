package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PDFValidator {
    private static final String REQUIRED_CONTENT_TYPE = "application/pdf";

    public static boolean isPDF(MultipartFile file) {
        String fileContentType = file.getContentType();
        return REQUIRED_CONTENT_TYPE.equals(fileContentType);
    }
}
