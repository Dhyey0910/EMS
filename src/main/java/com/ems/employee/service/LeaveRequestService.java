package com.ems.employee.service;

import com.ems.employee.dto.leave.LeaveRequestRequestDTO;
import com.ems.employee.dto.leave.LeaveRequestResponseDTO;
import com.ems.employee.entity.Employee;
import com.ems.employee.entity.LeaveRequest;
import com.ems.employee.exception.ResourceNotFoundException;
import com.ems.employee.repository.EmployeeRepository;
import com.ems.employee.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveRequestService {

    private LeaveRequestRepository leaveRequestRepository;
    private EmployeeRepository employeeRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository,
                               EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveRequestResponseDTO> getAllLeaveRequests() {

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();

        List<LeaveRequestResponseDTO> dtos = new ArrayList<>();

        for (LeaveRequest leaveRequest : leaveRequests) {
            dtos.add(convertToDTO(leaveRequest));
        }

        return dtos;
    }

    public LeaveRequestResponseDTO createLeaveRequest(
            LeaveRequestRequestDTO requestDTO) {

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setStartDate(requestDTO.getStartDate());
        leaveRequest.setEndDate(requestDTO.getEndDate());
        leaveRequest.setStatus(requestDTO.getStatus());
        leaveRequest.setReason(requestDTO.getReason());

        LeaveRequest savedLeaveRequest =
                leaveRequestRepository.save(leaveRequest);

        return convertToDTO(savedLeaveRequest);
    }

    public LeaveRequestResponseDTO getLeaveRequestById(int id) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        return convertToDTO(leaveRequest);
    }

    public LeaveRequestResponseDTO updateLeaveRequest(
            int id,
            LeaveRequestRequestDTO requestDTO) {

        LeaveRequest existingLeaveRequest =
                leaveRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Leave request not found"));

        existingLeaveRequest.setStartDate(requestDTO.getStartDate());
        existingLeaveRequest.setEndDate(requestDTO.getEndDate());
        existingLeaveRequest.setStatus(requestDTO.getStatus());
        existingLeaveRequest.setReason(requestDTO.getReason());

        LeaveRequest updatedLeaveRequest =
                leaveRequestRepository.save(existingLeaveRequest);

        return convertToDTO(updatedLeaveRequest);
    }

    public void deleteLeaveRequestById(int id) {

        leaveRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave request not found"));

        leaveRequestRepository.deleteById(id);
    }

    public LeaveRequestResponseDTO assignEmployee(
            int leaveRequestId,
            int employeeId) {

        LeaveRequest leaveRequest =
                leaveRequestRepository.findById(leaveRequestId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Leave request not found"));

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Employee not found"));

        leaveRequest.setEmployee(employee);

        LeaveRequest updatedLeaveRequest =
                leaveRequestRepository.save(leaveRequest);

        return convertToDTO(updatedLeaveRequest);
    }

    public LeaveRequestResponseDTO convertToDTO(
            LeaveRequest leaveRequest) {

        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();

        dto.setId(leaveRequest.getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setStatus(leaveRequest.getStatus());
        dto.setReason(leaveRequest.getReason());

        return dto;
    }
}