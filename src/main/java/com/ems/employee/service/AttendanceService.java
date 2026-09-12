package com.ems.employee.service;

import com.ems.employee.dto.attendance.AttendanceRequestDTO;
import com.ems.employee.dto.attendance.AttendanceResponseDTO;
import com.ems.employee.entity.Attendance;
import com.ems.employee.entity.Employee;
import com.ems.employee.exception.ResourceNotFoundException;
import com.ems.employee.repository.AttendanceRepository;
import com.ems.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    private AttendanceRepository attendanceRepository;
    private EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    public AttendanceResponseDTO createAttendance(AttendanceRequestDTO requestDTO) {

        Attendance attendance = new Attendance();

        attendance.setDate(requestDTO.getDate());
        attendance.setCheckIn(requestDTO.getCheckIn());
        attendance.setCheckOut(requestDTO.getCheckOut());
        attendance.setStatus(requestDTO.getStatus());

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return convertToDTO(savedAttendance);
    }

    public List<AttendanceResponseDTO> getAllAttendances() {

        List<Attendance> attendances = attendanceRepository.findAll();
        List<AttendanceResponseDTO> dtos = new ArrayList<>();

        for (Attendance attendance : attendances) {
            dtos.add(convertToDTO(attendance));
        }

        return dtos;
    }

    public AttendanceResponseDTO getAttendanceById(int id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found"));

        return convertToDTO(attendance);
    }

    public AttendanceResponseDTO updateAttendance(
            int id,
            AttendanceRequestDTO requestDTO) {

        Attendance existingAttendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found"));

        existingAttendance.setDate(requestDTO.getDate());
        existingAttendance.setCheckIn(requestDTO.getCheckIn());
        existingAttendance.setCheckOut(requestDTO.getCheckOut());
        existingAttendance.setStatus(requestDTO.getStatus());

        Attendance updatedAttendance = attendanceRepository.save(existingAttendance);

        return convertToDTO(updatedAttendance);
    }

    public void deleteAttendanceById(int id) {

        attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found"));

        attendanceRepository.deleteById(id);
    }

    public AttendanceResponseDTO assignEmployee(
            int attendanceId,
            int employeeId) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found"));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        attendance.setEmployee(employee);

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return convertToDTO(updatedAttendance);
    }

    public AttendanceResponseDTO convertToDTO(Attendance attendance) {

        AttendanceResponseDTO dto = new AttendanceResponseDTO();

        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setCheckIn(attendance.getCheckIn());
        dto.setCheckOut(attendance.getCheckOut());
        dto.setStatus(attendance.getStatus());

        return dto;
    }
}