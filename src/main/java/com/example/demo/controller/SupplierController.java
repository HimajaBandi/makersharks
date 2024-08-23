package com.example.demo.controller;


import com.example.demo.model.Supplier;
import com.example.demo.service.SupplierService;
import com.example.demo.service.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierServiceImpl supplierServiceImpl;


    @PostMapping("/query")
    public List<Supplier> querySuppliers(@RequestBody Map<String,String> queryParams) {

        String location=queryParams.get("location")!=null?queryParams.get("location"):"Location is null";
        String natureOfBusiness=queryParams.get("nature_of_business")!=null?queryParams.get("nature_of_business"):"bussiness is null";
        String manufacturingProcess=queryParams.get("manufacturing_process")!=null?queryParams.get("manufacturing_process"):"manufacturing is null";
        // For handling pagination
        int page = queryParams.get("page") != null ? Integer.parseInt(queryParams.get("page")) : 0;
        int size = queryParams.get("size") != null ? Integer.parseInt(queryParams.get("size")) : 10;

        return supplierService.getSupplier(location,natureOfBusiness,manufacturingProcess,page,size);



    }


}
