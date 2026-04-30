package com.waimai.skyserver.service.impl;

import com.waimai.skycommon.context.BaseContext;
import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.entity.AddressBook;
import com.waimai.skyserver.mapper.AddressBookMapper;
import com.waimai.skyserver.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     *
     * @param addressBook
     */
    public void save(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }




    /**
     * 查询登录用户所有地址
     *
     * @param addressBook
     * @return
     */
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }


    /**
     * 根据用户id修改地址
     *
     * @param addressBook
     */
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id获取地址
     *
     * @param id
     * @return
     */
    public AddressBook getById(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        return addressBook;
    }


    /**
     * 根据id删除地址
     *
     * @param id
     */
    public void delete(Long id) {
        addressBookMapper.deleteById(id);

    }


    /**
     * 设置默认地址
     *
     * @param addressBook
     */
    public void setDefault(AddressBook addressBook){
        //1. 将当前用户的所有地址的 is_default 设置为 0， update address_book set is_default = ? where user_id = ?
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.update(addressBook);

        //2. 将当前地址的 is_default 设置为 1
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }



}
