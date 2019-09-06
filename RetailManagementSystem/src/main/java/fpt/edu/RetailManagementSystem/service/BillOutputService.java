package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.BillOutputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDetailDTO;

import java.util.List;

public interface BillOutputService {
    Boolean create(List<BillOutputDetailDTO> billDetailDTOS, Integer accountID);
    List<BillOutputDTO> getAllBill();
    List<BillOutputDetailDTO> getAllProductOfBill(Integer billID);
    Boolean updateStatus(Integer id);
}
