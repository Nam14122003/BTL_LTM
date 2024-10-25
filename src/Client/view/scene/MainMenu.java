/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.scene;

import server.db.layers.dto.GameMatchTable;
import client.RunClient;
import client.view.helper.LookAndFeel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import shared.helper.CountDownTimer;
import server.db.layers.dal.GameMatchDAL;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class MainMenu extends javax.swing.JFrame {

    public enum State {
        DEFAULT,
        FINDING_MATCH,
        WAITING_ACCEPT,
        WAITING_COMPETITOR_ACCEPT
    };

    CountDownTimer acceptPairMatchTimer;
    CountDownTimer waitingPairTimer;
    final int acceptWaitingTime = 15;

    boolean pairAcceptChoosed = false;

    /**
     * Creates new form MainMenuF
     */
    public MainMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Caro Đại Chiến - Trang Chủ - "+ RunClient.socketHandler.getLoginUser());
           try {
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLw-jhsZE2DjYrRMcg7lXaRnhiYpjwKkRJ2w&s");
            Image logo = ImageIO.read(url);  
            setIconImage(logo); 
        } catch (IOException e) {
               System.out.println("1");
        }
        this.setSize(1200, 800);
        // default to hidden
        setDisplayState(State.DEFAULT);
        Font buttonFont = new Font("Arial", Font.BOLD, 18); // You can choose the font and size
        this.setLocation(600, 350);
        JLabel lblTitle = new JLabel("Đại chiến Caro");
        lblTitle.setOpaque(true); // Để có thể thấy màu nền
        lblTitle.setBackground(Color.RED); // Màu nền đỏ
        lblTitle.setForeground(Color.WHITE); // Màu chữ trắng
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24)); // Đặt font chữ cho tiêu đề
       jPanel2.setBackground(new java.awt.Color(240, 240, 240));
        jPanel1.setBackground(new java.awt.Color(240, 240, 240));
        btnFindMatch.setFont(buttonFont);
        btnFindMatch.setPreferredSize(new Dimension(200, 50)); // Width, Height

        btnRanking.setFont(buttonFont);
        btnRanking.setPreferredSize(new Dimension(200, 50));

        btnLogout.setFont(buttonFont);
        btnLogout.setPreferredSize(new Dimension(200, 50));

        btnProfile.setFont(buttonFont);
        btnProfile.setPreferredSize(new Dimension(200, 50));

        btnAcceptPairMatch.setFont(buttonFont);
        btnAcceptPairMatch.setPreferredSize(new Dimension(200, 50));

        btnDeclinePairMatch.setFont(buttonFont);
        btnDeclinePairMatch.setPreferredSize(new Dimension(200, 50));

        btnCancelFindMatch.setFont(buttonFont);
        btnCancelFindMatch.setPreferredSize(new Dimension(200, 50));

        btnRefreshListRoom.setFont(buttonFont);
        btnRefreshListRoom.setPreferredSize(new Dimension(200, 50));
        setComponentBackgrounds(this.getContentPane(), new Color(240, 240, 240));
        plBtns.setLayout(new FlowLayout());
    }

    public void setListRoom(Vector vdata, Vector vheader) {
        tbListRoom.setModel(new DefaultTableModel(vdata, vheader));
    }

    public void foundMatch(String competitorName) {
        setDisplayState(State.WAITING_ACCEPT);
        lbFoundMatch.setText("Đã tìm thấy đối thủ " + competitorName + " . Vào ngay?");
    }

    private void stopAcceptPairMatchTimer() {
        if (acceptPairMatchTimer != null) {
            acceptPairMatchTimer.cancel();
        }
    }

    private void startAcceptPairMatchTimer() {
        acceptPairMatchTimer = new CountDownTimer(acceptWaitingTime);
        acceptPairMatchTimer.setTimerCallBack(
                // end callback
                (Callable) () -> {
                    // reset acceptPairMatchTimer
                    acceptPairMatchTimer.restart();
                    acceptPairMatchTimer.pause();

                    // tự động từ chối nếu quá thời gian mà chưa chọn đồng ý
                    if (!pairAcceptChoosed) {
                        RunClient.socketHandler.declinePairMatch();
                    }
                    return null;
                },
                // tick callback
                (Callable) () -> {
                    lbTimerPairMatch.setText(acceptPairMatchTimer.getCurrentTick() + "s");
                    return null;
                },
                // tick interval
                1
        );
    }

    private void stopWaitingPairMatchTimer() {
        if (waitingPairTimer != null) {
            waitingPairTimer.cancel();
        }
    }

    private void startWaitingPairMatchTimer() {
        waitingPairTimer = new CountDownTimer(5 * 60); // 5p
        waitingPairTimer.setTimerCallBack(
                (Callable) () -> {
                    setDisplayState(State.DEFAULT);
                    JOptionPane.showMessageDialog(this, "Mãi chả thấy ai tìm trận.. Xui");
                    return null;
                },
                (Callable) () -> {
                    lbFindMatch.setText("Đang tìm trận.. " + (5 * 60 - waitingPairTimer.getCurrentTick()) + "s");
                    return null;
                },
                1
        );
    }

    public void setDisplayState(State s) {

        // mở hết lên
        LookAndFeel.enableComponents(plBtns, true);
        plFoundMatch.setVisible(true);
        plFindingMatch.setVisible(true);
        btnAcceptPairMatch.setEnabled(true);
        btnDeclinePairMatch.setEnabled(true);
        btnLogout.setEnabled(true);

        // xong đóng từng cái tùy theo state
        switch (s) {
            case DEFAULT:
                stopWaitingPairMatchTimer();
                stopAcceptPairMatchTimer();
                plFindingMatch.setVisible(false);
                plFoundMatch.setVisible(false);
                break;

            case FINDING_MATCH:
                startWaitingPairMatchTimer();
                stopAcceptPairMatchTimer();
                LookAndFeel.enableComponents(plBtns, false);
                plFoundMatch.setVisible(false);
                btnLogout.setEnabled(false);
                break;

            case WAITING_ACCEPT:
                stopWaitingPairMatchTimer();
                startAcceptPairMatchTimer();
                pairAcceptChoosed = false;
                LookAndFeel.enableComponents(plBtns, false);
                plFindingMatch.setVisible(false);
                btnLogout.setEnabled(false);
                break;

            case WAITING_COMPETITOR_ACCEPT:
                LookAndFeel.enableComponents(plBtns, false);
                pairAcceptChoosed = true;
                plFindingMatch.setVisible(false);
                btnAcceptPairMatch.setEnabled(false);
                btnDeclinePairMatch.setEnabled(false);
                btnLogout.setEnabled(false);
                lbFoundMatch.setText("Đang chờ đối thủ..");
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        plBtns = new javax.swing.JPanel();
        btnFindMatch = new javax.swing.JButton();
        btnRanking = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        plFindingMatch = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        lbFindMatch = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        btnCancelFindMatch = new javax.swing.JButton();
        tpRoomAndUser = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListRoom = new javax.swing.JTable();
        btnRefreshListRoom = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        plFoundMatch = new javax.swing.JPanel();
        lbFoundMatch = new javax.swing.JLabel();
        btnDeclinePairMatch = new javax.swing.JButton();
        btnAcceptPairMatch = new javax.swing.JButton();
        lbTimerPairMatch = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Caro Game");
        setResizable(false);

        Font titledBorderFont = new java.awt.Font("Dialog", java.awt.Font.BOLD, 18); // Chữ đậm, kích thước 18

// Tạo TitledBorder với font tùy chỉnh
        plBtns.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                titledBorderFont));

        btnFindMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_circled_play_24px.png"))); // NOI18N
        btnFindMatch.setText("Tìm trận");
        btnFindMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindMatchActionPerformed(evt);
            }
        });

        btnProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_contact_24px.png"))); // NOI18N
        btnProfile.setText("Hồ sơ");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });
        btnRanking.setText("Bảng xếp hạng");
        btnRanking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icon_ranking.png")));
        btnRanking.addActionListener(e -> {
            // Tạo cửa sổ bảng xếp hạng
            RankingApp rankingApp = new RankingApp(RunClient.socketHandler.getLoginUser());
        });
        javax.swing.GroupLayout plBtnsLayout = new javax.swing.GroupLayout(plBtns);
        plBtns.setLayout(plBtnsLayout);
        plBtnsLayout.setHorizontalGroup(
                plBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plBtnsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnFindMatch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(btnProfile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(btnRanking)
                                .addContainerGap()
                        )
        );
        plBtnsLayout.setVerticalGroup(
                plBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(plBtnsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(plBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFindMatch)
                                        .addComponent(btnProfile)
                                        .addComponent(btnRanking)
                                )
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );


        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_logout_rounded_left_24px.png"))); // NOI18N
        btnLogout.setText("Đăng xuất");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });


        jProgressBar1.setIndeterminate(true);

        lbFindMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFindMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFindMatch.setText("Đang tìm trận...");

        jProgressBar2.setIndeterminate(true);

        btnCancelFindMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_cancel_24px.png"))); // NOI18N
        btnCancelFindMatch.setText("Hủy");
        btnCancelFindMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelFindMatchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plFindingMatchLayout = new javax.swing.GroupLayout(plFindingMatch);
        plFindingMatch.setLayout(plFindingMatchLayout);
        plFindingMatchLayout.setHorizontalGroup(
                plFindingMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(plFindingMatchLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelFindMatch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(plFindingMatchLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbFindMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        plFindingMatchLayout.setVerticalGroup(
                plFindingMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(plFindingMatchLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbFindMatch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(plFindingMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancelFindMatch)
                                        .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        tbListRoom.setAutoCreateRowSorter(true);
        tbListRoom.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tbListRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbListRoom.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbListRoom.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbListRoom);

        btnRefreshListRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_replay_24px.png"))); // NOI18N
        btnRefreshListRoom.setText("Làm mới");
        btnRefreshListRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshListRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefreshListRoom))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshListRoom)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jList1);


        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_contact_24px.png"))); // NOI18N
        jButton1.setText("Xem thông tin");

        GameMatchDAL gm = new server.db.layers.dal.GameMatchDAL();
        List<server.db.layers.dto.GameMatch> matchList = gm.readDB(); // Lấy danh sách trận đấu từ database
        GameMatchTable gameMatchTable = new GameMatchTable();
        gameMatchTable.addGameMatchesToPanel(jPanel3, matchList); // Thêm bảng vào jPanel3
        JLabel lblHistory = new JLabel("Lịch sử");
        lblHistory.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18)); // Kích thước chữ 18
        lblHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

