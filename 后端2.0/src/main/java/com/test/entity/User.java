package com.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String UserAccount;
    private String Password;
    private int IsAdmin;
    private String EmailAddress;
    private String CompanyAddress;
    private String PhoneNumber;
}
