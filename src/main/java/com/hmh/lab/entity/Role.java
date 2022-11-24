package com.hmh.lab.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role")
public class Role extends Base {
    @Column(nullable = false)
    private String name;
}
