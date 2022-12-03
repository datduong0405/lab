package com.hmh.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "equipment")
public class Equipment extends Base {
    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "status")
    private String status;
    private String state;
    @ManyToMany(mappedBy = "equipments")
    @JsonIgnore
    private Set<Laboratory> laboratories;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private EquipmentType equipmentType;


}
