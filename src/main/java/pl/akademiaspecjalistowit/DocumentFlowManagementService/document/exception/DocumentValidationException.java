package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exception;

class DocumentValidationException extends RuntimeException {

    public DocumentValidationException(String message) {
        super(message);
    }
}
