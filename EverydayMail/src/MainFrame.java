
import static javax.swing.JOptionPane.*;
import user.UserDAO;
import user.User;
import mail.Mail;
import mail.MailDAO;
import java.util.*;
import spam.SpamDAO;
import spam.Spam;

/**
 *
 * @author ccoma
 */
public class MainFrame extends javax.swing.JFrame {
    User user = new User();
    int old_click = -1; // 더블 클릭시 메일 보이게 할 변수
    int listSelect; // 현재 선택한 list의 index
    Vector nowVector = new Vector(); // 현재 받은 메일함에서 컨트롤하는 벡터
    
    public void setUser(User user) {
        // 로그인한 유저 정보 가져오기
        this.user = user;
    }
    
    public void setMain() {
        // 메인 화면에 유저 정보 띄우기
        lblMainUserId.setText("아이디 : " + user.getUserId());
        lblMainUserName.setText("이름 : " + user.getUserName());
        
        spamList();
        
        refreshList();
    }
    
    public MainFrame() {
        initComponents();
        this.setTitle("매일메일(Everyday Mail)"); // 현재 프레임 제목 설정
    }
    
    public void listRowSelect() {
        int tabIndex = MainTab.getSelectedIndex(); // 현재 선택된 메인 탭 index 가져오기
        
        if (tabIndex == 0) {
            listMainBlockName.setSelectedIndex(listSelect);
            listMainBlockId.setSelectedIndex(listSelect);
            listMainBlockDate.setSelectedIndex(listSelect);
            
        } else if (tabIndex == 2) {
            listReceiveSender.setSelectedIndex(listSelect);
            listReceiveTitle.setSelectedIndex(listSelect);
            listReceiveDate.setSelectedIndex(listSelect);
            
        } else if (tabIndex == 3) {
            listSendReceiver.setSelectedIndex(listSelect);
            listSendTitle.setSelectedIndex(listSelect);
            listSendDate.setSelectedIndex(listSelect);
            
        } else if (tabIndex == 4) {
            listImportantSender.setSelectedIndex(listSelect);
            listImportantTitle.setSelectedIndex(listSelect);
            listImportantDate.setSelectedIndex(listSelect);
        
        } else if (tabIndex == 5) {
            listTrashSender.setSelectedIndex(listSelect);
            listTrashTitle.setSelectedIndex(listSelect);
            listTrashDate.setSelectedIndex(listSelect);
        }     
    }
    
    public void refreshList() {
        int tabIndex = MainTab.getSelectedIndex(); // 현재 선택된 메인 탭 index 가져오기
        
        if (tabIndex == 0) {
            // 메인 화면 차단 리스트 새로고침
            listMainBlockName.setListData((Vector)nowVector.get(1));
            listMainBlockId.setListData((Vector)nowVector.get(2));
            listMainBlockDate.setListData((Vector)nowVector.get(3));
            
        } else if (tabIndex == 2) {
            // 받은 메일함 리스트 새로고침
            listReceiveSender.setListData((Vector)nowVector.get(1));
            listReceiveTitle.setListData((Vector)nowVector.get(2));
            listReceiveDate.setListData((Vector)nowVector.get(3));
            lblLocation.setText("받은 메일함(" + ((Vector)nowVector.get(1)).size() + ")"); // 현재 리스트 갯수를 lblLocation(큰제목)에 추가
            
        } else if (tabIndex == 3) {
            // 보낸 메일함 리스트 새로고침
            listSendReceiver.setListData((Vector)nowVector.get(1));
            listSendTitle.setListData((Vector)nowVector.get(2));
            listSendDate.setListData((Vector)nowVector.get(3));
            lblLocation.setText("보낸 메일함(" + ((Vector)nowVector.get(1)).size() + ")"); // 현재 리스트 갯수를 lblLocation(큰제목)에 추가
            
        } else if (tabIndex == 4) {
            // 중요 메일함 리스트 새로고침
            listImportantSender.setListData((Vector)nowVector.get(1));
            listImportantTitle.setListData((Vector)nowVector.get(2));
            listImportantDate.setListData((Vector)nowVector.get(3));
            lblLocation.setText("중요 메일함(" + ((Vector)nowVector.get(1)).size() + ")"); // 현재 리스트 갯수를 lblLocation(큰제목)에 추가
        
        } else if (tabIndex == 5) {
            // 휴지통 리스트 새로고침
            listTrashSender.setListData((Vector)nowVector.get(1));
            listTrashTitle.setListData((Vector)nowVector.get(2));
            listTrashDate.setListData((Vector)nowVector.get(3));
            lblLocation.setText("휴지통(" + ((Vector)nowVector.get(1)).size() + ")"); // 현재 리스트 갯수를 lblLocation(큰제목)에 추가
        }       
    }
    
    public void spamList() {
        // nowVector에 스팸 유저 저장하기
        SpamDAO spamDAO = new SpamDAO();
        ArrayList<Spam> spamList = spamDAO.getSpamList(user.getUserId());
        Vector vSpamNo = new Vector();
        Vector vBlockName = new Vector();
        Vector vBlockId = new Vector();
        Vector vBlockDate = new Vector();
        
        for (int i = 0; i < spamList.size(); i++) {
            vSpamNo.addElement(spamList.get(i).getSpamNo());
            vBlockName.addElement(new UserDAO().getName(spamList.get(i).getBlockId()));
            vBlockId.addElement(spamList.get(i).getBlockId());
            vBlockDate.addElement(spamList.get(i).getBlockDate());
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vSpamNo); // 스팸 번호만 있는 벡터
        nowVector.addElement(vBlockName); // 스팸 이름만 있는 벡터
        nowVector.addElement(vBlockId); // 스팸 아이디만 있는 벡터
        nowVector.addElement(vBlockDate); // 스팸 날짜만 있는 벡터
    }
    
    public void allMails() {
        // nowVector에 모든 메일 저장하기
        MailDAO mailDAO = new MailDAO();
        ArrayList<Mail> mailList = mailDAO.getReceiveMailList(user.getUserId());   
        Vector vMailNo = new Vector();
        Vector vSender = new Vector();
        Vector vTitle = new Vector();
        Vector vDate = new Vector();
        Vector vContent = new Vector();
        
        for (int i = 0; i < mailList.size(); i++) {
            vMailNo.addElement(mailList.get(i).getMailNo());
            vSender.addElement(mailList.get(i).getSenderId());
            vTitle.addElement(mailList.get(i).getTitle());
            vDate.addElement(mailList.get(i).getSendDate());
            vContent.addElement(mailList.get(i).getContent());
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vMailNo); // 메일 번호만 있는 벡터
        nowVector.addElement(vSender); // 메일 보낸이만 있는 벡터
        nowVector.addElement(vTitle); // 메일 제목만 있는 벡터
        nowVector.addElement(vDate); // 메일 날짜만 있는 벡터
        nowVector.addElement(vContent); // 메일 내용만 있는 벡터
    }
    
    public void readMails(){
        // nowVector에 읽은 메일 저장하기
        MailDAO mailDAO = new MailDAO();
        ArrayList<Mail> mailList = mailDAO.getReceiveMailList(user.getUserId());  
        Vector vMailNo = new Vector();
        Vector vSender = new Vector();
        Vector vTitle = new Vector();
        Vector vDate = new Vector();
        Vector vContent = new Vector();

        for (int i = 0; i < mailList.size(); i++) {
            if (mailList.get(i).getIsRead() == 1) {
                vMailNo.addElement(mailList.get(i).getMailNo());
                vSender.addElement(mailList.get(i).getSenderId());
                vTitle.addElement(mailList.get(i).getTitle());
                vDate.addElement(mailList.get(i).getSendDate());
                vContent.addElement(mailList.get(i).getContent());
            }
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vMailNo); // 메일 번호만 있는 벡터
        nowVector.addElement(vSender); // 메일 보낸이만 있는 벡터
        nowVector.addElement(vTitle); // 메일 제목만 있는 벡터
        nowVector.addElement(vDate); // 메일 날짜만 있는 벡터
        nowVector.addElement(vContent); // 메일 내용만 있는 벡터
    }
    
