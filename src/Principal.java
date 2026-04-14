package main;

import model.Funcionario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        // Configuração de formatadores locais
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatadorMoeda = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatadorMoeda.setMinimumFractionDigits(2);

        // 3.1 - Inserção de dados conforme a tabela
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 - Remoção
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 - Listagem formatada
        System.out.println("LISTA DE FUNCIONÁRIOS:");
        funcionarios.forEach(f -> System.out.println(
            "Nome: " + f.getNome() + 
            " | Nascimento: " + f.getDataNascimento().format(formatadorData) +
            " | Salário: R$ " + formatadorMoeda.format(f.getSalario()) +
            " | Função: " + f.getFuncao()
        ));

        // 3.4 - Atualização salarial (10%)
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));

        // 3.5 e 3.6 - Agrupamento por Função
        Map<String, List<Funcionario>> mapaPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nAGRUPADOS POR FUNÇÃO:");
        mapaPorFuncao.forEach((funcao, lista) -> {
            System.out.println("-> " + funcao + ": " + 
                lista.stream().map(Funcionario::getNome).collect(Collectors.joining(", ")));
        });

        // 3.8 - Aniversariantes de Outubro (10) e Dezembro (12)
        System.out.println("\nANIVERSARIANTES DE OUTUBRO E DEZEMBRO:");
        funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
            .forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento().format(formatadorData)));

        // 3.9 - Maior idade
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("\nFUNCIONÁRIO MAIS VELHO: " + maisVelho.getNome() + " | Idade: " + idade);

        // 3.10 - Ordem alfabética
        System.out.println("\nORDEM ALFABÉTICA:");
        funcionarios.stream()
            .sorted(Comparator.comparing(Funcionario::getNome))
            .forEach(f -> System.out.println(f.getNome()));

        // 3.11 - Total de salários
        BigDecimal total = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTOTAL DOS SALÁRIOS: R$ " + formatadorMoeda.format(total));

        // 3.12 - Qtd de salários mínimos
        System.out.println("\nQUANTIDADE DE SALÁRIOS MÍNIMOS (Base R$ 1212.00):");
        BigDecimal min = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(min, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + qtd + " salários mínimos.");
        });
    }
}