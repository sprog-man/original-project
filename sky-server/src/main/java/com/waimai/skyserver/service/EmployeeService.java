package com.waimai.skyserver.service;

import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.EmployeeDTO;
import com.waimai.skypojo.dto.EmployeeLoginDTO;
import com.waimai.skypojo.dto.EmployeePageQueryDTO;
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


    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);


    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee getById(long id);

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}