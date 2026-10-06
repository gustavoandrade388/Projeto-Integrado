import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Agendamento {

    public enum Status { AGENDADO, CANCELADO }

    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm");

    private int codigo;
    private Cliente cliente;
    private Funcionario funcionario;
    private Servico servico;
    private LocalDate data;
    private LocalTime horario;
    private Status status;

    public Agendamento(int codigo, Cliente cliente, Funcionario funcionario,
                       Servico servico, LocalDate data, LocalTime horario) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.servico = servico;
        this.data = data;
        this.horario = horario;
        this.status = Status.AGENDADO;
    }

    public int getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public Funcionario getFuncionario() { return funcionario; }
    public Servico getServico() { return servico; }
    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public Status getStatus() { return status; }

    public boolean estaAtivo() {
        return status == Status.AGENDADO;
    }

    public void cancelar() {
        status = Status.CANCELADO;
    }

    public boolean conflitaCom(LocalDate outraData, LocalTime outroInicio, int outraDuracao) {

        if (!data.equals(outraData)) {
            return false;
        }

        int inicio = horario.toSecondOfDay() / 60;
        int fim = inicio + servico.getDuracaoMinutos();

        int outroIni = outroInicio.toSecondOfDay() / 60;
        int outroFim = outroIni + outraDuracao;

        return inicio < outroFim && outroIni < fim;
    }

    public void exibirDados() {
        System.out.println("\n-----------------------------");
        System.out.println("Agendamento: " + codigo);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Funcionário: " + funcionario.getNome());
        System.out.println("Serviço: " + servico.getNome());
        System.out.println("Preço: R$ " + String.format("%.2f", servico.getPreco()));
        System.out.println("Data: " + data.format(FORMATO_DATA));
        System.out.println("Horário: " + horario.format(FORMATO_HORA));
        System.out.println("Status: " + status);
        System.out.println("-----------------------------");
    }
}