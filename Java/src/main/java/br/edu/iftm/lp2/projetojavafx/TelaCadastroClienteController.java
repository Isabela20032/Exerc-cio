package br.edu.iftm.lp2.projetojavafx;

import br.edu.iftm.lp2.projetojavafx.util.Notificacao;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class
TelaCadastroClienteController implements Initializable {

    private List<Cliente> listaCliente = new ArrayList<>();

    @FXML
    private TextField campoCPF;

    @FXML
    private TextField campoEmail;

    @FXML
    private Button botaoSalvar;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEndereco;

    @FXML
    private TableColumn<Cliente, String> TableColumnCPF;

    @FXML
    private TableColumn<Cliente, String> TableColunmEmail;

    @FXML
    private TableColumn<Cliente, String> TableColumnName;

    @FXML
    private TableColumn<Cliente, String> TableColunmEndereco;

    @FXML
    private TableColumn<Cliente, Cliente> TableColumnRemover;

    @FXML
    private TableColumn<Cliente, Cliente> TableColumnEditar;

    @FXML
    private TableView<Cliente> TableCliente;
    private ObservableList<Cliente> obsList;

    //private TextField campoNumero;

    private Cliente cliente;
    @FXML
    void salvarCliente(ActionEvent event) {
        String nome = campoNome.getText();
        String cpf = campoCPF.getText();
        String email = campoEmail.getText();
        String endereco = campoEndereco.getText();

        if(nome.trim().equals("")|| cpf.trim().equals("") ||
        email.trim().equals("") || endereco.trim().equals("")){
            Notificacao.mostraNotificacao("erro","Aadsasd","cadastro possui varios campos", Alert.AlertType.ERROR);
        }else {
            if (cliente == null) {
                cliente = new Cliente(nome, cpf, email, endereco);
                listaCliente.add(cliente);
            } else {
                int posicao = listaCliente.indexOf(cliente);
                cliente = new Cliente(nome, cpf, email, endereco);
                listaCliente.set(posicao, cliente);
            }
        }

        limpaFormulario();

        AtualizaTela();

        for (Cliente cliente: listaCliente) {
            System.out.println(cliente);
        }
    }

    private void limpaFormulario(){
        campoNome.clear();
        campoCPF.clear();
        campoEmail.clear();
        campoEndereco.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaTela();
    }

    public void inicializaTela(){
        TableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        TableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        AtualizaTela();
    }


    public void AtualizaTela(){
        obsList = FXCollections.observableList(listaCliente);
        TableCliente.setItems(obsList);
        iniciarBotao();
    }


    private void iniciarBotao(){
        TableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        TableColumnEditar.setCellFactory(param -> new TableCell<Cliente, Cliente>(){
            private final Button botao = new Button("Editar");

            @Override
            protected void updateItem(Cliente obj, boolean vazio){
                super.updateItem(obj,vazio);
                if(obj == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(botao);
                botao.setOnAction(evente -> editarCliente(obj));
            }
        });
    }

    private void iniciaizaBotaoRemover(){
        TableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        TableColumnRemover.setCellFactory(param -> new TableCell<Cliente,Cliente>());
        
    }

    private void editarCliente(Cliente obj) {
        campoNome.setText(obj.getNome());
        campoCPF.setText(obj.getCpf());
        campoEmail.setText(obj.getEmail());
        campoEndereco.setText(obj.getEndereco());
        cliente = obj;
    }
}

