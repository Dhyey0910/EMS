package com.ems.employee.controller;

import com.ems.employee.entity.Attendance;
import com.ems.employee.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AttendanceController {

    private AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendances")
    public List<Attendance> getAllAttendances(){
        return attendanceService.getAllAttendances();
    }

    @PostMapping("/attendances")
    public Attendance createAttendance(@RequestBody Attendance attendance){
        return attendanceService.createAttendance(attendance);
    }

    @PutMapping("/attendances/{attendanceId}/employee/{employeeId}")
    public Attendance assignEmployee(@PathVariable int employeeId,@PathVariable int attendanceId){
        return attendanceService.assignEmployee(attendanceId,employeeId);
    }

    @GetMapping("/attendances/{id}")
    public Optional<Attendance> getAttendanceById(@PathVariable int id){
        return attendanceService.getAttendanceById(id);
    }

    @PutMapping("/attendances/{id}")
    public Attendance updateAttendance(@PathVariable int id, @RequestBody Attendance attendance){
        return attendanceService.updateAttendance(id,attendance);
    }

    @DeleteMapping("/attendances/{id}")
    public void deleteAttendanceById(@PathVariable int id){
        attendanceService.deleteAttendanceById(id);
    }
}
