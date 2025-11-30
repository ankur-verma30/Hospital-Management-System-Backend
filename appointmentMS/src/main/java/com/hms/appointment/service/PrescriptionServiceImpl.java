package com.hms.appointment.service;

import com.hms.appointment.clients.ProfileClient;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.entity.Prescription;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final MedicineService medicineService;
    private final ProfileClient profileClient;

    @Override
    public Long savePrescription(PrescriptionDTO prescriptionDTO) throws HMSException {
        prescriptionDTO.setPrescriptionDate(LocalDate.now());
        Long prescriptionId = prescriptionRepository.save(prescriptionDTO.toEntity()).getId();
        prescriptionDTO.getMedicines().forEach(medicine -> {
            medicine.setPrescriptionId(prescriptionId);
        });
        medicineService.saveAllMedicines(prescriptionDTO.getMedicines());
        return prescriptionId;
    }

    @Override
    public PrescriptionDTO getPrescriptionByAppointmentId(Long appointmentId) throws HMSException {
        PrescriptionDTO prescriptionDTO = prescriptionRepository.findByAppointment_Id(appointmentId).orElseThrow(() -> new HMSException(
                "PRESCRIPTION_NOT_FOUND")).toDTO();
        prescriptionDTO.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescriptionDTO.getId()));
        return prescriptionDTO;

    }

    @Override
    public PrescriptionDTO getPrescriptionById(Long prescriptionId) throws HMSException {
        PrescriptionDTO prescriptionDTO = prescriptionRepository.findById(prescriptionId).orElseThrow(() -> new HMSException(
                "PRESCRIPTION_NOT_FOUND")).toDTO();

        prescriptionDTO.setMedicines(medicineService.getAllMedicinesByPrescriptionId(prescriptionDTO.getId()));
        return prescriptionDTO;
    }

    @Override
    public List<PrescriptionDetails> getPrescriptionsByPatientId(Long patientId) throws HMSException {
        List<Prescription> prescriptions=prescriptionRepository.findAllByPatientId(patientId);

        List<PrescriptionDetails> prescriptionDetails=prescriptions.stream().map(Prescription::toDetails).toList();

        prescriptionDetails.forEach(detail->{
            try {
                detail.setMedicines(medicineService.getAllMedicinesByPrescriptionId(detail.getId()));
            } catch (HMSException e) {
                throw new RuntimeException(e);
            }
        });

        List<Long> doctorIds=prescriptionDetails.stream().map(PrescriptionDetails::getDoctorId).toList();

        List<DoctorName> doctorNames=profileClient.getDoctorsById(doctorIds);
        Map<Long, String> doctorMap=doctorNames.stream().collect(Collectors.toMap(DoctorName::getId, DoctorName::getName));

        prescriptionDetails.forEach(details ->{
            String doctorName=doctorMap.get(details.getDoctorId());
            if(doctorName!=null){
            details.setDoctorName(doctorName);
            }
            else{
                details.setDoctorName("Unknown Doctor");
            }
        });
        return prescriptionDetails;
    }
}
