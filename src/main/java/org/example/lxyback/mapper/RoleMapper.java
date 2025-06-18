package org.example.lxyback.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.lxyback.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-27
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select id from sys_role where rolekey =#{rolekey}")
    Integer selectByRolekey(@Param("rolekey") String rolekey);
}
