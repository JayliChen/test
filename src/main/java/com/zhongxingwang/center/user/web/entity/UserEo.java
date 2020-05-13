package com.zhongxingwang.center.user.web.entity;

import com.zhongxingwang.center.user.web.entity.BaseEo;
import lombok.*;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserEo extends BaseEo {
      private String name;
      private String userName;
      private byte[] passWord;
      private Integer age;
      private Integer sex;
      private String phone;
      private String identityCard;
      private String remark;
}
