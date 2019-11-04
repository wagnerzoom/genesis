package genesis.repositorio;

import genesis.BancoConexao;
import genesis.modelo.Pessoa;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepositorio implements PadraoRepositorio<Pessoa> {

    private final BancoConexao bancoConexao = new BancoConexao();
    private final List<Pessoa> pessoas = new ArrayList<>();

    @Override
    public Pessoa salvar(Pessoa t) {
        Pessoa pessoa = null;
        if (!bancoConexao.conectarBanco()) {
            return pessoa;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("insert into pessoa (documento,nome,fone) values(?,?,?)");
            preparedStatement.setString(1, t.getDocumento());
            preparedStatement.setString(2, t.getNome());
            preparedStatement.setString(3, t.getFone());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            pessoa = buscarUltimo();
            bancoConexao.desconectaBanco();
            return pessoa;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return pessoa;
        }
    }

    @Override
    public Pessoa deletar(Pessoa t) {
        int codigo = t.getId();
        Pessoa pessoa = buscarPorId(codigo);
        if (pessoa == null) {
            return pessoa;
        }
        if (!bancoConexao.conectarBanco()) {
            return null;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("delete from pessoa where id = ?");
            preparedStatement.setInt(1, codigo);
            preparedStatement.execute();
            preparedStatement.close();
            bancoConexao.desconectaBanco();
            return pessoa;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return null;
        }
    }

    @Override
    public void alterar(Pessoa t) {
        if (!bancoConexao.conectarBanco()) {
            return;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("update pessoa set documento = ?, nome = ?, fone = ? where id = ?");
            preparedStatement.setString(1, t.getDocumento());
            preparedStatement.setString(2, t.getNome());
            preparedStatement.setString(3, t.getFone());
            preparedStatement.setInt(4, t.getId());
            preparedStatement.execute();
            preparedStatement.close();
            bancoConexao.desconectaBanco();
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
        }
    }

    @Override
    public List<Pessoa> pesquisar(String pesquisa) {
        this.pessoas.clear();
        if (!bancoConexao.conectarBanco()) {
            return this.pessoas;
        }
        bancoConexao.execultarSql("select * from pessoa where nome like '%" + pesquisa + "%' or documento='" + pesquisa + "'");
        try {
            bancoConexao.getResultSet().first();
            do {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(bancoConexao.getResultSet().getInt("id"));
                pessoa.setDocumento(bancoConexao.getResultSet().getString("documento"));
                pessoa.setNome(bancoConexao.getResultSet().getString("nome"));
                pessoa.setFone(bancoConexao.getResultSet().getString("fone"));

                this.pessoas.add(pessoa);
                pessoa = null;
            } while (bancoConexao.getResultSet().next());
        } catch (SQLException ex) {
            this.pessoas.clear();

        }
        return this.pessoas;
    }

    @Override
    public List<Pessoa> listarTodos() {
        this.pessoas.clear();
        if (!bancoConexao.conectarBanco()) {
            return this.pessoas;
        }
        bancoConexao.execultarSql("select * from pessoa");
        try {
            bancoConexao.getResultSet().first();
            do {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(bancoConexao.getResultSet().getInt("id"));
                pessoa.setDocumento(bancoConexao.getResultSet().getString("documento"));
                pessoa.setNome(bancoConexao.getResultSet().getString("nome"));
                pessoa.setFone(bancoConexao.getResultSet().getString("fone"));

                this.pessoas.add(pessoa);
                pessoa = null;
            } while (bancoConexao.getResultSet().next());
            bancoConexao.desconectaBanco();

        } catch (SQLException ex) {
            this.pessoas.clear();
        }

        return this.pessoas;
    }

    @Override
    public Pessoa buscarUltimo() {
        bancoConexao.execultarSql("select * from pessoa order by id desc limit 1");
        try {
            bancoConexao.getResultSet().first();
            Pessoa pessoa = new Pessoa();
            pessoa.setId(bancoConexao.getResultSet().getInt("id"));
            pessoa.setDocumento(bancoConexao.getResultSet().getString("documento"));
            pessoa.setNome(bancoConexao.getResultSet().getString("nome"));
            pessoa.setFone(bancoConexao.getResultSet().getString("fone"));
            return pessoa;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Pessoa buscarPorId(Object o) {

        Pessoa pessoa = null;
        if (!bancoConexao.conectarBanco()) {
            return pessoa;
        }
        bancoConexao.execultarSql("select * from pessoa where id = " + o);
        try {
            bancoConexao.getResultSet().first();
            pessoa = new Pessoa();
            pessoa.setId(bancoConexao.getResultSet().getInt("id"));
            pessoa.setDocumento(bancoConexao.getResultSet().getString("documento"));
            pessoa.setNome(bancoConexao.getResultSet().getString("nome"));
            pessoa.setFone(bancoConexao.getResultSet().getString("fone"));
            bancoConexao.desconectaBanco();
            return pessoa;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return pessoa;
        }

    }


}
