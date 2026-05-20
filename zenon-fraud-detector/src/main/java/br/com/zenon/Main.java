package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    void main() {
       var t1 = new Transaction(1,TransectionType.PAYMENT, new BigDecimal("9838.64"),
                new TransactionCustomer("C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.32")),
                new TransactionCustomer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0")),
        false, false);
        var t2 = new Transaction(743,TransectionType.CASH_OUT, new BigDecimal("850002.52"),
                new TransactionCustomer("C1231006815", new BigDecimal("850002.52"), new BigDecimal("0.0")),
                new TransactionCustomer("M1979787155", new BigDecimal("651099.11"), new BigDecimal("7360101.63")),
                true, false);
        IO.println(t1.toString());
        IO.println(t2.toString());
        IO.println("--------------------------------------------------------------");

        var transactionIngestor = new TransactionIngestor();

        List<Transaction> transactions =
                transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");

        IO.println(transactions.size());

        transactions.stream()
                .limit(10)
                .forEach(IO::println);

        IO.println("------------------TRATAMENTO DE ERROS EM ARQUIVO---------------------");

        var transactionBadDataIngestor = new TransactionIngestor();

        List<Transaction> transactionsBad =
                transactionBadDataIngestor.read("data/PS_Analise_de_erros_em_arquivo_log.csv");

        IO.println(transactionsBad.size());

        transactionsBad.stream()
                .limit(10)
                .forEach(IO::println);

    }


}