package com.shashwathsh.production_ready_features;

import com.shashwathsh.production_ready_features.clients.EmployeeClient;
import com.shashwathsh.production_ready_features.dtos.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductionReadyFeaturesApplicationTests {

    @Autowired
    private EmployeeClient employeeClient;


	@Test
    @Order(3)
	void getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
        System.out.println(employeeDTOList);
	}


    @Test
    @Order(2)
    void getEmployeeIdTest(){
        EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L);
        System.out.println(employeeDTO);
    }

    @Test
    @Order(1)
    void createNewEmployeeTest(){
        EmployeeDTO employeeDTO = new EmployeeDTO(null,"Shashwath","Shashwath@gmail.com",20,"USER",
                5000.0, LocalDate.of(2025,8,21),true);
        EmployeeDTO savedEmployeeDto = employeeClient.createNewEmployee(employeeDTO);
        System.out.println(savedEmployeeDto);
    }

}
