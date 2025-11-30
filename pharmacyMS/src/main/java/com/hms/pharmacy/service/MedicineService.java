package com.hms.pharmacy.service;


import com.hms.pharmacy.dto.MedicineDTO;
import com.hms.pharmacy.entity.Medicine;
import com.hms.pharmacy.exception.HMSException;

import java.util.List;

public interface MedicineService {

    public Long addMedicine(MedicineDTO medicineDTO) throws HMSException;

    public MedicineDTO getMedicineById(Long id) throws HMSException;

    public void updateMedicine( MedicineDTO medicineDTO) throws HMSException;

    public List<MedicineDTO> getAllMedicines() throws HMSException;

    public Integer getStockById(Long id) throws HMSException;

    public Integer addStock(Long id, Integer quantity) throws  HMSException;

    public Integer removeStock(Long id, Integer quantity) throws  HMSException;

}
