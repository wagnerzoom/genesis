package genesis.repositorio;

import java.util.List;

public interface PadraoRepositorio<T> {
   public T salvar(T t);    
   public T deletar(T t);    
   public void alterar(T t);    
   public T buscarUltimo();    
   public T buscarPorId(Object o);    
   public List<T> pesquisar(String pesquisa);    
   public List<T> listarTodos();    
}
