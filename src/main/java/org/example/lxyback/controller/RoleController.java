package org.example.lxyback.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.lxyback.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import org.example.lxyback.service.IRoleService;
import org.example.lxyback.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-27
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;


    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Role role){
        return Result.success(roleService.saveOrUpdate(role));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deletebatch(@RequestBody List<Integer> ids)
    {
        return Result.success(roleService.removeBatchByIds(ids));

    }

    //查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    //根据ID查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
    /*
    绑定角色与菜单的关系
    通过rolemenu表双ID
     */
    @PostMapping("/rolemenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds ){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/rolemenu/{roleId}")
    public Result getroleMenu(@PathVariable Integer roleId){
        return Result.success(roleService.getroleMenu(roleId));
    }

}

