package com.waimai.skyserver.controller.admin;

import com.waimai.skycommon.constant.JwtClaimsConstant;
import com.waimai.skycommon.properties.JwtProperties;
import com.waimai.skycommon.result.PageResult;
import com.waimai.skycommon.result.Result;
import com.waimai.skycommon.utils.JwtUtil;
import com.waimai.skypojo.dto.EmployeeDTO;
import com.waimai.skypojo.dto.EmployeeLoginDTO;
import com.waimai.skypojo.dto.EmployeePageQueryDTO;
import com.waimai.skypojo.entity.Employee;
import com.waimai.skypojo.vo.EmployeeLoginVO;
import com.waimai.skyserver.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "管理员员工管理",description = "员工管理")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Operation(summary = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        Employee employee = employeeService.login(employeeLoginDTO);

        //登陆成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token= JwtUtil.ceateJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO= EmployeeLoginVO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增员工")
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工，员工数据：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }


    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     * @ParameterObject 告诉SpringDoc将对象参数展开为独立的Query参数，否则会被视为单个object类型
     */
    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult<Employee>> page(@ParameterObject EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工：{}", employeePageQueryDTO);
        PageResult<Employee> pageQueryData = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageQueryData);
    }


    /**
     * 启用禁用员工账号
     *
     * @param status 状态，1启用，0禁用
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用员工账号")
    //@PathVariable("status")是路径参数，如果参数名与路径参数一致，可以不写@PathVariable
    public Result startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("启用禁用员工账号：{},{}",status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询员工信息")
    public  Result<Employee> getById(@PathVariable("id") long id){
        log.info("查询员工信息，员工id为{}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }



}
