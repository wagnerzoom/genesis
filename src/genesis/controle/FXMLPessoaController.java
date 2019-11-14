package genesis.controle;

import genesis.modelo.Pessoa;
import genesis.repositorio.PessoaRepositorio;
import genesis.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FXMLPessoaController implements Initializable {

    @FXML
    private TableView<Pessoa> tabelaDados;

    @FXML
    private TableColumn<Pessoa, Integer> colId;

    @FXML
    private TableColumn<Pessoa, String> colDocumento;

    @FXML
    private TableColumn<Pessoa, String> colNome;

    @FXML
    private TableColumn<Pessoa, String> colFone;

    @FXML
    private TextField textDodumento;

    @FXML
    private Label labelCodigo;

    @FXML
    private TextField textNome;

    @FXML
    private TextField textFone;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnSair;

    @FXML
    private TextField textPesquisa;

    PessoaRepositorio pessoaRepositorio;

    ObservableList<Pessoa> pessoas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pessoaRepositorio = new PessoaRepositorio();
        this.pessoas = FXCollections.observableArrayList();
        inicializarColunas();
        listarPessoas();
        this.tabelaDados.setItems(pessoas);
        this.btnSalvar.setOnAction(evento -> this.salvar());
        this.btnExcluir.setOnAction(evento -> this.excluir());
        this.btnSair.setOnAction(evento -> this.sair());
        this.btnAlterar.setOnAction(evento -> this.alterar());
        this.textPesquisa.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                this.pesquisarPessoas(textPesquisa.getText());
            }
        });
        this.tabelaDados.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                this.carregarParaAlterar();
            }
        });
        this.textFone.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.salvar();
            }
        });

        Utilidades.formatarMaiusculasETamanho(textDodumento, 14);
        Utilidades.formatarMaiusculasETamanho(textNome, 100);
        Utilidades.formatarMaiusculasETamanho(textFone, 11);
        Utilidades.mudarFoco(textDodumento, textNome);
        Utilidades.mudarFoco(textNome, textFone);
    }

    private void inicializarColunas() {
        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        this.colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
    }

    private void listarPessoas() {
        this.pessoas.clear();
        this.pessoas.addAll(this.pessoaRepositorio.listarTodos());
    }

    private void salvar() {
        if (textNome.getText().trim().isEmpty()) {
            chamarMensagem("Aviso", "Campo Nome inválido.", Alert.AlertType.WARNING);
            textNome.requestFocus();
            return;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setDocumento(textDodumento.getText());
        pessoa.setNome(textNome.getText());
        pessoa.setFone(textFone.getText());
        Pessoa pessoaSalva = this.pessoaRepositorio.salvar(pessoa);
        if (pessoaSalva != null) {
            this.pessoas.add(pessoaSalva);
            chamarMensagem("Informação", "Pessoa salva com sucesso.", Alert.AlertType.INFORMATION);
            this.tabelaDados.scrollTo(pessoas.size());
            limparCampos();
            textDodumento.requestFocus();
            return;
        }
        chamarMensagem("Erro", "Erro ao salvar pessoa.", Alert.AlertType.ERROR);
    }

    private void chamarMensagem(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Mensagem");
        alert.setContentText(mensagem);
        alert.setHeaderText(titulo);
        alert.show();
    }

    private void excluir() {
        Pessoa pessoaSelecionada = this.tabelaDados.getSelectionModel().getSelectedItem();
        if (this.verificarPessoaNaoSelecionada(pessoaSelecionada)) {
            return;
        }
        Pessoa pessoaDeletada = pessoaRepositorio.deletar(pessoaSelecionada);
        if (pessoaDeletada == null) {
            chamarMensagem("Erro", " Não foi possível excluir ", Alert.AlertType.INFORMATION);
            return;
        }
        chamarMensagem("Informação", pessoaDeletada.getNome() + " Excluido(a) com sucesso.", Alert.AlertType.INFORMATION);
        this.listarPessoas();
    }

    private void alterar() {
        Pessoa pessoaSelecionada = this.tabelaDados.getSelectionModel().getSelectedItem();
        if (this.verificarPessoaNaoSelecionada(pessoaSelecionada)) {
            return;
        }
        pessoaSelecionada.setDocumento(this.textDodumento.getText());
        pessoaSelecionada.setNome(this.textNome.getText());
        pessoaSelecionada.setFone(this.textFone.getText());
        pessoaRepositorio.alterar(pessoaSelecionada);
        listarPessoas();
        limparCampos();
    }

    private void sair() {
        ((Stage) this.btnSair.getScene().getWindow()).close();
    }

    private boolean verificarPessoaNaoSelecionada(Pessoa pessoa) {
        if (pessoa == null) {
            chamarMensagem("Aviso", "Selecione uma pessoa.", Alert.AlertType.WARNING);
            return true;
        }
        return false;
    }

    private void limparCampos() {
        textDodumento.setText("");
        textNome.setText("");
        textFone.setText("");
        labelCodigo.setText("Codigo");
    }

    private void carregarParaAlterar() {
        Pessoa pessoaSelecionada = this.tabelaDados.getSelectionModel().getSelectedItem();
        textDodumento.setText(pessoaSelecionada.getDocumento());
        textNome.setText(pessoaSelecionada.getNome());
        textFone.setText(pessoaSelecionada.getFone());
        labelCodigo.setText(String.valueOf(pessoaSelecionada.getId()));
        textDodumento.requestFocus();
    }

    private void pesquisarPessoas(String valor){
        this.pessoas.clear();
        this.pessoas.addAll(pessoaRepositorio.pesquisar(valor));                
    }
}
