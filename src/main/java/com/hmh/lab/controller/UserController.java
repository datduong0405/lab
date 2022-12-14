package com.hmh.lab.controller;

import com.hmh.lab.dto.*;
import com.hmh.lab.entity.*;
import com.hmh.lab.exception.ResourceDeleteException;
import com.hmh.lab.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lab/user")
@CrossOrigin(origins = "*")
public class

UserController {

    private final ModelMapper modelMapper;

    @Autowired
    EquipmentTypeRepository equipmentTypeRepository;
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

    public UserController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

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

    @PatchMapping("/own/edit/{id}")
    public ResponseEntity<?> myEdit(@PathVariable(name = "id") Long id,
                                    @RequestBody UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        Instant instant = Instant.now();
        user.ifPresent(u -> {
            u.setUsername(userDto.getUsername());
            u.setPassword(userDto.getPassword());
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

    @GetMapping("/laboratory/labadmin/{id}")
    public ResponseEntity<?> getLabByAdmin(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok(labRepository.findAllByUser(user));

    }


    @PatchMapping("/laboratory/addEquipment/{id}")
    public ResponseEntity<?> addEquipmentToLab(@PathVariable(name = "id") Long id,
                                               @RequestBody LabDto labDto) {
        List<Equipment> equipmentList = equipmentRepository.findAllById(labDto.getEquipmentList());
        Instant instant = Instant.now();
        Optional<Laboratory> lab = labRepository.findById(id);
        lab.ifPresent(l -> {
            l.getEquipments().clear();
            l.setEquipments(equipmentList);
            l.setModifiedDate(Date.from(instant));
        });


        return ResponseEntity.ok(labRepository.save(lab.get()));
    }

    @GetMapping("/equipment/available")
    public ResponseEntity<?> getALlAvailableEquip() {
        return ResponseEntity.ok(equipmentRepository.getALlEquipAvailable());
    }

    @GetMapping("/equipment/using/{id}")
    public ResponseEntity<?> getAllEquipUsing(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(equipmentRepository.getAllEquipUsing(id));
    }

    @GetMapping("/equipment")
    public ResponseEntity<?> findAllEuip() {
        return ResponseEntity.ok(equipmentRepository.findAll());
    }


    @GetMapping("/etype")
    public ResponseEntity<?> findAllType() {
        return ResponseEntity.ok(equipmentTypeRepository.findAll());
    }

    @PostMapping("/etype/create")
    public ResponseEntity<?> createType(@RequestBody TypeDto typeDto) {
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEquipments(null);
        equipmentType.setId(typeDto.getId());
        equipmentType.setName(typeDto.getName());
        equipmentType.setQuantity(0);
        return ResponseEntity.ok(equipmentTypeRepository.save(equipmentType));
    }

    @PatchMapping("/etype/edit/{id}")
    public ResponseEntity<?> editEType(@PathVariable(name = "id") String id, @RequestBody TypeDto typeDto) {
        EquipmentType equipmentType = equipmentTypeRepository.findById(id).get();
        equipmentType.setName(typeDto.getName());
        EquipmentType e = equipmentTypeRepository.save(equipmentType);
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/etype/delete/{id}")
    public void deleteType(@PathVariable(name = "id") String id) {
        EquipmentType equipmentType = equipmentTypeRepository.findById(id).get();
        if (equipmentType.getEquipments().isEmpty()) {
            equipmentTypeRepository.delete(equipmentType);
        } else {
            throw new ResourceDeleteException("Can not delete this type");
        }
    }

    @DeleteMapping("/equipment/delete/{id}")
    public void deleteEquip(@PathVariable(name = "id") Long id) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        Optional<EquipmentType> equipmentType = equipmentTypeRepository.findById(equipment.get().getEquipmentType().getId());
        equipmentType.get().setQuantity(equipmentType.get().getQuantity() - 1);
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

        EquipmentType equipmentType = equipmentTypeRepository.findById(equipDto.getType()).get();
        equipmentType.setQuantity(equipmentType.getQuantity() + 1);

        equipment.setState(equipDto.getState());

        equipment.setEquipmentType(equipmentType);
        equipment.setStatus(equipDto.getStatus());
        equipment.setName(equipDto.getName());

        equipment.setDescription(equipDto.getDescription());
        equipment.setCreatedDate(Date.from(instant));

        equipmentTypeRepository.save(equipmentType);
        Equipment created = equipmentRepository.save(equipment);

        return ResponseEntity.ok(created);
    }

    @PatchMapping("/equipment/edit/{id}")
    public ResponseEntity<?> editEquip(@PathVariable(name = "id") Long id,
                                       @RequestBody EquipDto equipDto) {

        Equipment equipment = equipmentRepository.findById(id).get();
        EquipmentType equipmentType = equipmentTypeRepository.findById(equipDto.getType()).get();
        Instant instant = Instant.now();

        equipment.setState(equipDto.getState());
        equipment.setEquipmentType(equipmentType);
        equipment.setStatus(equipDto.getStatus());
        equipment.setName(equipDto.getName());
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

    @DeleteMapping("/reservation/delete/{id}")
    @Transactional
    public void deleteRes(@PathVariable(name = "id") Long id) {
        reservationRepository.deleleById(id);
    }

    @PatchMapping("/reservation/edit/{id}")
    @Transactional
    public void editRes(@PathVariable(name = "id") Long id, @RequestBody DateRes dateRes) {
        Date startDate = new Date(dateRes.getStartDate() * 1000L);
        Date endDate = new Date(dateRes.getEndDate() * 1000L);
        reservationRepository.editRes(id, startDate, endDate);
    }


    @GetMapping("/test")
    public List<BaseDto> test() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, BaseDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/reservation/create")
    public ResponseEntity<?> createRes(@RequestBody ResDto resDto) {

        logger.info("res dto: {}", resDto);

        Date startDate = new Date(resDto.getStartDate() * 1000L);
        Date endDate = new Date(resDto.getEndDate() * 1000L);
        User user = userRepository.findById(Long.parseLong(resDto.getUserId())).get();
        Laboratory lab = labRepository.findById(Long.parseLong(resDto.getLabId())).get();

        UserLabId userLabId = new UserLabId();
        userLabId.setUserId(Long.parseLong(resDto.getUserId()));
        userLabId.setLabId(Long.parseLong(resDto.getLabId()));

        Reservation res = new Reservation();
        res.setStatus("NEW");
        res.setUser(user);
        res.setLaboratory(lab);
        res.setStart_date(startDate);
        res.setEnd_date(endDate);

        Reservation newRes = reservationRepository.save(res);

        return ResponseEntity.ok(newRes);

    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(labRepository.getHistory());
    }
}
