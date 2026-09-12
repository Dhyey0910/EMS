package com.ems.employee.service;

import com.ems.employee.entity.Employee;
import com.ems.employee.entity.LeaveRequest;
import com.ems.employee.repository.EmployeeRepository;
import com.ems.employee.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    private LeaveRequestRepository leaveRequestRepository;
    private EmployeeRepository employeeRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository,
                               EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public Optional<LeaveRequest> getLeaveRequestById(int id) {
        return leaveRequestRepository.findById(id);
    }

    public LeaveRequest updateLeaveRequest(int id, LeaveRequest leaveRequest) {
        Optional<LeaveRequest> leaveRequest1 = leaveRequestRepository.findById(id);

        if (leaveRequest1.isPresent()) {
            LeaveRequest existingLeaveRequest = leaveRequest1.get();

            existingLeaveRequest.setStartDate(leaveRequest.getStartDate());
            existingLeaveRequest.setEndDate(leaveRequest.getEndDate());
            existingLeaveRequest.setStatus(leaveRequest.getStatus());
            existingLeaveRequest.setReason(leaveRequest.getReason());

            return leaveRequestRepository.save(existingLeaveRequest);
        } else {
            return null;
        }
    }

    public LeaveRequest assignEmployee(int leaveRequestId, int employeeId) {
        Optional<LeaveRequest> leaveRequest1 =
                leaveRequestRepository.findById(leaveRequestId);

        Optional<Employee> employee1 =
                employeeRepository.findById(employeeId);

        if (leaveRequest1.isPresent() && employee1.isPresent()) {
            LeaveRequest leaveRequest = leaveRequest1.get();
            Employee employee = employee1.get();

            leaveRequest.setEmployee(employee);

            return leaveRequestRepository.save(leaveRequest);
        } else {
            return null;
        }
    }

    public void deleteLeaveRequestById(int id) {
        leaveRequestRepository.deleteById(id);
    }
}