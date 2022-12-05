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
            ",e.state as state" +
            ",la.name as labName FROM equipment e  " +
            "left join lab_equipment l on e.id = l.equip_id " +
            "left join laboratory la on l.lab_id = la.id", nativeQuery = true)
    List<EquipAndLabs> getEquipmentInLabs();

    @Query(value = "select equipment.* from  equipment\n" +
            "        inner join lab_equipment on equipment.id = lab_equipment.equip_id\n" +
            "        inner join laboratory on laboratory.id = lab_equipment.lab_id\n" +
            "        where laboratory.id =:id", nativeQuery = true)
    List<Equipment> getAllEquipUsing(@Param("id") Long id);

    @Query(value = "select * from  equipment where id not in ( select equip_id from lab_equipment) and status = 'AVAILABLE' ", nativeQuery = true)
    List<Equipment> getALlEquipAvailable();







}
