package com.hmh.lab.repository;

import com.hmh.lab.dto.History;
import com.hmh.lab.entity.Laboratory;
import com.hmh.lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends JpaRepository<Laboratory, Long> {

    List<Laboratory> findAllByUser(User user);

    @Query(value = "SELECT history_id as id, table_name as tableName,old_row_data as OldData, new_row_data as newData, change_type as type, change_date as Date from history ", nativeQuery = true)
    List<History> getHistory();

}
