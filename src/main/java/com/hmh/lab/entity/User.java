package com.hmh.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Table(name = "user")
public class User extends Base {
    @NaturalId
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String department;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Reservation> laboratories = new ArrayList<>();

    @OneToMany(
            mappedBy = "user"
    )
    @JsonIgnore
    private Set<Reservation> reservations = new HashSet<>();
}
