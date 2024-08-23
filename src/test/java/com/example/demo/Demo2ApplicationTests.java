package com.example.demo;

import com.example.demo.controller.SupplierController;
import com.example.demo.model.Supplier;
import com.example.demo.service.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(SupplierController.class)
public class Demo2ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQuerySuppliers_Success() throws Exception {
        // Arrange
        Supplier supplier1 = new Supplier("1", "Alpha Manufacturing", "www.alpha.com", "India", "small_scale", List.of("3d_printing", "moulding"));
        Supplier supplier2 = new Supplier("3", "Gama Manufacturing", "www.gama.com", "China", "medium_scale", List.of("moulding"));

        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);
        when(supplierService.getSupplier("India", "small_scale", "3d_printing", 0,1)).thenReturn(suppliers);

        // Act & Assert
        mockMvc.perform(post("/api/supplier/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"location\": \"India\", \"nature_of_business\": \"small_scale\", \"manufacturing_process\": \"3d_printing\", \"limit\": \"5\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].supplierId").value("1"))
                .andExpect(jsonPath("$[0].companyName").value("ABC Manufacturing"))
                .andExpect(jsonPath("$[1].supplierId").value("4"))
                .andExpect(jsonPath("$[1].companyName").value("Future Printing"));

        // Verify the service call
        verify(supplierService, times(1)).getSupplier("India", "small_scale", "3d_printing",0,1);
    }

    @Test
    public void testQuerySuppliers_NoResults() throws Exception {
        // Arrange
        when(supplierService.getSupplier("India", "small_scale", "casting",0,2)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(post("/api/supplier/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"location\": \"India\", \"nature_of_business\": \"small_scale\", \"manufacturing_process\": \"casting\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        // Verify the service call
        verify(supplierService, times(1)).getSupplier("India", "small_scale", "casting", 0,1);
    }

    @Test
    public void testQuerySuppliers_InvalidRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/supplier/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"location\": \"India\", \"nature_of_business\": \"small_scale\", \"limit\": \"5\" }"))
                .andExpect(status().isBadRequest());
    }
}
