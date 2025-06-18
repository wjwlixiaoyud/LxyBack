package org.example.lxyback.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.lxyback.common.Constents;
import org.example.lxyback.common.Result;
import org.example.lxyback.entity.Dict;
import org.example.lxyback.mapper.DictMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import org.example.lxyback.service.IMenuService;
import org.example.lxyback.entity.Menu;

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
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu){
        return Result.success( menuService.saveOrUpdate(menu));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id)) ;
    }

    @PostMapping("/del/batch")
    public Result deletebatch(@RequestBody List<Integer> ids)
    {
        return Result.success(menuService.removeBatchByIds(ids)) ;

    }

    //查询所有数据
    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findMenus(name)) ;
    }

    //根据ID查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id)) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",Constents.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper)) ;
    }
}

