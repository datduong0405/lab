package com.hmh.lab.repository;

import com.hmh.lab.dto.TeacherUsingLab;
import com.hmh.lab.entity.Reservation;
import com.hmh.lab.entity.UserLabId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, UserLabId> {

    @Query(value = "select l.name as name, l.status as status, l.id as id, r.id as resId,  CONCAT(us.first_name, us.last_name)  as admin,  r.start_date as startDate, r.end_date as endDate from laboratory l \n" +
            "    inner join reservation r \n" +
            "    on l.id = r.laboratory_id \n" +
            "    inner join user u\n" +
            "    on r.user_id = u.id inner join user us on l.admin = us.id\n" +
            "    where u.id = :id \n" +
            "    and r.status != \"FINISH\"\n", nativeQuery = true)
    List<TeacherUsingLab> getTeacherUsingLabs(@Param("id") Long id);

    @Modifying
    @Query(value ="DELETE FROM reservation where id =:id" , nativeQuery = true)
    void deleleById(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE reservation set start_date=:startDate, end_date=:endDate where id = :id ",nativeQuery = true)
    void editRes(@Param("id") Long id, @Param("startDate")Date startDate, @Param("endDate") Date endDate);

}
