package com.test;

import com.test.entity.ReturnMessage;
import com.test.mapper.DeviceMapper;
import com.test.mapper.Device_Dyn_InfoMapper;
import com.test.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class StoresClass {
    private ReturnMessage returnMessage;
    @Autowired
    private Device_Dyn_InfoMapper deviceDynInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    public StoresClass(){
        this.returnMessage=new ReturnMessage();
    }

    public void setReturnMessage(int code,String message,Object data){
        this.returnMessage.setCode(code);
        this.returnMessage.setMessage(message);
        this.returnMessage.setData(data);
    }
}
