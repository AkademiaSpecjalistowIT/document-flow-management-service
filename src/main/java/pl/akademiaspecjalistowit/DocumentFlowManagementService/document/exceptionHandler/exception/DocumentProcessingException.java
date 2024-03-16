package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception;

public class DocumentProcessingException extends RuntimeException {

    public DocumentProcessingException(String message) {
        super(message);
    }
}
