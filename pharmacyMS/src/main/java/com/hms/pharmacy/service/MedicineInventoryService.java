package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.MedicineInventoryDTO;
import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.exception.HMSException;

import java.util.List;

public interface MedicineInventoryService{
    List<MedicineInventoryDTO> getAllMedicines() throws HMSException;

    MedicineInventoryDTO getMedicineById(Long id) throws HMSException;

    MedicineInventoryDTO addMedicine(MedicineInventoryDTO medicineInventoryDTO) throws HMSException;

    MedicineInventoryDTO updateMedicine(MedicineInventoryDTO medicineInventoryDTO) throws HMSException;

    void deleteMedicine(Long id) throws HMSException;

    public void deleteExpiredMedicine() throws HMSException;




}
