package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceData {
    private String UserAccount;
    private String DeviceName;
    private String Time;
    private float AirPressureSValue;
    private float WindSpeedSValue;
    private String WindDirSValue;
    private float HumiditySValue;
    private int Forecast;
    private float TemperatureSValue;
}
