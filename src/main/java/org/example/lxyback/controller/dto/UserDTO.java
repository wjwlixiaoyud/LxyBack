package org.example.lxyback.controller.dto;

import lombok.Data;
import org.example.lxyback.entity.Menu;

import java.util.List;

/*
接受前端登录请求数据
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}