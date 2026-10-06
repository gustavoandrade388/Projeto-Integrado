public class Funcionario extends Pessoa {

    private int codigo;
    private String cargo;

    public Funcionario(int codigo, String nome, String telefone,
                       String cpf, String cargo) {

        super(nome, telefone, cpf);

        this.codigo = codigo;
        this.cargo = cargo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public void exibirDados() {
        System.out.println("\n--- FUNCIONÁRIO ---");
        System.out.println("Código: " + codigo);
        System.out.println("Nome: " + getNome());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("CPF: " + getCpf());
        System.out.println("Cargo: " + cargo);
    }
}