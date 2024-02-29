package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionsDTO;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public  ResponseEntity<List<TransactionsDTO>> listTransactions() throws Exception {
        List<TransactionsDTO> transactions =  this.transactionService.findTransactionUser();
        return new ResponseEntity<>(transactions, HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception{
        Transaction transaction = this.transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

}
