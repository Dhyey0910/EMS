package com.ems.employee.controller;

import com.ems.employee.entity.LeaveRequest;
import com.ems.employee.service.LeaveRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LeaveRequestController {

    private LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/leave-requests")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @PostMapping("/leave-requests")
    public LeaveRequest createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.createLeaveRequest(leaveRequest);
    }

    @GetMapping("/leave-requests/{id}")
    public Optional<LeaveRequest> getLeaveRequestById(@PathVariable int id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    @PutMapping("/leave-requests/{id}")
    public LeaveRequest updateLeaveRequest(@PathVariable int id,
                                           @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.updateLeaveRequest(id, leaveRequest);
    }

    @PutMapping("/leave-requests/{leaveRequestId}/employee/{employeeId}")
    public LeaveRequest assignEmployee(@PathVariable int leaveRequestId,
                                       @PathVariable int employeeId) {
        return leaveRequestService.assignEmployee(leaveRequestId, employeeId);
    }

    @DeleteMapping("/leave-requests/{id}")
    public void deleteLeaveRequestById(@PathVariable int id) {
        leaveRequestService.deleteLeaveRequestById(id);
    }
}