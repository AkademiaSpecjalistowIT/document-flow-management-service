package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception.DocumentNotFoundException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception.DocumentProcessingException;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler.exception.DocumentValidationException;

import java.time.LocalDateTime;


@ControllerAdvice(annotations = RestController.class)
public class DocumentExceptionHandler {
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Document not found")
    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorInfoResponse> handleDocumentNotFoundException(DocumentNotFoundException e,
                                                                             HttpServletRequest request) {
        ErrorInfoResponse error = new ErrorInfoResponse(
                LocalDateTime.now(),
                e.getMessage(),
                request.getRequestURL().toString()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DocumentProcessingException.class)
    @ResponseStatus()
    public ResponseEntity<ErrorInfoResponse> handleDocumentProcessingException(DocumentProcessingException e,
                                                                               HttpServletRequest request){
        return ResponseEntity.ok().build();
    }
    @ExceptionHandler(DocumentValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorInfoResponse> handleDocumentValidationException(DocumentValidationException e,
                                                                               HttpServletRequest request){
        return ResponseEntity.ok().build();
    }
}
