package org.example.lxyback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author LxyBack
 * @since 2024-05-27
 */
@Getter
@Setter
@TableName("sys_role")
@ToString
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        private Integer id;

      /**
     * 名称
     */
      private String name;

      /**
     * 描述
     */
      private String description;

      private String rolekey;


}
