package com.restaurants.trazabilidad.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.model.StatusLog;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusPersistance;
import com.restaurants.trazabilidad.infraestructur.driven_rp.entity.OrderStatusLogEntity;
import com.restaurants.trazabilidad.infraestructur.driven_rp.mapper.OrderStatusLogMapper;
import com.restaurants.trazabilidad.infraestructur.driven_rp.persistence.OrderStatusRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogStatusPersistanceImpl implements ILogStatusPersistance {
    private final OrderStatusLogMapper mapper;
    private final OrderStatusRepository repository;

    @Override
    public void save(StatusLog status) {
        repository.save(mapper.toStatusEntity(status));
    }

    @Override
    public StatusLog findByOrderId(Integer ordeId) {
        return mapper.toStatusLog(repository.findByOrderId(ordeId).get());
    }

    @Override
    public List<StatusLog> findByCustomerIdAndDateStarBetween(Integer customId, String startOfDay, String endOfDay) {
        return mapper.toListStatusLog(repository.findByCustomerIdAndDateStarBetween(customId,startOfDay,endOfDay));
    }
}
