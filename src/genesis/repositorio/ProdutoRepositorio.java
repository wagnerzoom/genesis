package genesis.repositorio;

import genesis.BancoConexao;
import genesis.modelo.Produto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositorio implements PadraoRepositorio<Produto> {

    private BancoConexao bancoConexao = new BancoConexao();

    @Override
    public Produto salvar(Produto t) {
        Produto produto = null;
        if (!bancoConexao.conectarBanco()) {
            return produto;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("insert into produto (codigo, descricao, preco, estoque, comissao) values (?,?,?,?,?)");
            preparedStatement.setString(1, t.getCodigo());
            preparedStatement.setString(2, t.getDescricao());
            preparedStatement.setDouble(3, t.getPreco());
            preparedStatement.setDouble(4, t.getEstoque());
            preparedStatement.setDouble(5, t.getComissao());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            produto = buscarUltimo();
            bancoConexao.desconectaBanco();
            return produto;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Produto deletar(Produto t) {
        Produto produto = null;
        if (!bancoConexao.conectarBanco()) {
            return produto;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("delete from produto where id=?");
            preparedStatement.setInt(1, t.getId());
            preparedStatement.execute();
            preparedStatement.close();
            bancoConexao.desconectaBanco();
            return t;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return produto;
        }
    }

    @Override
    public void alterar(Produto t) {

        if (!bancoConexao.conectarBanco()) {
            return;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("update produto set codigo = ?, descricao= ?, preco= ?, estoque= ?, comissao= ? where id=?");
            preparedStatement.setString(1, t.getCodigo());
            preparedStatement.setString(2, t.getDescricao());
            preparedStatement.setDouble(3, t.getPreco());
            preparedStatement.setDouble(4, t.getEstoque());
            preparedStatement.setDouble(5, t.getComissao());
            preparedStatement.setInt(6, t.getId());
            preparedStatement.execute();
            preparedStatement.close();
            bancoConexao.desconectaBanco();
        } catch (SQLException ex) {
              bancoConexao.desconectaBanco();
        }

    }

    @Override
    public Produto buscarUltimo() {
        bancoConexao.execultarSql("select * from produto order by id desc limit 1");
        try {
            bancoConexao.getResultSet().first();
            Produto produto = this.preencherProduto();
            return produto;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Produto buscarPorId(Object o) {
        Produto produto = null;
        if (!bancoConexao.conectarBanco()) {
            return produto;
        }
        bancoConexao.execultarSql("select * from produto where id=" + o);
        try {
            bancoConexao.getResultSet().first();
            produto = this.preencherProduto();
            bancoConexao.desconectaBanco();
            return produto;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return null;
        }

    }

    @Override
    public List<Produto> pesquisar(String pesquisa) {
        return this.pesquisarConteudo("select * from produto where upper(descricao) like'%" + pesquisa + "%' order by descricao");
    }

    @Override
    public List<Produto> listarTodos() {
        return this.pesquisarConteudo("select * from produto order by descricao");
    }

    private List<Produto> pesquisarConteudo(String sql) {
        List<Produto> produtos = new ArrayList<>();
        if (!bancoConexao.conectarBanco()) {
            return produtos;
        }
        bancoConexao.execultarSql(sql);
        try {
            bancoConexao.getResultSet().first();
            do {
                produtos.add(this.preencherProduto());
            } while (bancoConexao.getResultSet().next());
            bancoConexao.desconectaBanco();
            return produtos;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            produtos.clear();
            return produtos;
        }
    }

    private Produto preencherProduto() throws SQLException {
        Produto produto = new Produto();
        produto.setId(bancoConexao.getResultSet().getInt("id"));
        produto.setCodigo(bancoConexao.getResultSet().getString("codigo"));
        produto.setDescricao(bancoConexao.getResultSet().getString("descricao"));
        produto.setPreco(bancoConexao.getResultSet().getDouble("preco"));
        produto.setEstoque(bancoConexao.getResultSet().getDouble("estoque"));
        produto.setComissao(bancoConexao.getResultSet().getDouble("comissao"));
        return produto;
    }

}
