/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.cliente.ClienteController;
import controller.pedido.PedidosClienteController;
import controller.pedido.TodosPedidosController;
import controller.preco.PrecoController;
import controller.sabor.SaborController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import model.cliente.Cliente;
import model.cliente.dao.ClienteDao;
import database.ConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FormularioPizza.FormularioPizza;
import model.forma.CategoriaPizza;
import model.forma.Circunferencia;
import model.forma.Forma;
import model.forma.Quadrado;
import model.forma.Triangulo;
import model.pedido.Pedido;
import model.pedido.StatusPedido;
import model.pedido.dao.TodosPedidosDao;
import model.pizza.Pizza;
import model.preco.Preco;
import model.preco.dao.PrecoDao;
import model.sabor.Sabor;
import model.sabor.dao.SaborDao;
import repositorio.RepositorioDados;

//alterar valor mockado unitario para multiplicacao com a area total
/**
 *
 * @author alan
 */
public class Pizzaria extends javax.swing.JFrame {

    String regexTelefone = "^[0-9]{10,11}$";

    private ClienteTableModel clienteTableModel = new ClienteTableModel();

    private PrecoTableModel precoTableModel = new PrecoTableModel();

    private SaborTableModel saborTableModel = new SaborTableModel();

    private PizzasPedidoTableModel listaPizzas = new PizzasPedidoTableModel();

    private PedidosClienteTableModel pedidosClienteTableModel = new PedidosClienteTableModel();

    private TodosPedidosTableModel todosPedidosTableModel = new TodosPedidosTableModel();

    private double totalPedido;

    private Forma forma;
    private Pizza preco;

    private static int contador = 0;

    public static int gerarID() {
        return ++contador;
    }

    private Cliente editarCliente;
    private Pedido editarPedido;

    private int linhaSelecionadaEditarCliente;
    private int atualizarLinhaSelecionada = -1;
    private int linhaSelecionadaEditarPedido;

    private static Pizza[] pizzas = new Pizza[3];

    private void setCliente(Cliente c) {
        this.nome.setText(c.getNome());
        this.sobrenome.setText(c.getSobrenome());
        this.telefone.setText(c.getTelefone());
    }

    private void setPedido(Pedido p) {
        this.filtrarPedido.setText(String.valueOf(p.getNumeroPedido()));
        this.opcoesStatus.setSelectedItem(p.getStatus());
        
    }
    

    private List<Pizza> getPizzasParaExcluirDaTabela() {
        int[] linhasSelecionadas = this.listaPizzasPedido.getSelectedRows();
        List<Pizza> listaExcluir = new ArrayList();
        for (int i = 0; i < linhasSelecionadas.length; i++) {
            Pizza pizza = listaPizzas.getPizza(linhasSelecionadas[i]);
            listaExcluir.add(pizza);
        }
        return listaExcluir;
    }

