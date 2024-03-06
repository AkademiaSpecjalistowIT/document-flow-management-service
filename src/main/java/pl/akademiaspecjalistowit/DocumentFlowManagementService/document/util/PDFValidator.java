package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;

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
    public static byte[] loadPDFFile(String filename) {
        try (InputStream inputStream = PDFValidator.class.getClassLoader().getResourceAsStream(filename);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (inputStream == null) {
                throw new FileNotFoundException("Plik PDF nie został znaleziony: " + filename);
            }

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
