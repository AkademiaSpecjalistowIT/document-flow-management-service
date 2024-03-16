package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
