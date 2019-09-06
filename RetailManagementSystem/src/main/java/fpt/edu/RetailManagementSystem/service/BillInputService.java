package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.BillInputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDetailDTO;

import java.util.List;

public interface BillInputService {
    Boolean create(List<BillInputDetailDTO> billDetailDTOS, Integer accountID);
    List<BillInputDTO> getAllBill();
    List<BillInputDetailDTO> getAllProductOfBill(Integer billID);
    Boolean updateStatus(Integer id);
}
