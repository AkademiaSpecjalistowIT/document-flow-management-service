package pl.akademiaspecjalistowit.documentflowmanagementservice.document.response;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class ErrorInfoResponse {
    private LocalDateTime timestamp;
    private String message;
    private String path;
}
