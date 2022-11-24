package com.hmh.lab.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {
    @EmbeddedId
    private UserLabId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("laboratory_id")
    private Laboratory laboratory;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "status")
    private String status;

    private Reservation(){}

    private Reservation(User user, Laboratory laboratory){
        this.user = user;
        this.laboratory = laboratory;
        this.id = new UserLabId(user.getId(), laboratory.getId());
    }

}
