package com.hmh.lab.controller;

import com.hmh.lab.dto.EquipDto;
import com.hmh.lab.dto.LabDto;
import com.hmh.lab.dto.ResDto;
import com.hmh.lab.dto.UserDto;
import com.hmh.lab.entity.*;
import com.hmh.lab.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/lab/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LabRepository labRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ReservationRepository reservationRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/username")
    public ResponseEntity<?> findUserByUsername(@RequestParam String key) {
        return ResponseEntity.ok(userRepository.findByUsername(key));
    }


    @GetMapping("/delete")
    public void deleteUser(@RequestParam String key) {
        User user = userRepository.findByUsername(key);
        userRepository.delete(user);
    }

    @GetMapping("/id")
    public ResponseEntity<?> findUserById(@RequestParam Long key) {
        return ResponseEntity.ok(userRepository.findById(key));
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable(name = "id") Long id,
                                      @RequestBody UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        Instant instant = Instant.now();
        user.ifPresent(u -> {
            u.setEmail(userDto.getEmail());
            u.setDepartment(userDto.getDepartment());
            u.setPhone(userDto.getPhone());
            u.setFirstName(userDto.getFirstName());
            u.setLastName(userDto.getLastName());
            u.setStatus(userDto.getStatus());
            u.setModifiedDate(Date.from(instant));
        });


        return ResponseEntity.ok(userRepository.save(user.get()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        Role role = roleRepository.findByNameEqualsIgnoreCase(userDto.getRole().toUpperCase());

        Instant instant = Instant.now();
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setDepartment(userDto.getDepartment());
        user.setPhone(userDto.getPhone());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setStatus(userDto.getStatus());
        user.setRole(role);
        user.setCreatedDate(Date.from(instant));

        User createdUser = userRepository.save(user);

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/laboratory")
    public ResponseEntity<?> findAllLab() {
        return ResponseEntity.ok(labRepository.findAll());
    }

    @DeleteMapping("/laboratory/delete/{id}")
    public void deleteLab(@PathVariable(name = "id") Long id) {
        labRepository.delete(labRepository.findById(id).get());
    }

    @PatchMapping("/laboratory/edit/{id}")
    public ResponseEntity<?> editLab(@PathVariable(name = "id") Long id,
                                     @RequestBody LabDto labDto) {
        Optional<Laboratory> laboratory = labRepository.findById(id);

        Optional<User> user = userRepository.findById(Long.parseLong(labDto.getUser()));

        Instant instant = Instant.now();

        laboratory.ifPresent(u -> {
            u.setUser(user.get());
            u.setStatus(labDto.getStatus());
            u.setType(labDto.getType());
            u.setName(labDto.getName());
            u.setModifiedDate(Date.from(instant));
        });

        return ResponseEntity.ok(labRepository.save(laboratory.get()));
    }

    @PostMapping("/laboratory/create")
    public ResponseEntity<?> createLab(
            @RequestBody LabDto labDto) {

        Optional<User> user = userRepository.findById(Long.parseLong(labDto.getUser()));

        Instant instant = Instant.now();

        Laboratory lab = new Laboratory();
        lab.setUser(user.get());
        lab.setType(labDto.getType());
        lab.setStatus(labDto.getStatus());
        lab.setName(labDto.getName());
        lab.setCreatedDate(Date.from(instant));
        Laboratory addUser = labRepository.save(lab);

        return ResponseEntity.ok(addUser);

    }

    @PatchMapping("/laboratory/addEquipment/{id}")
    public ResponseEntity<?> addEquipmentToLab(@PathVariable(name = "id") Long id,
                                               @RequestBody LabDto labDto) {
        List<Equipment> equipmentList = equipmentRepository.findAllById(labDto.getEquipmentList());
        Instant instant = Instant.now();
        Optional<Laboratory> lab = labRepository.findById(id);
        lab.ifPresent(l -> {
            l.setEquipments((Set<Equipment>) equipmentList);
            l.setModifiedDate(Date.from(instant));
        });


        return ResponseEntity.ok(labRepository.save(lab.get()));
    }

    @GetMapping("/equipment")
    public ResponseEntity<?> findAllEuip() {
        return ResponseEntity.ok(equipmentRepository.findAll());
    }


    @DeleteMapping("/equipment/delete/{id}")
    public void deleteEquip(@PathVariable(name = "id") Long id) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        equipmentRepository.delete(equipment.get());
    }

    @GetMapping("/equipment/labs")
    public ResponseEntity<?> getLabHasEquipmentId() {
        return ResponseEntity.ok(equipmentRepository.getEquipmentInLabs());
    }


    @PostMapping("/equipment/create")
    public ResponseEntity<?> createEquip(@RequestBody EquipDto equipDto) {
        Equipment equipment = new Equipment();
        Instant instant = Instant.now();

        equipment.setType(equipDto.getType());
        equipment.setStatus(equipDto.getStatus());
        equipment.setName(equipDto.getName());
        equipment.setQuantity(equipDto.getQuantity());
        equipment.setDescription(equipDto.getDescription());
        equipment.setCreatedDate(Date.from(instant));

        Equipment created = equipmentRepository.save(equipment);

        return ResponseEntity.ok(created);
    }

    @PatchMapping("/equipment/edit/{id}")
    public ResponseEntity<?> editEquip(@PathVariable(name = "id") Long id,
                                       @RequestBody EquipDto equipDto) {

        Equipment equipment = equipmentRepository.findById(id).get();

        Instant instant = Instant.now();

        equipment.setType(equipDto.getType());
        equipment.setStatus(equipDto.getStatus());
        equipment.setName(equipDto.getName());
        equipment.setQuantity(equipDto.getQuantity());
        equipment.setDescription(equipDto.getDescription());
        equipment.setModifiedDate(Date.from(instant));

        Equipment updated = equipmentRepository.save(equipment);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/reservation")
    public ResponseEntity<?> getAllReservation() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }


    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> getAllReservation(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(reservationRepository.getTeacherUsingLabs(id));
    }

    @PostMapping("/reservation/create")
    public ResponseEntity<?> createRes(@RequestBody ResDto resDto) {

        logger.info("res dto: {}", resDto);
        User user = userRepository.findById(Long.parseLong(resDto.getUserId())).get();
        Laboratory lab = labRepository.findById(Long.parseLong(resDto.getLabId())).get();

        UserLabId userLabId = new UserLabId();
        userLabId.setUserId(Long.parseLong(resDto.getUserId()));
        userLabId.setLabId(Long.parseLong(resDto.getLabId()));

        Reservation res = new Reservation();
        res.setStatus("NEW");
        res.setUser(user);
        res.setLaboratory(lab);
        res.setStart_date(resDto.getStartDate());
        res.setEnd_date(resDto.getEndDate());

        Reservation newRes = reservationRepository.save(res);

        return ResponseEntity.ok(newRes);

    }
}
