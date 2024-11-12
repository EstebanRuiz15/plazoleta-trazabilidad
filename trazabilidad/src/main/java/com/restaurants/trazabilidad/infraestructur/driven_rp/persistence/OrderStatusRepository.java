package com.restaurants.trazabilidad.infraestructur.driven_rp.persistence;
import com.restaurants.trazabilidad.infraestructur.driven_rp.entity.OrderStatusLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderStatusRepository extends MongoRepository<OrderStatusLogEntity,String> {
    Optional<OrderStatusLogEntity> findByCustomerId(Integer customerId);
    Optional<OrderStatusLogEntity> findByOrderId(Integer orderId);
    List<OrderStatusLogEntity> findByCustomerIdAndDateStarBetween(Integer customerId, String startOfDay, String endOfDay);

}
