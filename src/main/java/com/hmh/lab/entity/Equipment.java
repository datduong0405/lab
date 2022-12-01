package com.hmh.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@Table(name = "equipment")
public class Equipment extends Base {
    @Column
    private String name;
    @Column
    private int quantity;
    @Column
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;
    @ManyToMany(mappedBy = "equipments")
    @JsonIgnore
    private Set<Laboratory> laboratories;


}