    /**
     * Creates new form Pizzaria
     */
    public Pizzaria() throws SQLException {
        initComponents();

//        areaPizzaInformada.setEnabled(false);

//        Sabor sabor1 = new Sabor("Frango ", "Simples");
//        Sabor sabor2 = new Sabor("4 Queijos", "Especial");
//        Sabor sabor3 = new Sabor("Strogonoff", "Premium");
//        saborTableModel.adicionaSabor(sabor1);
//        saborTableModel.adicionaSabor(sabor2);
//        saborTableModel.adicionaSabor(sabor3);

//        RepositorioDados.listaSabores.add(sabor1);
//        RepositorioDados.listaSabores.add(sabor2);
//        RepositorioDados.listaSabores.add(sabor3);
//        Preco preco1 = new Preco(10.0, "Simples");
//        Preco preco2 = new Preco(20.0, "Especial");
//        Preco preco3 = new Preco(30.0, "Premium");
//        
//        
//        pizzas[0] = new Pizza("Simples", 10.0);
//        pizzas[1] = new Pizza("Especial", 20.0);
//        pizzas[2] = new Pizza("Premium", 30.0);
//        
//
//        RepositorioDados.listaPrecos.add(pizzas[0]);
//        RepositorioDados.listaPrecos.add(pizzas[1]);
//        RepositorioDados.listaPrecos.add(pizzas[2]);
//        precoTableModel.populaTabela();

        ClienteDao modelClienteDao = new ClienteDao(new ConnectionFactory());
        this.clienteTableModel.setListaCliente(modelClienteDao.getLista());

        SaborDao modelSaborDao = new SaborDao(new ConnectionFactory());
        this.saborTableModel.setListaSabor(modelSaborDao.getSabores());

        PrecoDao modelPrecoDao = new PrecoDao(new ConnectionFactory());
        this.precoTableModel.setListaPreco(modelPrecoDao.getPrecos());
        
        TodosPedidosDao modelTodosPedidosDao = new TodosPedidosDao(new ConnectionFactory());
        this.todosPedidosTableModel.setListaTodosPedidos(modelTodosPedidosDao.getListaPedidos());
        


        jtClientes.setModel(clienteTableModel);
        jTabelaPrecos.setModel(precoTableModel);
        jTabelaSabores.setModel(saborTableModel);
        listaPizzasPedido.setModel(listaPizzas);
        jtTodosPedidos.setModel(todosPedidosTableModel);
        listaPizzasPedido.setModel(listaPizzas);
        jtPizzasPedidoAndamento.setModel(pedidosClienteTableModel);

//        String[] nomesPizzas = Arrays.stream(pizzas)
//                                     .map(Pizza::getCategoria)
//                                     .toArray(String[]::new);
//        
//        tipo.setModel(new DefaultComboBoxModel<>(nomesPizzas));
        this.categoriaPizza.setModel(new DefaultComboBoxModel<>(CategoriaPizza.values()));
        this.tipoPizza.setModel(new DefaultComboBoxModel<>(CategoriaPizza.values()));

        String[] saboresCadastrados = new String[RepositorioDados.listaSabores.size()];
        String[] statusPedido = new String[]{"Aberto", "A caminho", "Entregue"};

//        for (int i = 0; i < RepositorioDados.listaSabores.size(); i++) {
//            Sabor saborCadastrado = RepositorioDados.listaSabores.get(i);
//            saboresCadastrados[i] = saborCadastrado.getSabor();
//        }

        List<Sabor> sabores = modelSaborDao.getSabores();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (Sabor sabor : sabores) {
            comboBoxModel.addElement(sabor.getSabor());
        }
        
        this.sabor1.setModel(comboBoxModel);
        
        
//        List<Sabor> sabores1 = modelSaborDao.getSabores(); 
//        DefaultComboBoxModel<String> comboBoxModel1 = new DefaultComboBoxModel<>();
//        this.sabor1.setModel(comboBoxModel1);

        String saborSelecionado = sabor1.getSelectedItem().toString();
//
        List<Sabor> sabores2 = modelSaborDao.getSabores(); 
//        if (saborSelecionado != null) {
//            sabores2.remove(saborSelecionado); 
//        }
        
        
        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();
        for (Sabor sabor : sabores2) {
            comboBoxModel2.addElement(sabor.getSabor());
        }
        
        this.sabor2.setModel(comboBoxModel2);

        this.opcoesStatus.setModel(new DefaultComboBoxModel<>(StatusPedido.values()));

        this.mensagemErroTelefone.setVisible(false);

        this.opcaoArea.setEnabled(false);
        this.opcaoFormato.setEnabled(false);
        this.opcaoCircular.setEnabled(false);
        this.opcaoQuadrado.setEnabled(false);
        this.opcaoTriangular.setEnabled(false);
        this.calcularArea.setEnabled(false);
        this.ladoOuRaio.setEnabled(false);
        this.umSabor.setEnabled(false);
        this.doisSabores.setEnabled(false);
        this.adicionarPizza.setEnabled(false);
        this.removerPizza.setEnabled(false);
        this.sabor1.setEnabled(false);
        this.sabor2.setEnabled(false);
        this.realizarPedido.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        informacaoPizza = new javax.swing.ButtonGroup();
        formaPizza = new javax.swing.ButtonGroup();
        qtSabores = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        SistemaPizzaria = new javax.swing.JTabbedPane();
        Cadastro = new javax.swing.JPanel();
        nomeLabel = new javax.swing.JLabel();
        sobrenomeLabel = new javax.swing.JLabel();
        telefoneLabel = new javax.swing.JLabel();
        cadastrar = new javax.swing.JButton();
        nome = new javax.swing.JTextField();
        sobrenome = new javax.swing.JTextField();
        telefone = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtClientes = new javax.swing.JTable();
        editar = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        mensagemErroTelefone = new javax.swing.JLabel();
        filtrarCliente = new javax.swing.JButton();
        mostrarTodosClientes = new javax.swing.JButton();
        RealizarPedido = new javax.swing.JPanel();
        procurarClienteLabel = new javax.swing.JLabel();
        telefoneCliente = new javax.swing.JTextField();
        procurarCliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPizzasPedidoAndamento = new javax.swing.JTable();
        opcaoArea = new javax.swing.JRadioButton();
        opcaoFormato = new javax.swing.JRadioButton();
        areaPizzaInformada = new javax.swing.JTextField();
        opcaoQuadrado = new javax.swing.JRadioButton();
        opcaoTriangular = new javax.swing.JRadioButton();
        opcaoCircular = new javax.swing.JRadioButton();
        ladoOuRaio = new javax.swing.JTextField();
        umSabor = new javax.swing.JRadioButton();
        doisSabores = new javax.swing.JRadioButton();
        sabor1 = new javax.swing.JComboBox<>();
        sabor2 = new javax.swing.JComboBox<>();
        adicionarPizza = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaPizzasPedido = new javax.swing.JTable();
        valorTotal = new javax.swing.JLabel();
        realizarPedido = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ladoOuRaioLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        calcularArea = new javax.swing.JButton();
        clienteFiltrado = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        removerPizza = new javax.swing.JButton();
        validarArea = new javax.swing.JButton();
        Precos = new javax.swing.JPanel();
        tipoPizza = new javax.swing.JComboBox<>();
        valorPizza = new javax.swing.JTextField();
        atualizarValor = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabelaPrecos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Sabores = new javax.swing.JPanel();
        nomeSabor = new javax.swing.JTextField();
        categoriaPizza = new javax.swing.JComboBox<>();
        criarSabor = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTabelaSabores = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Pedidos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtTodosPedidos = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        filtrarPedido = new javax.swing.JTextField();
        buscarPedido = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        opcoesStatus = new javax.swing.JComboBox<>();
        atualizarStatus = new javax.swing.JButton();
        mostrarTodosPedidos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nomeLabel.setText("Nome");

        sobrenomeLabel.setText("Sobrenome");

        telefoneLabel.setText("Telefone");

        cadastrar.setText("Cadastrar");
        cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarActionPerformed(evt);
            }
        });

        telefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefoneActionPerformed(evt);
            }
        });

        jtClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "Sobrenome", "Telefone"
            }
        ));
        jtClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClientesMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jtClientes);

        editar.setText("Editar");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        excluir.setText("Excluir");
        excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        mensagemErroTelefone.setForeground(new java.awt.Color(255, 51, 51));
        mensagemErroTelefone.setText("Telefone já cadastrado.");

        filtrarCliente.setText("Filtrar");
        filtrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarClienteActionPerformed(evt);
            }
        });

        mostrarTodosClientes.setText("Mostrar Todos");
        mostrarTodosClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarTodosClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastroLayout = new javax.swing.GroupLayout(Cadastro);
        Cadastro.setLayout(CadastroLayout);
        CadastroLayout.setHorizontalGroup(
            CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastroLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                    .addGroup(CadastroLayout.createSequentialGroup()
                        .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomeLabel)
                            .addComponent(sobrenomeLabel)
                            .addComponent(telefoneLabel))
                        .addGap(63, 63, 63)
                        .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mensagemErroTelefone)
                            .addGroup(CadastroLayout.createSequentialGroup()
                                .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(CadastroLayout.createSequentialGroup()
                                        .addComponent(cadastrar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editar))
                                    .addComponent(nome)
                                    .addComponent(sobrenome)
                                    .addComponent(telefone, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                                .addGap(64, 64, 64)
                                .addComponent(excluir)
                                .addGap(52, 52, 52)
                                .addComponent(filtrarCliente)
                                .addGap(43, 43, 43)
                                .addComponent(mostrarTodosClientes)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        CadastroLayout.setVerticalGroup(
            CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastroLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeLabel)
                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sobrenomeLabel)
                    .addComponent(sobrenome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(telefoneLabel)
                    .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mensagemErroTelefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cadastrar)
                    .addGroup(CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editar)
                        .addComponent(excluir)
                        .addComponent(filtrarCliente)
                        .addComponent(mostrarTodosClientes)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        SistemaPizzaria.addTab("Cadastro", Cadastro);

        RealizarPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RealizarPedidoMouseClicked(evt);
            }
        });

        procurarClienteLabel.setText("Telefone");

        telefoneCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefoneClienteActionPerformed(evt);
            }
        });

        procurarCliente.setText("Procurar");
        procurarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procurarClienteActionPerformed(evt);
            }
        });

        jtPizzasPedidoAndamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jtPizzasPedidoAndamento);

        informacaoPizza.add(opcaoArea);
        opcaoArea.setText("Área");
        opcaoArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoAreaActionPerformed(evt);
            }
        });

        informacaoPizza.add(opcaoFormato);
        opcaoFormato.setText("Formato");
        opcaoFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoFormatoActionPerformed(evt);
            }
        });

        areaPizzaInformada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                areaPizzaInformadaActionPerformed(evt);
            }
        });

        formaPizza.add(opcaoQuadrado);
        opcaoQuadrado.setText("Quadrado");
        opcaoQuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoQuadradoActionPerformed(evt);
            }
        });

        formaPizza.add(opcaoTriangular);
        opcaoTriangular.setText("Triangular");
        opcaoTriangular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoTriangularActionPerformed(evt);
            }
        });

        formaPizza.add(opcaoCircular);
        opcaoCircular.setText("Circular");
        opcaoCircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoCircularActionPerformed(evt);
            }
        });

        ladoOuRaio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ladoOuRaioActionPerformed(evt);
            }
        });

        qtSabores.add(umSabor);
        umSabor.setText("1 sabor");
        umSabor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                umSaborActionPerformed(evt);
            }
        });

        qtSabores.add(doisSabores);
        doisSabores.setText("2 sabores");
        doisSabores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doisSaboresActionPerformed(evt);
            }
        });

        sabor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sabor1ActionPerformed(evt);
            }
        });

        sabor2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        adicionarPizza.setText("Adicionar pizza");
        adicionarPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarPizzaActionPerformed(evt);
            }
        });

        listaPizzasPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(listaPizzasPedido);

        valorTotal.setText("Total: R$ 0,00");

        realizarPedido.setText("Realizar pedido");
        realizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarPedidoActionPerformed(evt);
            }
        });

        jLabel1.setText("Selecione:");

        jLabel2.setText("Informe a área:");

        jLabel3.setText("Formato");

        ladoOuRaioLabel.setText("Informe o lado:");

        jLabel7.setText("Qt. de sabores:");

        jLabel8.setText("Sabores:");

        calcularArea.setText("Calcular Area");
        calcularArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularAreaActionPerformed(evt);
            }
        });

        jLabel15.setText("Cliente");

        removerPizza.setText("Remover Pizza");
        removerPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerPizzaActionPerformed(evt);
            }
        });

        validarArea.setText("Validar Área");
        validarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validarAreaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RealizarPedidoLayout = new javax.swing.GroupLayout(RealizarPedido);
        RealizarPedido.setLayout(RealizarPedidoLayout);
        RealizarPedidoLayout.setHorizontalGroup(
            RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RealizarPedidoLayout.createSequentialGroup()
                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RealizarPedidoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(realizarPedido, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(valorTotal, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(RealizarPedidoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RealizarPedidoLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RealizarPedidoLayout.createSequentialGroup()
                                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3)
                                            .addComponent(ladoOuRaioLabel)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addGap(53, 53, 53)
                                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(RealizarPedidoLayout.createSequentialGroup()
                                                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(opcaoArea)
                                                    .addComponent(opcaoQuadrado))
                                                .addGap(44, 44, 44)
                                                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(RealizarPedidoLayout.createSequentialGroup()
                                                        .addComponent(opcaoFormato)
                                                        .addGap(510, 510, 510)
                                                        .addComponent(validarArea))
                                                    .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(calcularArea)
                                                        .addGroup(RealizarPedidoLayout.createSequentialGroup()
                                                            .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(doisSabores)
                                                                .addComponent(opcaoTriangular))
                                                            .addGap(35, 35, 35)
                                                            .addComponent(opcaoCircular)))))
                                            .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(RealizarPedidoLayout.createSequentialGroup()
                                                    .addComponent(jLabel2)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(areaPizzaInformada, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(umSabor)
                                                    .addComponent(ladoOuRaio, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(RealizarPedidoLayout.createSequentialGroup()
                                                        .addComponent(sabor1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(56, 56, 56)
                                                        .addComponent(sabor2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(47, 47, 47)
                                                        .addComponent(adicionarPizza)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(removerPizza)))))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2)))))
                    .addGroup(RealizarPedidoLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(procurarClienteLabel)
                            .addComponent(jLabel15))
                        .addGap(37, 37, 37)
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clienteFiltrado)
                            .addComponent(telefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(procurarCliente)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        RealizarPedidoLayout.setVerticalGroup(
            RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RealizarPedidoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procurarClienteLabel)
                    .addComponent(telefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(procurarCliente))
                .addGap(18, 18, 18)
                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clienteFiltrado)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(RealizarPedidoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(opcaoArea)
                            .addComponent(opcaoFormato)
                            .addComponent(areaPizzaInformada, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(validarArea))
                        .addGap(37, 37, 37)
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(opcaoQuadrado)
                            .addComponent(opcaoTriangular)
                            .addComponent(opcaoCircular)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ladoOuRaio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcularArea)))
                    .addComponent(ladoOuRaioLabel))
                .addGap(31, 31, 31)
                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(umSabor)
                    .addComponent(doisSabores)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sabor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sabor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adicionarPizza)
                    .addComponent(jLabel8)
                    .addComponent(removerPizza))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(realizarPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valorTotal)
                .addContainerGap())
        );

        SistemaPizzaria.addTab("Realizar Pedido", RealizarPedido);

        tipoPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoPizzaActionPerformed(evt);
            }
        });

        atualizarValor.setText("Atualizar");
        atualizarValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarValorActionPerformed(evt);
            }
        });

        jTabelaPrecos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTabelaPrecos);

        jLabel4.setText("Tipo de pizza:");

        jLabel5.setText("Valor:");

        javax.swing.GroupLayout PrecosLayout = new javax.swing.GroupLayout(Precos);
        Precos.setLayout(PrecosLayout);
        PrecosLayout.setHorizontalGroup(
            PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrecosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrecosLayout.createSequentialGroup()
                        .addGroup(PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(27, 27, 27)
                        .addGroup(PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tipoPizza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(valorPizza)
                            .addComponent(atualizarValor, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 955, Short.MAX_VALUE))
                .addContainerGap())
        );
        PrecosLayout.setVerticalGroup(
            PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrecosLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoPizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(PrecosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valorPizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(atualizarValor)
                .addGap(60, 60, 60)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(262, Short.MAX_VALUE))
        );

        SistemaPizzaria.addTab("Precos", Precos);

        categoriaPizza.setModel(new javax.swing.DefaultComboBoxModel<>(CategoriaPizza.values()));
        categoriaPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaPizzaActionPerformed(evt);
            }
        });

        criarSabor.setText("Criar sabor");
        criarSabor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarSaborActionPerformed(evt);
            }
        });

        jTabelaSabores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTabelaSabores);

        jLabel9.setText("Nome do sabor:");

        jLabel10.setText("Categoria da pizza:");

        javax.swing.GroupLayout SaboresLayout = new javax.swing.GroupLayout(Sabores);
        Sabores.setLayout(SaboresLayout);
        SaboresLayout.setHorizontalGroup(
            SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaboresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SaboresLayout.createSequentialGroup()
                        .addGroup(SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(40, 40, 40)
                        .addGroup(SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomeSabor)
                            .addComponent(categoriaPizza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(criarSabor, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE))
                .addContainerGap())
        );
        SaboresLayout.setVerticalGroup(
            SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaboresLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeSabor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(SaboresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoriaPizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(criarSabor)
                .addGap(66, 66, 66)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        SistemaPizzaria.addTab("Sabores", Sabores);

        jtTodosPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtTodosPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtTodosPedidosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jtTodosPedidos);

        jLabel13.setText("Buscar Pedido");

        filtrarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarPedidoActionPerformed(evt);
            }
        });

        buscarPedido.setText("Buscar");
        buscarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPedidoActionPerformed(evt);
            }
        });

        jLabel14.setText("Atualizar Status");

        opcoesStatus.setModel(new javax.swing.DefaultComboBoxModel<>(StatusPedido.values()));

        atualizarStatus.setText("Atualizar");
        atualizarStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarStatusActionPerformed(evt);
            }
        });

        mostrarTodosPedidos.setText("Mostrar Pedidos");
        mostrarTodosPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarTodosPedidosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PedidosLayout = new javax.swing.GroupLayout(Pedidos);
        Pedidos.setLayout(PedidosLayout);
        PedidosLayout.setHorizontalGroup(
            PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addGroup(PedidosLayout.createSequentialGroup()
                        .addGroup(PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(29, 29, 29)
                        .addGroup(PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mostrarTodosPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(filtrarPedido)
                            .addComponent(opcoesStatus, 0, 152, Short.MAX_VALUE))
                        .addGap(37, 37, 37)
                        .addGroup(PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarPedido)
                            .addComponent(atualizarStatus))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PedidosLayout.setVerticalGroup(
            PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PedidosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(filtrarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarPedido))
                .addGap(24, 24, 24)
                .addGroup(PedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(opcoesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(atualizarStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mostrarTodosPedidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        SistemaPizzaria.addTab("Pedidos", Pedidos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SistemaPizzaria, javax.swing.GroupLayout.PREFERRED_SIZE, 975, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SistemaPizzaria)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPedidoActionPerformed
        // TODO add your handling code here:
//        try {
//            if (filtrarPedido.getText().isEmpty()) {
//                throw new IllegalArgumentException("Número do pedido vazio. Por favor, digite um número válido.");
//            }
//            int numeroPedido = Integer.parseInt(filtrarPedido.getText());
//            todosPedidosTableModel.filtraPedidos(numeroPedido);
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "O número do pedido é inválido. Por favor, digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (IllegalArgumentException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao filtrar os pedidos.", "Erro", JOptionPane.ERROR_MESSAGE);
//        }


    }//GEN-LAST:event_buscarPedidoActionPerformed

    private void filtrarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filtrarPedidoActionPerformed

    private void criarSaborActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarSaborActionPerformed
        // TODO add your handling code here:

//        String novoSabor = this.nomeSabor.getText();
//        String tipoNovoSabor = this.tipo.getSelectedItem().toString();
//
//        Sabor sabor = new Sabor(novoSabor, tipoNovoSabor);
//        RepositorioDados.listaSabores.add(sabor);
//        saborTableModel.adicionaSabor(sabor);
//
//        String[] saboresCadastrados = new String[RepositorioDados.listaSabores.size()];
//
//        for(int i = 0; i < RepositorioDados.listaSabores.size(); i++) {
//            Sabor saborCadastrado = RepositorioDados.listaSabores.get(i);
//            saboresCadastrados[i] = saborCadastrado.getSabor();
//        }
//
//        System.out.println(saboresCadastrados);
//
//        this.sabor1.setModel(new DefaultComboBoxModel<>(saboresCadastrados));
    }//GEN-LAST:event_criarSaborActionPerformed

    private void categoriaPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaPizzaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoriaPizzaActionPerformed

    private void atualizarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarValorActionPerformed
        // TODO add your handling code here:
//        try {
//            double atualizarValorPizza = Double.parseDouble(this.valorPizza.getText());
//            String tipoPizzaSelecionada = this.tipoPizza.getSelectedItem().toString();
//
//            preco = new Pizza(tipoPizzaSelecionada, atualizarValorPizza);
//
//            precoTableModel.atualizarPreco(tipoPizzaSelecionada, atualizarValorPizza);
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "O valor da pizza é inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar o preço da pizza.", "Erro", JOptionPane.ERROR_MESSAGE);
//        }

    }//GEN-LAST:event_atualizarValorActionPerformed

    private void tipoPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoPizzaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_tipoPizzaActionPerformed

    private void RealizarPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RealizarPedidoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_RealizarPedidoMouseClicked

    private void removerPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerPizzaActionPerformed
        // TODO add your handling code here:
        try {
            List<Pizza> listaExcluir = getPizzasParaExcluirDaTabela();

            RepositorioDados.pizzasPedido.removeAll(listaExcluir);
            this.listaPizzas.removerPizzas(listaExcluir);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir as pizzas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_removerPizzaActionPerformed

    private void calcularAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularAreaActionPerformed
        // TODO add your handling code here:
        try {
            double medidaInformada = Double.parseDouble(this.ladoOuRaio.getText());
            String areaTotal = "";

            if (opcaoCircular.isSelected()) {
                if (medidaInformada >= 7 && medidaInformada <= 23) {
                    forma = new Circunferencia(medidaInformada);
                    areaTotal = forma.area() + "";
                } else {
                    JOptionPane.showMessageDialog(null, "Raio inválido. Informe um valor entre 7 e 23.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else if (opcaoTriangular.isSelected()) {
                if (medidaInformada >= 20 && medidaInformada <= 60) {
                    forma = new Triangulo(medidaInformada);
                    areaTotal = forma.area() + "";
                } else {
                    JOptionPane.showMessageDialog(null, "Lado do triângulo inválido. Informe um valor entre 20 e 60.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else if (opcaoQuadrado.isSelected()) {
                if (medidaInformada >= 10 && medidaInformada <= 40) {
                    forma = new Quadrado(medidaInformada);
                    areaTotal = forma.area() + "";
                } else {
                    JOptionPane.showMessageDialog(null, "Lado do quadrado inválido. Informe um valor entre 10 e 40.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            this.areaPizzaInformada.setText(areaTotal);
            this.umSabor.setEnabled(true);
            this.doisSabores.setEnabled(true);
        } catch (NumberFormatException e) {
            this.umSabor.setEnabled((false));
            this.doisSabores.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Medida inválida. Informe um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_calcularAreaActionPerformed

    private void realizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarPedidoActionPerformed
        // TODO add your handling code here:
//        try {
//            String telefoneFiltro = this.telefoneCliente.getText();
//            int id = 0;
//
//            List<Pedido> listaPedido = new ArrayList<>();
//            List<Pizza> excluirPizzas = new ArrayList<>();
//            Cliente cliente = clienteTableModel.filtraCliente(telefoneFiltro, clienteTableModel);
//
//            this.pedidosClienteTableModel.filtraPedidos(telefoneFiltro, "Aberto");
//
//            List<Pedido> pedidosAbertos = this.pedidosClienteTableModel.getPedidosAbertos();
//
//            if (pedidosAbertos.size() > 0) {
//                for (Pedido pedidoAberto : pedidosAbertos) {
//                    id = pedidoAberto.getId();
//                }
//            } else {
//                id = gerarID();
//            }
//
//            for (int i = 0; i < RepositorioDados.pizzasPedido.size(); i++) {
//                Pizza pizza = RepositorioDados.pizzasPedido.get(i);
//                Pedido pedido = new Pedido(pizza, cliente, id, "Aberto", totalPedido);
//                listaPedido.add(pedido);
//                excluirPizzas.add(pizza);
//            }
//
//            if (!listaPedido.isEmpty()) {
//                this.pedidosClienteTableModel.adicionaPedido(listaPedido);
//                RepositorioDados.listaPedidos.addAll(listaPedido);
//                listaPizzas.removerPizzas(RepositorioDados.pizzasPedido);
//                RepositorioDados.pizzasPedido.removeAll(excluirPizzas);
//                todosPedidosTableModel.populaTabela();
//            } else {
//                JOptionPane.showMessageDialog(null, "A lista de pedidos está vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar os pedidos.", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//        this.realizarPedido.setEnabled(false);
//        this.valorTotal.setText("Total: R$ 0,00");
//        this.totalPedido = 0;
    }//GEN-LAST:event_realizarPedidoActionPerformed

    private void adicionarPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarPizzaActionPerformed
        // TODO add your handling code here:
//        String sabor1 = this.sabor1.getSelectedItem().toString();
//        String sabor2 = this.sabor2.getSelectedItem().toString();
//
//        String tipo1 = saborTableModel.getTipoBySabor(sabor1);
//        String tipo2 = "";
//
//        double valor1 = precoTableModel.getPrecoByTipo(tipo1) * Double.parseDouble(this.areaPizzaInformada.getText());
//        double valor2 = 0.0;
//
//        if (!sabor2.isEmpty()) {
//            tipo2 = saborTableModel.getTipoBySabor(sabor2);
//            valor2 = precoTableModel.getPrecoByTipo(tipo2) * Double.parseDouble(this.areaPizzaInformada.getText());
//        }
//
//        double mediaValor = (valor1 != valor2) ? (valor1 + valor2) / 2 : valor1;
//
//        Pizza pizza;
//
//        if (this.umSabor.isSelected()) {
//            pizza = new Pizza(this.forma.formato(), sabor1, valor1);
//        } else if (this.doisSabores.isSelected()) {
//            pizza = new Pizza(this.forma.formato(), sabor1, sabor2, mediaValor);
//        } else {
//            pizza = new Pizza("", "", 0.0);
//        }
//
//        listaPizzas.adicionaSabor(pizza);
//        RepositorioDados.pizzasPedido.add(pizza);
//        this.realizarPedido.setEnabled((true));
//
//        double total = 0;
//
//        for (Pizza precoPizza : RepositorioDados.pizzasPedido) {
//            total += precoPizza.getPreco();
//        }
//
//        this.totalPedido = total;
//
//        this.valorTotal.setText("Total: R$" + String.valueOf(totalPedido));

    }//GEN-LAST:event_adicionarPizzaActionPerformed

    private void sabor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sabor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sabor1ActionPerformed

    private void ladoOuRaioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ladoOuRaioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ladoOuRaioActionPerformed

    private void areaPizzaInformadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_areaPizzaInformadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_areaPizzaInformadaActionPerformed

    private void opcaoFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoFormatoActionPerformed
        // TODO add your handling code here:
        this.areaPizzaInformada.setEnabled(false);
        this.opcaoCircular.setEnabled(true);
        this.opcaoQuadrado.setEnabled(true);
        this.opcaoTriangular.setEnabled(true);
        this.validarArea.setEnabled(false);
        this.ladoOuRaio.setEnabled(this.opcaoCircular.isSelected() || this.opcaoQuadrado.isSelected() || this.opcaoTriangular.isSelected());
        this.calcularArea.setEnabled(this.opcaoCircular.isSelected() || this.opcaoQuadrado.isSelected() || this.opcaoTriangular.isSelected());
    }//GEN-LAST:event_opcaoFormatoActionPerformed

    private void opcaoAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoAreaActionPerformed
        // TODO add your handling code here:
        this.areaPizzaInformada.setEnabled(true);
        this.opcaoCircular.setEnabled(true);
        this.opcaoQuadrado.setEnabled(true);
        this.opcaoTriangular.setEnabled(true);
        this.ladoOuRaio.setEnabled(false);
        this.calcularArea.setEnabled(false);
        this.validarArea.setEnabled(true);
    }//GEN-LAST:event_opcaoAreaActionPerformed

    private void procurarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procurarClienteActionPerformed
        // TODO add your handling code here:
//        String telefoneFiltro = this.telefoneCliente.getText();
//
//        try {
//            if (telefoneFiltro.isEmpty()) {
//                throw new IllegalArgumentException("Telefone vazio. Por favor, digite um telefone válido.");
//            }
//
//            Cliente c = clienteTableModel.filtraCliente(telefoneFiltro, clienteTableModel);
//            clienteFiltrado.setText(c.getNome() + " " + c.getSobrenome());
//
//            this.pedidosClienteTableModel.filtraPedidos(telefoneFiltro, "Aberto");
//
//            this.jtPizzasPedidoAndamento.removeAll();
//            this.areaPizzaInformada.setText("");
//            this.opcaoQuadrado.setEnabled(false);
//            this.opcaoCircular.setEnabled(false);
//            this.opcaoTriangular.setEnabled(false);
//            this.calcularArea.setEnabled(false);
//            this.umSabor.setEnabled(false);
//            this.doisSabores.setEnabled(false);
//            this.sabor1.setEnabled(false);
//            this.sabor2.setEnabled(false);
//            this.adicionarPizza.setEnabled(false);
//            this.removerPizza.setEnabled(false);
//            this.opcaoArea.setSelected(false);
//            this.opcaoFormato.setSelected(false);
//
//            this.opcaoArea.setEnabled(true);
//            this.opcaoFormato.setEnabled(true);
//
//        } catch (IllegalArgumentException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            clienteFiltrado.setText("");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Telefone não encontrado na lista de clientes. \n", "Erro", JOptionPane.ERROR_MESSAGE);
//            clienteFiltrado.setText("");
//        }

    }//GEN-LAST:event_procurarClienteActionPerformed

    private void telefoneClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefoneClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefoneClienteActionPerformed

    private void excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirActionPerformed
//        // TODO add your handling code here:
//        List<Cliente> listaExcluir = getClientesParaExcluirDaTabela();
//        this.clienteTableModel.removeClientes(listaExcluir);
//        
//        for(Pedido pedido1 : RepositorioDados.listaPedidos) {
//            System.out.println(pedido1.getCliente().getTelefone() + " oiii " + telefoneCliente.getText());
//        }
//        
//        this.todosPedidosTableModel.excluiPedidos(telefoneCliente.getText());
//        
//        this.jtPizzasPedidoAndamento.removeAll();
//        this.areaPizzaInformada.setText("");
//        this.opcaoQuadrado.setEnabled(false);
//        this.opcaoCircular.setEnabled(false);
//        this.opcaoTriangular.setEnabled(false);
//        this.calcularArea.setEnabled(false);
//        this.umSabor.setEnabled(false);
//        this.doisSabores.setEnabled(false);
//        this.sabor1.setEnabled(false);
//        this.sabor2.setEnabled(false);
//        this.adicionarPizza.setEnabled(false);
//        this.removerPizza.setEnabled(false);
//        this.opcaoArea.setSelected(false);
//        this.opcaoFormato.setSelected(false);
//        this.areaPizzaInformada.setEnabled(false);
//        this.validarArea.setEnabled(false);
//        
//        this.pedidosClienteTableModel.limpaTabela();


    }//GEN-LAST:event_excluirActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        // TODO add your handling code here:

//        String nome = this.nome.getText();
//        String sobrenome = this.sobrenome.getText();
//        String telefone = this.telefone.getText();
//        
//        boolean telefoneExistente = false;
//        
//        if(!editarCliente.getTelefone().equals(telefone)) {
//            for (Cliente cliente : RepositorioDados.listaClientes) {
//                if(cliente.getTelefone().equals(telefone)) {
//                    telefoneExistente = true;
//                    break;
//                }
//            }
//        }
//        
//        if (!telefoneExistente) {
//                editarCliente.setNome(nome);
//                editarCliente.setSobrenome(sobrenome);
//                editarCliente.setTelefone(telefone);
//                this.mensagemErroTelefone.setVisible(false);
//
//        } else {
//            this.mensagemErroTelefone.setVisible(true);
//        }
//        
//        clienteTableModel.fireTableRowsUpdated(atualizarLinhaSelecionada, atualizarLinhaSelecionada);

    }//GEN-LAST:event_editarActionPerformed

    private void jtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClientesMouseClicked
        // TODO add your handling code here:
        this.linhaSelecionadaEditarCliente = jtClientes.rowAtPoint(evt.getPoint());
        this.editarCliente = this.clienteTableModel.getCliente(this.linhaSelecionadaEditarCliente);

        this.setCliente(editarCliente);
    }//GEN-LAST:event_jtClientesMouseClicked

    private void atualizarStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarStatusActionPerformed
        // TODO add your handling code here:
//        int numeroPedido = Integer.parseInt(filtrarPedido.getText());
//        StatusPedido status = (StatusPedido) this.opcoesStatus.getSelectedItem();
//        this.todosPedidosTableModel.atualizarStatus(numeroPedido, status);
//
//        String telefoneFiltro = telefoneCliente.getText();
//
//        this.pedidosClienteTableModel.filtraPedidos(telefoneFiltro, "Aberto");
    }//GEN-LAST:event_atualizarStatusActionPerformed

    private void mostrarTodosPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarTodosPedidosActionPerformed
        // TODO add your handling code here:
