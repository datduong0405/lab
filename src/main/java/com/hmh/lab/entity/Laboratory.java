package com.hmh.lab.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "laboratory")
public class Laboratory extends Base {
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String status;

    @OneToMany(
            mappedBy = "laboratory",
            cascade = CascadeType.ALL
    )
    private List<Reservation> users = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "lab_equipment",
            joinColumns = @JoinColumn(name = "lab_id"),
            inverseJoinColumns = @JoinColumn(name = "equip_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Equipment> equipments;


}
