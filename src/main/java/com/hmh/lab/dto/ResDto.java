package com.hmh.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ResDto {
    private String userId;
    private String labId;

    private Long startDate;

    private Long endDate;
}

