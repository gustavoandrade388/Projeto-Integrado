import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Sistema sistema = new Sistema();

    public static void main(String[] args) {

        int opcao;

        do {

            exibirMenu();

            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    System.out.println("DEBUG: opção 2");
                    sistema.listarClientes();
                    break;
                case 3:
                    cadastrarFuncionario();
                    break;
                case 4:
                    sistema.listarFuncionarios();
                    break;
                case 5:
                    cadastrarServico();
                    break;
                case 6:
                    sistema.listarServicos();
                    break;
                case 7:
                    criarAgendamento();
                    break;
                case 8:
                    sistema.listarAgendamentos();
                    break;
                case 9:
                    cancelarAgendamento();
                    break;
                case 10:
                    System.out.println("DEBUG: opção 10");
                    exibirResumo();
                    break;
                case 0:
                    System.out.println("\nSistema encerrado.");
                    break;
                default:
                    System.out.println("\nOpção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {

        System.out.println("\n================================");
        System.out.println("     SISTEMA DE GERENCIAMENTO");
        System.out.println("================================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Listar clientes");
        System.out.println("3 - Cadastrar funcionário");
        System.out.println("4 - Listar funcionários");
        System.out.println("5 - Cadastrar serviço");
        System.out.println("6 - Listar serviços");
        System.out.println("7 - Criar agendamento");
        System.out.println("8 - Listar agendamentos");
        System.out.println("9 - Cancelar agendamento");
        System.out.println("10 - Ver resumo do sistema");
        System.out.println("0 - Sair");
        System.out.println("================================");
    }

    private static void cadastrarCliente() {

        System.out.println("\n===== CADASTRO DE CLIENTE =====");

        String nome = lerTexto("Nome: ");
        String telefone = lerTexto("Telefone: ");
        String cpf = lerTexto("CPF: ");

        sistema.cadastrarCliente(nome, telefone, cpf);

        System.out.println("\nCliente cadastrado com sucesso!");
    }

    private static void cadastrarFuncionario() {

        System.out.println("\n===== CADASTRO DE FUNCIONÁRIO =====");

        String nome = lerTexto("Nome: ");
        String telefone = lerTexto("Telefone: ");
        String cpf = lerTexto("CPF: ");
        String cargo = lerTexto("Cargo: ");

        sistema.cadastrarFuncionario(nome, telefone, cpf, cargo);

        System.out.println("\nFuncionário cadastrado com sucesso!");
    }

    private static void cadastrarServico() {

        System.out.println("\n===== CADASTRO DE SERVIÇO =====");

        String nome = lerTexto("Nome do serviço: ");
        double preco = lerDouble("Preço: R$ ");
        int duracao = lerInteiro("Duração em minutos: ");

        if (preco <= 0 || duracao <= 0) {
            System.out.println("\nPreço e duração devem ser maiores que zero.");
            return;
        }

        sistema.cadastrarServico(nome, preco, duracao);

        System.out.println("\nServiço cadastrado com sucesso!");
    }

    private static void criarAgendamento() {

        System.out.println("\n===== NOVO AGENDAMENTO =====");

        if (sistema.quantidadeClientes() == 0
                || sistema.quantidadeFuncionarios() == 0
                || sistema.quantidadeServicos() == 0) {

            System.out.println(
                "É necessário ter ao menos um cliente, " +
                "um funcionário e um serviço cadastrados.");
            return;
        }

        sistema.listarClientes();
        Cliente cliente =
                sistema.buscarCliente(lerInteiro("\nCódigo do cliente: "));

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        sistema.listarFuncionarios();
        Funcionario funcionario =
                sistema.buscarFuncionario(lerInteiro("\nCódigo do funcionário: "));

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        sistema.listarServicos();
        Servico servico =
                sistema.buscarServico(lerInteiro("\nCódigo do serviço: "));

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        LocalDate data = lerData("Data (dd/mm/aaaa): ");
        LocalTime horario = lerHorario("Horário (hh:mm): ");

        Agendamento agendamento =
                sistema.criarAgendamento(cliente, funcionario, servico, data, horario);

        if (agendamento == null) {
            System.out.println(
                "\nERRO: o funcionário ou o cliente já possui " +
                "um agendamento que conflita com esse horário.");
            return;
        }

        System.out.println("\nAgendamento criado com sucesso!");
    }

    private static void cancelarAgendamento() {

        sistema.listarAgendamentos();

        int codigo = lerInteiro("\nCódigo do agendamento: ");

        if (sistema.cancelarAgendamento(codigo)) {
            System.out.println("\nAgendamento cancelado com sucesso!");
        } else {
            System.out.println("\nNão foi possível cancelar o agendamento.");
        }
    }

    private static void exibirResumo() {

        System.out.println("\n===== RESUMO DO SISTEMA =====");
        System.out.println("Clientes: " + sistema.quantidadeClientes());
        System.out.println("Funcionários: " + sistema.quantidadeFuncionarios());
        System.out.println("Serviços: " + sistema.quantidadeServicos());
        System.out.println("Agendamentos: " + sistema.quantidadeAgendamentos());
    }

    // Não aceita texto vazio
    private static String lerTexto(String mensagem) {

        while (true) {

            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este campo não pode ficar vazio.");
        }
    }

    private static LocalDate lerData(String mensagem) {

        while (true) {

            try {
                return LocalDate.parse(lerTexto(mensagem), Agendamento.FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/mm/aaaa.");
            }
        }
    }

    private static LocalTime lerHorario(String mensagem) {

        while (true) {

            try {
                return LocalTime.parse(lerTexto(mensagem), Agendamento.FORMATO_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Horário inválido. Use o formato hh:mm (24h).");
            }
        }
    }

    private static int lerInteiro(String mensagem) {

        while (true) {

            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {

        while (true) {

            try {
                System.out.print(mensagem);
                String valor = scanner.nextLine().trim().replace(",", ".");
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }
}