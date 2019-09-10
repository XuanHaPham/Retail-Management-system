package fpt.edu.RetailManagementSystem.api.impl;

import fpt.edu.RetailManagementSystem.api.BillInputApi;
import fpt.edu.RetailManagementSystem.persistent.entity.BillInput;
import fpt.edu.RetailManagementSystem.service.BillInputService;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BillInputController implements BillInputApi {

    @Autowired
    BillInputService billService;

    @Override
    public ResponseEntity<List<BillInputDetailDTO>> create(@RequestBody List<BillInputDetailDTO> billDetailDTO,
                                                       @RequestParam Integer accountID,
                                                       @RequestParam String code,
                                                       @RequestParam float tax,
                                                       @RequestParam Integer supplier){
        List<BillInputDetailDTO> result = billService.create(billDetailDTO,code,accountID,tax,supplier  );
        return ResponseEntity.ok(result);
    }
//List<BillInputDetailDTO> billDetailDTOS, String code, Integer accountID, float tax, Integer supllier
    @Override
    public ResponseEntity<List<BillInputDTO>> getAllBill(){
        List<BillInputDTO> billDTOS = billService.getAllBill();
        return ResponseEntity.ok(billDTOS);
    }

    @Override
    public ResponseEntity<List<BillInputDetailDTO>> getAllProductOfBill(@PathVariable Integer billID){
        List<BillInputDetailDTO> billDetailDTOS = billService.getAllProductOfBill(billID);
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


    //updateIsPaid(@PathVariable Integer id);
}
