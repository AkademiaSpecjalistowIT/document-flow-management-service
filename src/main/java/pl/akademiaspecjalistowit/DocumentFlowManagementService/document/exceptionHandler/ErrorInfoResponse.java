package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.exceptionHandler;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class ErrorInfoResponse {
    private LocalDateTime dateTime;
    private String message;
    private String path;
}
