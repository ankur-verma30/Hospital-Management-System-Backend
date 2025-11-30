package com.hms.appointment.service;

import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.exception.HMSException;

import java.util.List;

public interface PrescriptionService {

    public Long savePrescription(PrescriptionDTO prescriptionDTO) throws HMSException;
    public PrescriptionDTO getPrescriptionByAppointmentId(Long appointmentId) throws HMSException;
    public PrescriptionDTO getPrescriptionById(Long prescriptionId) throws HMSException;

    public List<PrescriptionDetails> getPrescriptionsByPatientId(Long patientId) throws HMSException;

}
