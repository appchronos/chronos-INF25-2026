
package Telas;

import javax.swing.JOptionPane;

// 1 - Importar as bibliotecas
import java.sql.*;
import AcessoDB.ModuloDbConecta;
import java.awt.Color;

public class TelaAdicionarTarefa extends javax.swing.JInternalFrame {
    
    // 2 - criar as variáveis necessárias à conexão
    Connection conexao = null;  // É a variável que retorna a conexao
    PreparedStatement pst = null; // É variável com o comando SQL
    ResultSet rs = null; // Variável com o resultado do comando executado
    
    public void fazLimpar() {
        txtCodTopico.setText("");
        txtCodUsuario.setText(""); //Limpando o campo!
        txtTarefa.setText("");
        txtDesTarefa.setText("");
        txtValor.setText("");
        txtCodTopico.requestFocus(); // Coloca o foco do cursor, neste campo!   
    }

    public void fazIncluir() {
        // Fazer a validação das informações da Tela
        String txtDescPerTela = txtTarefa.getText();
        // Validando...
        if  (txtDescPerTela.isEmpty()) {
            // Faz critica
            JOptionPane.showMessageDialog(null," Campos inválidos/não preenchidos na Tela!!!");
        }else {
            // 4 - Definição da String com o comando SQL de Alteração = UPDATE!
            String sql = "insert INTO T_TAREFA(id_tarefa, id_topico, id_usuario, nm_tarefa, ds_tarefa, vl_tarefa, dt_criacao, dt_conclusao) values(?, ?, ?, ?, ?, ?, ?, ?)";

            // 5 - Fazer acesso ao banco de dados com a consulta/chave informada!
            try {
                // Bloco de comandos OK
                // 6 - Associar o comando SQL na conexão do banco
                pst = conexao.prepareStatement(sql);
                // Substituir a "?" pela informação do campo Chave da Tela
                pst.setString(2,txtCodTopico.getText());
                pst.setString(3,txtCodUsuario.getText());
                pst.setString(4,txtTarefa.getText());
                pst.setString(5,txtDesTarefa.getText());
                pst.setString(6,txtValor.getText());
                // 7 - Executar o comando UPDATE na Conexão do banco
                int fgInsOK = pst.executeUpdate();  //Faz a Altualização
                // 8 - Testar se consultou OK, se achou!?
                if ( fgInsOK > 0 ) { // Se Alterou OK
                    JOptionPane.showMessageDialog(null," Registro incluído com sucesso!!!");
                fazLimpar();                   
                }else{
                    JOptionPane.showMessageDialog(null," ERRO na Inclusão de registros, favor verificar!!!!");

                }

            } catch ( Exception varERRO ) { // Fazer o tratamento da Exceção/ERRO
                JOptionPane.showMessageDialog(null," Erro na Inclusão Tabela - t_tarefa!");
                System.out.println("O ERRO é: " + varERRO.toString());  // Exibe na console o erro!
            }
        }        
        
    }
    
    public void fazConsultar() {
        // 4 - Definição da String com o comando SQL de COnsulta!
        String sql = "select * from t_permissao where id_Permissao = ?";
        
        // 5 - Fazer acesso ao banco de dados com a consulta/chave informada!
        try {
            // Bloco de comandos OK
            // 6 - Associar o comando SQL na conexão do banco
            pst = conexao.prepareStatement(sql);
            // Substituir a "?" pela informação do campo Chave da Tela
            pst.setString(1,txtCodTopico.getText());
            // 7 - Executar o comando/consulta na Conexão do banco
            rs = pst.executeQuery();  //Faz a Consulta
            // 8 - Testar se consultou OK, se achou!?
            if ( rs.next() ) { // Se encontrou o registro/próximo
                // Escrever os campos, salvos na variável "rs", na Tela!
                txtCodTopico.setText(rs.getString(2)); // Nro. da Coluna do banco
                txtCodUsuario.setText(rs.getString(3));
                txtTarefa.setText(rs.getString(4));
                txtCodUsuario.setText(rs.getString(5));
                txtCodUsuario.setText(rs.getString(6));
            }else{
                JOptionPane.showMessageDialog(null," Código de Permissão INEXISTENTE!");
                fazLimpar();
            }
                 
        } catch ( Exception varERRO ) { // Fazer o tratamento da Exceção/ERRO
            JOptionPane.showMessageDialog(null," Erro na Consulta Tabela - tpermissao!");
            System.out.println("O ERRO é: " + varERRO.toString());  // Exibe na console o erro!
        }        
    }
    
