package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.SaleDTO;
import com.hms.pharmacy.exception.HMSException;

public interface SaleService  {

    Long createSale(SaleDTO saleDTO) throws HMSException;

    void updateSale(SaleDTO saleDTO) throws HMSException;

    SaleDTO getSale(Long id) throws HMSException;

    SaleDTO getSaleByPrescriptionId(Long prescriptionId) throws HMSException;

}
