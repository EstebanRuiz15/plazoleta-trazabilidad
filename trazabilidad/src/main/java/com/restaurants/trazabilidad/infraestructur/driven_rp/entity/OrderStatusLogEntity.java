package com.restaurants.trazabilidad.infraestructur.driven_rp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_status_logs")
public class OrderStatusLogEntity {

    @Id
    private String id;
    private Integer orderId;
    private Integer customerId;
    private String customerEmail;
    private String dateStar;
    private String lastUpdate;
    private String previousStatus;
    private String newStatus;
    private Integer employeeId;
    private String employeeEmail;


}