    public void consultarPrior() {
        //  Definição da String com o comando SQL de COnsulta Anterior!
        String sql = "select * from t_permissao where id_Permissao = ? - 1";
        // Fazendo acesso ao registro anterior com o try/catch
                    try {
                // Bloco de comandos OK
                // 6 - Associar o comando SQL na conexão do banco
                pst = conexao.prepareStatement(sql);
                // Substituir a "?" pela informação do campo Chave da Tela
                pst.setString(1,txtCodTopico.getText());
                // 7 - Executar o comando UPDATE na Conexão do banco
                rs = pst.executeQuery();  //Faz a Consulta
                // 8 - Testar se consultou OK, se achou!?
                if ( rs.next() ) { // Se Alterou OK
                // Escrever os campos, salvos na variável "rs", na Tela!
                txtCodTopico.setText(rs.getString(1)); // Nome do Tópico do banco
                txtDescPermissao.setText(rs.getString(2));
                lblDataHoje.setText("14/05/2026");                                 
                }else{
                    JOptionPane.showMessageDialog(null," Anterior INEXISTENTE!!!!");
                    fazLimpar();
                }

            } catch ( Exception varERRO ) { // Fazer o tratamento da Exceção/ERRO
                JOptionPane.showMessageDialog(null," Erro na consulta do anterior - tpermissao!");
                System.out.println("O ERRO é: " + varERRO.toString());  // Exibe na console o erro!
            }
        
    
    
    }
    public void consultarNext() {
            //  Definição da String com o comando SQL de COnsulta Anterior!
        String sql = "select * from t_permissao where id_Permissao = ? + 1";
        // Fazendo acesso ao registro anterior com o try/catch
                    try {
                // Bloco de comandos OK
                // 6 - Associar o comando SQL na conexão do banco
                pst = conexao.prepareStatement(sql);
                // Substituir a "?" pela informação do campo Chave da Tela
                pst.setString(1,txtCodTopico.getText());
                // 7 - Executar o comando UPDATE na Conexão do banco
                rs = pst.executeQuery();  //Faz a Consulta
                // 8 - Testar se consultou OK, se achou!?
                if ( rs.next() ) { // Se Alterou OK
                // Escrever os campos, salvos na variável "rs", na Tela!
                txtCodTopico.setText(rs.getString(1)); // Nro. da Coluna do banco
                txtDescPermissao.setText(rs.getString(2));
                lblDataHoje.setText("14/05/2026");                                 
                }else{
                    JOptionPane.showMessageDialog(null," Anterior INEXISTENTE!!!!");
                    fazLimpar();
                }

            } catch ( Exception varERRO ) { // Fazer o tratamento da Exceção/ERRO
                JOptionPane.showMessageDialog(null," Erro na consulta do anterior - t_permissao!");
                System.out.println("O ERRO é: " + varERRO.toString());  // Exibe na console o erro!
            }
    }
    
    public void consultarFirst() {
            //  Definição da String com o comando SQL de COnsulta Anterior!
        String sql = "select * from t_permissao";
        // Fazendo acesso ao registro anterior com o try/catch
            try {
                // Bloco de comandos OK
                // 6 - Associar o comando SQL na conexão do banco
                pst = conexao.prepareStatement(sql);            
                // 7 - Executar o comando UPDATE na Conexão do banco
                rs = pst.executeQuery();  //Faz a Consulta
                // 8 - Testar se consultou OK, se achou!?
                if ( rs.first() ) {
                // Escrever os campos, salvos na variável "rs", na Tela!
                txtCodPermissao.setText(rs.getString(1)); // Nro. da Coluna do banco
                txtDescPermissao.setText(rs.getString(2));
                lblDataHoje.setText("14/05/2026");                                 
                }else{
                    JOptionPane.showMessageDialog(null," Cadastro de Permissões VAZIO!!!!");
                    fazLimpar();
                }

            } catch ( Exception varERRO ) { // Fazer o tratamento da Exceção/ERRO
                JOptionPane.showMessageDialog(null," Erro na consulta do Primeiro Registro - t_permissao!");
                System.out.println("O ERRO é: " + varERRO.toString());  // Exibe na console o erro!
            }
    }
    
    public void consultarLast() {
            //  Definição da String com o comando SQL de COnsulta Anterior!
        String sql = "select * from t_permissao";
        // Fazendo acesso ao registro anterior com o try/catch
            try {
                // Bloco de comandos OK
                // 6 - Associar o comando SQL na conexão do banco
                pst = conexao.prepareStatement(sql);            
                // 7 - Executar o comando UPDATE na Conexão do banco
                rs = pst.executeQuery();  //Faz a Consulta
                // 8 - Testar se consultou OK, se achou!?
                if ( rs.last() ) {
                // Escrever os campos, salvos na variável "rs", na Tela!
                txtCodPermissao.setText(rs.getString(1)); // Nro. da Coluna do banco
                txtDescPermissao.setText(rs.getString(2));
                lblDataHoje.setText("14/05/2026");                                 
                }else {
                    JOptionPane.showMessageDialog(null," Cadastro de Permissões VAZIO!!!!");
                    fazLimpar();
                }

            } catch ( Exception varERRO ) { // Fazer o tratamento da Exceção/ERRO
                JOptionPane.showMessageDialog(null," Erro na consulta do Último Registro - t_permissao!");
                System.out.println("O ERRO é: " + varERRO.toString());  // Exibe na console o erro!
            }


}
    
