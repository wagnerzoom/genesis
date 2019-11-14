package testes;

import genesis.modelo.Produto;
import genesis.repositorio.ProdutoRepositorio;

public class ProdutoRepositorioTeste {

    public static void main(String[] args) {

        ProdutoRepositorio produtoRepositorio = new ProdutoRepositorio();
        Produto produto = new Produto();
        
        produto.setCodigo("78910");
        produto.setDescricao("BOTOX 500G");
        produto.setPreco(140.00);
        produto.setEstoque(10.00);
        produto.setComissao(2.00);
        
        Produto produtoSalvo = produtoRepositorio.salvar(produto);
        
        System.out.println("produtoSalvo = " + produtoSalvo);
    }
    
}
