package com.restaurants.trazabilidad;

import com.restaurant.plazoleta.domain.model.StatusLog;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusPersistance;
import com.restaurants.trazabilidad.domain.interfaces.IUserServiceClient;
import com.restaurants.trazabilidad.domain.model.OrderStatus;
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

        verify(persistance, times(1)).save(any(com.restaurant.plazoleta.domain.model.StatusLog.class));
    }

    @Test
    void registerChange_shouldUpdateAndSaveStatusLog() {
        Integer orderId = 1;
        String employeeEmail = "employee@example.com";
        Integer employeeId = 1;
        OrderStatus newStatus = OrderStatus.DELIVERED;

        // Crear un StatusLog con un email de cliente vacío (para activar la asignación de empleado)
        com.restaurant.plazoleta.domain.model.StatusLog existingLog = new StatusLog();
        existingLog.setOrderId(orderId);
        existingLog.setCustomerEmail("");  // Dejar el email vacío para probar la asignación del empleado
        existingLog.setNewStatus(OrderStatus.PENDING.toString());

        // Configurar el mock para devolver este StatusLog
        when(persistance.findByOrderId(orderId)).thenReturn(existingLog);

        // Llamar al método de servicio para registrar el cambio de estado
        logStatusService.registerChange(newStatus, orderId, employeeEmail, employeeId);

        // Verificar que el save fue invocado con el StatusLog actualizado
        verify(persistance, times(1)).save(any(StatusLog.class));

        // Asegurarse de que los valores se han actualizado correctamente
        assertEquals(OrderStatus.DELIVERED.toString(), existingLog.getNewStatus());
        assertEquals(OrderStatus.PENDING.toString(), existingLog.getPreviousStatus());
        assertEquals(employeeEmail, existingLog.getEmployeeEmail());  // Ahora debería ser asignado
        assertEquals(employeeId, existingLog.getEmployeeId());  // Ahora debería ser asignado
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