    /**
     * Creates new form TelaAdicionarTarefa
     */
    public TelaAdicionarTarefa() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLast = new javax.swing.JButton();
        btnPrior = new javax.swing.JButton();
        lblObservacao = new javax.swing.JLabel();
        lblAdicionarTarefa = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnAdicionarTarefa = new javax.swing.JButton();
        txtTarefa = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        btnConsultarTopico = new javax.swing.JButton();
        lblDescricaoTarefa = new javax.swing.JLabel();
        lblCadPermissoes = new javax.swing.JLabel();
        txtDesTarefa = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();
        lblValor = new javax.swing.JLabel();
        lblSelecionarTopico = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        btnFechar = new javax.swing.JButton();
        txtCodTopico = new javax.swing.JTextField();
        lblSelecionarTopico1 = new javax.swing.JLabel();
        txtCodUsuario = new javax.swing.JTextField();

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLast.setText(">>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnPrior.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPrior.setText("<");
        btnPrior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPriorActionPerformed(evt);
            }
        });

        lblObservacao.setText("(*) = Campos Obrigatórios.");

        lblAdicionarTarefa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAdicionarTarefa.setText("(*)Adicionar Tarefa:");

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnAdicionarTarefa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdicionarTarefa.setText("Adicionar Tarefa");
        btnAdicionarTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarTarefaActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnConsultarTopico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnConsultarTopico.setText("Consultar Tópico");
        btnConsultarTopico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarTopicoActionPerformed(evt);
            }
        });

        lblDescricaoTarefa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDescricaoTarefa.setText("Descrição da Tarefa:");

        lblCadPermissoes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCadPermissoes.setText("Nova Tarefa");

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        lblValor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblValor.setText("Valor:");

        lblSelecionarTopico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSelecionarTopico.setText("(*)Selecionar Tópico:");

        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        txtCodTopico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTopicoActionPerformed(evt);
            }
        });

        lblSelecionarTopico1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSelecionarTopico1.setText("(*)Selecionar Usuário:");

        txtCodUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(171, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblAdicionarTarefa)
                                        .addComponent(lblDescricaoTarefa)
                                        .addComponent(lblValor)))
                                .addComponent(lblSelecionarTopico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCodTopico, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDesTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnFirst)
                                    .addGap(32, 32, 32)
                                    .addComponent(btnPrior)
                                    .addGap(42, 42, 42)
                                    .addComponent(btnNext)
                                    .addGap(32, 32, 32)
                                    .addComponent(btnLast))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnAdicionarTarefa)
                                    .addGap(26, 26, 26)
                                    .addComponent(btnConsultarTopico))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(177, 177, 177)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnFechar)
                                .addComponent(btnLimpar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSelecionarTopico1)
                        .addGap(18, 18, 18)
                        .addComponent(txtCodUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(315, 315, 315)))
                .addGap(68, 68, 68))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(214, 214, 214)
                            .addComponent(lblCadPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(158, 158, 158)
                            .addComponent(lblObservacao)))
                    .addContainerGap(350, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelecionarTopico1)
                    .addComponent(txtCodUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelecionarTopico)
                    .addComponent(txtCodTopico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdicionarTarefa)
                    .addComponent(txtTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoTarefa)
                    .addComponent(txtDesTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValor))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarTarefa)
                    .addComponent(btnConsultarTopico))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrior)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(28, 28, 28)
                .addComponent(btnLimpar)
                .addGap(18, 18, 18)
                .addComponent(btnFechar)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCadPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(422, 422, 422)
                    .addComponent(lblObservacao)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        consultarLast();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnPriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPriorActionPerformed
        // TODO add your handling code here:
        consultarPrior(); // Consulta o registro anterior
    }//GEN-LAST:event_btnPriorActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        consultarFirst();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnAdicionarTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarTarefaActionPerformed
        // TODO add your handling code here:
        fazIncluir();
    }//GEN-LAST:event_btnAdicionarTarefaActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        consultarNext();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnConsultarTopicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarTopicoActionPerformed
        // TODO add your handling code here:
        fazConsultar();
    }//GEN-LAST:event_btnConsultarTopicoActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        fazLimpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();  // Fechara Tela corrente/atual!
    }//GEN-LAST:event_btnFecharActionPerformed

    private void txtCodTopicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTopicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTopicoActionPerformed

    private void txtCodUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarTarefa;
    private javax.swing.JButton btnConsultarTopico;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrior;
    private javax.swing.JLabel lblAdicionarTarefa;
    private javax.swing.JLabel lblCadPermissoes;
    private javax.swing.JLabel lblDescricaoTarefa;
    private javax.swing.JLabel lblObservacao;
    private javax.swing.JLabel lblSelecionarTopico;
    private javax.swing.JLabel lblSelecionarTopico1;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTextField txtCodTopico;
    private javax.swing.JTextField txtCodUsuario;
    private javax.swing.JTextField txtDesTarefa;
    private javax.swing.JTextField txtTarefa;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
