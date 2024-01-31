package com.test.service;

import com.test.StoresClass;
import com.test.entity.ReturnMessage;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Service
@RequestMapping("/Register")
public class RegisterService {
    @Autowired
    private StoresClass storesClass;

    public RegisterService(){
        this.storesClass=new StoresClass();
    }

    private boolean isValid(String Password){
        // 使用正则表达式检测是否只包含数字和英文字母
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Password);
        return matcher.matches();
    }

    //通过账号搜索用户
    @CrossOrigin
    @PostMapping("/Search_User_By_Name")
    public ReturnMessage Search_User_By_Name(@RequestBody User user){
/*        System.out.println(user);*/
        List<User>data=this.storesClass.getUserMapper().fuzzyMatchByUserAccount(user.getUserAccount());
        this.storesClass.setReturnMessage(1,"查询成功",data);
        return this.storesClass.getReturnMessage();
    }

    //通过电话搜索用户
    @CrossOrigin
    @PostMapping("Search_User_By_Phone")
    public ReturnMessage Search_User_By_Phone(@RequestBody User user){
        List<User>data=this.storesClass.getUserMapper().fuzzyMatchByPhoneNumber(user.getPhoneNumber());
        this.storesClass.setReturnMessage(1,"查询成功",data);
        return this.storesClass.getReturnMessage();
    }

    //用户注册
    @CrossOrigin
    @PostMapping("/User_Register")
    public ReturnMessage User_Register(@RequestBody User user){
        if(!storesClass.getUserMapper().selectUserByUserAccount(user.getUserAccount()).isEmpty()){
            this.storesClass.setReturnMessage(2,"账户已经被注册",null);
        }
        else if(user.getUserAccount().length()>20){
            this.storesClass.setReturnMessage(3,"账号长度过长",null);
        }
        else if(!isValid(user.getPassword())){
            this.storesClass.setReturnMessage(4,"密码字符非法",null);
        }
        else if(user.getPassword().length()>20){
            this.storesClass.setReturnMessage(5,"密码长度过长",null);
        }
        else if(user.getPassword().length()<8){
            this.storesClass.setReturnMessage(6,"密码长度过短",null);
        }
        else{
            this.storesClass.getUserMapper().User_Register(user);
            this.storesClass.setReturnMessage(1,"注册成功",null);
        }
        return this.storesClass.getReturnMessage();
    }

    //获取所有用户
    @CrossOrigin
    @GetMapping("/List_User")
    public ReturnMessage getAllUser(){
        this.storesClass.setReturnMessage(1,"查询成功",this.storesClass.getUserMapper().getAllUser());
        return this.storesClass.getReturnMessage();
    }

    //用户登录
    @CrossOrigin
    @PostMapping("/User_Login")
    public ReturnMessage User_Login(@RequestBody User user){
/*        System.out.println(user);*/
        if(user.getUserAccount().length()>20){
            this.storesClass.setReturnMessage(23,"账号长度过长",null);
        }
        else if(user.getPassword().length()>20){
            this.storesClass.setReturnMessage(5,"密码长度过长",null);
        }
        else if(user.getPassword().length()<8){
            this.storesClass.setReturnMessage(6,"密码长度过短",null);
        }
        else if(!isValid(user.getPassword())){
            this.storesClass.setReturnMessage(4,"密码含有非法字符",null);
        }
        else if(this.storesClass.getUserMapper().User_Login(user.getUserAccount(),user.getPassword()).isEmpty()){
            this.storesClass.setReturnMessage(9,"账号或密码错误",null);
        }
        else{
            List<User>ur=this.storesClass.getUserMapper().User_Login(user.getUserAccount(),user.getPassword());
            this.storesClass.setReturnMessage(1,"登陆成功",ur.get(0));
        }
        return this.storesClass.getReturnMessage();
    }

    //注销用户
    @CrossOrigin
    @PostMapping("/User_Unregister")
    public ReturnMessage User_UnRegister(@RequestBody User user){
        this.storesClass.setReturnMessage(1,"删除用户成功",null);
        this.storesClass.getUserMapper().deleteUser(user.getUserAccount());
        this.storesClass.getDeviceMapper().delDeviceByUserAccount(user.getUserAccount());
        this.storesClass.getDeviceDynInfoMapper().delInfoByUserAccount(user.getUserAccount());
        return storesClass.getReturnMessage();
    }

    //修改用户密码
    @CrossOrigin
    @PostMapping("/User_Passwd_Modify")
    public ReturnMessage User_Passwd_Modfy(@RequestParam("UserAccount") String UserAccount,@RequestParam("OldPassword") String OldPassword,@RequestParam("NewPassword") String NewPassword){
        if(NewPassword.length()<8){
            this.storesClass.setReturnMessage(41,"新密码过短",null);
        }
        else if(NewPassword.length()>20){
            this.storesClass.setReturnMessage(20,"新密码过长",null);
        }
        else if(!isValid(OldPassword)||!isValid(NewPassword)){
            this.storesClass.setReturnMessage(40,"新密码或者旧密码含有非法字符",null);
        }
        else if(OldPassword.equals(NewPassword)){
            this.storesClass.setReturnMessage(14,"新旧密码一样",null);
        }
        else if(this.storesClass.getUserMapper().User_Login(UserAccount,OldPassword).isEmpty()){
            this.storesClass.setReturnMessage(13,"密码错误",null);
        }
        else{
            this.storesClass.setReturnMessage(1,"修改成功",null);
            this.storesClass.getUserMapper().User_Passwd_Modify(UserAccount,OldPassword,NewPassword);
        }
        return this.storesClass.getReturnMessage();
    }
}