//        this.todosPedidosTableModel.mostrarTodosPedidos(RepositorioDados.listaPedidos);
    }//GEN-LAST:event_mostrarTodosPedidosActionPerformed

    private void opcaoQuadradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoQuadradoActionPerformed
        // TODO add your handling code here:
        ladoOuRaio.setEnabled(this.opcaoFormato.isSelected());
        calcularArea.setEnabled(this.opcaoFormato.isSelected());
    }//GEN-LAST:event_opcaoQuadradoActionPerformed

    private void umSaborActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_umSaborActionPerformed
        // TODO add your handling code here:
        this.sabor1.setEnabled(true);
        this.sabor2.setEnabled(false);
        this.adicionarPizza.setEnabled(true);
        this.removerPizza.setEnabled(true);
    }//GEN-LAST:event_umSaborActionPerformed

    private void doisSaboresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doisSaboresActionPerformed
        // TODO add your handling code here:
        this.sabor1.setEnabled(true);
        this.sabor2.setEnabled(true);
        this.adicionarPizza.setEnabled(true);
        this.removerPizza.setEnabled(true);
    }//GEN-LAST:event_doisSaboresActionPerformed

    private void opcaoTriangularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoTriangularActionPerformed
        // TODO add your handling code here:
        ladoOuRaio.setEnabled(this.opcaoFormato.isSelected());
        calcularArea.setEnabled(this.opcaoFormato.isSelected());
    }//GEN-LAST:event_opcaoTriangularActionPerformed

    private void opcaoCircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoCircularActionPerformed
        // TODO add your handling code here:
        ladoOuRaio.setEnabled(this.opcaoFormato.isSelected());
        calcularArea.setEnabled(this.opcaoFormato.isSelected());
    }//GEN-LAST:event_opcaoCircularActionPerformed

    private void jtTodosPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtTodosPedidosMouseClicked
        // TODO add your handling code here:

        this.linhaSelecionadaEditarPedido = jtTodosPedidos.rowAtPoint(evt.getPoint());

        this.editarPedido = this.todosPedidosTableModel.getPedido(linhaSelecionadaEditarPedido);
        
        this.setPedido(editarPedido);
    }//GEN-LAST:event_jtTodosPedidosMouseClicked

    private void validarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validarAreaActionPerformed
        // TODO add your handling code here:
        double medidaInformada = Double.parseDouble(this.areaPizzaInformada.getText());
        String areaTotal = "";

        if (medidaInformada >= 100 && medidaInformada <= 1600) {
            if (opcaoCircular.isSelected()) {
                forma = new Circunferencia(medidaInformada);
                areaTotal = forma.ladoCalculado() + "";
            } else if (opcaoTriangular.isSelected()) {
                forma = new Triangulo(medidaInformada);
                areaTotal = forma.ladoCalculado() + "";
            } else if (opcaoQuadrado.isSelected()) {
                forma = new Quadrado(medidaInformada);
                areaTotal = forma.ladoCalculado() + "";
            }
        } else {
            JOptionPane.showMessageDialog(null, "A área informada deve ser entre 100 e 1600.", "Erro", JOptionPane.ERROR_MESSAGE);
            this.umSabor.setEnabled(false);
            this.doisSabores.setEnabled(false);
        }

        this.ladoOuRaio.setText(areaTotal);
        this.umSabor.setEnabled(true);
        this.doisSabores.setEnabled(true);


    }//GEN-LAST:event_validarAreaActionPerformed

    private void filtrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarClienteActionPerformed
        // TODO add your handling code here:
