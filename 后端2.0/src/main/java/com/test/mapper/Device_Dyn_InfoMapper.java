package com.test.mapper;

import com.test.entity.DeviceData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Device_Dyn_InfoMapper {

    @Delete("delete from Device_Dyn_Info where UserAccount = #{UserAccount}")
    int delInfoByUserAccount(@Param("UserAccount") String UserAccount);
    @Select("select * from Device_Dyn_Info where UserAccount = #{UserAccount} and DeviceName = #{DeviceName}")
    List<DeviceData>Current_Msg_Query(@Param("UserAccount") String UserAccount,@Param("DeviceName") String DeviceName);

    @Update("update Device_Dyn_Info set DeviceName = #{NewDeviceName} where UserAccount = #{UserAccount} and DeviceName = #{OldDeviceName}")
    int modifyDeviceName(@Param("UserAccount") String UserAccount, @Param("OldDeviceName") String OldDeviceName, @Param("NewDeviceName") String NewDeviceName);
    @Delete("delete from Device_Dyn_Info where UserAccount = #{UserAccount} and DeviceName = #{DeviceName}")
    int delInfoByUserAccountAndDeviceName(@Param("UserAccount") String UserAccount,@Param("DeviceName") String DeviceName);
}
