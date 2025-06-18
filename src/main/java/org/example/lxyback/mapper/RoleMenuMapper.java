package org.example.lxyback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.lxyback.entity.RoleMenu;
import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select menu_id from sys_role_menu where role_id=#{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
