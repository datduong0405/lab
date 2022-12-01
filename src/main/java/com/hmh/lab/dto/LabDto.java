package com.hmh.lab.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LabDto {
    private String name;
    private String type;
    private String status;
    private Date createdDate;
    private Date mofifiedDate;
    private String user;
    private List<Long> equipmentList;
}
