package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception.DocumentNotFoundException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception.DocumentProcessingException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception.DocumentValidationException;

import java.time.LocalDateTime;


@ControllerAdvice(annotations = RestController.class)
public class DocumentExceptionHandler {

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorInfoResponse> handleDocumentNotFoundException(DocumentNotFoundException e,
                                                                             HttpServletRequest request) {
        String message = String.format("Document with id %s not found", e.getMessage());

        return buildResponseEntity(HttpStatus.NOT_FOUND, message, request);
    }

    @ExceptionHandler(DocumentProcessingException.class)
    public ResponseEntity<ErrorInfoResponse> handleDocumentProcessingException(DocumentProcessingException e,
                                                                               HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error processing document: " + e.getMessage(),
                request);
    }

    @ExceptionHandler(DocumentValidationException.class)
    public ResponseEntity<ErrorInfoResponse> handleDocumentValidationException(DocumentValidationException e,
                                                                               HttpServletRequest request) {
        ErrorInfoResponse error = new ErrorInfoResponse(LocalDateTime.now(),
                "Invalid document: " + e.getMessage(), request.getRequestURL().toString());
        return buildResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Invalid document: " + e.getMessage(),
                request);
    }

    private ResponseEntity<ErrorInfoResponse> buildResponseEntity(HttpStatus status, String message,
                                                                  HttpServletRequest request) {
        String path = request.getRequestURL().toString();
        ErrorInfoResponse errorInfo = new ErrorInfoResponse(LocalDateTime.now(),
                message,
                path);
        return new ResponseEntity<>(errorInfo, status);
    }
}
