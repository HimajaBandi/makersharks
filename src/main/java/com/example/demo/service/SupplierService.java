package com.example.demo.service;

import com.example.demo.model.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {

    public List<Supplier> getSupplier(String location, String natureOfBusiness, String manufacturingProcess, int page,int size );

}
