package com.hmh.lab.repository;

import com.hmh.lab.dto.EquipAndLabs;
import com.hmh.lab.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query(value = "SELECT e.id as id, e.name as name" +
            ",e.description as description" +
            ",e.status as status" +
            ",e.type as type" +
            ",e.quantity as quantity" +
            ",group_concat(la.name separator ', ') as labName FROM equipment e  left join lab_equipment l on e.id = l.equip_id left join laboratory la on l.lab_id = la.id group by e.id", nativeQuery = true)
    List<EquipAndLabs> getEquipmentInLabs();


}
