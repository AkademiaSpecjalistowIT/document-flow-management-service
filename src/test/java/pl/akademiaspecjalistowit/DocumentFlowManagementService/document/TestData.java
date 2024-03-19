package pl.akademiaspecjalistowit.DocumentFlowManagementService.document;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestData {
    public static MultipartFile preparedTestPdfFileForUpload() {
        byte[] testFileContent = getTestFileContent("testFile_isPdf.pdf");
        return new MockMultipartFile("file", "testFile_isPdf.pdf",
                "application/pdf", testFileContent);
    }

    public static MultipartFile preparedTestNotPdfFileForUpload() {
        byte[] testFileContent = getTestFileContent("testFile_notPDF.txt");
        return new MockMultipartFile("file", "testFile_notPDF.txt",
                "application/plain", testFileContent);
    }

    public static byte[] preparedValidFileForTestDocumentEntity(){
        return getTestFileContent("testFile_isPdf.pdf");
    }

    private static byte[] getTestFileContent(String fileName) {
        try {
            return Files.readAllBytes(Path.of("src/test/resources/" + fileName));
        } catch (IOException e) {
            return null;
        }
    }
}
