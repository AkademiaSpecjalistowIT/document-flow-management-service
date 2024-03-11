package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.TestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFValidatorTest {
    @Test
    void happyPath_isPdf_shouldBeAPdfFile() {
        MultipartFile givenFile = TestData.preparedTestPdfFileForUpload();
        assertTrue(PDFValidator.isPDF(givenFile));
    }

    @Test
    void unhappyPath_isPdf_shouldThrowExceptionForNonPdfFile() {
        MultipartFile file = TestData.preparedTestNotPdfFileForUpload();
        Executable executable = () -> PDFValidator.isPDF(file);
        assertThrows(RuntimeException.class, executable);
    }
}
