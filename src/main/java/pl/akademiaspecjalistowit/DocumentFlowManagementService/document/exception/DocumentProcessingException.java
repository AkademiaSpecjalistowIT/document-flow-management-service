package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception;

public class DocumentProcessingException extends RuntimeException {
    public DocumentProcessingException(String message) {
        super(message);
    }

    public DocumentProcessingException() {
        super();
    }
}