// Thêm JLabel vào tab
        tpRoomAndUser.addTab(null, jPanel3); // Thêm tab mà không có tiêu đề
        tpRoomAndUser.setTabComponentAt(tpRoomAndUser.indexOfComponent(jPanel3), lblHistory); // Đặt JLabel là tiêu đề tab
        tpRoomAndUser.revalidate();
        tpRoomAndUser.repaint();
        lbFoundMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFoundMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFoundMatch.setText("Đã tìm thấy đối thủ ... Vào ngay?");

        btnDeclinePairMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_cancel_24px.png"))); // NOI18N
        btnDeclinePairMatch.setText("Từ chối");
        btnDeclinePairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclinePairMatchActionPerformed(evt);
            }
        });

        btnAcceptPairMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_ok_24px.png"))); // NOI18N
        btnAcceptPairMatch.setText("Chấp nhận");
        btnAcceptPairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptPairMatchActionPerformed(evt);
            }
        });

        lbTimerPairMatch.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lbTimerPairMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimerPairMatch.setText("15s");

        javax.swing.GroupLayout plFoundMatchLayout = new javax.swing.GroupLayout(plFoundMatch);
        plFoundMatch.setLayout(plFoundMatchLayout);
        plFoundMatchLayout.setHorizontalGroup(
                plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(plFoundMatchLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbFoundMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbTimerPairMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plFoundMatchLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDeclinePairMatch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAcceptPairMatch)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        plFoundMatchLayout.setVerticalGroup(
                plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(plFoundMatchLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbFoundMatch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTimerPairMatch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDeclinePairMatch)
                                        .addComponent(btnAcceptPairMatch))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

// Tạo một GroupLayout cho jPanel1 (nơi chứa nút Đăng xuất)
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

// Đặt cấu hình cho horizontal group
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Đẩy nội dung sang bên phải
                        .addComponent(btnLogout) // Nút Đăng xuất
                        .addContainerGap()
        );

