package com.shashwathsh.production_ready_features.clients.impl;

import com.shashwathsh.production_ready_features.advice.ApiResponse;
import com.shashwathsh.production_ready_features.clients.EmployeeClient;
import com.shashwathsh.production_ready_features.dtos.EmployeeDTO;
import com.shashwathsh.production_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {


    private final RestClient restClient;
    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

//    public EmployeeClientImpl(@Qualifier("employeeRestClient") RestClient restClient) {
//        this.restClient = restClient;
//    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

//        log.error("error log");
//        log.warn("warn log");
//        log.debug("debug log");
//        log.info("info log");
//        log.trace("trace log");

        log.info("Trying to retrieve all employees in getAllEmployees");
        try {
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("/employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.debug("Successfully retrieved the employees in getAllEmployees");
            log.trace("Retrieved employees list in getAllEmployees: {}", employeeDTOList.getData());
            return employeeDTOList.getData();

        } catch (Exception e){
            log.error("Exception occurred in getAllEmployees",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        try{
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("/employees/{employeeId}",id)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return employeeResponse.getData();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("/employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return employeeDTOApiResponse.getBody().getData();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