    public void notReadMails(){
        // nowVector에 안 읽은 메일 저장하기
        MailDAO mailDAO = new MailDAO();
        ArrayList<Mail> mailList = mailDAO.getReceiveMailList(user.getUserId());
        Vector vMailNo = new Vector();
        Vector vSender = new Vector();
        Vector vTitle = new Vector();
        Vector vDate = new Vector();
        Vector vContent = new Vector();
        
        for (int i = 0; i < mailList.size(); i++) {
            if (mailList.get(i).getIsRead() == 0) {
                vMailNo.addElement(mailList.get(i).getMailNo());
                vSender.addElement(mailList.get(i).getSenderId());
                vTitle.addElement(mailList.get(i).getTitle());
                vDate.addElement(mailList.get(i).getSendDate());
                vContent.addElement(mailList.get(i).getContent());
            }
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vMailNo); // 메일 번호만 있는 벡터
        nowVector.addElement(vSender); // 메일 보낸이만 있는 벡터
        nowVector.addElement(vTitle); // 메일 제목만 있는 벡터
        nowVector.addElement(vDate); // 메일 날짜만 있는 벡터
        nowVector.addElement(vContent); // 메일 내용만 있는 벡터
    }
    
    public void sendMails() {
        // nowVector에 보낸 메일 저장하기
        MailDAO mailDAO = new MailDAO();
        ArrayList<Mail> mailList = mailDAO.getSendMailList(user.getUserId());
        Vector vMailNo = new Vector();
        Vector vReceiver = new Vector();
        Vector vTitle = new Vector();
        Vector vDate = new Vector();
        Vector vContent = new Vector();
        
        for (int i = 0; i < mailList.size(); i++) {
            vMailNo.addElement(mailList.get(i).getMailNo());
            vReceiver.addElement(mailList.get(i).getReceiverId());
            vTitle.addElement(mailList.get(i).getTitle());
            vDate.addElement(mailList.get(i).getSendDate());
            vContent.addElement(mailList.get(i).getContent());
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vMailNo); // 메일 번호만 있는 벡터
        nowVector.addElement(vReceiver); // 메일 보낸이만 있는 벡터
        nowVector.addElement(vTitle); // 메일 제목만 있는 벡터
        nowVector.addElement(vDate); // 메일 날짜만 있는 벡터
        nowVector.addElement(vContent); // 메일 내용만 있는 벡터
    }
    
    public void importantMails() {
        // nowVector에 중요 메일 저장하기
        MailDAO mailDAO = new MailDAO();
        ArrayList<Mail> mailList = mailDAO.getReceiveMailList(user.getUserId());
        Vector vMailNo = new Vector();
        Vector vSender = new Vector();
        Vector vTitle = new Vector();
        Vector vDate = new Vector();
        Vector vContent = new Vector();
        
        for (int i = 0; i < mailList.size(); i++) {
            if (mailList.get(i).getIsImportant() == 1) {
                vMailNo.addElement(mailList.get(i).getMailNo());
                vSender.addElement(mailList.get(i).getSenderId());
                vTitle.addElement(mailList.get(i).getTitle());
                vDate.addElement(mailList.get(i).getSendDate());
                vContent.addElement(mailList.get(i).getContent());
            }
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vMailNo); // 메일 번호만 있는 벡터
        nowVector.addElement(vSender); // 메일 보낸이만 있는 벡터
        nowVector.addElement(vTitle); // 메일 제목만 있는 벡터
        nowVector.addElement(vDate); // 메일 날짜만 있는 벡터
        nowVector.addElement(vContent); // 메일 내용만 있는 벡터
    }
    
    public void trashMails() {
        // nowVector에 휴지통 메일 저장하기
        MailDAO mailDAO = new MailDAO();
        ArrayList<Mail> mailList = mailDAO.getTrashMailList(user.getUserId());
        Vector vMailNo = new Vector();
        Vector vSender = new Vector();
        Vector vTitle = new Vector();
        Vector vDate = new Vector();
        Vector vContent = new Vector();
        
        for (int i = 0; i < mailList.size(); i++) {
            vMailNo.addElement(mailList.get(i).getMailNo());
            vSender.addElement(mailList.get(i).getSenderId());
            vTitle.addElement(mailList.get(i).getTitle());
            vDate.addElement(mailList.get(i).getSendDate());
            vContent.addElement(mailList.get(i).getContent());
        }
        
        nowVector.removeAllElements(); // 초기화
        nowVector.addElement(vMailNo); // 메일 번호만 있는 벡터
        nowVector.addElement(vSender); // 메일 보낸이만 있는 벡터
        nowVector.addElement(vTitle); // 메일 제목만 있는 벡터
        nowVector.addElement(vDate); // 메일 날짜만 있는 벡터
        nowVector.addElement(vContent); // 메일 내용만 있는 벡터
    }
    
    public void searchMails(int idx, String txtSearch) {
        Vector vSearchMails = new Vector();
        
        if (idx == 0) {
            // 검색 필터가 제목
            vSearchMails = (Vector)nowVector.get(2); // 제목만 있는 메일 벡터
            
            for (int i = 0; i < vSearchMails.size(); i++) {
                if (!((String)vSearchMails.get(i)).contains(txtSearch)) {
                    // 해당 검색어가 제목에 포함되어 있지 않는 메일들 nowVector에서 삭제
                    for (int k = 0; k < nowVector.size(); k++) {
                        ((Vector)nowVector.get(k)).removeElementAt(i);                        
                    }
                }
            }    
            
        } else if (idx == 1) {
            // 검색 필터가 보낸이 혹은 받는이(보낸 메일함 경우)
            vSearchMails = (Vector)nowVector.get(1); // 보낸이 혹은 받는이만 있는 메일 벡터
            
            for (int i = 0; i < vSearchMails.size(); i++) {
                if (!((String)vSearchMails.get(i)).contains(txtSearch)) {
                    // 해당 검색어가 보낸이 혹은 받는이(보낸 메일함 경우)에 포함되어 있지 않는 메일들 nowVector에서 삭제
                    for (int k = 0; k < nowVector.size(); k++) {
                        ((Vector)nowVector.get(k)).removeElementAt(i);                        
                    }
                }
            }
            
        } else if (idx == 2) {
            // 검색 필터가 내용
            vSearchMails = (Vector)nowVector.get(4); // 내용만 있는 메일 벡터
            
            for (int i = 0; i < vSearchMails.size(); i++) {
                if (!((String)vSearchMails.get(i)).contains(txtSearch)) {
                    // 해당 검색어가 내용에 포함되어 있지 않는 메일들 nowVector에서 삭제
                    for (int k = 0; k < nowVector.size(); k++) {
                        ((Vector)nowVector.get(k)).removeElementAt(i);                        
                    }
                }
            }
            
        }
    }
    
    public void deleteAfterVector(int addBit) {
        int idx = listSelect;
        
        new MailDAO().deleteAction((int)((Vector)nowVector.get(0)).get(idx), addBit); // 현재 선택된 메일 삭제 처리
        
        for (int i = 0; i < nowVector.size(); i++) {
            // 삭제 후 nowVecotr 새로고침
            ((Vector)nowVector.get(i)).removeElementAt(idx); // 선택된 메일 nowVector에서 삭제
        }
    }
    
