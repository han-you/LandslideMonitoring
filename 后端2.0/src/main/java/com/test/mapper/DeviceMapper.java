package com.test.mapper;

import com.test.entity.Device;
import com.test.entity.DeviceClass;
import com.test.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface DeviceMapper {

    @Delete("delete from Device_Class where DeviceClassID = #{DeviceClassID}")
    int deleteDeviceClass(@Param("DeviceClassID") int DeviceClassID);
    @Insert("INSERT INTO Device_Class(WindSpeedSType,DeviceClassArch,DeviceClassName,Manufacture,TemperatureSType,AirPressureSType,HumiditySType,WindDirSType) values (#{WindSpeedSType},#{DeviceClassArch},#{DeviceClassName},#{Manufacture},#{TemperatureSType},#{AirPressureSType},#{HumiditySType},#{WindDirSType})")
    int Dev_Class_Add(@Param("WindSpeedSType") String WindSpeedSType,
                      @Param("DeviceClassArch") String DeviceClassArch,
                      @Param("DeviceClassName") String DeviceClassName,
                      @Param("Manufacture") String Manufacture,
                      @Param("TemperatureSType") String TemperatureSType,
                      @Param("AirPressureSType") String AirPressureSType,
                      @Param("HumiditySType") String HumiditySType,
                      @Param("WindDirSType") String WindDirSType);

    @Select("select * from Device_Class")
    List<DeviceClass>Dev_List();
    @Select("SELECT UserAccount,DeviceName, Device.DeviceClassID,WindSpeedSType,DeviceClassArch,DeviceClassName,Manufacture,TemperatureSType,AirPressureSType,HumiditySType,WindDirSType " +
            "FROM Device " +
            "JOIN Device_Class on Device.DeviceClassID=Device_Class.DeviceClassID " +
            "WHERE UserAccount =#{UserAccount}")
    List<Device>selectDeviceByUserAccount(@Param("UserAccount") String UserAccount);

    @Delete("delete from Device where UserAccount = #{UserAccount}")
    int delDeviceByUserAccount(@Param("UserAccount") String UserAccount);
    @Update("update Device set DeviceName = #{NewDeviceName} where UserAccount = #{UserAccount} and DeviceName = #{OldDeviceName}")
    int modifyDeviceName(@Param("UserAccount") String UserAccount, @Param("OldDeviceName") String OldDeviceName, @Param("NewDeviceName") String NewDeviceName);

    @Delete("delete from Device where UserAccount=#{UserAccount} and DeviceName=#{DeviceName}")
    int deviceDelByDeviceNameAndUserAccount(@Param("UserAccount") String UserAcount,@Param("DeviceName") String DeviceName);

    @Select("SELECT UserAccount,DeviceName, Device.DeviceClassID,WindSpeedSType,DeviceClassArch,DeviceClassName,Manufacture,TemperatureSType,AirPressureSType,HumiditySType,WindDirSType " +
            "FROM Device " +
            "JOIN Device_Class on Device.DeviceClassID=Device_Class.DeviceClassID " +
            "WHERE UserAccount =#{UserAccount} and DeviceName =#{DeviceName}")
    List<Device>selectDeviceByUserAccountAndDeviceName(@Param("UserAccount") String UserAcount,@Param("DeviceName") String DeviceName);

    @Insert("insert into Device(UserAccount,DeviceName,DeviceClassID) values(#{UserAccount},#{DeviceName},#{DeviceClassID})")
    int addDevice(@Param("UserAccount") String User,@Param("DeviceName") String DeviceName,@Param("DeviceClassID") int DeviceClassID);
}
