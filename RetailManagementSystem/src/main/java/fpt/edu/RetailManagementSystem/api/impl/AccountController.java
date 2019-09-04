package fpt.edu.RetailManagementSystem.api.impl;
import fpt.edu.RetailManagementSystem.api.AccountApi;
import fpt.edu.RetailManagementSystem.service.AccountService;
import fpt.edu.RetailManagementSystem.service.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController implements AccountApi {

    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        AccountDTO dto = accountService.insert(accountDTO);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO accountDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> accountDTOS = accountService.getAll();
        return ResponseEntity.ok(accountDTOS);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id) {
        Boolean result = accountService.delete(id);
        Map<String, Boolean> resul = new HashMap<>();
        resul.put("Content", result);
        return ResponseEntity.ok(resul);
    }
}