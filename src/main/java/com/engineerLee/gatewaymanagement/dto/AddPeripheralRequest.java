package com.engineerLee.gatewaymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPeripheralRequest {
    private String serialNumber;
    private String vendor;
}
