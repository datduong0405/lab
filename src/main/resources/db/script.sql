create table if not exists equipment_type
(
	id varchar(255) not null
		primary key,
	name varchar(255) null,
	quantity int not null
);

create table if not exists equipment
(
	id bigint auto_increment
		primary key,
	created_date datetime(6) null,
	modified_date datetime(6) null,
	description varchar(255) null,
	name varchar(255) null,
	state varchar(255) null,
	status varchar(255) null,
	type varchar(255) null,
	constraint FKi8fhhtdiat4tturkug85spc92
		foreign key (type) references equipment_type (id)
);

create trigger equip_delete_trigger
	after delete
	on equipment
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "equipment",
        JSON_OBJECT(
            "name", OLD.name,
            "type", OLD.type,
            "status", OLD.status,
            "state", OLD.state,
            "createdDate", OLD.created_date,
            "modified_date", OLD.modified_date,
            "description", OLD.description
        ),
       null,
        'UPDATE',
        CURRENT_TIMESTAMP
    );

create trigger equip_insert_trigger
	after insert
	on equipment
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "equipment",
        null,
        JSON_OBJECT(
            "name", NEW.name,
            "type", NEW.type,
            "status", NEW.status,
            "state", NEW.state,
            "createdDate", NEW.created_date,
            "description", NEW.description
        ),
        'INSERT',
        CURRENT_TIMESTAMP
    );

create trigger equip_update_trigger
	after update
	on equipment
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "equipment",
        JSON_OBJECT(
            "name", OLD.name,
            "type", OLD.type,
            "status", OLD.status,
            "state", OLD.state,
            "createdDate", OLD.created_date,
            "modified_date", OLD.modified_date,
            "description", OLD.description
        ),
        JSON_OBJECT(
            "name", NEW.name,
            "type", NEW.type,
            "status", NEW.status,
            "quantity", NEW.state,
            "createdDate", NEW.created_date,
             "modified_date", NEW.modified_date,
            "description", NEW.description
        ),
        'UPDATE',
        CURRENT_TIMESTAMP
    );

create table if not exists history
(
	history_id bigint auto_increment,
	table_name varchar(255) not null,
	old_row_data json null,
	new_row_data json null,
	change_type enum('INSERT', 'UPDATE', 'DELETE') not null,
	change_date datetime not null,
	primary key (history_id, table_name, change_type, change_date)
);

create table if not exists role
(
	id bigint auto_increment
		primary key,
	created_date datetime(6) null,
	modified_date datetime(6) null,
	name varchar(255) not null
);

create table if not exists user
(
	id bigint auto_increment
		primary key,
	created_date datetime(6) null,
	modified_date datetime(6) null,
	address varchar(255) null,
	department varchar(255) null,
	email varchar(255) not null,
	first_name varchar(255) null,
	last_name varchar(255) null,
	password varchar(60) not null,
	phone varchar(255) null,
	status varchar(255) null,
	username varchar(50) not null,
	role bigint null,
	constraint UK_t8tbwelrnviudxdaggwr1kd9b
		unique (username),
	constraint FKl5alypubd40lwejc45vl35wjb
		foreign key (role) references role (id)
);

create table if not exists laboratory
(
	id bigint auto_increment
		primary key,
	created_date datetime(6) null,
	modified_date datetime(6) null,
	name varchar(255) null,
	status varchar(255) null,
	type varchar(255) null,
	admin bigint null,
	constraint FKcn0v9xycw9ydoeqb3t0m0q6fn
		foreign key (admin) references user (id)
);

create table if not exists lab_equipment
(
	lab_id bigint not null,
	equip_id bigint not null,
	primary key (lab_id, equip_id),
	constraint FKdnmcbnjfg0htjb7q976x61xfv
		foreign key (equip_id) references equipment (id),
	constraint FKnim5c8pupl1uw4r34nyuh0svq
		foreign key (lab_id) references laboratory (id)
);

create trigger lab_delete_trigger
	after delete
	on laboratory
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "laboratory",
        JSON_OBJECT(
            "name", OLD.name,
            "type", OLD.type,
            "status", OLD.status,
            "modified_date", OLD.modified_date,
            "admin",  (select concat(first_name,  " ",last_name) as admin  from user where id =  OLD.admin)
        ),
        null,
        'DELETE',
        CURRENT_TIMESTAMP
    );

create trigger lab_insert_trigger
	after insert
	on laboratory
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "laboratory",
        null,
        JSON_OBJECT(
            "name", NEW.name,
            "type", NEW.type,
            "status", NEW.status,
            "createdDate", NEW.created_date,
            "admin",  (select concat(first_name,  " ",last_name) as admin  from user where id =  NEW.admin)
        ),
        'INSERT',
        CURRENT_TIMESTAMP
    );

