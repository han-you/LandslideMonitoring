package com.test.service;

import com.test.StoresClass;
import com.test.entity.Device;
import com.test.entity.DeviceClass;
import com.test.entity.ReturnMessage;
import com.test.entity.User;
import com.test.mapper.DeviceMapper;
import org.apache.catalina.Store;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Service
@RequestMapping("/Dev_Msg")
public class Dev_MsgService {
    @Autowired
    private StoresClass storesClass;
    public Dev_MsgService(){
        this.storesClass=new StoresClass();
    }

/*    //用户获取所有的设备
    @CrossOrigin
    @PostMapping("/Device_Msg_Query")
    public ReturnMessage Device_Msg_Query(@RequestParam("UserAccount") String UserAccount){
        List<Device>data=this.storesClass.getDeviceMapper().selectDeviceByUserAccount(UserAccount);
        this.storesClass.setReturnMessage(1,"查询成功",data);
        return this.storesClass.getReturnMessage();
    }*/

    //添加设备类
    @CrossOrigin
    @PostMapping("/Dev_Class_Add")
    public ReturnMessage Dev_Class_Add(@RequestBody DeviceClass deviceClass){
        this.storesClass.setReturnMessage(1,"添加设备类成功",null);
        this.storesClass.getDeviceMapper().Dev_Class_Add(deviceClass.getWindSpeedSType(),
                deviceClass.getDeviceClassArch(),
                deviceClass.getDeviceClassName(),
                deviceClass.getManufacture(),
                deviceClass.getTemperatureSType(),
                deviceClass.getAirPressureSType(),
                deviceClass.getHumiditySType(),
                deviceClass.getWindDirSType());
        System.out.println(deviceClass);
        return this.storesClass.getReturnMessage();
    }

    //用户获取所有设备类
    @CrossOrigin
    @PostMapping("Dev_List")
    public ReturnMessage Dev_List(){
        this.storesClass.setReturnMessage(1,"设备类获取成功",this.storesClass.getDeviceMapper().Dev_List());
        return this.storesClass.getReturnMessage();
    }
    //修改设备名称
    @CrossOrigin
    @PostMapping("/Device_Name_Modify")
    ReturnMessage Device_Name_Modify(@RequestParam("UserAccount") String UserAccount,@RequestParam("OldDeviceName") String OldDeviceName,@RequestParam("NewDeviceName") String NewDeviceName){
        if(!this.storesClass.getDeviceMapper().selectDeviceByUserAccountAndDeviceName(UserAccount,NewDeviceName).isEmpty()){
            this.storesClass.setReturnMessage(30,"设备名已存在",null);
        }
        else if(NewDeviceName.length()>20){
            this.storesClass.setReturnMessage(32,"设备名称过长",null);
        }
        else{
            this.storesClass.setReturnMessage(1,"修改成功",null);
            this.storesClass.getDeviceMapper().modifyDeviceName(UserAccount,OldDeviceName,NewDeviceName);
            this.storesClass.getDeviceDynInfoMapper().modifyDeviceName(UserAccount,OldDeviceName,NewDeviceName);
        }
        return storesClass.getReturnMessage();
    }

    //删除设备
    @CrossOrigin
    @PostMapping("/Device_Del")
    ReturnMessage Device_Del(@RequestParam(name = "UserAccount") String UserAccount, @RequestParam(name="DeviceName") String DeviceName){
/*        System.out.println(UserAccount);
        System.out.println(DeviceName);*/
        this.storesClass.setReturnMessage(1,"删除成功",null);
        this.storesClass.getDeviceMapper().deviceDelByDeviceNameAndUserAccount(UserAccount,DeviceName);
        this.storesClass.getDeviceDynInfoMapper().delInfoByUserAccountAndDeviceName(UserAccount,DeviceName);
        return this.storesClass.getReturnMessage();
    }

    //删除设备类
    @CrossOrigin
    @PostMapping("/Device_Class_Del")
    ReturnMessage Device_Class_Del(@RequestParam("DeviceClassID") int DeviceClassID){
        this.storesClass.setReturnMessage(1,"删除设备类成功",null);
        this.storesClass.getDeviceMapper().deleteDeviceClass(DeviceClassID);
        return this.storesClass.getReturnMessage();
    }
    //添加设备
    @CrossOrigin
    @PostMapping("/Device_Add")
    public ReturnMessage Device_Add(@RequestParam("UserAccount") String UserAccount,@RequestParam("DeviceName") String DeviceName,@RequestParam("DeviceClassID") String DeviceClassID){
//        System.out.println(DeviceClassID);
        if(DeviceName.length()>20){
            this.storesClass.setReturnMessage(27,"设备名称过长",null);
        }
        else if(!this.storesClass.getDeviceMapper().selectDeviceByUserAccountAndDeviceName(UserAccount,DeviceName).isEmpty()){
            this.storesClass.setReturnMessage(29,"设别名重复",null);
        }
        else{
            this.storesClass.setReturnMessage(1,"设别添加成功",null);
            this.storesClass.getDeviceMapper().addDevice(UserAccount,DeviceName,Integer.parseInt(DeviceClassID));
        }
        return this.storesClass.getReturnMessage();
    }
}
