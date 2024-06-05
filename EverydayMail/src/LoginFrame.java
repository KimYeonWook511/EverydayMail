
import user.UserDAO;
import static javax.swing.JOptionPane.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uhs22
 */
public class LoginFrame extends javax.swing.JFrame {
    UserDAO userDAO = new UserDAO(); // 데이터베이스에 접근하기 위한 객체 생성
    int result_of_login; // 로그인 결과를 담을 변수
    
//    윈도우 프레임 기준 창 띄우는 위치  
//    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//    int x = (d.width-400)/2;
//    int y = (d.height-300)/2; 
//    setLocation(x, y);
        
    public LoginFrame() {
        initComponents();
        this.setTitle("매일메일(Everyday Mail) | 로그인");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FindDialog = new javax.swing.JDialog();
        lblFind = new javax.swing.JLabel();
        txtFindAnswer = new javax.swing.JTextField();
        cmbFindQuestion = new javax.swing.JComboBox<>();
        lblImg = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        lblQuestion = new javax.swing.JLabel();
        lblAnswer = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtFindId = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        btnPasswordFind = new javax.swing.JButton();
        btnJoin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblLoginId = new javax.swing.JLabel();
        lblLoginPassword = new javax.swing.JLabel();

        FindDialog.setModal(true);
        FindDialog.setPreferredSize(new java.awt.Dimension(522, 400));
        FindDialog.setResizable(false);

        lblFind.setFont(new java.awt.Font("굴림", 1, 18)); // NOI18N
        lblFind.setText("비밀번호 찾기");

        cmbFindQuestion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "출신 초등학교 이름은?", "어머님의 성함은?", "가장 인상 깊게 읽었던 책 제목은?" }));

        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/이메일 로고.png"))); // NOI18N

        btnFind.setText("찾기");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        lblQuestion.setText("질문");

        lblAnswer.setText("답");

        lblId.setText("아이디");

        javax.swing.GroupLayout FindDialogLayout = new javax.swing.GroupLayout(FindDialog.getContentPane());
        FindDialog.getContentPane().setLayout(FindDialogLayout);
        FindDialogLayout.setHorizontalGroup(
            FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FindDialogLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(FindDialogLayout.createSequentialGroup()
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFindId))
                    .addGroup(FindDialogLayout.createSequentialGroup()
                        .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(lblAnswer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFindAnswer)
                            .addComponent(cmbFindQuestion, 0, 305, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(FindDialogLayout.createSequentialGroup()
                .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FindDialogLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(lblImg))
                    .addGroup(FindDialogLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(lblFind)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FindDialogLayout.setVerticalGroup(
            FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FindDialogLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFind, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtFindId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFindQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuestion))
                .addGap(18, 18, 18)
                .addGroup(FindDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFindAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAnswer))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnLogin.setFont(new java.awt.Font("돋움", 0, 14)); // NOI18N
        btnLogin.setText("로그인");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnPasswordFind.setFont(new java.awt.Font("굴림", 0, 10)); // NOI18N
        btnPasswordFind.setText("비밀번호 찾기");
        btnPasswordFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasswordFindActionPerformed(evt);
            }
        });

        btnJoin.setFont(new java.awt.Font("굴림", 0, 10)); // NOI18N
        btnJoin.setText("회원가입");
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/이메일 로고.png"))); // NOI18N

        lblLoginId.setText("아이디");

        lblLoginPassword.setText("비밀번호");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPasswordFind)
                        .addGap(18, 18, 18)
                        .addComponent(btnJoin)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblLoginPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLoginId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtId)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                        .addGap(30, 30, 30)))
                .addComponent(btnLogin)
                .addGap(175, 175, 175))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLoginId, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLoginPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(7, 7, 7)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnJoin)
                    .addComponent(btnPasswordFind))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // 로그인 버튼
        result_of_login = userDAO.login(txtId.getText().trim(), txtPassword.getText().trim()); 
        
        switch (result_of_login) {
            case 1:
                showMessageDialog(null, "로그인 성공");
                // 다음 단계로 넘어가는 코드
                this.setVisible(false);
                MainFrame mainFrame = new MainFrame();
                mainFrame.setUser(userDAO.getUser(txtId.getText().trim())); // 로그인한 유저 정보를 넘겨줌
                mainFrame.setMain(); // 메인 화면에 정보 띄우기
                mainFrame.setVisible(true);
                break;
            case 0:
                showMessageDialog(null, "비밀번호가 잘못되었습니다.");
                break;
            case -1:
                showMessageDialog(null, "존재하지 않는 아이디입니다.");
                break;
            case -2:
                showMessageDialog(null, "에러가 발생하였습니다.");
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        // 회원가입 버튼
//        this.setVisible(false); // 현재 프레임 안보이게 하기
        new JoinFrame().setVisible(true); // 회원가입 프레임 보이게 하기
    }//GEN-LAST:event_btnJoinActionPerformed

    private void btnPasswordFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasswordFindActionPerformed
        // 비밀번호 찾기 버튼
        FindDialog.setTitle("비밀번호 찾기");
        FindDialog.setSize(522, 400);
        FindDialog.show();
    }//GEN-LAST:event_btnPasswordFindActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
         // 비밀번호 찾기 프레임에서의 찾기 버튼
        if (txtFindId.getText().trim().equals("")) {
            showMessageDialog(null, "아이디를 입력해 주세요.");
        } else if (txtFindAnswer.getText().trim().equals("")) {
            showMessageDialog(null, "답변을 입력해 주세요.");
        } else {
            String password = userDAO.passwordFind(txtFindId.getText().trim(), cmbFindQuestion.getSelectedIndex(), txtFindAnswer.getText().trim());

            if (password == null) {
                // 정보 없음
                showMessageDialog(null, "입력한 정보를 찾을 수 없습니다.");
            } else {
                showMessageDialog(null, "현재 비밀번호 : " + password);
            }
        }
    }//GEN-LAST:event_btnFindActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog FindDialog;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnPasswordFind;
    private javax.swing.JComboBox<String> cmbFindQuestion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAnswer;
    private javax.swing.JLabel lblFind;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblLoginId;
    private javax.swing.JLabel lblLoginPassword;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JTextField txtFindAnswer;
    private javax.swing.JTextField txtFindId;
    private javax.swing.JTextField txtId;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
