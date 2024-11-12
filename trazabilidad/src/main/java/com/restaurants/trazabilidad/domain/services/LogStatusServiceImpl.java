package com.restaurants.trazabilidad.domain.services;

import com.restaurants.trazabilidad.domain.exception.ErrorException;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusPersistance;
import com.restaurants.trazabilidad.domain.interfaces.ILogStatusService;
import com.restaurants.trazabilidad.domain.interfaces.IUserServiceClient;
import com.restaurants.trazabilidad.domain.model.*;
import com.restaurants.trazabilidad.domain.utils.ConstantsDomain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        String startOfDay = today.atStartOfDay().format(DateTimeFormatter.ofPattern(ConstantsDomain.PATERN_DATE));
        String endOfDay = today.atTime(23, 59, 59).format(DateTimeFormatter.ofPattern(ConstantsDomain.PATERN_DATE));

        return persistance.findByCustomerIdAndDateStarBetween(customId, startOfDay,endOfDay);

    }

    @Override
    public List<StatusLog> getOrderStatus() {
        Integer customId=userService.GetUser().getId();
        LocalDate today = LocalDate.now();
        String startOfDay = today.atStartOfDay().format(DateTimeFormatter.ofPattern(ConstantsDomain.PATERN_DATE));
        String endOfDay = today.atTime(23, 59, 59).format(DateTimeFormatter.ofPattern(ConstantsDomain.PATERN_DATE));
        return persistance.findByCustomerIdAndDateStarBetween(customId, startOfDay,endOfDay);
    }

    @Override
    public OrderEfficiency getRecordsOrders(String filter) {
        if(!filter.equalsIgnoreCase(ConstantsDomain.RANKING) && !filter.equalsIgnoreCase(ConstantsDomain.TIME_END)) {
            throw new ErrorException(ConstantsDomain.MESAGE_INVALID_FILTE);
        }
        OrderEfficiency oderEficience= new OrderEfficiency();
        List<StatusLog> statusLogs=persistance.findAll();
        if(filter.equalsIgnoreCase(ConstantsDomain.RANKING)) {
            oderEficience.setRaking(calculateRaking(statusLogs));
            return oderEficience;
        }
        List<TimeOrderEnd> timeOrderEndList = new ArrayList<>();
        for (StatusLog log : statusLogs) {
            if (log.getDateStar() != null && log.getLastUpdate() != null) {
                Duration duration = calculateProcessingTime(log.getDateStar(), log.getLastUpdate());
                String elapsedTime = formatDuration(duration);

                TimeOrderEnd timeOrderEnd = new TimeOrderEnd();
                timeOrderEnd.setOrderId(log.getOrderId());
                timeOrderEnd.setElapsedTime(elapsedTime);
                timeOrderEnd.setStatus(OrderStatus.valueOf(log.getNewStatus()));

                timeOrderEndList.add(timeOrderEnd);
            }
        }

        oderEficience.setTimeOrderEnd(timeOrderEndList);
        return  oderEficience;
    }

    private String dateFormate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsDomain.PATERN_DATE);
        return  now.format(formatter);
    }

    private static Duration calculateProcessingTime(String dateStar, String lastUpdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsDomain.PATERN_DATE);
        LocalDateTime start = LocalDateTime.parse(dateStar, formatter);
        LocalDateTime end = LocalDateTime.parse(lastUpdate, formatter);

        return Duration.between(start, end);
    }

    private List<Raking> calculateRaking(List<StatusLog> logs) {
        Map<Integer, List<Duration>> employeeDurations = new HashMap<>();
        Map<Integer, String> employeeEmails = new HashMap<>();
        List<Raking> rakings = new ArrayList<>();

        for (StatusLog log : logs) {
            if (log.getEmployeeId() != null && log.getLastUpdate() != null) {
                Duration duration = calculateProcessingTime(log.getDateStar(), log.getLastUpdate());
                employeeDurations.computeIfAbsent(log.getEmployeeId(), k -> new ArrayList<>()).add(duration);
                employeeEmails.putIfAbsent(log.getEmployeeId(), log.getEmployeeEmail());
            }
        }

        for (Map.Entry<Integer, List<Duration>> entry : employeeDurations.entrySet()) {
            Integer employeeId = entry.getKey();
            List<Duration> durations = entry.getValue();
            Duration averageTime = calculateAverageDuration(durations);

            Raking raking = new Raking();
            raking.setEmployeId(employeeId);
            raking.setAverageTime(formatDuration(averageTime));
            raking.setEmployeEmail(employeeEmails.get(employeeId));

            rakings.add(raking);
        }

        rakings.sort(Comparator.comparing(Raking::getAverageTime));

        return rakings;
    }

    private Duration calculateAverageDuration(List<Duration> durations) {
        long totalSeconds = durations.stream().mapToLong(Duration::getSeconds).sum();
        return Duration.ofSeconds(totalSeconds / durations.size());
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
