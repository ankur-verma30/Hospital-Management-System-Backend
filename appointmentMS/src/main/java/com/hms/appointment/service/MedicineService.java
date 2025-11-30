package com.hms.appointment.service;

import com.hms.appointment.dto.MedicineDTO;
import com.hms.appointment.exception.HMSException;

import java.util.List;

public interface MedicineService {
    public Long saveMedicine(MedicineDTO medicineDTO) throws HMSException;
    public List<MedicineDTO> saveAllMedicines(List<MedicineDTO> medicineDTOList) throws HMSException;
    public List<MedicineDTO> getAllMedicinesByPrescriptionId(Long prescriptionId) throws HMSException;
}
