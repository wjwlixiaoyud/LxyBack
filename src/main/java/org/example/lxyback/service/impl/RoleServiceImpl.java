package org.example.lxyback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.lxyback.entity.Role;
import org.example.lxyback.entity.RoleMenu;
import org.example.lxyback.mapper.RoleMapper;
import org.example.lxyback.mapper.RoleMenuMapper;
import org.example.lxyback.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        //先删除当前角色所有ID绑定关系
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        //再把前端传过来的数据菜单ID数组绑定到当前角色id
        for(Integer menuID:menuIds){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenu_id(menuID);
            roleMenu.setRole_id(roleId);
            roleMenuMapper.insert(roleMenu);

        }
    }

    @Override
    public List<Integer> getroleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
