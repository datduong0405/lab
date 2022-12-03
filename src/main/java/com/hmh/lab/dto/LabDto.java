package com.hmh.lab.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class LabDto {
    private String name;
    private String type;
    private String status;
    private Date createdDate;
    private Date mofifiedDate;
    private String user;
    private Set<Long> equipmentList;
}
