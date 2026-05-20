package br.com.zenon;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TransactionIngestor {

    public List<Transaction> read(String filename) {

        Path path = Path.of(filename);

        try {

            List<String> lines = Files.readAllLines(path);

            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ler o arquivo: " , ex);
        }
    }

    //modelo antigo no java 8 vou manter pra estudo
//    public List<Transaction> readOld(String filename) {
//
//        List<Transaction> transactions = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(filename);
//             Scanner scanner = new Scanner(fis)) {
//
//            int lineCount = 0;
//
//            while (scanner.hasNextLine()) {
//
//                String line = scanner.nextLine();
//                lineCount++;
//
//                if (lineCount == 1) {
//                    continue;
//                }
//
//                if (lineCount > 1000) {
//                    break;
//                }
//
//                var transaction = parseTransaction(line);
//                transactions.add(transaction);
//            }
//
//        } catch (Exception ex) {
//          throw new RuntimeException("Erro na leitura do arquivo de auditoria " + filename, ex);
//
//        }
//
//        return transactions;
//    }

    private Optional<Transaction> parseTransaction(String line) {
        try {
            String[] chunks = line.split(",");

            int step = Integer.parseInt(chunks[0]);
            TransectionType type = TransectionType.valueOf(chunks[1]);

            BigDecimal amount = parseBigDecimal(chunks[2], "amount");

            var origin = new TransactionCustomer(chunks[3],
                    parseBigDecimal(chunks[4], "oldBalanceOrig"),
                    parseBigDecimal(chunks[5], "newBalanceOrig"));

            var recipient = new TransactionCustomer(chunks[6],
                    parseBigDecimal(chunks[7], "oldBalanceDest"),
                    parseBigDecimal(chunks[8], "newBalanceDest"));

            boolean isFraud = "1".equals(chunks[9]);

            boolean isFlaggedFraud = "1".equals(chunks[10]);

            return Optional.of(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));
        } catch (Exception e){
            IO.println("Erro ao fazer parse: " +line + " | " + e);
            return Optional.empty();
        }
    }
    private BigDecimal parseBigDecimal(String value, String fieldName) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O campo '" + fieldName + "' não pode ser nulo ou vazio"
            );
        }

        try {
            return new BigDecimal(value);

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Valor inválido para o campo '" + fieldName + "': " + value
            );
        }
    }
}
