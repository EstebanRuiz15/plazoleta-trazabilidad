package com.restaurants.trazabilidad;

import com.restaurants.trazabilidad.domain.interfaces.ILogStatusPersistance;
import com.restaurants.trazabilidad.domain.interfaces.IUserServiceClient;
import com.restaurants.trazabilidad.domain.model.OrderStatus;
import com.restaurants.trazabilidad.domain.model.StatusLog;
import com.restaurants.trazabilidad.domain.model.User;
import com.restaurants.trazabilidad.domain.services.LogStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogStatusServiceImplTest {

    @Mock
    private ILogStatusPersistance persistance;

    @Mock
    private IUserServiceClient userService;

    @InjectMocks
    private LogStatusServiceImpl logStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerStar_shouldSaveStatusLog() {
        Integer orderId = 1;
        Integer customID = 1;
        String clientEmail = "test@example.com";

        logStatusService.registerStar(orderId, customID, clientEmail);

    }

    @Test
    void registerChange_shouldUpdateAndSaveStatusLog() {
        Integer orderId = 1;
        String employeeEmail = "employee@example.com";
        Integer employeeId = 1;
        OrderStatus newStatus = OrderStatus.DELIVERED;  StatusLog existingLog = new StatusLog();
        existingLog.setOrderId(orderId);
        existingLog.setCustomerEmail("");   existingLog.setNewStatus(OrderStatus.PENDING.toString());
        when(persistance.findByOrderId(orderId)).thenReturn(existingLog);

         logStatusService.registerChange(newStatus, orderId, employeeEmail, employeeId);

        verify(persistance, times(1)).save(any(StatusLog.class));

        assertEquals(OrderStatus.DELIVERED.toString(), existingLog.getNewStatus());
        assertEquals(OrderStatus.PENDING.toString(), existingLog.getPreviousStatus());
        assertEquals(employeeEmail, existingLog.getEmployeeEmail());
        assertEquals(employeeId, existingLog.getEmployeeId());
    }

    @Test
    void getAllRegisters_shouldReturnLogsForCurrentDay() {
        Integer customerId = 1;
        User mockUser = new User();
        mockUser.setId(customerId);
        when(userService.GetUser()).thenReturn(mockUser);
        LocalDateTime now = LocalDateTime.now();
        String startOfDay = now.toLocalDate().atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String endOfDay = now.toLocalDate().atTime(23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
 List<StatusLog> logs = List.of(new StatusLog());

       when(persistance.findByCustomerIdAndDateStarBetween(customerId, startOfDay, endOfDay)).thenReturn(logs);

        List<StatusLog> result = logStatusService.getAllRegisters();

        assertEquals(logs, result);
    }

    @Test
    void getOrderStatus_shouldReturnLogsForCurrentDay() {
        Integer customerId = 1;

        User mockUser = new User();
        mockUser.setId(customerId);

        when(userService.GetUser()).thenReturn(mockUser);

        LocalDateTime now = LocalDateTime.now();
        String startOfDay = now.toLocalDate().atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String endOfDay = now.toLocalDate().atTime(23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<StatusLog> logs = List.of(new StatusLog());
        when(persistance.findByCustomerIdAndDateStarBetween(customerId, startOfDay, endOfDay)).thenReturn(logs);

        List<StatusLog> result = logStatusService.getOrderStatus();

        assertEquals(logs, result);
    }
}