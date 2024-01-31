package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceClass {
    private int DeviceClassID;
    private String WindSpeedSType;
    private String DeviceClassArch;
    private String DeviceClassName;
    private String Manufacture;
    private String TemperatureSType;
    private String AirPressureSType;
    private String HumiditySType;
    private String WindDirSType;
}
