package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO getByUsernameAndPassword(String username, String password);

    List<AccountDTO> getAll();

    Boolean delete(Integer id);

    AccountDTO insert(AccountDTO accountDTO);

    AccountDTO update(AccountDTO accountDTO);
}