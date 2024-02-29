package com.picpaysimplificado.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionsDTO {
    public Long getId();
    public BigDecimal getAmount();
    public LocalDateTime getTimestamp();
    public String getNameSender();
    public String getNameReceiver();
}
