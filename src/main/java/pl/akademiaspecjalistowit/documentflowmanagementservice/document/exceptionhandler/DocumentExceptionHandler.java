package pl.akademiaspecjalistowit.documentflowmanagementservice.document.exceptionhandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.exception.*;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.response.ErrorInfoResponse;

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
