package com.engineerLee.gatewaymanagement.controller;

import com.engineerLee.gatewaymanagement.dto.AddPeripheralRequest;
import com.engineerLee.gatewaymanagement.dto.ApiResponse;
import com.engineerLee.gatewaymanagement.dto.GateWayRequest;
import com.engineerLee.gatewaymanagement.model.Gateway;
import com.engineerLee.gatewaymanagement.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GatewayController {
    private final GatewayService gatewayService;

    @GetMapping
    public ApiResponse<?> getAllGateways() {
        return gatewayService.getAllGateways();
    }

    @GetMapping("/{serialNumber}")
    public ApiResponse<?> getGatewayBySerialNumber(@PathVariable String serialNumber) {
        return gatewayService.getGatewayBySerialNumber(serialNumber);
    }

    @PostMapping ("addGateWay")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Gateway> saveGateway(@RequestBody GateWayRequest gateWayRequest) {
        return gatewayService.saveGateway(gateWayRequest);
    }

    @DeleteMapping("/{serialNumber}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteGateway(@PathVariable String serialNumber) {
        gatewayService.deleteGateway(serialNumber);
    }

    @PostMapping("addPeripheral")
    public ApiResponse<?> addPeripheralDevice(@RequestBody AddPeripheralRequest request) {
        return gatewayService.addPeripheralDevice(request);
    }

    @DeleteMapping("removePeripheralDevice")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> removePeripheralDevice(
            @RequestParam("serialNumber") String serialNumber,
            @RequestParam("deviceId") Long deviceId) {
        return gatewayService.removePeripheralDevice(serialNumber, deviceId);
    }
}
