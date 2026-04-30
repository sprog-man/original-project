package com.waimai.skyserver.controller.user;

import com.waimai.skycommon.context.BaseContext;
import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.entity.AddressBook;
import com.waimai.skyserver.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Tag(name = "用户地址簿模块")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     *
     * @param addressBook
     */
    @PostMapping
    @Operation(summary = "新增地址")
    public Result save(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 查询登录用户所有地址
     */
    @GetMapping("/list")
    @Operation(summary = "查询登录用户所有地址")
    public Result<List<AddressBook>> list() {
        log.info("查询登录用户所有地址");
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    /**
     * 根据用户id修改地址
     */
    @PutMapping
    @Operation(summary = "根据用户id修改地址")
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("根据用户id修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址")
    public Result<AddressBook> getById(@RequestBody Long id) {
        log.info("根据id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id删除地址
     *
     * @param id
     */
    @DeleteMapping
    @Operation(summary = "根据id删除地址")
    public Result delete(Long id) {
        log.info("根据id删除地址：{}", id);
        addressBookService.delete(id);
        return Result.success();
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     */
    @PutMapping("/default")
    @Operation(summary = "设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址：{}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @GetMapping("default")
    @Operation(summary = "查询默认地址")
    public Result<AddressBook> getDefault() {
        log.info("查询默认地址");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        List<AddressBook> list= addressBookService.list(addressBook);

        if (list != null && list.size() == 1){
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }
}
