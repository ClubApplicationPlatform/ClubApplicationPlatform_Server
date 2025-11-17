package com.JoinUs.dp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class MyApplicationResponse {
    private Long applicationId;
    private Long clubId;
    private String clubName;
    private String result;
    private String confirmStatus;
    private Timestamp createdAt;
}
