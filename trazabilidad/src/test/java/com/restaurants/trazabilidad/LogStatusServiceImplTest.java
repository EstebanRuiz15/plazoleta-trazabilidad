package com.restaurants.trazabilidad;

import com.restaurants.trazabilidad.domain.exception.ErrorException;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusPersistance;
import com.restaurants.trazabilidad.domain.interfaces.IUserServiceClient;
import com.restaurants.trazabilidad.domain.model.OrderEfficiency;
import com.restaurants.trazabilidad.domain.model.OrderStatus;
import com.restaurants.trazabilidad.domain.model.StatusLog;
import com.restaurants.trazabilidad.domain.model.User;
import com.restaurants.trazabilidad.domain.services.LogStatusServiceImpl;
import com.restaurants.trazabilidad.domain.utils.ConstantsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getRecordsOrders_whenFilterIsRaking_shouldReturnRakingList() {
        List<StatusLog> statusLogs = new ArrayList<>();
        statusLogs.add(new StatusLog("1", 1, 101, "customer1@example.com", "2023-11-12 10:00", "2023-11-12 10:30", null, "PENDING", 201, "employee1@example.com"));
        statusLogs.add(new StatusLog("2", 2, 102, "customer2@example.com", "2023-11-12 11:00", "2023-11-12 11:45", null, "COMPLETED", 202, "employee2@example.com"));

        when(persistance.findAll()).thenReturn(statusLogs);

        OrderEfficiency result = logStatusService.getRecordsOrders(ConstantsDomain.RANKING);

        assertNotNull(result.getRaking());
        assertNull(result.getTimeOrderEnd());
        assertEquals(2, result.getRaking().size());
        verify(persistance, times(1)).findAll();
    }

    @Test
    void getRecordsOrders_whenFilterIsTimeEnd_shouldReturnTimeOrderEndList() {
        List<StatusLog> statusLogs = new ArrayList<>();
        statusLogs.add(new StatusLog("1", 1, 101, "customer1@example.com", "2023-11-12 10:00", "2023-11-12 10:30", "PENDING", "PENDING", 201, "employee1@example.com"));
        statusLogs.add(new StatusLog("2", 2, 102, "customer2@example.com", "2023-11-12 11:00", "2023-11-12 11:45", "CANCELED", "DELIVERED", 202, "employee2@example.com"));

        when(persistance.findAll()).thenReturn(statusLogs);

        OrderEfficiency result = logStatusService.getRecordsOrders(ConstantsDomain.TIME_END);

        assertNotNull(result.getTimeOrderEnd());
        assertNull(result.getRaking());
        assertEquals(2, result.getTimeOrderEnd().size());
        verify(persistance, times(1)).findAll();
    }

    @Test
    void getRecordsOrders_whenFilterIsInvalid_shouldThrowErrorException() {
        assertThrows(ErrorException.class, () -> logStatusService.getRecordsOrders("INVALID_FILTER"));
        verify(persistance, never()).findAll();
    }
}