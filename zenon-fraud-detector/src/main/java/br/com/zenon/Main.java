package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

     IO.println("------------------ANALISE DE TRANSAÇÕES---------------------");
     var fraudAnalyzer = new FraudeAnalyzer(transactions);

     long fraudCount = fraudAnalyzer.countFrauds();
     IO.println("Total de fraudes = " + fraudCount);


     IO.println("Top 3 fraudes  de maior valor");
     List<BigDecimal> hishestFraudAmoubts = fraudAnalyzer.findHighestValueFraudAmounts(3);
     //esse é nova pra mim! kkkkk 1 linha só! meu Deus!
     hishestFraudAmoubts.forEach(amount -> IO.println("- %.2f".formatted(amount)));
     // vou explicar essa pra fixar melhor:
     // estou pegando da lista hishestFrauds uma sublista só com amount
     // ai faço um laço pra pintar os amount;

     List<String> suspiciousClients = fraudAnalyzer.findTopSuspiciousClients(5);
     IO.println("top 5 clientes suspeitos");
     suspiciousClients.forEach(IO::println);

      BigDecimal totalFraudLoss =   fraudAnalyzer.cauculateTotalFraudLoss();
      IO.println("Prejuizo total = " + totalFraudLoss);

      Map<TransectionType, Long> fraudCountByType =fraudAnalyzer.countFraudsByType();
       IO.println("fraudes por tipo: ");
       fraudCountByType.forEach(( type, count) -> IO.println("- %s: %d".formatted(type, count)));

    }


}