    public void showMail(boolean isShow) {
        ViewDialog.setTitle("메일 보기");
        ViewDialog.setSize(624, 550);
        txtViewSender.setBackground(new java.awt.Color(240, 240, 240));
        txtViewReceiver.setBackground(new java.awt.Color(240, 240, 240));
        txtViewTitle.setBackground(new java.awt.Color(240, 240, 240));
        ViewDialog.show();
        // 중요박스, 답장, 차단 버튼 활성화 / 비활성화
        chbViewImportant.show(true);
        chbViewImportant.show(isShow);
        btnViewResend.show(isShow);
        btnViewBlocking.show(isShow);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rBtnMailGroup = new javax.swing.ButtonGroup();
        ViewDialog = new javax.swing.JDialog();
        lblView = new javax.swing.JLabel();
        btnViewResend = new javax.swing.JButton();
        btnViewDelete = new javax.swing.JButton();
        btnViewBlocking = new javax.swing.JButton();
        lblViewDate = new javax.swing.JLabel();
        lblViewSender = new javax.swing.JLabel();
        lblViewReceiver = new javax.swing.JLabel();
        lblViewTitle = new javax.swing.JLabel();
        txtViewSender = new javax.swing.JTextField();
        txtViewReceiver = new javax.swing.JTextField();
        txtViewTitle = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txaViewContent = new javax.swing.JTextArea();
        chbViewImportant = new javax.swing.JCheckBox();
        btnMainMail = new javax.swing.JButton();
        btnWriteMail = new javax.swing.JButton();
        btnReceiveMail = new javax.swing.JButton();
        btnSendMail = new javax.swing.JButton();
        btnImportantMail = new javax.swing.JButton();
        btnTrashMail = new javax.swing.JButton();
        lblLocation = new javax.swing.JLabel();
        MainTab = new javax.swing.JTabbedPane();
        MainMail = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblMainUserInfo = new javax.swing.JLabel();
        lblMainUserId = new javax.swing.JLabel();
        lblMainUserName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblMainBlockList = new javax.swing.JLabel();
        btnMainBlockCancel = new javax.swing.JButton();
        lblMainBlockName = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        listMainBlockName = new javax.swing.JList<>();
        lblMainBlockId = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        listMainBlockId = new javax.swing.JList<>();
        lblMainBlockDate = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        listMainBlockDate = new javax.swing.JList<>();
        WriteMail = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaWriteContent = new javax.swing.JTextArea();
        txtWriteSender = new javax.swing.JTextField();
        txtWriteReceiver = new javax.swing.JTextField();
        txtWriteTitle = new javax.swing.JTextField();
        lblWriteSender = new javax.swing.JLabel();
        lblWriteReceiver = new javax.swing.JLabel();
        lblWriteTitle = new javax.swing.JLabel();
        btnWriteSend = new javax.swing.JButton();
        ReceiveMail = new javax.swing.JPanel();
        txtReceiveSearch = new javax.swing.JTextField();
        btnReceiveSearch = new javax.swing.JButton();
        cmbReceiveSearch = new javax.swing.JComboBox<>();
        rBtnAll = new javax.swing.JRadioButton();
        rBtnReadOnly = new javax.swing.JRadioButton();
        rBtnNotReadOnly = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listReceiveSender = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        listReceiveTitle = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        listReceiveDate = new javax.swing.JList<>();
        lblReceiveSender = new javax.swing.JLabel();
        lblReceiveTitle = new javax.swing.JLabel();
        lblReceiveDate = new javax.swing.JLabel();
        btnReceiveDelete1 = new javax.swing.JButton();
        SendMail = new javax.swing.JPanel();
        cmbSendSearch = new javax.swing.JComboBox<>();
        txtSendSearch = new javax.swing.JTextField();
        btnSendSearch = new javax.swing.JButton();
        btnSendDelete = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        listSendReceiver = new javax.swing.JList<>();
        jScrollPane11 = new javax.swing.JScrollPane();
        listSendTitle = new javax.swing.JList<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        listSendDate = new javax.swing.JList<>();
        lblSendReceiver = new javax.swing.JLabel();
        lbISendTitle = new javax.swing.JLabel();
        lblSendDate = new javax.swing.JLabel();
        ImportantMail = new javax.swing.JPanel();
        cmbImportantSearch = new javax.swing.JComboBox<>();
        txtImportantSearch = new javax.swing.JTextField();
        btnImportantSearch = new javax.swing.JButton();
        btnImportantDelete = new javax.swing.JButton();
        lblImportantSender = new javax.swing.JLabel();
        lblImportantTitle = new javax.swing.JLabel();
        lblImportantDate = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listImportantSender = new javax.swing.JList<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        listImportantTitle = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        listImportantDate = new javax.swing.JList<>();
        TrashMail = new javax.swing.JPanel();
        cmbTrashSearch = new javax.swing.JComboBox<>();
        txtTrashSearch = new javax.swing.JTextField();
        btnTrashSearch = new javax.swing.JButton();
        btnTrashDelete = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        listTrashSender = new javax.swing.JList<>();
        jScrollPane14 = new javax.swing.JScrollPane();
        listTrashTitle = new javax.swing.JList<>();
        jScrollPane15 = new javax.swing.JScrollPane();
        listTrashDate = new javax.swing.JList<>();
        lblTrashSender = new javax.swing.JLabel();
        lblTrashTitle = new javax.swing.JLabel();
        lblTrashDate = new javax.swing.JLabel();
        btnTrashRestore = new javax.swing.JButton();

        ViewDialog.setResizable(false);
        ViewDialog.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                ViewDialogComponentShown(evt);
            }
        });

        lblView.setFont(new java.awt.Font("굴림", 1, 18)); // NOI18N
        lblView.setText("메일 보기");

        btnViewResend.setText("답장");
        btnViewResend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewResendActionPerformed(evt);
            }
        });

        btnViewDelete.setText("삭제");
        btnViewDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDeleteActionPerformed(evt);
            }
        });

        btnViewBlocking.setText("차단");
        btnViewBlocking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewBlockingActionPerformed(evt);
            }
        });

        lblViewDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblViewDate.setText("2021-11-11 11:11:11");

        lblViewSender.setText("보낸이");

        lblViewReceiver.setText("받는이");

        lblViewTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblViewTitle.setText("제목");

        txtViewSender.setEditable(false);

        txtViewReceiver.setEditable(false);

        txtViewTitle.setEditable(false);

        txaViewContent.setEditable(false);
        txaViewContent.setBackground(new java.awt.Color(240, 240, 240));
        txaViewContent.setColumns(20);
        txaViewContent.setRows(5);
        jScrollPane6.setViewportView(txaViewContent);

        chbViewImportant.setText("중요메일");
        chbViewImportant.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chbViewImportantStateChanged(evt);
            }
        });

        javax.swing.GroupLayout ViewDialogLayout = new javax.swing.GroupLayout(ViewDialog.getContentPane());
        ViewDialog.getContentPane().setLayout(ViewDialogLayout);
        ViewDialogLayout.setHorizontalGroup(
            ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewDialogLayout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(lblView)
                .addGap(273, 273, 273))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewDialogLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewDialogLayout.createSequentialGroup()
                        .addComponent(btnViewResend, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewBlocking, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chbViewImportant)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblViewDate))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViewReceiver, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblViewTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtViewTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                            .addComponent(txtViewReceiver)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewDialogLayout.createSequentialGroup()
                        .addComponent(lblViewSender)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(txtViewSender, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        ViewDialogLayout.setVerticalGroup(
            ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewDialogLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblView)
                .addGap(18, 18, 18)
                .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewResend)
                    .addComponent(btnViewDelete)
                    .addComponent(btnViewBlocking)
                    .addComponent(lblViewDate)
                    .addComponent(chbViewImportant))
                .addGap(18, 18, 18)
                .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtViewSender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViewSender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtViewReceiver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViewReceiver))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtViewTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViewTitle))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMainMail.setText("메인 화면");
        btnMainMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainMailActionPerformed(evt);
            }
        });
        getContentPane().add(btnMainMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 97, 35));

        btnWriteMail.setText("메일 쓰기");
        btnWriteMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWriteMailActionPerformed(evt);
            }
        });
        getContentPane().add(btnWriteMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 97, 35));

        btnReceiveMail.setText("받은 메일함");
        btnReceiveMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceiveMailActionPerformed(evt);
            }
        });
        getContentPane().add(btnReceiveMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 97, 35));

        btnSendMail.setText("보낸 메일함");
        btnSendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMailActionPerformed(evt);
            }
        });
        getContentPane().add(btnSendMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 97, 35));

        btnImportantMail.setText("중요 메일함");
        btnImportantMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportantMailActionPerformed(evt);
            }
        });
        getContentPane().add(btnImportantMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 97, 35));

        btnTrashMail.setText("휴지통");
        btnTrashMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrashMailActionPerformed(evt);
            }
        });
        getContentPane().add(btnTrashMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 97, 35));

        lblLocation.setFont(new java.awt.Font("굴림", 1, 36)); // NOI18N
        lblLocation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLocation.setText("메인 화면");
        getContentPane().add(lblLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 19, 292, 46));

        MainMail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMainUserInfo.setFont(new java.awt.Font("굴림", 1, 18)); // NOI18N
        lblMainUserInfo.setText("로그인정보");
        jPanel1.add(lblMainUserInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblMainUserId.setFont(new java.awt.Font("굴림", 1, 14)); // NOI18N
        lblMainUserId.setText("아이디 : ");
        jPanel1.add(lblMainUserId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 890, -1));

        lblMainUserName.setFont(new java.awt.Font("굴림", 1, 14)); // NOI18N
        lblMainUserName.setText("이름 :");
        jPanel1.add(lblMainUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 890, -1));

        MainMail.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 910, 120));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMainBlockList.setFont(new java.awt.Font("굴림", 1, 18)); // NOI18N
        lblMainBlockList.setText("차단 목록");
        jPanel2.add(lblMainBlockList, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 30));

        btnMainBlockCancel.setText("차단 해제");
        btnMainBlockCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainBlockCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnMainBlockCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 90, -1));

        lblMainBlockName.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblMainBlockName.setText(" 이름");
        jPanel2.add(lblMainBlockName, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 40, 40, -1));

        listMainBlockName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listMainBlockName.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listMainBlockNameValueChanged(evt);
            }
        });
        jScrollPane17.setViewportView(listMainBlockName);

        jPanel2.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 220, 360));

        lblMainBlockId.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblMainBlockId.setText("아이디");
        jPanel2.add(lblMainBlockId, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, -1, -1));

        listMainBlockId.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listMainBlockIdValueChanged(evt);
            }
        });
        jScrollPane18.setViewportView(listMainBlockId);

        jPanel2.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 300, 360));

        lblMainBlockDate.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblMainBlockDate.setText(" 날짜");
        jPanel2.add(lblMainBlockDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 40, -1));

        listMainBlockDate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listMainBlockDateValueChanged(evt);
            }
        });
        jScrollPane19.setViewportView(listMainBlockDate);

        jPanel2.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 390, 360));

        MainMail.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 910, 430));

        MainTab.addTab("메인", MainMail);

        WriteMail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txaWriteContent.setColumns(20);
        txaWriteContent.setRows(5);
        jScrollPane1.setViewportView(txaWriteContent);

        WriteMail.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 224, 921, 387));

        txtWriteSender.setEditable(false);
        WriteMail.add(txtWriteSender, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 842, -1));
        WriteMail.add(txtWriteReceiver, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 126, 842, -1));
        WriteMail.add(txtWriteTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 164, 842, -1));

        lblWriteSender.setText("보내는 사람");
        WriteMail.add(lblWriteSender, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 90, 70, 20));

        lblWriteReceiver.setText(" 받는 사람");
        WriteMail.add(lblWriteReceiver, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 70, 30));

        lblWriteTitle.setText("제목");
        WriteMail.add(lblWriteTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 165, 70, 20));

        btnWriteSend.setText("보내기");
        btnWriteSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWriteSendActionPerformed(evt);
            }
        });
        WriteMail.add(btnWriteSend, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 617, 153, 35));

        MainTab.addTab("메일 쓰기", WriteMail);

        ReceiveMail.setMinimumSize(new java.awt.Dimension(100, 100));
        ReceiveMail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ReceiveMail.add(txtReceiveSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 780, 30));

        btnReceiveSearch.setText("검색");
        btnReceiveSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceiveSearchActionPerformed(evt);
            }
        });
        ReceiveMail.add(btnReceiveSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, -1, 30));

        cmbReceiveSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "제목", "보낸이", "내용", " " }));
        ReceiveMail.add(cmbReceiveSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));

        rBtnMailGroup.add(rBtnAll);
        rBtnAll.setSelected(true);
        rBtnAll.setText("모든 메일");
        rBtnAll.setToolTipText("");
        rBtnAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rBtnAllMouseClicked(evt);
            }
        });
        ReceiveMail.add(rBtnAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        rBtnMailGroup.add(rBtnReadOnly);
        rBtnReadOnly.setText("읽은 메일");
        rBtnReadOnly.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rBtnReadOnlyMouseClicked(evt);
            }
        });
        ReceiveMail.add(rBtnReadOnly, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, -1));

        rBtnMailGroup.add(rBtnNotReadOnly);
        rBtnNotReadOnly.setText("안 읽은 메일");
        rBtnNotReadOnly.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rBtnNotReadOnlyMouseClicked(evt);
            }
        });
        ReceiveMail.add(rBtnNotReadOnly, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, -1, -1));

        listReceiveSender.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listReceiveSender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listReceiveSenderMouseClicked(evt);
            }
        });
        listReceiveSender.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listReceiveSenderValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listReceiveSender);

        ReceiveMail.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 90, 450));

        listReceiveTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listReceiveTitleMouseClicked(evt);
            }
        });
        listReceiveTitle.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listReceiveTitleValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(listReceiveTitle);

        ReceiveMail.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 640, 450));

        listReceiveDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listReceiveDateMouseClicked(evt);
            }
        });
        listReceiveDate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listReceiveDateValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(listReceiveDate);

        ReceiveMail.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 210, 450));

        lblReceiveSender.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblReceiveSender.setText("보낸이");
        ReceiveMail.add(lblReceiveSender, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        lblReceiveTitle.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblReceiveTitle.setText("제목");
        ReceiveMail.add(lblReceiveTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        lblReceiveDate.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblReceiveDate.setText("날짜");
        ReceiveMail.add(lblReceiveDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, -1, -1));

        btnReceiveDelete1.setText("삭제");
        btnReceiveDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceiveDelete1ActionPerformed(evt);
            }
        });
        ReceiveMail.add(btnReceiveDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, -1, 30));

        MainTab.addTab("받은 메일함", ReceiveMail);

        SendMail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbSendSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "제목", "받는이", "내용", " " }));
        SendMail.add(cmbSendSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));
        SendMail.add(txtSendSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 780, 30));

        btnSendSearch.setText("검색");
        btnSendSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendSearchActionPerformed(evt);
            }
        });
        SendMail.add(btnSendSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, -1, 30));

        btnSendDelete.setText("삭제");
        btnSendDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendDeleteActionPerformed(evt);
            }
        });
        SendMail.add(btnSendDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, -1, 30));

        listSendReceiver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listSendReceiver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSendReceiverMouseClicked(evt);
            }
        });
        listSendReceiver.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSendReceiverValueChanged(evt);
            }
        });
        jScrollPane10.setViewportView(listSendReceiver);

        SendMail.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 90, 450));

        listSendTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSendTitleMouseClicked(evt);
            }
        });
        listSendTitle.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSendTitleValueChanged(evt);
            }
        });
        jScrollPane11.setViewportView(listSendTitle);

        SendMail.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 640, 450));

        listSendDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSendDateMouseClicked(evt);
            }
        });
        listSendDate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSendDateValueChanged(evt);
            }
        });
        jScrollPane12.setViewportView(listSendDate);

        SendMail.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 210, 450));

        lblSendReceiver.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblSendReceiver.setText("받는이");
        SendMail.add(lblSendReceiver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        lbISendTitle.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lbISendTitle.setText("제목");
        SendMail.add(lbISendTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        lblSendDate.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblSendDate.setText("날짜");
        SendMail.add(lblSendDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, -1, -1));

        MainTab.addTab("보낸 메일함", SendMail);

        ImportantMail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbImportantSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "제목", "보낸이", "내용", " " }));
        ImportantMail.add(cmbImportantSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));
        ImportantMail.add(txtImportantSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 780, 30));

        btnImportantSearch.setText("검색");
        btnImportantSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportantSearchActionPerformed(evt);
            }
        });
        ImportantMail.add(btnImportantSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, -1, 30));

        btnImportantDelete.setText("삭제");
        btnImportantDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportantDeleteActionPerformed(evt);
            }
        });
        ImportantMail.add(btnImportantDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, -1, 30));

        lblImportantSender.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblImportantSender.setText("보낸이");
        ImportantMail.add(lblImportantSender, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        lblImportantTitle.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblImportantTitle.setText("제목");
        ImportantMail.add(lblImportantTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        lblImportantDate.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblImportantDate.setText("날짜");
        ImportantMail.add(lblImportantDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, -1, -1));

        listImportantSender.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listImportantSender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listImportantSenderMouseClicked(evt);
            }
        });
        listImportantSender.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listImportantSenderValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(listImportantSender);

        ImportantMail.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 90, 450));

        listImportantTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listImportantTitleMouseClicked(evt);
            }
        });
        listImportantTitle.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listImportantTitleValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(listImportantTitle);

        ImportantMail.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 640, 450));

        listImportantDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listImportantDateMouseClicked(evt);
            }
        });
        listImportantDate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listImportantDateValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(listImportantDate);

        ImportantMail.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 210, 450));

        MainTab.addTab("중요 메일함", ImportantMail);

        TrashMail.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbTrashSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "제목", "보낸이", "내용", " " }));
        TrashMail.add(cmbTrashSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));
        TrashMail.add(txtTrashSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 780, 30));

        btnTrashSearch.setText("검색");
        btnTrashSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrashSearchActionPerformed(evt);
            }
        });
        TrashMail.add(btnTrashSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, -1, 30));

        btnTrashDelete.setText("삭제");
        btnTrashDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrashDeleteActionPerformed(evt);
            }
        });
        TrashMail.add(btnTrashDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, -1, 30));

        listTrashSender.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listTrashSender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTrashSenderMouseClicked(evt);
            }
        });
        listTrashSender.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listTrashSenderValueChanged(evt);
            }
        });
        jScrollPane13.setViewportView(listTrashSender);

        TrashMail.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 90, 450));

        listTrashTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTrashTitleMouseClicked(evt);
            }
        });
        listTrashTitle.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listTrashTitleValueChanged(evt);
            }
        });
        jScrollPane14.setViewportView(listTrashTitle);

        TrashMail.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 640, 450));

        listTrashDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTrashDateMouseClicked(evt);
            }
        });
        listTrashDate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listTrashDateValueChanged(evt);
            }
        });
        jScrollPane15.setViewportView(listTrashDate);

        TrashMail.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 210, 450));

        lblTrashSender.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblTrashSender.setText("보낸이");
        TrashMail.add(lblTrashSender, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        lblTrashTitle.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblTrashTitle.setText("제목");
        TrashMail.add(lblTrashTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        lblTrashDate.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lblTrashDate.setText("날짜");
        TrashMail.add(lblTrashDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, -1, -1));

        btnTrashRestore.setText("복원");
        btnTrashRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrashRestoreActionPerformed(evt);
            }
        });
        TrashMail.add(btnTrashRestore, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 130, -1, 30));

        MainTab.addTab("휴지통", TrashMail);

        getContentPane().add(MainTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, -30, 950, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMainMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainMailActionPerformed
        lblLocation.setText("메인 화면");
        lblMainUserId.setText("아이디 : " + user.getUserId());
        lblMainUserName.setText("이름 : " + user.getUserName());
        MainTab.setSelectedIndex(0);
        
        listSelect = -1;
        old_click = -1;
        spamList();
        
        refreshList();
    }//GEN-LAST:event_btnMainMailActionPerformed

    private void btnWriteMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWriteMailActionPerformed
        lblLocation.setText("메일 쓰기");
        txtWriteSender.setBackground(new java.awt.Color(240, 240, 240));
        MainTab.setSelectedIndex(1);
        
        txtWriteSender.setText(user.getUserName() + "(" + user.getUserId() + ")");
        
        listSelect = -1;
        old_click = -1;
    }//GEN-LAST:event_btnWriteMailActionPerformed

    private void btnReceiveMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiveMailActionPerformed
        lblLocation.setText("받은 메일함");
        MainTab.setSelectedIndex(2);
        
        listSelect = -1;
        old_click = -1;
        allMails();
        
        refreshList();
        rBtnAll.setSelected(true);
        txtReceiveSearch.setText("");
        cmbReceiveSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnReceiveMailActionPerformed

    private void btnWriteSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWriteSendActionPerformed
        if(txtWriteReceiver.getText().trim().equals("")){
            showMessageDialog(null, "수신자를 입력하세요.");
        }else if(txtWriteTitle.getText().trim().equals("")){
            showMessageDialog(null, "제목을 입력하세요.");
        }else if(txaWriteContent.getText().trim().equals("")){
            showMessageDialog(null, "내용을 입력하세요.");
        }else if(txtWriteReceiver.getText().trim().equals(user.getUserId())){
            showMessageDialog(null, "자신에게 메일을 보낼 수 없습니다.");
        }else{
            MailDAO mailDAO = new MailDAO();
            int result_of_sendMail; // 메일 전송 결과 저장
            result_of_sendMail = mailDAO.sendMail(user.getUserId(), txtWriteReceiver.getText().trim(), txtWriteTitle.getText().trim(), txaWriteContent.getText());
            
            switch(result_of_sendMail){
                case 1:
                    showMessageDialog(null, "메일 전송에 성공하였습니다.");
                    txtWriteReceiver.setText("");
                    txtWriteTitle.setText("");
                    txaWriteContent.setText("");
                    break;
                case 0:
                    showMessageDialog(null, "존재하지 않는 수신자입니다.");
                    break;
                case -1:
                    showMessageDialog(null, "메일 전송에 실패하였습니다.");
                    break;
            }
            
        }
    }//GEN-LAST:event_btnWriteSendActionPerformed

    private void btnReceiveSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiveSearchActionPerformed
        // 받은 메일함에서 검색 버튼 클릭 시
        if (rBtnAll.isSelected()) {
            allMails();
        } else if (rBtnReadOnly.isSelected()) {
            readMails();
        } else if (rBtnNotReadOnly.isSelected()) {
            notReadMails();
        }
        
        searchMails(cmbReceiveSearch.getSelectedIndex(), txtReceiveSearch.getText());
        
        refreshList();
    }//GEN-LAST:event_btnReceiveSearchActionPerformed

    private void listReceiveTitleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listReceiveTitleValueChanged
        listSelect = listReceiveTitle.getSelectedIndex();
        
        // 받은 메일함에서 선택된 list 맞추기
        listRowSelect();
    }//GEN-LAST:event_listReceiveTitleValueChanged

    private void listReceiveSenderValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listReceiveSenderValueChanged
        listSelect = listReceiveSender.getSelectedIndex();
        
        // 받은 메일함에서 선택된 list 맞추기
        listRowSelect();
    }//GEN-LAST:event_listReceiveSenderValueChanged

    private void listReceiveDateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listReceiveDateValueChanged
        listSelect = listReceiveDate.getSelectedIndex();
        
        // 받은 메일함에서 선택된 list 맞추기
        listRowSelect();
    }//GEN-LAST:event_listReceiveDateValueChanged

    private void rBtnReadOnlyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rBtnReadOnlyMouseClicked
        // 받은 메일함에서 읽은 메일 선택
        readMails();     
        
        refreshList();
        txtReceiveSearch.setText("");
        cmbReceiveSearch.setSelectedIndex(0);
    }//GEN-LAST:event_rBtnReadOnlyMouseClicked

    private void rBtnNotReadOnlyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rBtnNotReadOnlyMouseClicked
        // 받은 메일함에서 안 읽은 메일 선택
        notReadMails();    
        
        refreshList();
        txtReceiveSearch.setText("");
        cmbReceiveSearch.setSelectedIndex(0);
    }//GEN-LAST:event_rBtnNotReadOnlyMouseClicked

    private void rBtnAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rBtnAllMouseClicked
        // 받은 메일함에서 모든 메일 선택
        allMails();     
        
        refreshList();
        txtReceiveSearch.setText("");
        cmbReceiveSearch.setSelectedIndex(0);
    }//GEN-LAST:event_rBtnAllMouseClicked

    private void listReceiveTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listReceiveTitleMouseClicked
        // 받은 메일함에서 메일(제목영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) { 
            old_click = -1;
        } else if (old_click == listReceiveTitle.getSelectedIndex()) {
            showMail(true);
            old_click = -1;
        } else {
            old_click = listReceiveTitle.getSelectedIndex();
        }
    }//GEN-LAST:event_listReceiveTitleMouseClicked

    private void listReceiveSenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listReceiveSenderMouseClicked
        // 받은 메일함에서 메일(보낸이영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listReceiveSender.getSelectedIndex()) {
            showMail(true);
            old_click = -1;
        } else {
            old_click = listReceiveSender.getSelectedIndex();
        }
    }//GEN-LAST:event_listReceiveSenderMouseClicked

    private void listReceiveDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listReceiveDateMouseClicked
        // 받은 메일함에서 메일(날짜영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listReceiveDate.getSelectedIndex()) {
            showMail(true);
            old_click = -1;
        } else {
            old_click = listReceiveDate.getSelectedIndex();
        }
    }//GEN-LAST:event_listReceiveDateMouseClicked

    private void ViewDialogComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ViewDialogComponentShown
        // 메일 보기 창 뜰 시
        Mail mail = new MailDAO().getMail((int)((Vector)nowVector.get(0)).get(listSelect)); // 선택된 메일 번호를 int로 형변환까지
        new MailDAO().readMail(mail.getMailNo()); // 현재 선택된 메일 읽음 처리
        
        lblViewDate.setText(mail.getSendDate());
        txtViewSender.setText(new UserDAO().getName(mail.getSenderId()) + "(" + mail.getSenderId() + ")");
        txtViewReceiver.setText(new UserDAO().getName(mail.getReceiverId()) + "(" + mail.getReceiverId() + ")");
        txtViewTitle.setText(mail.getTitle());
        txaViewContent.setText(mail.getContent());
        
        if (mail.getIsImportant() == 0) {
            // 중요메일이 아닐 때
            chbViewImportant.setSelected(false); // 체크박스 해제
        } else {
            // 중요메일일 때 (== 1)
            chbViewImportant.setSelected(true); // 체크박스 체크
        }
        
        if (MainTab.getSelectedIndex() == 2 && rBtnNotReadOnly.isSelected()) {
            // 안 읽은 메일함인 경우에만 새로고침
            for (int i = 0; i < nowVector.size(); i++) {
                ((Vector)nowVector.get(i)).removeElementAt(listReceiveTitle.getSelectedIndex()); // 현재 선택된 메일을 nowVector에서 제거
            }
            refreshList();
        }
    }//GEN-LAST:event_ViewDialogComponentShown

    private void chbViewImportantStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chbViewImportantStateChanged
        // 중요메일 체크박스 선택 시
        new MailDAO().importantMail((int)((Vector)nowVector.get(0)).get(listReceiveTitle.getSelectedIndex()), chbViewImportant.isSelected());
    }//GEN-LAST:event_chbViewImportantStateChanged

    private void btnViewDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDeleteActionPerformed
        // 메일 보기 내에서 삭제 버튼 클릭 시
        ViewDialog.show(false); // 현재 보고있는 메일 창 닫기
        int tabIndex = MainTab.getSelectedIndex(); // 현재 선택된 메인 탭 index 가져오기
        
        if (tabIndex == 2) {
            // 받은 메일함에서 보는 메일을 삭제 누른 경우
            deleteAfterVector(1); // 삭제 처리 후 nowVector 갱신 (isDelete에 1bit추가)
            
            refreshList();
            
        } else if (tabIndex == 3) {
            // 보낸 메일함에서 보는 메일을 삭제 누른 경우
            deleteAfterVector(4); // 삭제 처리 후 nowVector 갱신 (isDelete에 4bit 추가)
            
            refreshList();
            
        } else if (tabIndex == 4) {
            // 중요 메일함에서 보는 메일을 삭제 누른 경우
            deleteAfterVector(1); // 삭제 처리 후 nowVector 갱신 (isDelete에 1bit 추가)
            
            refreshList();
        
        } else if (tabIndex == 5) {
            // 휴지통에서 보는 메일을 삭제 누른 경우
            deleteAfterVector(2); // 삭제 처리 후 nowVector 갱신 (isDelete에 2bit 추가)
            
            refreshList();
        }
    }//GEN-LAST:event_btnViewDeleteActionPerformed

    private void btnSendMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMailActionPerformed
        // 보낸 메일함 버튼 클릭 시
        lblLocation.setText("보낸 메일함");
        MainTab.setSelectedIndex(3);
        
        listSelect = -1;
        old_click = -1;
        sendMails();
        
        refreshList();
        txtSendSearch.setText("");
        cmbSendSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnSendMailActionPerformed

    private void btnImportantMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportantMailActionPerformed
        // 중요 메일함 버튼 클릭 시
        lblLocation.setText("중요 메일함");
        MainTab.setSelectedIndex(4);
        
        listSelect = -1;
        old_click = -1;   
        importantMails();
        
        refreshList();
        txtImportantSearch.setText("");
        cmbImportantSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnImportantMailActionPerformed

    private void btnTrashMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrashMailActionPerformed
        // 휴지통 버튼 클릭 시
        lblLocation.setText("휴지통");
        MainTab.setSelectedIndex(5);
        
        listSelect = -1;
        old_click = -1;
        trashMails();
        
        refreshList();
        txtTrashSearch.setText("");
        cmbTrashSearch.setSelectedIndex(0);
    }//GEN-LAST:event_btnTrashMailActionPerformed

    private void listImportantDateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listImportantDateValueChanged
        listSelect = listImportantDate.getSelectedIndex();
        
        // 중요 메일함에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listImportantDateValueChanged

    private void listImportantTitleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listImportantTitleValueChanged
        listSelect = listImportantTitle.getSelectedIndex();
        
        // 중요 메일함에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listImportantTitleValueChanged

    private void listImportantSenderValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listImportantSenderValueChanged
        listSelect = listImportantSender.getSelectedIndex();
        
        // 중요 메일함에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listImportantSenderValueChanged

    private void listImportantSenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listImportantSenderMouseClicked
        // 중요 메일함에서 메일(보낸이영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listImportantSender.getSelectedIndex()) {
            showMail(true);
            old_click = -1;
        } else {
            old_click = listImportantSender.getSelectedIndex();
        }
    }//GEN-LAST:event_listImportantSenderMouseClicked

    private void listImportantTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listImportantTitleMouseClicked
        // 중요 메일함에서 메일(제목영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listImportantTitle.getSelectedIndex()) {
            showMail(true);
            old_click = -1;
        } else {
            old_click = listImportantTitle.getSelectedIndex();
        }
    }//GEN-LAST:event_listImportantTitleMouseClicked

    private void listImportantDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listImportantDateMouseClicked
        // 중요 메일함에서 메일(날짜영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listImportantDate.getSelectedIndex()) {
            showMail(true);
            old_click = -1;
        } else {
            old_click = listImportantDate.getSelectedIndex();
        }
    }//GEN-LAST:event_listImportantDateMouseClicked

    private void btnImportantSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportantSearchActionPerformed
        // 중요 메일함에서 검색 버튼 클릭 시
        importantMails();
        
        searchMails(cmbImportantSearch.getSelectedIndex(), txtImportantSearch.getText());
        
        refreshList();
    }//GEN-LAST:event_btnImportantSearchActionPerformed

    private void btnImportantDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportantDeleteActionPerformed
        // 중요 메일함에서 삭제 버튼 클릭 시
        deleteAfterVector(1); // 삭제 처리 후 nowVector 갱신 (isDelete에 1bit 추가)
        
        refreshList();
    }//GEN-LAST:event_btnImportantDeleteActionPerformed

    private void listSendReceiverValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSendReceiverValueChanged
        listSelect = listSendReceiver.getSelectedIndex();
        
        // 보낸 메일함에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listSendReceiverValueChanged

    private void listSendTitleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSendTitleValueChanged
        listSelect = listSendTitle.getSelectedIndex();
        
        // 보낸 메일함에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listSendTitleValueChanged

    private void listSendDateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSendDateValueChanged
        listSelect = listSendDate.getSelectedIndex();
        
        // 보낸 메일함에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listSendDateValueChanged

    private void listSendReceiverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSendReceiverMouseClicked
        // 보낸 메일함에서 메일(받는이영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listSendReceiver.getSelectedIndex()) {
            showMail(false);
            old_click = -1;
        } else {
            old_click = listSendReceiver.getSelectedIndex();
        }
    }//GEN-LAST:event_listSendReceiverMouseClicked

    private void listSendTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSendTitleMouseClicked
        // 보낸 메일함에서 메일(제목영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listSendTitle.getSelectedIndex()) {
            showMail(false);
            old_click = -1;
        } else {
            old_click = listSendTitle.getSelectedIndex();
        }
    }//GEN-LAST:event_listSendTitleMouseClicked

    private void listSendDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSendDateMouseClicked
        // 보낸 메일함에서 메일(날짜영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listSendDate.getSelectedIndex()) {
            showMail(false);
            old_click = -1;
        } else {
            old_click = listSendDate.getSelectedIndex();
        }
    }//GEN-LAST:event_listSendDateMouseClicked

    private void btnSendSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendSearchActionPerformed
        // 보낸 메일함에서 검색 버튼 클릭 시
        sendMails();
        
        searchMails(cmbSendSearch.getSelectedIndex(), txtSendSearch.getText());
        
        refreshList();
    }//GEN-LAST:event_btnSendSearchActionPerformed

    private void btnSendDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendDeleteActionPerformed
        // 보낸 메일함에서 삭제 버튼 클릭 시
        deleteAfterVector(4); // 삭제 처리 후 nowVector 갱신 (isDelete에 4bit 추가)
        
        refreshList();
    }//GEN-LAST:event_btnSendDeleteActionPerformed

    private void listTrashSenderValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listTrashSenderValueChanged
        listSelect = listTrashSender.getSelectedIndex();
        
         // 휴지통에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listTrashSenderValueChanged

    private void listTrashTitleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listTrashTitleValueChanged
        listSelect = listTrashTitle.getSelectedIndex();
        
         // 휴지통에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listTrashTitleValueChanged

    private void listTrashDateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listTrashDateValueChanged
        listSelect = listTrashDate.getSelectedIndex();
        
         // 휴지통에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listTrashDateValueChanged

    private void listTrashSenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTrashSenderMouseClicked
        // 휴지통에서 메일(보낸이영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listTrashSender.getSelectedIndex()) {
            showMail(true);
            chbViewImportant.show(false);
            old_click = -1;
        } else {
            old_click = listTrashSender.getSelectedIndex();
        }
    }//GEN-LAST:event_listTrashSenderMouseClicked

    private void listTrashTitleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTrashTitleMouseClicked
        // 휴지통에서 메일(제목영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listTrashTitle.getSelectedIndex()) {
            showMail(true);
            chbViewImportant.show(false);
            old_click = -1;
        } else {
            old_click = listTrashTitle.getSelectedIndex();
        }
    }//GEN-LAST:event_listTrashTitleMouseClicked

    private void listTrashDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTrashDateMouseClicked
        // 휴지통에서 메일(날짜영역) 클릭 시
        if (((Vector)nowVector.get(0)).isEmpty()) {
            old_click = -1;
        } else if (old_click == listTrashDate.getSelectedIndex()) {
            showMail(true);
            chbViewImportant.show(false);
            old_click = -1;
        } else {
            old_click = listTrashDate.getSelectedIndex();
        }
    }//GEN-LAST:event_listTrashDateMouseClicked

    private void btnTrashSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrashSearchActionPerformed
        // 휴지통에서 검색 버튼 클릭 시
        trashMails();
        
        searchMails(cmbTrashSearch.getSelectedIndex(), txtTrashSearch.getText());
        
        refreshList();
    }//GEN-LAST:event_btnTrashSearchActionPerformed

    private void btnTrashDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrashDeleteActionPerformed
        // 휴지통에서 삭제 버튼 클릭 시
        deleteAfterVector(2); // 삭제 처리 후 nowVector 갱신 (isDelete에 2bit 추가)
        
        refreshList();
    }//GEN-LAST:event_btnTrashDeleteActionPerformed

    private void btnViewResendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewResendActionPerformed
        // 메일 보기 화면에서 답장 버튼 클릭 시
        Mail mail = new MailDAO().getMail((int)((Vector)nowVector.get(0)).get(listSelect));
        String resendSender = txtViewReceiver.getText();
        String resendReceiver = mail.getSenderId();
        String resendContent = txaViewContent.getText() + "\n" + lblViewDate.getText() + "\n----------------------------\n";
        ViewDialog.show(false); // 현재 보고있는 메일 창 닫기
        
        MainTab.setSelectedIndex(1); // 메일 쓰기 창으로 이동
        
        txtWriteSender.setText(resendSender);
        txtWriteReceiver.setText(resendReceiver);
        txaWriteContent.setText(resendContent);
    }//GEN-LAST:event_btnViewResendActionPerformed

    private void btnViewBlockingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewBlockingActionPerformed
        // 메일 보기 화면에서 차단 버튼 클릭 시
        Mail mail = new MailDAO().getMail((int)((Vector)nowVector.get(0)).get(listSelect));
        new SpamDAO().addSpam(user.getUserId(), mail.getSenderId());
        
        for (int i = 0; i < ((Vector)nowVector.get(1)).size(); i++) {
            if (((Vector)nowVector.get(1)).get(i).equals(mail.getSenderId())) {
                // 현재 벡터에서 스팸처리한 보낸이가 보낸 메일 제거
                for (int k = 0; k < nowVector.size(); k++) {
                    ((Vector)nowVector.get(k)).removeElementAt(i);
                }
            }
        }
        
        ViewDialog.show(false); // 현재 보고있는 메일 창 닫기
        
        refreshList();
    }//GEN-LAST:event_btnViewBlockingActionPerformed

    private void listMainBlockNameValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listMainBlockNameValueChanged
        listSelect = listMainBlockName.getSelectedIndex();
        
         // 메인 화면에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listMainBlockNameValueChanged

    private void listMainBlockIdValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listMainBlockIdValueChanged
        listSelect = listMainBlockId.getSelectedIndex();
        
         // 메인 화면에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listMainBlockIdValueChanged

    private void listMainBlockDateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listMainBlockDateValueChanged
        listSelect = listMainBlockDate.getSelectedIndex();
        
         // 메인 화면에서 선택된 리스트 맞추기
        listRowSelect();
    }//GEN-LAST:event_listMainBlockDateValueChanged

    private void btnMainBlockCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainBlockCancelActionPerformed
        // 메인 화면에서 차단 해제 버튼 클릭 시
        new SpamDAO().spamCancel(user.getUserId(), listMainBlockId.getSelectedValue());
        
        for (int i = 0; i < nowVector.size(); i++) {
            ((Vector)nowVector.get(i)).removeElementAt(listSelect);        
        }
        
        refreshList();
    }//GEN-LAST:event_btnMainBlockCancelActionPerformed

    private void btnReceiveDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiveDelete1ActionPerformed
        // 휴지통에서 복원 버튼 클릭 시
        new MailDAO().restoreMail((int)((Vector)nowVector.get(0)).get(listSelect));

        for (int i = 0; i < nowVector.size(); i++) {
            ((Vector)nowVector.get(i)).removeElementAt(listSelect);
        }
        
        refreshList();
    }//GEN-LAST:event_btnReceiveDelete1ActionPerformed

    private void btnTrashRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrashRestoreActionPerformed
        // 휴지통에서 복원 버튼 클릭 시
        new MailDAO().restoreMail((int)((Vector)nowVector.get(0)).get(listSelect));
        
        for (int i = 0; i < nowVector.size(); i++) {
            ((Vector)nowVector.get(i)).removeElementAt(listSelect);
        }
        
        refreshList();
    }//GEN-LAST:event_btnTrashRestoreActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ImportantMail;
    private javax.swing.JPanel MainMail;
    private javax.swing.JTabbedPane MainTab;
    private javax.swing.JPanel ReceiveMail;
    private javax.swing.JPanel SendMail;
    private javax.swing.JPanel TrashMail;
    private javax.swing.JDialog ViewDialog;
    private javax.swing.JPanel WriteMail;
    private javax.swing.JButton btnImportantDelete;
    private javax.swing.JButton btnImportantMail;
    private javax.swing.JButton btnImportantSearch;
    private javax.swing.JButton btnMainBlockCancel;
    private javax.swing.JButton btnMainMail;
    private javax.swing.JButton btnReceiveDelete1;
    private javax.swing.JButton btnReceiveMail;
    private javax.swing.JButton btnReceiveSearch;
    private javax.swing.JButton btnSendDelete;
    private javax.swing.JButton btnSendMail;
    private javax.swing.JButton btnSendSearch;
    private javax.swing.JButton btnTrashDelete;
    private javax.swing.JButton btnTrashMail;
    private javax.swing.JButton btnTrashRestore;
    private javax.swing.JButton btnTrashSearch;
    private javax.swing.JButton btnViewBlocking;
    private javax.swing.JButton btnViewDelete;
    private javax.swing.JButton btnViewResend;
    private javax.swing.JButton btnWriteMail;
    private javax.swing.JButton btnWriteSend;
    private javax.swing.JCheckBox chbViewImportant;
    private javax.swing.JComboBox<String> cmbImportantSearch;
    private javax.swing.JComboBox<String> cmbReceiveSearch;
    private javax.swing.JComboBox<String> cmbSendSearch;
    private javax.swing.JComboBox<String> cmbTrashSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbISendTitle;
    private javax.swing.JLabel lblImportantDate;
    private javax.swing.JLabel lblImportantSender;
    private javax.swing.JLabel lblImportantTitle;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblMainBlockDate;
    private javax.swing.JLabel lblMainBlockId;
    private javax.swing.JLabel lblMainBlockList;
    private javax.swing.JLabel lblMainBlockName;
    private javax.swing.JLabel lblMainUserId;
    private javax.swing.JLabel lblMainUserInfo;
    private javax.swing.JLabel lblMainUserName;
    private javax.swing.JLabel lblReceiveDate;
    private javax.swing.JLabel lblReceiveSender;
    private javax.swing.JLabel lblReceiveTitle;
    private javax.swing.JLabel lblSendDate;
    private javax.swing.JLabel lblSendReceiver;
    private javax.swing.JLabel lblTrashDate;
    private javax.swing.JLabel lblTrashSender;
    private javax.swing.JLabel lblTrashTitle;
    private javax.swing.JLabel lblView;
    private javax.swing.JLabel lblViewDate;
    private javax.swing.JLabel lblViewReceiver;
    private javax.swing.JLabel lblViewSender;
    private javax.swing.JLabel lblViewTitle;
    private javax.swing.JLabel lblWriteReceiver;
    private javax.swing.JLabel lblWriteSender;
    private javax.swing.JLabel lblWriteTitle;
    private javax.swing.JList<String> listImportantDate;
    private javax.swing.JList<String> listImportantSender;
    private javax.swing.JList<String> listImportantTitle;
    private javax.swing.JList<String> listMainBlockDate;
    private javax.swing.JList<String> listMainBlockId;
    private javax.swing.JList<String> listMainBlockName;
    private javax.swing.JList<String> listReceiveDate;
    private javax.swing.JList<String> listReceiveSender;
    private javax.swing.JList<String> listReceiveTitle;
    private javax.swing.JList<String> listSendDate;
    private javax.swing.JList<String> listSendReceiver;
    private javax.swing.JList<String> listSendTitle;
    private javax.swing.JList<String> listTrashDate;
    private javax.swing.JList<String> listTrashSender;
    private javax.swing.JList<String> listTrashTitle;
    private javax.swing.JRadioButton rBtnAll;
    private javax.swing.ButtonGroup rBtnMailGroup;
    private javax.swing.JRadioButton rBtnNotReadOnly;
    private javax.swing.JRadioButton rBtnReadOnly;
    private javax.swing.JTextArea txaViewContent;
    private javax.swing.JTextArea txaWriteContent;
    private javax.swing.JTextField txtImportantSearch;
    private javax.swing.JTextField txtReceiveSearch;
    private javax.swing.JTextField txtSendSearch;
    private javax.swing.JTextField txtTrashSearch;
    private javax.swing.JTextField txtViewReceiver;
    private javax.swing.JTextField txtViewSender;
    private javax.swing.JTextField txtViewTitle;
    private javax.swing.JTextField txtWriteReceiver;
    private javax.swing.JTextField txtWriteSender;
    private javax.swing.JTextField txtWriteTitle;
    // End of variables declaration//GEN-END:variables
}
