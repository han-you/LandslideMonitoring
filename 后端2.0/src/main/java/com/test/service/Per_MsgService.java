package com.test.service;

import com.test.StoresClass;
import com.test.entity.ReturnMessage;
import com.test.entity.User;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RestController
@RequestMapping("/Per_Msg")
public class Per_MsgService {
    @Autowired
    private StoresClass storesClass;
    public Per_MsgService(){
        this.storesClass=new StoresClass();
    }
    private boolean isPhoneValid(String PhoneNumber){
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(PhoneNumber);
        return matcher.matches()&&!(PhoneNumber.charAt(0)=='0');

    }
    public static boolean isEmailValid(String MailAddress) {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(MailAddress);
        return matcher.matches();
    }

    //修改电话号码
    @CrossOrigin
    @PostMapping("/Phone_Num_Modify")
    public ReturnMessage Phone_Num_Modify(@RequestBody User user){
        if(user.getPhoneNumber().length()!=11){
            this.storesClass.setReturnMessage(19,"手机号长度不符合要求",null);
        }
        else if(!isPhoneValid(user.getPhoneNumber())){
            this.storesClass.setReturnMessage(15,"手机号非法",null);
        }
        else if(this.storesClass.getUserMapper().selectUserByUserAccount(user.getUserAccount()).get(0).getPhoneNumber().equals(user.getPhoneNumber())){
            this.storesClass.setReturnMessage(16,"新旧手机号一样",null);
        }
        else{
            this.storesClass.setReturnMessage(1,"手机号修改成功",null);
            this.storesClass.getUserMapper().Phone_Num_Modify(user);
        }
        return this.storesClass.getReturnMessage();
    }

    //修改公司地址
    @CrossOrigin
    @PostMapping("/Company_Addr_Modify")
    public ReturnMessage Company_Addr_Modify(@RequestBody User user){
        List<User> OldUser=this.storesClass.getUserMapper().selectUserByUserAccount(user.getUserAccount());
        if(user.getCompanyAddress().length()>200){
            this.storesClass.setReturnMessage(17,"公司地址过长",null);
        }
        else if(user.getCompanyAddress().equals(OldUser.get(0).getCompanyAddress())){
            this.storesClass.setReturnMessage(18,"新公司地址和旧公司地址一样",null);
        }
        else{
            this.storesClass.setReturnMessage(1,"公司地址修改成功",null);
            this.storesClass.getUserMapper().Company_Addr_Modify(user);
        }
        return this.storesClass.getReturnMessage();
    }

    //修改电子邮箱
    @CrossOrigin
    @PostMapping("/Email_Addr_Modify")
    public ReturnMessage Email_Addr_Modify(@RequestBody User user){
        List<User> OldUser=storesClass.getUserMapper().selectUserByUserAccount(user.getUserAccount());
        if(user.getEmailAddress().length()>30){
            storesClass.setReturnMessage(24,"邮箱过长",null);
        }
        else if(!isEmailValid(user.getEmailAddress())){
            storesClass.setReturnMessage(26,"邮箱格式错误",null);
        }
        else if(user.getEmailAddress().equals(OldUser.get(0).getEmailAddress())){
            storesClass.setReturnMessage(25,"新邮箱和旧邮箱地址一样",null);
        }
        else{
            storesClass.setReturnMessage(1,"邮箱修改成功",null);
            storesClass.getUserMapper().Email_Addr_Modify(user);
        }
        return storesClass.getReturnMessage();
    }
}
