package fpt.edu.RetailManagementSystem.api.impl;

import fpt.edu.RetailManagementSystem.api.BillOutputApi;
import fpt.edu.RetailManagementSystem.service.BillOutputService;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BillOutputController implements BillOutputApi {

    @Autowired
    BillOutputService billService;
//String code, float tax, Integer seller,
    @Override
    public ResponseEntity<Map<String, Boolean>> create(@RequestBody List<BillOutputDetailDTO> billDetailDTO,
                                                       @PathVariable String code,
                                                       @PathVariable float tax,
                                                       @PathVariable Integer seller,
                                                       @PathVariable Integer customerID){
        Boolean result = billService.create(billDetailDTO, code, tax, seller, customerID);
        Map<String, Boolean> resul = new HashMap<>();
        resul.put("Content", result);
        return ResponseEntity.ok(resul);
    }

    @Override
    public ResponseEntity<List<BillOutputDTO>> getAllBill(){
        List<BillOutputDTO> billDTOS = billService.getAllBill();
        return ResponseEntity.ok(billDTOS);
    }

    @Override
    public ResponseEntity<List<BillOutputDetailDTO>> getAllProductOfBill(@PathVariable Integer billID){
        List<BillOutputDetailDTO> billDetailDTOS = billService.getAllProductOfBill(billID);
        return ResponseEntity.ok(billDetailDTOS);
    }

    @Override
    public  ResponseEntity<Map<String, Boolean>> updateBillStatus(@PathVariable Integer id){
        Boolean result = billService.updateStatus(id);
        Map<String, Boolean> resul = new HashMap<>();
        resul.put("Content", result);
        return ResponseEntity.ok(resul);
    }

    @Override
    public  ResponseEntity<Map<String, Boolean>> updateIsPaid(@PathVariable Integer id){
        Boolean result = billService.updateIsPaid(id);
        Map<String, Boolean> resul = new HashMap<>();
        resul.put("Content", result);
        return ResponseEntity.ok(resul);
    }
}
