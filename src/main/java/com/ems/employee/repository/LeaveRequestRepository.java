package com.ems.employee.repository;

import com.ems.employee.entity.Department;
import com.ems.employee.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Integer> {

}
