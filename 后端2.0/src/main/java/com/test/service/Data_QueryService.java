package com.test.service;

import com.test.StoresClass;
import com.test.entity.Device;
import com.test.entity.DeviceData;
import com.test.entity.ReturnMessage;
import com.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
@RequestMapping("/Data_Query")
public class Data_QueryService {
    @Autowired
    private StoresClass storesClass;
    public Data_QueryService(){
        this.storesClass=new StoresClass();
    }

    //查询当前设备的预警信息等
    @CrossOrigin
    @PostMapping("/Current_Msg_Query")
    public ReturnMessage Current_Msg_Query(@RequestParam("UserAccount") String UserAccount, @RequestParam("DeviceName")String DeviceName){
        List<DeviceData>data=this.storesClass.getDeviceDynInfoMapper().Current_Msg_Query(UserAccount,DeviceName);
        this.storesClass.setReturnMessage(1,"查询成功",data);
/*        System.out.print("data is");
        System.out.println(data);*/
        return this.storesClass.getReturnMessage();
    }

    //查询当前用户拥有的设备
    @CrossOrigin
    @PostMapping("/Device_Msg_Query")
    public ReturnMessage Device_Msg_Query(@RequestParam("UserAccount") String UserAccount){
        System.out.println(UserAccount);

        List<Device>data=this.storesClass.getDeviceMapper().selectDeviceByUserAccount(UserAccount);
/*        System.out.print("data is");
        System.out.println(data);*/
        this.storesClass.setReturnMessage(1,"查询成功",data);
        return this.storesClass.getReturnMessage();
    }
}
