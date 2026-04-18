package com.waimai.skyserver.service;

import com.waimai.skypojo.dto.EmployeeDTO;
import com.waimai.skypojo.dto.EmployeeLoginDTO;
import com.waimai.skypojo.entity.Employee;
import org.springframework.stereotype.Service;


public interface EmployeeService {
    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 保存员工信息
     *
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

}