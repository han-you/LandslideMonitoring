package com.test.mapper;

import com.test.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface UserMapper {
    //查询用户是否存在
    @Select("select * from User where UserAccount=#{UserAccount}")
    List<User>selectUserByUserAccount(@Param("UserAccount") String UserAccount);

    //删除用户
    @Delete("delete from User where UserAccount = #{UserAccount}")
    int deleteUser(@Param("UserAccount") String UserAccount);
    //根据账号名查询用户
    @Select("select * from User where UserAccount like  CONCAT('%', #{UserAccount}, '%')")
    List<User>fuzzyMatchByUserAccount(@Param("UserAccount") String UserAccount);

    //根据手机号码查询用户
    @Select("select * from User where PhoneNumber like CONCAT('%',#{PhoneNumber},'%')")
    List<User>fuzzyMatchByPhoneNumber(@Param("PhoneNumber") String PhoneNumber);

    //查询所有用户
    @Select("select * from User")
    List<User>getAllUser();

    //注册信息
    @Insert("insert into User(UserAccount,Password,IsAdmin,EmailAddress,CompanyAddress,PhoneNumber) values (#{UserAccount},#{Password},#{IsAdmin},#{EmailAddress},#{CompanyAddress},#{PhoneNumber})")
    int User_Register(User user);

    //登录
    @Select("select * from User where UserAccount=#{UserAccount} and Password=#{Password}")
    List<User>User_Login(@Param("UserAccount") String UserAccount,@Param("Password") String Password);

    //修改密码
    @Update("update User set Password = #{NewPassword} where UserAccount = #{UserAccount} and Password =#{OldPassword}")
    int User_Passwd_Modify(@Param("UserAccount") String UserAccount,@Param("OldPassword") String OldPassword,@Param("NewPassword")String NewPassword);

    //修改手机号码
    @Update("update User set PhoneNumber =#{PhoneNumber} where UserAccount=#{UserAccount}")
    int Phone_Num_Modify(User user);

    //修改公司地址
    @Update("update User set CompanyAddress =#{CompanyAddress} where UserAccount=#{UserAccount}")
    int Company_Addr_Modify(User user);

    //修改邮箱地址
    @Update("update User set EmailAddress =#{EmailAddress} where UserAccount=#{UserAccount}")
    int Email_Addr_Modify(User user);
}
