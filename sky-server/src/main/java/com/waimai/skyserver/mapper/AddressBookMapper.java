package com.waimai.skyserver.mapper;

import com.waimai.skypojo.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressBookMapper {
    /**
     * 新增 地址
     *
     * @param addressBook
     */
    @Insert("insert into address_book" +
            "        (user_id, consignee, phone, sex, province_code, province_name, city_code, city_name, district_code," +
            "         district_name, detail, label, is_default)" +
            "        values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}," +
            "                #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(AddressBook addressBook);

    /**
     * 条件查询 登录用户的所有地址
     *
     * @param addressBook
     * @return
     * 配置xml
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 修改地址
     *
     * @param addressBook
     * 配置xml
     */
    void update(AddressBook addressBook);



    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);


    /**
     * 根据id删除地址
     *
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);


    /**
     * 设置默认地址
     *
     * @param id
     */
    void setDefault(Long id);
}
