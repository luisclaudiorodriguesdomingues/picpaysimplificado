package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select t.id, t.amount, t.timestamp, " +
            "   (us.first_name || ' ' || us.last_name) as nameSender, " +
            "   (ur.first_name || ' ' || ur.last_name) as nameReceiver, " +
            "   from transactions t" +
            "   join users us on us.id = t.sender_id" +
            "   join users ur on ur.id = t.receiver_id",
            nativeQuery = true)

//    @Query(value = "select new com.picpaysimplificado.dtos.TransactionsDTO(" +
//            "t.id as id, t.amount as amount, t.timestamp as timestamp, " +
//            "(us.first_name) as nameSender, " +
//            "(ur.first_name) as nameReceiver, " +
//            "from transactions t " +
//            "inner join users us " +
//            "inner join users ur ) ")

    public List<TransactionsDTO> findTransactionUser();
}
