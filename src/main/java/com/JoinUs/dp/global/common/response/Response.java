package com.JoinUs.dp.global.common.response;


import java.time.LocalDateTime;

public class Response<T> {
    private int status;
    private T data;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public Response(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public int getStatus() { return status; }
    public T getData() { return data; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setStatus(int status) { this.status = status; }
    public void setData(T data) { this.data = data; }
    public void setMessage(String message) { this.message = message; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}