// Đặt cấu hình cho vertical group
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Đẩy nội dung lên trên
                        .addComponent(btnLogout) // Nút Đăng xuất sẽ nằm dưới cùng
                        .addContainerGap()
        );

// Cập nhật GroupLayout cho jPanel2 để căn chỉnh nút Đăng xuất
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tpRoomAndUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(plFindingMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(plBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(plFoundMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                )
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Đẩy nút Đăng xuất sang phải
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) // Nút Đăng xuất ở dưới cùng
                                .addContainerGap()
                        )
        );

        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(plBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plFindingMatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plFoundMatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tpRoomAndUser, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE) // Tăng chiều cao lên 400
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Thêm khoảng trống giữa các thành phần
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) // Nút Đăng xuất ở dưới cùng
                        .addContainerGap()
        );



        jPanel2.revalidate();
        jPanel2.repaint();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        RunClient.socketHandler.logout();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        RunClient.openScene(RunClient.SceneName.PROFILE);
        RunClient.profileScene.loadProfileData(RunClient.socketHandler.getLoginUser());
        System.out.println("Loaded profile data");
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnFindMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindMatchActionPerformed
        // chỉ gửi yêu cầu lên server chứ ko đổi giao diện ngay
        // socketHandler sẽ đọc kết quả trả về từ server và quyết định có đổi stateDisplay hay không
        RunClient.socketHandler.findMatch();
    }//GEN-LAST:event_btnFindMatchActionPerformed

    private void btnCancelFindMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelFindMatchActionPerformed
        // chỉ gửi yêu cầu lên server chứ ko đổi giao diện ngay
        // socketHandler sẽ đọc kết quả trả về từ server và quyết định có đổi stateDisplay hay không
        RunClient.socketHandler.cancelFindMatch();
    }//GEN-LAST:event_btnCancelFindMatchActionPerformed

    private void btnDeclinePairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclinePairMatchActionPerformed
        setDisplayState(State.DEFAULT);
        RunClient.socketHandler.declinePairMatch();
    }//GEN-LAST:event_btnDeclinePairMatchActionPerformed

    private void btnAcceptPairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptPairMatchActionPerformed
        setDisplayState(State.WAITING_COMPETITOR_ACCEPT);
        RunClient.socketHandler.acceptPairMatch();
    }//GEN-LAST:event_btnAcceptPairMatchActionPerformed

    private void btnWatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWatchActionPerformed
        // https://stackoverflow.com/a/38981623
        int column = 0;
        int row = tbListRoom.getSelectedRow();
        if (row >= 0) {
            String roomId = tbListRoom.getModel().getValueAt(row, column).toString();
            RunClient.socketHandler.watchRoom(roomId);
        }
    }//GEN-LAST:event_btnWatchActionPerformed

    private void btnRefreshListRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshListRoomActionPerformed
        RunClient.socketHandler.listRoom();
    }//GEN-LAST:event_btnRefreshListRoomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
    private void setComponentBackgrounds(Component component, Color color) {
        component.setBackground(color);
        if (component instanceof JComponent) {
            for (Component child : ((JComponent) component).getComponents()) {
                setComponentBackgrounds(child, color);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptPairMatch;
    private javax.swing.JButton btnCancelFindMatch;
    private javax.swing.JButton btnDeclinePairMatch;
    private javax.swing.JButton btnFindMatch;
    private javax.swing.JButton btnRanking;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnRefreshListRoom;
    private javax.swing.JButton jButton1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbFindMatch;
    private javax.swing.JLabel lbFoundMatch;
    private javax.swing.JLabel lbTimerPairMatch;
    private javax.swing.JPanel plBtns;
    private javax.swing.JPanel plFindingMatch;
    private javax.swing.JPanel plFoundMatch;
    private javax.swing.JTable tbListRoom;
    private javax.swing.JTabbedPane tpRoomAndUser;
    // End of variables declaration//GEN-END:variables
}
