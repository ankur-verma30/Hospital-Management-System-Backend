package com.hms.pharmacy.api;

import com.hms.pharmacy.dto.ResponseDTO;
import com.hms.pharmacy.dto.SaleDTO;
import com.hms.pharmacy.dto.SaleItemDTO;
import com.hms.pharmacy.exception.HMSException;
import com.hms.pharmacy.service.SaleItemService;
import com.hms.pharmacy.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacy/sales")
public class SaleAPI {

    private final SaleService saleService;

    private final SaleItemService saleItemService;

    @PostMapping("/create")
    public ResponseEntity<Long> createSale(@RequestBody SaleDTO saleDTO) throws HMSException {
        return new ResponseEntity<>(saleService.createSale(saleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateSale(@RequestBody SaleDTO saleDTO) throws HMSException{
        saleService.updateSale(saleDTO);
        return new ResponseEntity<>(new ResponseDTO("Sale updated successfully"),HttpStatus.OK);
    }

    @GetMapping("/getSaleItems/{saleId}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItems(@PathVariable Long saleId) throws HMSException{
        saleItemService.getSaleItemsBySaleId(saleId);
        return new ResponseEntity<>(saleItemService.getSaleItemsBySaleId(saleId),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable Long id) throws HMSException{
        SaleDTO sale=saleService.getSale(id);
        return new ResponseEntity<>(sale,HttpStatus.OK);
    }

}
