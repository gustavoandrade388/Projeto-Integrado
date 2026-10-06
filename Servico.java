public class Servico {

    private int codigo;
    private String nome;
    private double preco;
    private int duracaoMinutos;

    public Servico(int codigo, String nome, double preco, int duracaoMinutos) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.duracaoMinutos = duracaoMinutos;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void exibirDados() {
        System.out.println(
            "Código: " + codigo +
            " | Serviço: " + nome +
            " | Preço: R$ " + String.format("%.2f", preco) +
            " | Duração: " + duracaoMinutos + " minutos"
        );
    }
}