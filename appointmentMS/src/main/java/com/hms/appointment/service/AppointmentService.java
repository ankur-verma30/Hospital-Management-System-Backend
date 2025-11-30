package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.exception.HMSException;

import java.util.List;

public interface AppointmentService {

    Long scheduleAppointment(AppointmentDTO appointmentDTO) throws HMSException;
    void cancelAppointment(Long  id) throws HMSException;
    void completeAppointment(Long id) throws HMSException;
    void rescheduleAppointment(Long id, AppointmentDTO appointmentDTO) throws HMSException;
    AppointmentDTO getAppointmentDetails(Long id) throws HMSException;
    AppointmentDetails getAppointmentDetailsWithName(Long id) throws HMSException;
    List<AppointmentDetails> getAppointmentDetailsByPatientId(Long patientId) throws HMSException;
    List<AppointmentDetails> getAppointmentDetailsByDoctorId(Long doctorId) throws HMSException;


}
