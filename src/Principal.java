import model.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();

        // Inserir funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2000"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("3000"), "Gerente"));
        funcionarios.add(new Funcionario("Ana", LocalDate.of(1985, 12, 20), new BigDecimal("2500"), "Operador"));
        funcionarios.add(new Funcionario("Carlos", LocalDate.of(1995, 3, 10), new BigDecimal("4000"), "Supervisor"));

        // Remover João
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // Imprimir funcionários formatado
        System.out.println("\n--- Lista de Funcionários ---");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        funcionarios.forEach(f -> {
            String data = f.getDataNascimento().format(formatter);
            String salario = String.format("%,.2f", f.getSalario())
                    .replace(",", "X").replace(".", ",").replace("X", ".");

            System.out.println(f.getNome() + " | " + data + " | " + salario + " | " + f.getFuncao());
        });

        // Aumento de 10%
        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")))
        );

        // Agrupar por função
        Map<String, List<Funcionario>> agrupados =
                funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir agrupados
        System.out.println("\n--- Agrupados por Função ---");
        agrupados.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        // Aniversariantes mês 10 e 12
        System.out.println("\n--- Aniversariantes (Outubro e Dezembro) ---");
        funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(f -> System.out.println(f.getNome()));

        // Mais velho
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .get();

        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("\nMais velho: " + maisVelho.getNome() + " - " + idade + " anos");

        // Ordem alfabética
        System.out.println("\n--- Ordem Alfabética ---");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // Total salários
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("\nTotal salários: " + total);

        // Salários mínimos
        BigDecimal salarioMinimo = new BigDecimal("1212");

        System.out.println("\n--- Salários mínimos ---");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + ": " + qtd);
        });
    }
}