package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFValidatorTest {
    @Test
    void should_be_a_PDF_file() {
        MultipartFile givenFile = preparedTestPdfFile();
        assertTrue(PDFValidator.isPDF(givenFile));
    }

    @Test
    void should_throw_exception_for_non_pdf_file() {
        MultipartFile file = preparedTestNotPdfFile();
        Executable executable = () -> PDFValidator.isPDF(file);
        assertThrows(RuntimeException.class, executable);
    }


    MultipartFile preparedTestPdfFile() {
        byte[] testFileContent = getTestFileContent("testFile_isPdf.pdf");
        return new MockMultipartFile("file", "testFile_isPdf.pdf",
                "application/pdf", testFileContent);
    }

    MultipartFile preparedTestNotPdfFile() {
        byte[] testFileContent = getTestFileContent("testFile_notPDF.txt");
        return new MockMultipartFile("file", "testFile_notPDF.txt",
                "application/plain", testFileContent);
    }


    private static byte[] getTestFileContent(String fileName) {
        try {
            return Files.readAllBytes(Path.of("src/test/resources/" + fileName));
        } catch (IOException e) {
            return null;
        }
    }

}
