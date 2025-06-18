package org.example.lxyback.service;

import org.example.lxyback.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-27
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