create trigger lab_update_trigger
	after update
	on laboratory
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "laboratory",
        JSON_OBJECT(
            "name", OLD.name,
            "type", OLD.type,
            "status", OLD.status,
            "modified_date", OLD.modified_date,
            "admin",  (select concat(first_name,  " ",last_name) as admin  from user where id =  OLD.admin)
        ),
        JSON_OBJECT(
            "name", NEW.name,
            "type", NEW.type,
            "status", NEW.status,
            "modified_date", NEW.modified_date,
            "admin",  (select concat(first_name,  " ",last_name) as admin  from user where id =  NEW.admin)
        ),
        'UPDATE',
        CURRENT_TIMESTAMP
    );

create table if not exists reservation
(
	id bigint auto_increment
		primary key,
	end_date datetime(6) null,
	start_date datetime(6) null,
	status varchar(255) null,
	laboratory_id bigint null,
	user_id bigint null,
	constraint FKgpgp4ebufkigbyvqclo73k38c
		foreign key (laboratory_id) references laboratory (id),
	constraint FKm4oimk0l1757o9pwavorj6ljg
		foreign key (user_id) references user (id)
);

create trigger reservation_insert_trigger
	after insert
	on reservation
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "reservation",
        null,
        JSON_OBJECT(
            "user", (select concat(first_name, " ",last_name) as user  from user where id =  NEW.user_id),
            "laboratory", (select name as lab_name from laboratory where id =  NEW.laboratory_id),
            "start_date", NEW.start_date,
            "end_date", NEW.end_date,
            "status", NEW.status),
        'INSERT',
        CURRENT_TIMESTAMP
        
    );

create trigger reservation_update_trigger
	after update
	on reservation
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "reservation",
          JSON_OBJECT(
            "status", OLD.status),
        JSON_OBJECT(
            "status", NEW.status),
        'UPDATE',
        CURRENT_TIMESTAMP
        
    );

create trigger user_delete_trigger
	after delete
	on user
	for each row
	INSERT INTO history (
       table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "user",
        JSON_OBJECT(
            "firstName", OLD.first_name,
            "lastName", OLD.last_name,
            "email", OLD.email,
            "status", OLD.status,
            "phone", OLD.phone,
            "modified_date", OLD.modified_date,
			"created_date", OLD.created_date,
            "department", OLD.department
        ),
        null,
        'DELETE',
        CURRENT_TIMESTAMP
    );

create trigger user_insert_trigger
	after insert
	on user
	for each row
	INSERT INTO history (
		table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "user",
        null,
        JSON_OBJECT(
            "firstName", NEW.first_name,
            "lastName", NEW.last_name,
            "email", NEW.email,
            "status", NEW.status,
            "phone", NEW.phone,
            "role", NEW.role,
            "created_date", NEW.created_date,
            "department", NEW.department,
            "address", NEW.address
        ),
        'INSERT',
        CURRENT_TIMESTAMP
    );

create trigger user_update_trigger
	after update
	on user
	for each row
	INSERT INTO history (
       table_name,
        old_row_data,
        new_row_data,
        change_type,
        change_date
    )
    VALUES(
        "user",
        JSON_OBJECT(
            "firstName", OLD.first_name,
            "lastName", OLD.last_name,
            "email", OLD.email,
            "status", OLD.status,
            "phone", OLD.phone,
            "modified_date", OLD.modified_date,
            "department", OLD.department
        ),
        JSON_OBJECT(
           "firstName", NEW.first_name,
            "lastName", NEW.last_name,
            "email", NEW.email,
            "status", NEW.status,
            "phone", NEW.phone,
            "modified_date", NEW.modified_date,
            "department", NEW.department
        ),
        'UPDATE',
        CURRENT_TIMESTAMP
    );

create event if not exists delete_res_finish on schedule
	every '1' HOUR
	starts '2022-12-03 17:57:03'
	enable
	do
	delete from reservation where status = "FINISH";

create event if not exists update_lab_by_status_end on schedule
	every '5' SECOND
	starts '2022-12-03 17:26:35'
	enable
	do
	UPDATE  laboratory INNER JOIN reservation 
   ON laboratory.id = reservation.laboratory_id  
   SET  laboratory.status = "AVAILABLE"
   where laboratory.status ="IN USE" AND reservation.status = "FINISH"
		AND (now() not between reservation.start_date and reservation.end_date);

create event if not exists update_lab_by_status_start on schedule
	every '5' SECOND
	starts '2022-12-03 17:40:56'
	enable
	do
	UPDATE  laboratory INNER JOIN reservation 
   ON laboratory.id = reservation.laboratory_id  
   SET  laboratory.status = "IN USE"
   where laboratory.status ="AVAILABLE"  AND reservation.status = "STARTING"
		AND now() between reservation.start_date and reservation.end_date;

create event if not exists update_lab_status on schedule
	every '5' SECOND
	starts '2022-12-03 17:35:25'
	enable
	do
	UPDATE reservation  SET  reservation.status = "FINISH" WHERE reservation.end_date < now() AND (reservation.status = "STARTING" or (reservation.end_date is null));

create event if not exists update_res_status_start on schedule
	every '5' SECOND
	starts '2022-12-03 17:31:47'
	enable
	do
	UPDATE  reservation
   SET  reservation.status = "STARTING"
   where reservation.status = 'NEW' AND now() between reservation.start_date and reservation.end_date;


