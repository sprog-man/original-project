package com.waimai.skypojo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    //1：男，0：女
    private String sex;

    private String idNumber;

    private Integer status;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 会导致前端接收到的时间格式不对
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
