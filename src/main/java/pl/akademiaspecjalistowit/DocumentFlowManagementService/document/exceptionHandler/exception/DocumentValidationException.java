package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception;
public class DocumentValidationException extends RuntimeException {

    public DocumentValidationException(String message) {
        super(message);
    }
}
