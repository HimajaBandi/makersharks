package com.example.demo.service;

import com.example.demo.model.Supplier;
import com.example.demo.utils.SupplierNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl  implements  SupplierService {


    private  List<Supplier> suppliers = new ArrayList<>();

    public static final Logger logger= LoggerFactory.getLogger(SupplierServiceImpl.class);



    public SupplierServiceImpl() {
        logger.debug("Supplier service created");
        //Initializing with dummy data
        for (Supplier supplier : Arrays.asList(new Supplier("1", "Alpha Manufacturing", "www.alpha.com", "India", "small_scale", List.of("3d_printing", "moulding")), new Supplier("2", "Beta Manufacturing", "www.beta.com", "India", "large_scale", List.of("casting", "coating")), new Supplier("3", "Gama Manufacturing", "www.gama.com", "China", "medium_scale", List.of("moulding")), new Supplier("4", "Zeta Manufacturing", "www.zeta.com", "India", "small_scale", List.of("3d_printing")))) {
            suppliers.add(supplier);
        }
    }
    @Override
    public List<Supplier> getSupplier(String location, String natureOfBusiness, String manufacturingProcess, int page,int size) {
        logger.debug("location:{} ",location);
        List<Supplier> paginationSuppliers =
                suppliers.stream()
                .filter(supplier -> supplier.getLocation() != null&&supplier.getLocation().equalsIgnoreCase(location))
                .filter(supplier -> supplier.getNatureOfBusiness().equalsIgnoreCase(natureOfBusiness))
                .filter(supplier -> supplier.getManufacturingProcesses().contains(manufacturingProcess))
                .collect(Collectors.toList());

        // Handle case when no suppliers are found
        if (paginationSuppliers.isEmpty()) {
            throw new SupplierNotFoundException("No suppliers found matching the given criteria.");
        }

        // Applying pagination
        int start = Math.min(page * size, paginationSuppliers.size());
        int end = Math.min(start + size, paginationSuppliers.size());
        return paginationSuppliers.subList(start, end);
    }
}
