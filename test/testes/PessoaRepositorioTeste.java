package testes;

import genesis.modelo.Pessoa;
import genesis.repositorio.PessoaRepositorio;
import java.util.List;

public class PessoaRepositorioTeste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PessoaRepositorio pessoaRepositorio = new PessoaRepositorio();
        alterar(pessoaRepositorio);
    }

    private static void excluir(PessoaRepositorio pessoaRepositorio) {
        Pessoa pessoa = pessoaRepositorio.buscarPorId(1);
        pessoaRepositorio.deletar(pessoa);
    }

    private static void alterar(PessoaRepositorio pessoaRepositorio) {
        Pessoa pessoa = pessoaRepositorio.buscarPorId(10);
        if (pessoa != null) {
            pessoa.setId(2);
            pessoa.setNome("Eudes Batista");
            pessoaRepositorio.alterar(pessoa);
        }
    }

    private static void salvar(PessoaRepositorio pessoaRepositorio) {
        Pessoa pessoa = new Pessoa();
        pessoa.setDocumento("101010");
        pessoa.setNome("Tonho");
        pessoa.setFone("333333");
        Pessoa salvar = pessoaRepositorio.salvar(pessoa);
        System.out.println("salvar = " + salvar);
    }

    private static void listarTodos(PessoaRepositorio pessoaRepositorio) {
        List<Pessoa> listarTodos = pessoaRepositorio.listarTodos();
        for (Pessoa pessoa : listarTodos) {
            System.out.println("Nome = " + pessoa.getNome());
        }
    }

    private static void pesquisar(PessoaRepositorio pessoaRepositorio) {
        List<Pessoa> listarTodos = pessoaRepositorio.pesquisar("ray");
        for (Pessoa pessoa : listarTodos) {
            System.out.println("Nome = " + pessoa.getNome());
        }
    }

}
