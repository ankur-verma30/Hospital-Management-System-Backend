package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.exception.HMSException;

import java.util.List;

public interface AppointmentRecordService {
    public Long createAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HMSException;

    public void updateAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HMSException;

    public AppointmentRecordDTO getAppointmentRecordByAppointmentId(Long id) throws HMSException;

    public AppointmentRecordDTO getAppointmentRecordDetailsByAppointmentId(Long appointmentId) throws HMSException;

    public AppointmentRecordDTO getAppointmentRecordById(Long id) throws HMSException;

    public List<RecordDetails> getAppointmentRecordByPatientId(Long patientId) throws HMSException;

    public Boolean appointmentRecordExists(Long id) throws HMSException;
}
