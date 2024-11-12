package com.restaurants.trazabilidad.domain.services;

import com.restaurant.plazoleta.domain.model.StatusLog;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusPersistance;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusService;
import com.restaurants.trazabilidad.domain.interfaces.IUserServiceClient;
import com.restaurants.trazabilidad.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class LogStatusServiceImpl implements ILogStatusService {
    private final ILogStatusPersistance persistance;
    private final IUserServiceClient userService;
    @Override
    public void registerStar(Integer orderId, Integer customID, String clientEmail) {
        StatusLog logStatus= new StatusLog();
        logStatus.setDateStar(dateFormate());
        logStatus.setCustomerId(customID);
        logStatus.setCustomerEmail(clientEmail);
        logStatus.setOrderId(orderId);
        logStatus.setNewStatus(OrderStatus.PENDING.toString());
         persistance.save(logStatus);
    }


    @Override
    public void registerChange(OrderStatus status, Integer ordeId, String employeEmail, Integer employeId) {

            StatusLog statusLog = persistance.findByOrderId(ordeId);
            String lastStatus = statusLog.getNewStatus();
            if (statusLog.getEmployeeEmail() == null ||statusLog.getEmployeeEmail().isEmpty()) {
                statusLog.setEmployeeId(employeId);
                statusLog.setEmployeeEmail(employeEmail);
            }
            statusLog.setNewStatus(status.toString());
            statusLog.setPreviousStatus(lastStatus);
            statusLog.setLastUpdate(dateFormate());
            persistance.save(statusLog);

    }

    @Override
    public List<StatusLog> getAllRegisters() {
        Integer customId=userService.GetUser().getId();
        LocalDate today = LocalDate.now();
        String startOfDay = today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String endOfDay = today.atTime(23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return persistance.findByCustomerIdAndDateStarBetween(customId, startOfDay,endOfDay);

    }

    @Override
    public List<StatusLog> getOrderStatus() {
        Integer customId=userService.GetUser().getId();
        LocalDate today = LocalDate.now();
        String startOfDay = today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String endOfDay = today.atTime(23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return persistance.findByCustomerIdAndDateStarBetween(customId, startOfDay,endOfDay);
    }

    private String dateFormate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return  now.format(formatter);
    }
}
