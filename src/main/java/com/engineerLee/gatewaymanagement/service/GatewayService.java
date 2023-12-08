package com.engineerLee.gatewaymanagement.service;

import com.engineerLee.gatewaymanagement.dto.ApiResponse;
import com.engineerLee.gatewaymanagement.dto.GateWayRequest;
import com.engineerLee.gatewaymanagement.model.Gateway;
import com.engineerLee.gatewaymanagement.model.PeripheralDevice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GatewayService {

    List<ApiResponse<Gateway>> getAllGateways();

    ApiResponse<Gateway> getGatewayBySerialNumber(String serialNumber);

    ApiResponse<Gateway> saveGateway(GateWayRequest gateWayRequest);

    void deleteGateway(String serialNumber);

    void addPeripheralDevice(String serialNumber, PeripheralDevice device);

    void removePeripheralDevice(String serialNumber, Long deviceId);
}

