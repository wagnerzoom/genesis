package genesis.controle;

import genesis.modelo.Produto;
import genesis.repositorio.ProdutoRepositorio;
import genesis.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLProdutoController implements Initializable {

    @FXML
    private AnchorPane ancoraCampos;

    @FXML
    private TextField textCodigo;

    @FXML
    private TextField TextDescricao;

    @FXML
    private TextField textPreco;

    @FXML
    private TextField textEstoque;

    @FXML
    private TextField textComissao;

    @FXML
    private TableColumn<Produto, Integer> colId;

    @FXML
    private TableColumn<Produto, String> colCodigo;

    @FXML
    private TableColumn<Produto, String> colDescricao;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableColumn<Produto, Double> colEstoque;

    @FXML
    private TableColumn<Produto, Double> colComissao;

    @FXML
    private TableView<Produto> tabelaProduto;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnSair;

    @FXML
    private Label labelCodigo;

    ProdutoRepositorio produtoRepositorio;

    ObservableList<Produto> produtos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.produtoRepositorio = new ProdutoRepositorio();
        this.produtos = FXCollections.observableArrayList();
        inicializarColunas();
        listarProdutos();
        this.tabelaProduto.setItems(produtos);
        this.tabelaProduto.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                carregarParaAlterar();
            }
        });
        adicionarEventosDosBotoes();
        Utilidades.mudarFoco(textCodigo, TextDescricao);
        Utilidades.mudarFoco(TextDescricao, textPreco);
        Utilidades.mudarFoco(textPreco, textEstoque);
        Utilidades.mudarFoco(textEstoque, textComissao);
        Utilidades.formatarMaiusculasETamanho(TextDescricao, 50);
        
        
        textComissao.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                salvar();
            }
        });
    }

    private void adicionarEventosDosBotoes() {
        this.btnSalvar.setOnAction(event -> this.salvar());
        this.btnAlterar.setOnAction(event -> this.alterar());
        this.btnExcluir.setOnAction(event -> this.excluir());
        this.btnSair.setOnAction(event -> this.sair());
    }

    private void inicializarColunas() {
        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        this.colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        this.colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        this.colComissao.setCellValueFactory(new PropertyValueFactory<>("comissao"));
    }

    private void listarProdutos() {
        this.produtos.clear();
        this.produtos.addAll(this.produtoRepositorio.listarTodos());
    }

    private void salvar() {
        Produto produto = new Produto();
        produto.setCodigo(this.textCodigo.getText());
        produto.setDescricao(this.TextDescricao.getText());
        produto.setPreco(converterMoeda(this.textPreco.getText()));
        produto.setEstoque(converterMoeda(this.textEstoque.getText()));
        produto.setComissao(converterMoeda(this.textComissao.getText()));
        Produto produtoSalvo = produtoRepositorio.salvar(produto);
        if (produtoSalvo == null) {
            Utilidades.chamarMensagem("Erro", "Não foi possível salvar.", Alert.AlertType.ERROR);
            return;
        }
        Utilidades.chamarMensagem("Informação", "Produto Salvo com sucesso.", Alert.AlertType.INFORMATION);
        produtos.add(produtoSalvo);
        limparCampos();
    }

    private double converterMoeda(String valor) {
        if (valor.contains(".")) {
            valor = valor.split(Pattern.quote(".")).length > 1 ? valor.replaceFirst(Pattern.quote("."), "") : valor;
            if (valor.contains(".")) {
                return Double.parseDouble(valor);
            }
            return Double.parseDouble(valor.replace(",", "."));
        }
        return Double.parseDouble(valor.replace(".", "").replace(",", "."));
    }

    private void limparCampos() {

        ObservableList<Node> children = this.ancoraCampos.getChildren();
        for (Node node : children) {
            if (!(node instanceof TextField)) {
                continue;
            }
            TextField textField = (TextField) node;
            textField.setText("");

        }
        labelCodigo.setText("Codigo");
    }

    private void alterar() {
        if ("Código".equals(labelCodigo.getText())) {
            Utilidades.chamarMensagem("Aviso", "Selecione um produto para alteração.", Alert.AlertType.WARNING);
            return;
        }
        Produto produto = new Produto();
        produto.setId(Integer.parseInt(labelCodigo.getText()));
        produto.setCodigo(textCodigo.getText());
        produto.setDescricao(TextDescricao.getText());
        produto.setPreco(Double.parseDouble(textPreco.getText()));
        produto.setEstoque(Double.parseDouble(textEstoque.getText()));
        produto.setComissao(Double.parseDouble(textComissao.getText()));
        produtoRepositorio.alterar(produto);
        limparCampos();
        listarProdutos();
        this.textCodigo.requestFocus();
    }

    private void carregarParaAlterar() {
        Produto produtoSelecionado = this.tabelaProduto.getSelectionModel().getSelectedItem();
        this.labelCodigo.setText(String.valueOf(produtoSelecionado.getId()));
        this.textCodigo.setText(produtoSelecionado.getCodigo());
        this.TextDescricao.setText(produtoSelecionado.getDescricao());
        this.textPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
        this.textEstoque.setText(String.valueOf(produtoSelecionado.getEstoque()));
        this.textComissao.setText(String.valueOf(produtoSelecionado.getComissao()));
    }

    private void excluir() {
        Produto selectedItem = this.tabelaProduto.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Utilidades.chamarMensagem("Aviso", "Selecione um produto para apagar.", Alert.AlertType.WARNING);
            return;
        }
        produtoRepositorio.deletar(selectedItem);
        listarProdutos();
        Utilidades.chamarMensagem("Informação", "Produto apagado.", Alert.AlertType.INFORMATION);
    }

    private void sair() {
        ((Stage) btnSair.getScene().getWindow()).close();
        
    }
    
}
