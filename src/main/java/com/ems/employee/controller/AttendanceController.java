package com.ems.employee.controller;

import com.ems.employee.dto.attendance.AttendanceRequestDTO;
import com.ems.employee.dto.attendance.AttendanceResponseDTO;
import com.ems.employee.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttendanceController {

    private AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendances")
    public List<AttendanceResponseDTO> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @PostMapping("/attendances")
    public AttendanceResponseDTO createAttendance(
            @Valid @RequestBody AttendanceRequestDTO requestDTO) {

        return attendanceService.createAttendance(requestDTO);
    }

    @GetMapping("/attendances/{id}")
    public AttendanceResponseDTO getAttendanceById(@PathVariable int id) {
        return attendanceService.getAttendanceById(id);
    }

    @PutMapping("/attendances/{id}")
    public AttendanceResponseDTO updateAttendance(
            @PathVariable int id,
            @Valid @RequestBody AttendanceRequestDTO requestDTO) {

        return attendanceService.updateAttendance(id, requestDTO);
    }

    @PutMapping("/attendances/{attendanceId}/employee/{employeeId}")
    public AttendanceResponseDTO assignEmployee(
            @PathVariable int attendanceId,
            @PathVariable int employeeId) {

        return attendanceService.assignEmployee(attendanceId, employeeId);
    }

    @DeleteMapping("/attendances/{id}")
    public void deleteAttendanceById(@PathVariable int id) {
        attendanceService.deleteAttendanceById(id);
    }
}