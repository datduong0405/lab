package com.hmh.lab.dto;

import java.util.Date;

public interface History {
    String getOldData();
    String getNewData();
    String getTableName();
    String getId();
    String getType();
    Date getDate();
}
