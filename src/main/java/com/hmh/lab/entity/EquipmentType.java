package com.hmh.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "equipment_type")
public class EquipmentType {
    @Id
    @Column(nullable = false)
    private String id;
    private String name;
    private int quantity;

    @OneToMany(
            mappedBy = "equipmentType",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Equipment> equipments = new ArrayList<>();

}
