package com.hms.appointment.service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.*;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {


    private final ApiService apiService;

    private final ProfileClient profileClient;

    private final AppointmentRepository appointmentRepository;

    @Override
    public Long scheduleAppointment(AppointmentDTO appointmentDTO) throws HMSException {
        Boolean isDoctorExist = profileClient.doctorExists(appointmentDTO.getDoctorId());
        if(isDoctorExist==null || !isDoctorExist){
            throw new HMSException("DOCTOR_NOT_FOUND");
        }
        Boolean isPatientExist = profileClient.patientExists(appointmentDTO.getPatientId());
        if(isPatientExist==null || !isPatientExist){
            throw new HMSException("PATIENT_NOT_FOUND");
        }
        appointmentDTO.setStatus(Status.SCHEDULED);
       Appointment appointment = appointmentRepository.save(appointmentDTO.toEntity());
       return appointment.getId();
    }

    @Override
    public void cancelAppointment(Long id) throws HMSException {
    Appointment appointment=appointmentRepository.findById(id).orElseThrow(() -> new HMSException("APPOINTMENT_NOT_FOUND"));
    if(appointment.getStatus().equals(Status.CANCELLED)){
        throw new HMSException("APPOINTMENT_ALREADY_CANCELLED");
    }
    appointment.setStatus(Status.CANCELLED);
    appointmentRepository.save(appointment);
    }

    @Override
    public void completeAppointment(Long id) throws HMSException {

    }

    @Override
    public void rescheduleAppointment(Long id, AppointmentDTO appointmentDTO) throws HMSException {

    }

    @Override
    public AppointmentDTO getAppointmentDetails(Long id) throws HMSException {
    appointmentRepository.findById(id).orElseThrow(() -> new HMSException("APPOINTMENT_NOT_FOUND"));
        return appointmentRepository.findById(id).get().toDTO();
    }

    @Override
    public AppointmentDetails getAppointmentDetailsWithName(Long id) throws HMSException {
        AppointmentDTO appointmentDTO = appointmentRepository.findById(id).orElseThrow(() -> new HMSException(
                "APPOINTMENT_NOT_FOUND")).toDTO();
        DoctorDTO doctorDTO=profileClient.getDoctorById(appointmentDTO.getDoctorId());
        PatientDTO patientDTO=profileClient.getPatientById(appointmentDTO.getPatientId());
        return new AppointmentDetails(appointmentDTO.getId(),appointmentDTO.getPatientId(),
                appointmentDTO.getDoctorId(),doctorDTO.getName(),patientDTO.getName(),
                patientDTO.getEmail(),patientDTO.getPhone(),appointmentDTO.getAppointmentTime(),
                appointmentDTO.getStatus(),appointmentDTO.getReason(),appointmentDTO.getNotes());

    }

    @Override
    public List<AppointmentDetails> getAppointmentDetailsByPatientId(Long patientId) throws HMSException {
        return appointmentRepository.findAllByPatientId(patientId).stream().map(appointment -> {
            DoctorDTO doctorDTO =profileClient
                    .getDoctorById(appointment.getDoctorId());
            appointment.setDoctorName(doctorDTO.getName());
            return  appointment;
        }).toList();
    }

    @Override
    public List<AppointmentDetails> getAppointmentDetailsByDoctorId(Long doctorId) throws HMSException {
        return appointmentRepository.findAllByDoctorId(doctorId).stream().map(appointment -> {
            PatientDTO patientDTO =profileClient
                    .getPatientById(appointment.getPatientId());
            appointment.setPatientName(patientDTO.getName());
            appointment.setPatientEmail(patientDTO.getEmail());
            appointment.setPatientPhone(patientDTO.getPhone());
            return  appointment;
        }).toList();
    }
}