//        String sobrenomeFiltro = this.sobrenome.getText();
//        String telefoneFiltro = this.telefone.getText();
//        
//        
//        this.clienteTableModel.filtraListaCliente(telefoneFiltro, sobrenomeFiltro);

    }//GEN-LAST:event_filtrarClienteActionPerformed

    private void mostrarTodosClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarTodosClientesActionPerformed
        // TODO add your handling code here:
//        
//        this.clienteTableModel.setListaCliente(RepositorioDados.listaClientes);

    }//GEN-LAST:event_mostrarTodosClientesActionPerformed

    private void cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cadastrarActionPerformed

    private void telefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefoneActionPerformed

    /**
     * @param args the command line arguments
     */
    public void initView() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> this.setVisible(true));
    }

    public void setClienteController(ClienteController controller) {

        this.cadastrar.addActionListener(e -> controller.cadastrarCliente());
        this.editar.addActionListener(e -> controller.atualizarCliente());
        this.excluir.addActionListener(e -> controller.excluirCliente());
        this.mostrarTodosClientes.addActionListener(e -> controller.listarTodosClientes());
        this.filtrarCliente.addActionListener(e -> controller.filtraCliente());
//        this.botaoListar.addActionListener(e -> controller.listarContato());

    }

    public void setPedidosClienteController(PedidosClienteController controller) {

        this.adicionarPizza.addActionListener(e -> controller.adicionaPizzaPedido());
        this.procurarCliente.addActionListener(e -> {
            try {
                controller.selecionaCliente();
            } catch (SQLException ex) {
                Logger.getLogger(Pizzaria.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.realizarPedido.addActionListener(e -> controller.criaPedido());

    }
    
    public void setTodosPedidosController(TodosPedidosController controller) {
        this.buscarPedido.addActionListener(e -> controller.filtraPedidos());
        this.atualizarStatus.addActionListener(e -> {
            try {
                controller.atualizaPedido();
            } catch (SQLException ex) {
                Logger.getLogger(Pizzaria.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setSaborController(SaborController controller) {
        this.criarSabor.addActionListener(e -> controller.criaSabor());
    }
    
    public void setPrecoController(PrecoController controller) {
        this.atualizarValor.addActionListener(e -> controller.atualizarPreco());
    }
    
    public FormularioPizza getFormularioPizza() {
        boolean opcaoArea = this.opcaoArea.isSelected();
        boolean opcaoFormato = this.opcaoFormato.isSelected();
        double areaPizzaInformada = Double.parseDouble(this.areaPizzaInformada.getText());
        String formato = this.forma.formato();
        
        double medidaInformada = Double.parseDouble(this.ladoOuRaio.getText());
        
        boolean umSabor = this.umSabor.isSelected();
        boolean doisSabores = this.doisSabores.isSelected();
        
        String sabor1 = this.sabor1.getSelectedItem().toString();
        String sabor2 = this.sabor2.getSelectedItem().toString();
        
        Pizza pizza = new Pizza(formato, sabor1, sabor2);
        
        
        FormularioPizza formularioPizza = new FormularioPizza(
                pizza, 
                opcaoArea, 
                opcaoFormato, 
                areaPizzaInformada, 
                medidaInformada, 
                umSabor, 
                doisSabores);
        
        return formularioPizza;
    }
    
    public void inserirPizzaPedidoTabela(Pizza pizza) {
        listaPizzas.adicionaPizza(pizza);
    }
    
    public void setValorTotalPedido(double valorTotal) {
        this.valorTotal.setText(String.valueOf(valorTotal));
    }
    
    public void setRealizarPedidoEnabled(boolean habilitar) {
        this.realizarPedido.setEnabled(habilitar);
    }
    
    public double getValorTotalPedido() {
        
        int columnIndex = 3; 
        double total = 0.0;

        for (int row = 0; row < listaPizzas.getRowCount(); row++) {
            Object valorPizza = listaPizzas.getValueAt(row, columnIndex);
            System.out.println( valorPizza + "valorTotal");
            // Converta o valor da célula em um número, por exemplo, double
            if (valorPizza instanceof Number) {
                double valorDouble = ((Number) valorPizza).doubleValue();
                total += valorDouble;
            }
        }

        return total;
    }

    public Sabor getSaborFormulario() {
        String novoSabor = this.nomeSabor.getText();
        String categoria = this.categoriaPizza.getSelectedItem().toString();

        Sabor sabor = new Sabor(novoSabor, categoria);

        return sabor;
    }

    public void inserirSaborTabela(Sabor sabor) {
        saborTableModel.adicionaSabor(sabor);
    }
    
    public void inserirPedidoTabela(List<Pedido> pedido){
        this.pedidosClienteTableModel.adicionaPedido(pedido);
        this.todosPedidosTableModel.adicionaPedido(pedido);
//        this.listaPizzas.removerPizzas(listaParaExcluir);
    }
    
    public List<Pedido> getPizzasPedido() {
//        try {;
//            String telefoneFiltro = this.telefoneCliente.getText();
//            int id = 0;
//
//            List<Pedido> listaPedido = new ArrayList<>();
//            List<Pizza> excluirPizzas = new ArrayList<>();
//            Cliente cliente = clienteTableModel.filtraCliente(telefoneFiltro, clienteTableModel);
//
//            this.pedidosClienteTableModel.filtraPedidos(telefoneFiltro, "Aberto");
//
//            List<Pedido> pedidosAbertos = this.pedidosClienteTableModel.getPedidosAbertos();
//
//            if (pedidosAbertos.size() > 0) {
//                for (Pedido pedidoAberto : pedidosAbertos) {
//                    id = pedidoAberto.getId();
//                }
//            } else {
//                id = gerarID();
//            }
//
//            for (int i = 0; i < RepositorioDados.pizzasPedido.size(); i++) {
//                Pizza pizza = RepositorioDados.pizzasPedido.get(i);
//                Pedido pedido = new Pedido(pizza, cliente, id, "Aberto", totalPedido);
//                listaPedido.add(pedido);
//                excluirPizzas.add(pizza);
//            }
//RepositorioDados.listaPedidos
//
//            if (!listaPedido.isEmpty()) {
//                this.pedidosClienteTableModel.adicionaPedido(listaPedido);
//                RepositorioDados.listaPedidos.addAll(listaPedido);
//                listaPizzas.removerPizzas(RepositorioDados.pizzasPedido);
//                RepositorioDados.pizzasPedido.removeAll(excluirPizzas);
//                todosPedidosTableModel.populaTabela();
//            } else {
//                JOptionPane.showMessageDialog(null, "A lista de pedidos está vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
//            }
//            
//            return listaPedido;
//            
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar os pedidos.", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//        this.realizarPedido.setEnabled(false);
//        this.valorTotal.setText("Total: R$ 0,00");
//        this.totalPedido = 0;

        List<Pedido> listaPedido = new ArrayList<>();
        List<Pedido> pedidosAbertos = this.pedidosClienteTableModel.getPedidosAbertos();
//        
        int numeroPedido = 0;
//        
        if (pedidosAbertos.size() > 0) {
            for (Pedido pedidoAberto : pedidosAbertos) {
                numeroPedido = pedidoAberto.getId();
            }
        } else {
            numeroPedido = gerarID();
        }

        for (int i = 0; i < listaPizzas.getRowCount(); i++) {


            String sabor1 = (String) listaPizzas.getValueAt(i, 0);
            String sabor2 = (String) listaPizzas.getValueAt(i, 1);
            String formato = (String) listaPizzas.getValueAt(i, 2);
            double valorPizza = (double) listaPizzas.getValueAt(i, 3);
            
            Pizza pizza = new Pizza(formato, sabor1, sabor2, valorPizza);
            
            double valorTotalPedido = Double.parseDouble(valorTotal.getText());
           
            
            Pedido pedido = new Pedido(pizza, getClientePedido().getTelefone(), 1, StatusPedido.ABERTO, valorTotalPedido, numeroPedido);
            
            listaPedido.add(pedido);
        }
        
        return listaPedido;

    }
    

    public boolean telefoneExistente(String telefone) {

        boolean telefoneExistente;

        telefoneExistente = RepositorioDados.listaClientes.stream().anyMatch(cliente -> cliente.getTelefone().equals(telefone));

        if (telefoneExistente) {
            this.mensagemErroTelefone.setVisible(true);
            return true;
        } else {
            this.mensagemErroTelefone.setVisible(false);
            return false;
        }
    }

    public void limparClienteAtualizar() {
        editarCliente = null;
    }

    public void mostraListaClientes(List<Cliente> lista) {
        this.clienteTableModel.setListaCliente(lista);
    }
    
    public void mostraListaPedidosFiltrado(List<Pedido> lista) {
        this.todosPedidosTableModel.setListaTodosPedidos(lista);
    }

    public Cliente filtrarCliente() {
        String nome = this.nome.getText();
        String sobrenome = this.sobrenome.getText();
        String telefone = this.telefone.getText();

        //this.clienteTableModel.filtraListaCliente(telefoneFiltro, sobrenomeFiltro, nomeFiltro);
        Cliente c = new Cliente(nome, sobrenome, telefone);

        return c;
    }
    
    public int filtraPedido() {
        
        String numeroPedido = this.filtrarPedido.getText();
        
        return Integer.parseInt(numeroPedido);
        
    }

    public Cliente getClienteFormulario() {
        String nome = this.nome.getText();
        String sobrenome = this.sobrenome.getText();
        String telefone = this.telefone.getText();

        boolean isTelefoneValido = telefone.matches(regexTelefone);

        if (!nome.isEmpty() && !sobrenome.isEmpty() && !telefone.isEmpty()) {
            if (isTelefoneValido) {

                Cliente c = new Cliente(nome, sobrenome, telefone);

                RepositorioDados.listaClientes.add(c);

                return c;
            } else {
                this.mensagemErroTelefone.setText("Telefone invalido.");
                this.mensagemErroTelefone.setVisible(true);

                return null;
            }

        } else {
            this.mensagemErroTelefone.setText("Por favor, preencha todos os campos.");
            this.mensagemErroTelefone.setVisible(true);

            return null;
        }

    }

    public Cliente getClienteParaAtualizar() {
        String nome = this.nome.getText();
        String sobrenome = this.sobrenome.getText();
        String telefone = this.telefone.getText();

        editarCliente.setNome(nome);
        editarCliente.setSobrenome(sobrenome);
        editarCliente.setTelefone(telefone);
        this.mensagemErroTelefone.setVisible(false);

        return editarCliente;

    }
    
    public Pedido getPedidoParaAtualizar() {
        
        int numeroPedido = Integer.parseInt(this.filtrarPedido.getText());
        StatusPedido status = (StatusPedido) this.opcoesStatus.getSelectedItem();
        
        Pedido pedido = new Pedido(numeroPedido, status);

        return pedido;
    }
    
    public void atualizarTodosPedidosTabela(List<Pedido> pedidos) {
        
        todosPedidosTableModel.setListaTodosPedidos(pedidos);
    }

    public void atualizarClienteTabela() {
        clienteTableModel.fireTableRowsUpdated(atualizarLinhaSelecionada, atualizarLinhaSelecionada);
    }

    public void inserirClienteTabela(Cliente c) {
        clienteTableModel.adicionaCliente(c);
    }

    public List<Cliente> getClientesParaExcluir() {
        int[] linhasSelecionadas = jtClientes.getSelectedRows();
        List<Cliente> listaExcluir = new ArrayList();
        for (int i = 0; i < linhasSelecionadas.length; i++) {
            Cliente cliente = clienteTableModel.getCliente(linhasSelecionadas[i]);
            listaExcluir.add(cliente);
        }
        return listaExcluir;
    }

    public void excluirCliente() {

        List<Cliente> listaExcluir = getClientesParaExcluir();
        this.clienteTableModel.removeClientes(listaExcluir);

//        this.todosPedidosTableModel.excluiPedidos(telefoneCliente.getText());
        //deve excluir todos os pedidos do cliente ao exclui-lo
//        this.jtPizzasPedidoAndamento.removeAll();
//        this.areaPizzaInformada.setText("");
//        this.opcaoQuadrado.setEnabled(false);
//        this.opcaoCircular.setEnabled(false);
//        this.opcaoTriangular.setEnabled(false);
//        this.calcularArea.setEnabled(false);
//        this.umSabor.setEnabled(false);
//        this.doisSabores.setEnabled(false);
//        this.sabor1.setEnabled(false);
//        this.sabor2.setEnabled(false);
//        this.adicionarPizza.setEnabled(false);
//        this.removerPizza.setEnabled(false);
//        this.opcaoArea.setSelected(false);
//        this.opcaoFormato.setSelected(false);
//        this.areaPizzaInformada.setEnabled(false);
//        this.validarArea.setEnabled(false);
//        
//        this.pedidosClienteTableModel.limpaTabela();
    }
    
    public String getTelefoneCliente() {
        
        String telefoneFiltro = this.telefoneCliente.getText();

        if (telefoneFiltro.isEmpty()) {
            throw new IllegalArgumentException("Telefone vazio. Por favor, digite um telefone válido.");
        }
        
        return telefoneFiltro;
        
    }
    
    public void setClientePedido() {

        Cliente c = clienteTableModel.filtraCliente(getTelefoneCliente(), clienteTableModel);
        
        clienteFiltrado.setText(c.getNome() + " " + c.getSobrenome());
        
            this.jtPizzasPedidoAndamento.removeAll();
            this.areaPizzaInformada.setText("");
            this.opcaoQuadrado.setEnabled(false);
            this.opcaoCircular.setEnabled(false);
            this.opcaoTriangular.setEnabled(false);
            this.calcularArea.setEnabled(false);
            this.umSabor.setEnabled(false);
            this.doisSabores.setEnabled(false);
            this.sabor1.setEnabled(false);
            this.sabor2.setEnabled(false);
            this.adicionarPizza.setEnabled(false);
            this.removerPizza.setEnabled(false);
            this.opcaoArea.setSelected(false);
            this.opcaoFormato.setSelected(false);

            this.opcaoArea.setEnabled(true);
            this.opcaoFormato.setEnabled(true);
        
    }
    
    public Cliente getClientePedido() {
        
        Cliente c = clienteTableModel.filtraCliente(getTelefoneCliente(), clienteTableModel);
        
        return c;
    }
    
    public void limpaTabelaPedidoCliente(){
        this.pedidosClienteTableModel.limpaTabela();
    }

    public Preco getPreco() {
        try {
            double novoValor = Double.parseDouble(this.valorPizza.getText());
            String categoriaSelecionada = this.tipoPizza.getSelectedItem().toString();

            Preco preco = new Preco(novoValor, categoriaSelecionada);

            return preco;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O valor da pizza é inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar o preço da pizza.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void atualizarPrecoTabela(List<Preco> precos) {
        precoTableModel.setListaPreco(precos);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cadastro;
    private javax.swing.JPanel Pedidos;
    private javax.swing.JPanel Precos;
    private javax.swing.JPanel RealizarPedido;
    private javax.swing.JPanel Sabores;
    private javax.swing.JTabbedPane SistemaPizzaria;
    private javax.swing.JButton adicionarPizza;
    private javax.swing.JTextField areaPizzaInformada;
    private javax.swing.JButton atualizarStatus;
    private javax.swing.JButton atualizarValor;
    private javax.swing.JButton buscarPedido;
    private javax.swing.JButton cadastrar;
    private javax.swing.JButton calcularArea;
    private javax.swing.JComboBox<CategoriaPizza> categoriaPizza;
    private javax.swing.JLabel clienteFiltrado;
    private javax.swing.JButton criarSabor;
    private javax.swing.JRadioButton doisSabores;
    private javax.swing.JButton editar;
    private javax.swing.JButton excluir;
    private javax.swing.JButton filtrarCliente;
    private javax.swing.JTextField filtrarPedido;
    private javax.swing.ButtonGroup formaPizza;
    private javax.swing.ButtonGroup informacaoPizza;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTabelaPrecos;
    private javax.swing.JTable jTabelaSabores;
    private javax.swing.JTable jtClientes;
    private javax.swing.JTable jtPizzasPedidoAndamento;
    private javax.swing.JTable jtTodosPedidos;
    private javax.swing.JTextField ladoOuRaio;
    private javax.swing.JLabel ladoOuRaioLabel;
    private javax.swing.JTable listaPizzasPedido;
    private javax.swing.JLabel mensagemErroTelefone;
    private javax.swing.JButton mostrarTodosClientes;
    private javax.swing.JButton mostrarTodosPedidos;
    private javax.swing.JTextField nome;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JTextField nomeSabor;
    private javax.swing.JRadioButton opcaoArea;
    private javax.swing.JRadioButton opcaoCircular;
    private javax.swing.JRadioButton opcaoFormato;
    private javax.swing.JRadioButton opcaoQuadrado;
    private javax.swing.JRadioButton opcaoTriangular;
    private javax.swing.JComboBox<StatusPedido> opcoesStatus;
    private javax.swing.JButton procurarCliente;
    private javax.swing.JLabel procurarClienteLabel;
    private javax.swing.ButtonGroup qtSabores;
    private javax.swing.JButton realizarPedido;
    private javax.swing.JButton removerPizza;
    private javax.swing.JComboBox<String> sabor1;
    private javax.swing.JComboBox<String> sabor2;
    private javax.swing.JTextField sobrenome;
    private javax.swing.JLabel sobrenomeLabel;
    private javax.swing.JTextField telefone;
    private javax.swing.JTextField telefoneCliente;
    private javax.swing.JLabel telefoneLabel;
    private javax.swing.JComboBox<CategoriaPizza> tipoPizza;
    private javax.swing.JRadioButton umSabor;
    private javax.swing.JButton validarArea;
    private javax.swing.JTextField valorPizza;
    private javax.swing.JLabel valorTotal;
    // End of variables declaration//GEN-END:variables

}
