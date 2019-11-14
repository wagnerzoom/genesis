package genesis.repositorio;

import genesis.BancoConexao;
import genesis.modelo.Pedido;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoRepositorio implements PadraoRepositorio<Pedido> {
    
    BancoConexao bancoConexao = new BancoConexao();
    
    @Override
    public Pedido salvar(Pedido pedido) {
        if (!bancoConexao.conectarBanco()) {
            return null;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("insert into pedido (codigo_pessoa, codigo_produto, quantidade, preco) values(?,?,?,?)");
            preparedStatement.setInt(1, pedido.getCodigoPessoa());
            preparedStatement.setInt(2, pedido.getCodigoProduto());
            preparedStatement.setDouble(3, pedido.getQuantidade());
            preparedStatement.setDouble(3, pedido.getPreco());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Pedido pedidoSalvo = buscarUltimo();
            bancoConexao.desconectaBanco();
            return pedidoSalvo;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return null;
        }
    }
    
    @Override
    public Pedido deletar(Pedido pedido) {
        if (!bancoConexao.conectarBanco()) {
            return null;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("delete from pedido where id= ?");
            preparedStatement.execute();
            preparedStatement.close();
            bancoConexao.desconectaBanco();
            return pedido;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return null;
        }
        
    }
    
    @Override
    public void alterar(Pedido pedido) {
        if (!bancoConexao.conectarBanco()) {
            return;
        }
        try {
            PreparedStatement preparedStatement = bancoConexao.getConnection().prepareStatement("update pedido set codigo_pessoa=?, codigo_produto=?, quantidade=?, preco=? where id= ?");
            preparedStatement.setInt(1, pedido.getCodigoPessoa());
            preparedStatement.setInt(2, pedido.getCodigoProduto());
            preparedStatement.setDouble(3, pedido.getQuantidade());
            preparedStatement.setDouble(4, pedido.getPreco());
            preparedStatement.setInt(5, pedido.getId());
            preparedStatement.execute();
            preparedStatement.close();
            bancoConexao.desconectaBanco();
        } catch (SQLException ex) {            
            bancoConexao.desconectaBanco();
        }
    }
    
    @Override
    public Pedido buscarUltimo() {
        bancoConexao.execultarSql("select * from pedido order by id desc limit 1");
        try {
            bancoConexao.getResultSet().first();
            return preencherPedido();
            
        } catch (SQLException ex) {
            return null;
        }
    }
    
    @Override
    public Pedido buscarPorId(Object o) {
        if (!bancoConexao.conectarBanco()) {
            return null;
        }
        bancoConexao.execultarSql("select * from pedido where id=" + o);
        try {
            bancoConexao.getResultSet().first();
            Pedido pedido = this.preencherPedido();
            bancoConexao.desconectaBanco();
            return pedido;
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            return null;
        }
        
    }
    
    @Override
    public List<Pedido> pesquisar(String pesquisa) {
        return this.preencherListaDePedidos("select * from pedido inner join pessoa on(id = codigo_pessoa) where nome like '%" + pesquisa + "%' oder by nome");
    }
    
    @Override
    public List<Pedido> listarTodos() {
        return this.preencherListaDePedidos("select * from pedido");
    }
    
    public List<Pedido> preencherListaDePedidos(String sql) {
        List<Pedido> pedidos = new ArrayList<>();
        if (!bancoConexao.conectarBanco()) {
            return pedidos;
        }
        bancoConexao.execultarSql(sql);
        try {
            bancoConexao.getResultSet().first();
            do {
                Pedido pedido = this.preencherPedido();
                pedidos.add(pedido);
            } while (bancoConexao.getResultSet().next());
            bancoConexao.desconectaBanco();
        } catch (SQLException ex) {
            bancoConexao.desconectaBanco();
            pedidos.clear();
        }
        return pedidos;
    }
    
    private Pedido preencherPedido() throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(bancoConexao.getResultSet().getInt("id"));
        pedido.setCodigoPessoa(bancoConexao.getResultSet().getInt("codigo_pessoa"));
        pedido.setCodigoProduto(bancoConexao.getResultSet().getInt("codigo_produto"));
        pedido.setQuantidade(bancoConexao.getResultSet().getDouble("quantidae"));
        pedido.setPreco(bancoConexao.getResultSet().getDouble("preco"));
        return pedido;
    }
}
