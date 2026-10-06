import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {

    private int codigo;
    private List<Agendamento> agendamentos;

    public Cliente(int codigo, String nome, String telefone, String cpf) {
        super(nome, telefone, cpf);
        this.codigo = codigo;
        this.agendamentos = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    @Override
    public void exibirDados() {
        System.out.println("\n--- CLIENTE ---");
        System.out.println("Código: " + codigo);
        System.out.println("Nome: " + getNome());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("CPF: " + getCpf());
    }
}