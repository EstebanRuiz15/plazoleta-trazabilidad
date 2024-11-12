package com.restaurants.trazabilidad.infraestructur.driving_http.controllers;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusService;
import com.restaurants.trazabilidad.domain.model.OrderStatus;
import com.restaurants.trazabilidad.infraestructur.driving_http.dtos.response.StatusLogResponseDto;
import com.restaurants.trazabilidad.infraestructur.driving_http.mappers.StatusLogResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/LogStatus")
public class StatusLogController {
    private final StatusLogResponseMapper mapperResponse;
    private final ILogStatusService statusLogService;

    @Operation(
            summary = "Get status log of orders",
            description = "This endpoint allows you to get the status log of orders for the current day and for the client making the request."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved status logs",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StatusLogResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Client is not registered",
                    content = @Content(
                            schema = @Schema(type = "object", example = "{\"error\": \"Client is not registered\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while retrieving status logs\"}")
                    )
            )
    })
    @GetMapping("/status")
    public ResponseEntity<List<StatusLogResponseDto>> getStatusResponse(){
        return ResponseEntity.ok(
               mapperResponse.toListResponseDto(statusLogService.getOrderStatus()));
    }

    @Operation(
            summary = "Register initial status of an order",
            description = "This endpoint allows you to register the initial status of an order when the order is started."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status successfully registered"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            schema = @Schema(type = "object", example = "{\"error\": \"Invalid input data\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while registering order status\"}")
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<Void> registerStarTrazabilityOrder(
            @RequestParam Integer orderId,
            @RequestParam Integer customID,
            @RequestParam String clientEmail){
        statusLogService.registerStar(orderId,customID,clientEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Register a change in the status of an order",
            description = "This endpoint allows you to register changes in the status of an order whenever the order status is updated."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status change successfully registered"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"error\": \"Invalid input data\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while registering order status change\"}")
                    )
            )
    })
    @PatchMapping ("/registerChange")
    public ResponseEntity<?> registerChangeTrazabilityOrder(
            @RequestParam OrderStatus status,
            @RequestParam Integer orderId,
            @RequestParam String employeEmail,
            @RequestParam Integer employeId){
        statusLogService.registerChange(status,orderId,employeEmail,employeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
