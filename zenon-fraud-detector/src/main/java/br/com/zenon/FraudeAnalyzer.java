package br.com.zenon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FraudeAnalyzer {

    private final List<Transaction> transactions;

    public FraudeAnalyzer(List<Transaction> transactions) {
       Objects.requireNonNull(transactions);
       this.transactions = transactions;
    }

    public long countFrauds(){
        return frausStream()
                .count();
    }

    // método que filtra por transações e compara o valor de amount
    //  do maior pro menos (reversed) vai pegar só os limit
    //  (exempro os 10(varíavel limit) maiores) maiores
    public List<BigDecimal> findHighestValueFraudAmounts(int limit) {
        return highValueFraudsStrean()
                .map(Transaction::amount)
                .limit(limit)
                .toList();
    }



    public List<String> findTopSuspiciousClients(int limit) {
        return  highValueFraudsStrean()
                .map(transaction -> transaction.origin().name())
                .distinct()
                .limit(limit)
                .toList();
    }


    public BigDecimal cauculateTotalFraudLoss() {
        return frausStream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransectionType, Long> countFraudsByType() {
        return frausStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> frausStream() {
        return transactions
                .stream()
                .filter(Transaction::isFraud);
    }

    private Stream<Transaction> highValueFraudsStrean() {
        return frausStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed());
    }

}
