package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PDFValidator {
    public static boolean isPDF(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            PDDocument.load(bis);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isAllowedSize(byte[] data) {
        double fileSizeInMegabytes = (double) data.length / (1024 * 1024);
        if (fileSizeInMegabytes <= 20) {
            return true;
        }
        return false;
    }
}
