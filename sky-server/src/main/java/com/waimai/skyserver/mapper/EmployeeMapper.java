package com.waimai.skyserver.mapper;

import com.github.pagehelper.Page;
import com.waimai.skycommon.enumeration.OperationType;
import com.waimai.skypojo.dto.EmployeePageQueryDTO;
import com.waimai.skypojo.entity.Employee;
import com.waimai.skyserver.annotation.AutoFill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 保存员工信息
     *
     * @param employee
     */
    @Insert("insert into employee"+
            " (username,password, name, phone, sex, id_number, status, create_time, update_time, create_user, update_user)"
            +
            "values (#{username},#{password}, #{name}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     * @return
     * 配置了xml
     */
    Page<Employee> pageQuery( EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 启用禁用员工账号
     * @param employee
     * 配置了xml，这个可以配置全部字段进行修改，这样以后需要修改的字段，就不用再写sql了
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(long id);
}
