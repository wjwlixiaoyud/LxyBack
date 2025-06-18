package org.example.lxyback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.lxyback.common.Constents;
import org.example.lxyback.controller.dto.UserDTO;
import org.example.lxyback.entity.Menu;
import org.example.lxyback.entity.User;
import org.example.lxyback.exception.ServiceException;
import org.example.lxyback.mapper.RoleMapper;
import org.example.lxyback.mapper.RoleMenuMapper;
import org.example.lxyback.mapper.UserMapper;
import org.example.lxyback.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.lxyback.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuServiceImpl menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if(one !=null) {
            BeanUtil.copyProperties(one, userDTO,true);
            //设置token
             String token= TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);

            String role=one.getRole();//ROLE_ADMIN
            //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }
        else{
            throw new ServiceException(Constents.CODE_600,"用户名密码错误");
        }
    }

    @Override
    public User resgister(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if(one==null){
            one =new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);
        }else{
            throw new ServiceException(Constents.CODE_600,"用户已存在");
        }
        return one;
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one ;
        try{
            one  = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constents.CODE_500,"系统错误");
        }
        return one;
    }

    private List<Menu> getRoleMenus(String rolekey){
        Integer roleId = roleMapper.selectByRolekey(rolekey);
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查出所有用户的菜单
        List<Menu> menus = menuService.findMenus("");
        //最后筛选完的List
        List<Menu> roleMenus=new ArrayList<>();
        //筛选当前用户角色的菜单
        for (Menu menu:menus) {
            if(!menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //removeIf 一处children 里面不在menuIds集合中的元素
            children.removeIf(child ->!menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
