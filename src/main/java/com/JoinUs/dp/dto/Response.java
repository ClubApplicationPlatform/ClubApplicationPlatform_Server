package com.JoinUs.dp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class Response<T> {
    private int status;
    private T data;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public Response(HttpStatus status, T data, String message) {
        this.status = status.value();
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
