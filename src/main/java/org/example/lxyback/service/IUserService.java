package org.example.lxyback.service;

import org.example.lxyback.common.Result;
import org.example.lxyback.controller.dto.UserDTO;
import org.example.lxyback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-08
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);


    User resgister(UserDTO userDTO);
}
