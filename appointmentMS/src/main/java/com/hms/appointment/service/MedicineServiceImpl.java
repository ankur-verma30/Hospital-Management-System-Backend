package com.hms.appointment.service;

import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.entity.Medicine;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService{

    private final MedicineRepository medicineRepository;

    @Override
    public Long saveMedicine(MedicineDTO medicineDTO) throws HMSException {
        return medicineRepository.save(medicineDTO.toEntity()).getId();
    }

    @Override
    public List<MedicineDTO> saveAllMedicines(List<MedicineDTO> medicineDTOList) throws HMSException {
        return medicineRepository.saveAll(medicineDTOList.stream().map(MedicineDTO::toEntity).toList()).stream().map(Medicine::toDTO).toList();

    }

    @Override
    public List<MedicineDTO> getAllMedicinesByPrescriptionId(Long prescriptionId) throws HMSException {
        return medicineRepository.findAllByPrescription_Id(prescriptionId).stream().map(Medicine::toDTO).toList();
    }
}
