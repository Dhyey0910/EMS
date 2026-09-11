package com.ems.employee.service;

import com.ems.employee.entity.Attendance;
import com.ems.employee.entity.Employee;
import com.ems.employee.repository.AttendanceRepository;
import com.ems.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private AttendanceRepository attendanceRepository;

    private EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository  = employeeRepository;
    }

    public List<Attendance> getAllAttendances(){
        return attendanceRepository.findAll();
    }

    public Attendance createAttendance(Attendance attendance){
        return attendanceRepository.save(attendance);
    }

    public Attendance assignEmployee(int attendanceId,int employeeId){
        Optional<Attendance> attendance = attendanceRepository.findById(attendanceId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent() && attendance.isPresent()){
            Attendance attendance1 = attendance.get();
            Employee employee1 = employee.get();
            attendance1.setEmployee(employee1);

            return attendanceRepository.save(attendance1);
        }
        else{
            return null;
        }
    }

    public Optional<Attendance> getAttendanceById(int id){
        return attendanceRepository.findById(id);
    }

    public Attendance updateAttendance(int id, Attendance attendance){
        Optional<Attendance> attendance1 =  attendanceRepository.findById(id);

        if(attendance1.isPresent()){
            Attendance updatedAttendance = attendance1.get();
            updatedAttendance.setCheckIn(attendance.getCheckIn());
            updatedAttendance.setCheckOut(attendance.getCheckOut());
            updatedAttendance.setDate(attendance.getDate());
            updatedAttendance.setStatus(attendance.getStatus());

            return attendanceRepository.save(updatedAttendance);
        }
        else{
            return null;
        }
    }

    public void deleteAttendanceById(int id){
        attendanceRepository.deleteById(id);
        return;
    }
}
