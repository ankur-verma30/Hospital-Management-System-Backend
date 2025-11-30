package com.hms.appointment.service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.AppointmentRecordRepository;
import com.hms.appointment.utility.StringListConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentRecordServiceImpl implements AppointmentRecordService{

    private final AppointmentRecordRepository appointmentRecordRepository;

    private final PrescriptionService prescriptionService;

    private final ProfileClient profileClient;

    @Override
    public Long createAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HMSException {

        Optional<AppointmentRecord> existingAppointmentRecord=appointmentRecordRepository.findByAppointment_Id(appointmentRecordDTO.getAppointmentId());
        if(existingAppointmentRecord.isPresent()){
            throw new HMSException("APPOINTMENT_RECORD_ALREADY_PRESENT");
        }

        appointmentRecordDTO.setCreatedAt(LocalDateTime.now());

        Long id = appointmentRecordRepository.save(appointmentRecordDTO.toEntity()).getId();

        if(appointmentRecordDTO.getPrescription()!=null){
            appointmentRecordDTO.getPrescription().setAppointmentId(appointmentRecordDTO.getAppointmentId());
            prescriptionService.savePrescription(appointmentRecordDTO.getPrescription());
        }
        return  id;
    }

    @Override
    public void updateAppointmentRecord(AppointmentRecordDTO appointmentRecordDTO) throws HMSException {
    AppointmentRecord existingAppointmentRecord=
            appointmentRecordRepository.findById(appointmentRecordDTO.getId()).orElseThrow(()-> new HMSException(
                    "APPOINTMENT_RECORD_NOT_FOUND"));
    existingAppointmentRecord.setNotes(appointmentRecordDTO.getNotes());
    existingAppointmentRecord.setDiagnosis(appointmentRecordDTO.getDiagnosis());
    existingAppointmentRecord.setFollowUpDate(appointmentRecordDTO.getFollowUpDate());

    existingAppointmentRecord.setSymptoms(StringListConverter.convertStringListToString(appointmentRecordDTO.getSymptoms()));
    existingAppointmentRecord.setTests(StringListConverter.convertStringListToString(appointmentRecordDTO.getTests()));

    existingAppointmentRecord.setReferral(appointmentRecordDTO.getReferral());
    appointmentRecordRepository.save(existingAppointmentRecord);
    }

    @Override
    public AppointmentRecordDTO getAppointmentRecordByAppointmentId(Long id) throws HMSException {
        return appointmentRecordRepository.findByAppointment_Id(id).orElseThrow(() -> new HMSException(
                "APPOINTMENT_RECORD_NOT_FOUND")).toDTO();
    }

    @Override
    public AppointmentRecordDTO getAppointmentRecordDetailsByAppointmentId(Long appointmentId) throws HMSException {
        AppointmentRecordDTO appointmentRecord = appointmentRecordRepository.findByAppointment_Id(appointmentId).orElseThrow(() -> new HMSException(
                "APPOINTMENT_RECORD_NOT_FOUND")).toDTO();

        appointmentRecord.setPrescription(prescriptionService.getPrescriptionByAppointmentId(appointmentId));

        return appointmentRecord;

    }

    @Override
    public AppointmentRecordDTO getAppointmentRecordById(Long id) throws HMSException {
        return appointmentRecordRepository.findById(id).orElseThrow(() -> new HMSException(
                "APPOINTMENT_RECORD_NOT_FOUND")).toDTO();
    }

    @Override
    public List<RecordDetails> getAppointmentRecordByPatientId(Long patientId) throws HMSException {
        List<RecordDetails> records = appointmentRecordRepository.findByPatientId(patientId).stream().map(AppointmentRecord::toRecordDetails).toList();

    List<Long> doctorIds = records.stream().map(RecordDetails::getDoctorId).distinct().toList();

    List<DoctorName> doctorNames = profileClient.getDoctorsById(doctorIds);

    Map<Long, String> doctorMap=doctorNames.stream().collect(Collectors.toMap(DoctorName::getId, DoctorName::getName));

    records.forEach(record->{
        String doctorName=doctorMap.get(record.getDoctorId());
        if(doctorName!=null){
        record.setDoctorName(doctorName);
        }
        else{
            record.setDoctorName("Unknown Doctor");
        }
    });
    return  records;
    }

    @Override
    public Boolean appointmentRecordExists(Long appoitmentId) throws HMSException {
        return appointmentRecordRepository.existsByAppointment_Id(appoitmentId);
    }
}
