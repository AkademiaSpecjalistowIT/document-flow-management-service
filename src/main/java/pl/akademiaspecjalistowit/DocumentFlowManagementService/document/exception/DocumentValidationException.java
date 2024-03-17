package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception;
public class DocumentValidationException extends RuntimeException {
    public DocumentValidationException(String message) {
        super(message);
    }
}
