package com.shashwathsh.production_ready_features.clients.impl;

import com.shashwathsh.production_ready_features.advice.ApiResponse;
import com.shashwathsh.production_ready_features.clients.EmployeeClient;
import com.shashwathsh.production_ready_features.dtos.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeClientImpl implements EmployeeClient {


    private final RestClient restClient;

    public EmployeeClientImpl(@Qualifier("employeeRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("/employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeDTOList.getData();
        } catch (Exception e){
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
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.post()
                    .uri("/employees")
                    .body(employeeDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return employeeDTOApiResponse.getData();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
