package com.test.service;

import com.test.StoresClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("/Data_Upload")
public class Data_UploadService {
    @Autowired
    private StoresClass storesClass;
    public Data_UploadService(){
        this.storesClass=new StoresClass();
    }
}
