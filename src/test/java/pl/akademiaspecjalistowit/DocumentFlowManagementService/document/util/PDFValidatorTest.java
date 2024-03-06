package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFValidatorTest {

    @Test
    void testIsPDF() {

        byte[] pdfData = PDFValidator.loadPDFFile("test.pdf");

        assertTrue(PDFValidator.isPDF(pdfData), "Metoda isPDF powinna zwrócić true dla prawidłowego pliku PDF");

        assertFalse(PDFValidator.isPDF(new byte[0]), "Metoda isPDF powinna zwrócić false dla pustych danych");
    }

    @Test
    void testIsAllowedSize() {
        byte[] smallFile = new byte[10 * 1024]; // Plik o rozmiarze 10 KB
        byte[] largeFile = new byte[25 * 1024 * 1024]; // Plik o rozmiarze 25 MB

        assertTrue(PDFValidator.isAllowedSize(smallFile), "Metoda isAllowedSize powinna zwrócić true dla małego pliku");


        assertFalse(PDFValidator.isAllowedSize(largeFile), "Metoda isAllowedSize powinna zwrócić false dla dużego pliku");
    }
}