package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.SaleItemDTO;
import com.hms.pharmacy.exception.HMSException;

import java.util.List;

public interface SaleItemService {

    Long createSaleItem(SaleItemDTO saleItemDTO) throws HMSException;

    void createMultipleSaleItems(Long saleId,Long medicineId,List<SaleItemDTO> saleItemDTOs) throws HMSException;

    void updateSaleItem(SaleItemDTO saleItemDTO) throws HMSException;

    List<SaleItemDTO> getSaleItemsBySaleId(Long saleId) throws HMSException;

    SaleItemDTO getSaleItem(Long id) throws HMSException;
}
