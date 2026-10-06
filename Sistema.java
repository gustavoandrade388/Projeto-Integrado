import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class Sistema {

    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<Servico> servicos;
    private List<Agendamento> agendamentos;

    private int proximoCliente = 1;
    private int proximoFuncionario = 1;
    private int proximoServico = 1;
    private int proximoAgendamento = 1;

    public Sistema() {
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
        servicos = new ArrayList<>();
        agendamentos = new ArrayList<>();
    }

    // ---------- Clientes ----------

    public Cliente cadastrarCliente(String nome, String telefone, String cpf) {

        Cliente cliente = new Cliente(proximoCliente, nome, telefone, cpf);

        clientes.add(cliente);
        proximoCliente++;

        return cliente;
    }

    public void listarClientes() {

        if (clientes.isEmpty()) {
            System.out.println("\nNenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n===== CLIENTES =====");

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }

    public Cliente buscarCliente(int codigo) {

        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }

        return null;
    }

    // ---------- Funcionários ----------

    public Funcionario cadastrarFuncionario(
            String nome, String telefone, String cpf, String cargo) {

        Funcionario funcionario = new Funcionario(
                proximoFuncionario, nome, telefone, cpf, cargo);

        funcionarios.add(funcionario);
        proximoFuncionario++;

        return funcionario;
    }

    public void listarFuncionarios() {

        if (funcionarios.isEmpty()) {
            System.out.println("\nNenhum funcionário cadastrado.");
            return;
        }

        System.out.println("\n===== FUNCIONÁRIOS =====");

        for (Funcionario funcionario : funcionarios) {
            funcionario.exibirDados();
        }
    }

    public Funcionario buscarFuncionario(int codigo) {

        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getCodigo() == codigo) {
                return funcionario;
            }
        }

        return null;
    }

    // ---------- Serviços ----------

    public Servico cadastrarServico(String nome, double preco, int duracao) {

        Servico servico = new Servico(proximoServico, nome, preco, duracao);

        servicos.add(servico);
        proximoServico++;

        return servico;
    }

    public void listarServicos() {

        if (servicos.isEmpty()) {
            System.out.println("\nNenhum serviço cadastrado.");
            return;
        }

        System.out.println("\n===== SERVIÇOS =====");

        for (Servico servico : servicos) {
            servico.exibirDados();
        }
    }

    public Servico buscarServico(int codigo) {

        for (Servico servico : servicos) {
            if (servico.getCodigo() == codigo) {
                return servico;
            }
        }

        return null;
    }

    // ---------- Agendamentos ----------

    public boolean horarioDisponivel(
            Cliente cliente,
            Funcionario funcionario,
            Servico servico,
            LocalDate data,
            LocalTime horario) {

        for (Agendamento agendamento : agendamentos) {

            if (!agendamento.estaAtivo()) {
                continue;
            }

            boolean mesmoFuncionario =
                    agendamento.getFuncionario().getCodigo() == funcionario.getCodigo();

            boolean mesmoCliente =
                    agendamento.getCliente().getCodigo() == cliente.getCodigo();

            if ((mesmoFuncionario || mesmoCliente)
                    && agendamento.conflitaCom(data, horario, servico.getDuracaoMinutos())) {
                return false;
            }
        }

        return true;
    }

    public Agendamento criarAgendamento(
            Cliente cliente,
            Funcionario funcionario,
            Servico servico,
            LocalDate data,
            LocalTime horario) {

        if (!horarioDisponivel(cliente, funcionario, servico, data, horario)) {
            return null;
        }

        Agendamento agendamento = new Agendamento(
                proximoAgendamento,
                cliente,
                funcionario,
                servico,
                data,
                horario
        );

        agendamentos.add(agendamento);
        cliente.adicionarAgendamento(agendamento);
        proximoAgendamento++;

        return agendamento;
    }

    public void listarAgendamentos() {

        if (agendamentos.isEmpty()) {
            System.out.println("\nNenhum agendamento cadastrado.");
            return;
        }

        System.out.println("\n===== AGENDAMENTOS =====");

        for (Agendamento agendamento : agendamentos) {
            agendamento.exibirDados();
        }
    }

    public boolean cancelarAgendamento(int codigo) {

        for (Agendamento agendamento : agendamentos) {

            if (agendamento.getCodigo() == codigo) {

                if (!agendamento.estaAtivo()) {
                    return false;
                }

                agendamento.cancelar();
                return true;
            }
        }

        return false;
    }

    // ---------- Resumo ----------

    public int quantidadeClientes() {
        return clientes.size();
    }

    public int quantidadeFuncionarios() {
        return funcionarios.size();
    }

    public int quantidadeServicos() {
        return servicos.size();
    }

    public int quantidadeAgendamentos() {
        return agendamentos.size();
    }
}