package pl.akademiaspecjalistowit.documentflowmanagementservice.document.exception;

public class DocumentEventAppendingException extends RuntimeException{
    public DocumentEventAppendingException(String message) {
        super(message);
    }

    public DocumentEventAppendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
