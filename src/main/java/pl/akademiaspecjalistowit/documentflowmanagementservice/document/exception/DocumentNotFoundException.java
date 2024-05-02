package pl.akademiaspecjalistowit.documentflowmanagementservice.document.exception;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
