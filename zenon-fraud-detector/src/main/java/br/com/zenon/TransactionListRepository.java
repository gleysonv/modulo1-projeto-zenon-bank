package br.com.zenon;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> transaction;


    public TransactionListRepository(List<Transaction> transaction) {
        Objects.requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public Optional<Transaction> findByOriginName(String originalName){
        return transaction
                .stream()
                .filter(transaction -> transaction.origin().name().equals(originalName))
                .findFirst();
    }
}
