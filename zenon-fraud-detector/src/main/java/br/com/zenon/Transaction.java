package br.com.zenon;

import java.math.BigDecimal;
import java.util.Objects;

public record  Transaction (
    int step, TransectionType type, BigDecimal amount, TransactionCustomer origin,
    TransactionCustomer recipient, boolean isFraud, boolean isFlaggedFraud){


    public Transaction{
        Objects.requireNonNull(type);
        if (step <= 0) throw new IllegalArgumentException("O valor de step deve ser positivo" + step);
        if (amount.signum() < 0) throw new IllegalArgumentException("O valor de amount deve ser positivo" + amount);
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "step=" + step +
                ", type=" + type +
                ", amount=" + amount +
                ", origin=" + origin +
                ", recipient=" + recipient +
                ", isFraud=" + isFraud +
                ", isFlaggedFraud=" + isFlaggedFraud +
                '}';
    }
}
