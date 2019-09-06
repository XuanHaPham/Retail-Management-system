package fpt.edu.RetailManagementSystem.api.impl;

import fpt.edu.RetailManagementSystem.api.PaymentBillApi;
import fpt.edu.RetailManagementSystem.service.PaymentBillService;
import fpt.edu.RetailManagementSystem.service.dto.PaymentBillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PaymentBillController implements PaymentBillApi {
    @Autowired
    PaymentBillService paymentBillService;

    @Override
    public ResponseEntity<List<PaymentBillDTO>> getAllPaymentBill(){
        List<PaymentBillDTO> paymentBillDTOS = paymentBillService.getAll();
        return ResponseEntity.ok(paymentBillDTOS);
    }


    @Override
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id) {
        Boolean result = paymentBillService.deleteByID(id);
        Map<String, Boolean> resul = new HashMap<>();
        resul.put("Content", result);
        return ResponseEntity.ok(resul);
    }

    @Override
    public ResponseEntity<PaymentBillDTO> findByID(@PathVariable("id") Integer id){
        PaymentBillDTO paymentBillDTO = paymentBillService.findByID(id);
        return ResponseEntity.ok(paymentBillDTO);
    }

    @Override
    public ResponseEntity<PaymentBillDTO> insert(@RequestBody PaymentBillDTO paymentBillDTO) {
        PaymentBillDTO dto = paymentBillService.insert(paymentBillDTO);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<PaymentBillDTO> update(@RequestBody PaymentBillDTO paymentBillDTO) {
        PaymentBillDTO dto = paymentBillService.update(paymentBillDTO);
        return ResponseEntity.ok(dto);
    }
}
