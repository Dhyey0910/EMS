package com.ems.employee.controller;

import com.ems.employee.dto.leave.LeaveRequestRequestDTO;
import com.ems.employee.dto.leave.LeaveRequestResponseDTO;
import com.ems.employee.service.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaveRequestController {

    private LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/leave-requests")
    public List<LeaveRequestResponseDTO> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @PostMapping("/leave-requests")
    public LeaveRequestResponseDTO createLeaveRequest(
            @Valid @RequestBody LeaveRequestRequestDTO requestDTO) {

        return leaveRequestService.createLeaveRequest(requestDTO);
    }

    @GetMapping("/leave-requests/{id}")
    public LeaveRequestResponseDTO getLeaveRequestById(
            @PathVariable int id) {

        return leaveRequestService.getLeaveRequestById(id);
    }

    @PutMapping("/leave-requests/{id}")
    public LeaveRequestResponseDTO updateLeaveRequest(
            @PathVariable int id,
            @Valid @RequestBody LeaveRequestRequestDTO requestDTO) {

        return leaveRequestService.updateLeaveRequest(id, requestDTO);
    }

    @PutMapping("/leave-requests/{leaveRequestId}/employee/{employeeId}")
    public LeaveRequestResponseDTO assignEmployee(
            @PathVariable int leaveRequestId,
            @PathVariable int employeeId) {

        return leaveRequestService.assignEmployee(
                leaveRequestId, employeeId);
    }

    @DeleteMapping("/leave-requests/{id}")
    public void deleteLeaveRequestById(@PathVariable int id) {
        leaveRequestService.deleteLeaveRequestById(id);
    }
}