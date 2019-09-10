package fpt.edu.RetailManagementSystem.api;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDetailDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDetailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
@RequestMapping("/billsOutput")
public interface BillOutputApi {
    @ApiOperation(tags = {"Bill Output",}, notes = "", value = "Create new Bill")
    @PostMapping("{accountID}")
    ResponseEntity<Map<String, Boolean>> create(@RequestBody List<BillOutputDetailDTO> billDetailDTO,
                                                @RequestParam String code,
                                                @RequestParam float tax,
                                                @RequestParam Integer seller,
                                                @RequestParam Integer customerID);

    @ApiOperation(tags = {"Bill Output",}, notes = "", value = "Get All Bill")
    @GetMapping("")
    ResponseEntity<List<BillOutputDTO>> getAllBill();

    @ApiOperation(tags = {"Bill Output",}, notes = "", value = "get all product in Bill")
    @GetMapping("{billID}")
    ResponseEntity<List<BillOutputDetailDTO>> getAllProductOfBill(@PathVariable Integer billID);

    @ApiOperation(tags = {"Bill Output",}, notes = "", value = "Update status of bill")
    @PutMapping("/updateBillStatus/{id}")
    ResponseEntity<Map<String, Boolean>> updateBillStatus(@PathVariable Integer id);

    @ApiOperation(tags = {"Bill Output",}, notes = "", value = "Update isPaid of bill")
    @PutMapping("/updateIsPaid/{id}")
    ResponseEntity<Map<String, Boolean>> updateIsPaid(@PathVariable Integer id);
}
