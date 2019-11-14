package genesis.modelo;

public class Pedido {
    private int id;
    private int codigoPessoa;
    private int codigoProduto;
    private double quantidade;
    private double preco;

    public Pedido() {
   
    
    }

    public Pedido(int id, int codigoPessoa, int codigoProduto, double quantidade, double preco) {
        this.id = id;
        this.codigoPessoa = codigoPessoa;
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Pedido(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(int codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedido other = (Pedido) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", codigoPessoa=" + codigoPessoa + ", codigoProduto=" + codigoProduto + ", quantidade=" + quantidade + ", preco=" + preco + '}';
    }

   
    
    
}
