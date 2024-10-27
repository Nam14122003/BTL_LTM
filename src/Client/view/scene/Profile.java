/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.scene;

import client.RunClient;
import client.model.ProfileData;
import client.view.helper.Validation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import shared.constant.Avatar;


public class Profile extends javax.swing.JFrame {

    HashMap<String, ImageIcon> hAvatar = new HashMap<>();
    ProfileData currentProfile = new ProfileData();

    /**
     * Creates new form ProfileData
     */
    public Profile() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Thông tin cá nhân");
        try {
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLw-jhsZE2DjYrRMcg7lXaRnhiYpjwKkRJ2w&s");
            Image logo = ImageIO.read(url);
            setIconImage(logo);
        } catch (IOException e) {
            System.out.println("1");
        }
        // hide cancel/save button in profile
        plProfileBtn.setVisible(false);

        // avatar combobox
        cbAvatar.setMaximumRowCount(5);
        setAvatar(Avatar.LIST);

        // validation
        txYearOfBirth.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                Validation.checkNumberInputChanged(txYearOfBirth);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }
        });
        Color backgroundColor = new Color(240, 240, 240);
        setBackgroundColor(backgroundColor);
        Font font = new Font("Dialog", Font.PLAIN, 16);
        setFontForAllComponents(font);
    }

    private void setFontForAllComponents(Font font) {
        btnProfileCancel.setFont(font);
        btnProfileChangePass.setFont(font);
        btnProfileSave.setFont(font);
        cbAvatar.setFont(font);
        cbGender.setFont(font);
        jLabel1.setFont(font);
        jLabel2.setFont(font);
        jLabel3.setFont(font);
        jLabel4.setFont(font);
        jLabel5.setFont(font);
        jLabel6.setFont(font);
        jLabel7.setFont(font);
        jPanel1.setFont(font);
        jPanel2.setFont(font);
        jPanel3.setFont(font);
        jPanel4.setFont(font);
        jPanel6.setFont(font);
        jPanel7.setFont(font);
        lbBirthday.setFont(font);
        lbCurrentStreak.setFont(font);
        lbGender.setFont(font);
        lbLoseCount.setFont(font);
        lbMatchCount.setFont(font);
        lbPassword.setFont(font);
        lbScore.setFont(font);
        lbTieCount.setFont(font);
        lbUser.setFont(font);
        lbUserName.setFont(font);
        lbWinCount.setFont(font);
        lbWinRate.setFont(font);
        pgbProfileLoading.setFont(font);
        plContainer.setFont(font);
        plProfileBtn.setFont(font);
        txName.setFont(font);
        txYearOfBirth.setFont(font);
        txtUser.setFont(font);
    }

    private void setBackgroundColor(Color color) {
        btnProfileCancel.setBackground(color);
        btnProfileChangePass.setBackground(color);
        btnProfileSave.setBackground(color);
        cbAvatar.setBackground(color);
        cbGender.setBackground(color);
        jLabel1.setBackground(color);
        jLabel2.setBackground(color);
        jLabel3.setBackground(color);
        jLabel4.setBackground(color);
        jLabel5.setBackground(color);
        jLabel6.setBackground(color);
        jLabel7.setBackground(color);
        jPanel1.setBackground(color);
        jPanel2.setBackground(color);
        jPanel3.setBackground(color);
        jPanel4.setBackground(color);
        jPanel6.setBackground(color);
        jPanel7.setBackground(color);
        lbBirthday.setBackground(color);
        lbCurrentStreak.setBackground(color);
        lbGender.setBackground(color);
        lbLoseCount.setBackground(color);
        lbMatchCount.setBackground(color);
        lbPassword.setBackground(color);
        lbScore.setBackground(color);
        lbTieCount.setBackground(color);
        lbUser.setBackground(color);
        lbUserName.setBackground(color);
        lbWinCount.setBackground(color);
        lbWinRate.setBackground(color);
        pgbProfileLoading.setBackground(color);
        plContainer.setBackground(color);
        plProfileBtn.setBackground(color);

    }

    private void setAvatar(String[] avas) {
        cbAvatar.removeAllItems();
        hAvatar.clear();

        for (String s : avas) {
            ImageIcon i = new ImageIcon(Avatar.PATH + s);
            hAvatar.put(s, i);
            cbAvatar.addItem(i);
        }
    }

    private String getCurrentStreakStr(int currentStreak) {
        if (currentStreak > 0) {
            return "thắng " + currentStreak;
        }

        if (currentStreak < 0) {
            return "thua " + currentStreak;
        }

        return "" + currentStreak;
    }

    public void loadProfileData(String username) {
        setLoading(true);
        System.out.println("Name : " + username);
        RunClient.socketHandler.getProfile(username);
    }

    public void setProfileData(ProfileData p) {

        // save current profile
        currentProfile = p;
        boolean isMe = p.getUser().equals(RunClient.socketHandler.getLoginUser());

        // put data to form
        lbWinCount.setText("" + p.getWinCount());
        lbTieCount.setText("" + p.getTieCount());
        lbLoseCount.setText("" + p.getLoseCount());

        plContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, p.getName() + " #" + p.getId(), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 16))); // NOI18N
        txtUser.setText(p.getUser());
        txName.setText(p.getName());
        txYearOfBirth.setText("" + p.getYearOfBirth());
        cbGender.setSelectedItem(p.getGender());
        cbAvatar.setSelectedItem(hAvatar.get(p.getAvatar()));

        lbScore.setText(String.valueOf(p.getScore()));
        lbMatchCount.setText("" + p.getMatchCount());
        lbCurrentStreak.setText(getCurrentStreakStr(p.getCurrentStreak()));
        lbWinRate.setText(p.getWinRate() + "%");

        // editable chỉ khi là tài khoản của mình
        txtUser.setEditable(isMe);
        txName.setEditable(isMe);
        txYearOfBirth.setEditable(isMe);
        cbGender.setEnabled(isMe);
        plProfileBtn.setVisible(isMe);
    }

    public void setProfileSaveLoading(boolean isLoading) {
        btnProfileSave.setText(isLoading ? "Đang xử lý" : "Lưu");
        btnProfileSave.setEnabled(!isLoading);
        btnProfileCancel.setEnabled(!isLoading);

        pgbProfileLoading.setVisible(isLoading);
    }

    public void setLoading(boolean isLoading) {
        pgbProfileLoading.setVisible(isLoading);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plContainer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbUserName = new javax.swing.JLabel();
        txName = new javax.swing.JTextField();
        lbGender = new javax.swing.JLabel();
        cbGender = new javax.swing.JComboBox<>();
        lbBirthday = new javax.swing.JLabel();
        txYearOfBirth = new javax.swing.JTextField();
        plProfileBtn = new javax.swing.JPanel();
        btnProfileSave = new javax.swing.JButton();
        btnProfileCancel = new javax.swing.JButton();
        btnProfileChangePass = new javax.swing.JButton();
        lbPassword = new javax.swing.JLabel();
        pgbProfileLoading = new javax.swing.JProgressBar();
        jPanel6 = new javax.swing.JPanel();
        cbAvatar = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbScore = new javax.swing.JLabel();
        lbMatchCount = new javax.swing.JLabel();
        lbCurrentStreak = new javax.swing.JLabel();
        lbWinRate = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbWinCount = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbTieCount = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbLoseCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hồ sơ");

        plContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Người chơi", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 14))); // NOI18N

        lbUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_email_48px.png"))); // NOI18N

        txtUser.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtUser.setToolTipText("Email");

        lbUserName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_name_48px_1.png"))); // NOI18N

        txName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txName.setToolTipText("Họ tên");

        lbGender.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lbGender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_gender_48px.png"))); // NOI18N

        cbGender.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Ẩn" }));
        cbGender.setToolTipText("Giới tính");
        cbGender.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lbBirthday.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lbBirthday.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_birthday_cake_48px_2.png"))); // NOI18N

        txYearOfBirth.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txYearOfBirth.setToolTipText("Năm sinh");

        btnProfileSave.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnProfileSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_ok_24px.png"))); // NOI18N
        btnProfileSave.setText("Lưu");
        btnProfileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileSaveActionPerformed(evt);
            }
        });

        btnProfileCancel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnProfileCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_replay_24px.png"))); // NOI18N
        btnProfileCancel.setText("Làm mới");
        btnProfileCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileCancelActionPerformed(evt);
            }
        });

        btnProfileChangePass.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnProfileChangePass.setText("Đổi mật khẩu");
        btnProfileChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileChangePassActionPerformed(evt);
            }
        });

        lbPassword.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lbPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_password_48px.png"))); // NOI18N

        javax.swing.GroupLayout plProfileBtnLayout = new javax.swing.GroupLayout(plProfileBtn);
        plProfileBtn.setLayout(plProfileBtnLayout);
        plProfileBtnLayout.setHorizontalGroup(
            plProfileBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plProfileBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProfileChangePass)
                .addContainerGap(108, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plProfileBtnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnProfileCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProfileSave)
                .addContainerGap())
        );
        plProfileBtnLayout.setVerticalGroup(
            plProfileBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plProfileBtnLayout.createSequentialGroup()
                .addGroup(plProfileBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbPassword)
                    .addComponent(btnProfileChangePass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plProfileBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProfileSave)
                    .addComponent(btnProfileCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pgbProfileLoading.setIndeterminate(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pgbProfileLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(lbUser)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUser))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(lbUserName)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txName))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(lbGender)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbGender, 0, 228, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lbBirthday)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txYearOfBirth))))
                    .addComponent(plProfileBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txYearOfBirth)
                    .addComponent(lbBirthday))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbGender)
                    .addComponent(cbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plProfileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pgbProfileLoading, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                .addContainerGap())
        );

        cbAvatar.setToolTipText("Ảnh đại diện");
        cbAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Điểm");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Trận");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Chuỗi");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Thắng");

        lbScore.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbScore.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbScore.setText("_");
        lbScore.setToolTipText("");

        lbMatchCount.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbMatchCount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbMatchCount.setText("_");

        lbCurrentStreak.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbCurrentStreak.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbCurrentStreak.setText("_");

        lbWinRate.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbWinRate.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbWinRate.setText("_%");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbScore, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(lbMatchCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbWinRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbCurrentStreak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbScore))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbMatchCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbCurrentStreak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbWinRate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 102), 2, true));
        jPanel1.setToolTipText("Số trận thắng");
        jPanel1.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel5.setText("Thắng");

        lbWinCount.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        lbWinCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWinCount.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbWinCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbWinCount, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 0), 2, true));
        jPanel2.setToolTipText("Số trận hòa");
        jPanel2.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel6.setText("Hòa");

        lbTieCount.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        lbTieCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTieCount.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTieCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 42, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTieCount, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 2, true));
        jPanel4.setToolTipText("Số trận thua");
        jPanel4.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel7.setText("Thua");

        lbLoseCount.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        lbLoseCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbLoseCount.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbLoseCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbLoseCount, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout plContainerLayout = new javax.swing.GroupLayout(plContainer);
        plContainer.setLayout(plContainerLayout);
        plContainerLayout.setHorizontalGroup(
            plContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plContainerLayout.createSequentialGroup()
                .addGroup(plContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plContainerLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(plContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(plContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        plContainerLayout.setVerticalGroup(
            plContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(plContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProfileChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileChangePassActionPerformed
        RunClient.openScene(RunClient.SceneName.CHANGEPASSWORD);
    }//GEN-LAST:event_btnProfileChangePassActionPerformed

    private void btnProfileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileSaveActionPerformed
        // get data from form
        String username = txtUser.getText();
        String name = txName.getText();
        String avatar = Avatar.getAvatarFilNameFromPath(cbAvatar.getSelectedItem().toString());
        String yearOfBirth = txYearOfBirth.getText();
        String gender = cbGender.getSelectedItem().toString();

        // TODO validate data
        if (!Validation.checkName(name)) {
            JOptionPane.showMessageDialog(this, "Tên là tiếng việt không dấu và"
                    + " không quá 15 ký tự", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txName.requestFocus();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearOfBirth);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm sinh phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validation.checkYearOfBirth(year)) {
            JOptionPane.showMessageDialog(this, "Chưa sinh ra hoặc quá 100 tuổi vui lòng đừng chơi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txYearOfBirth.requestFocus();
            return;
        }

        // loading
        setProfileSaveLoading(true);

        // call sockethandler function
        RunClient.socketHandler.editProfile(name, avatar, yearOfBirth, gender);
    }//GEN-LAST:event_btnProfileSaveActionPerformed

    private void btnProfileCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileCancelActionPerformed
        setLoading(true);
        loadProfileData(currentProfile.getUser());
    }//GEN-LAST:event_btnProfileCancelActionPerformed

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
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProfileCancel;
    private javax.swing.JButton btnProfileChangePass;
    private javax.swing.JButton btnProfileSave;
    private javax.swing.JComboBox<ImageIcon> cbAvatar;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lbBirthday;
    private javax.swing.JLabel lbCurrentStreak;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbLoseCount;
    private javax.swing.JLabel lbMatchCount;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbScore;
    private javax.swing.JLabel lbTieCount;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lbUserName;
    private javax.swing.JLabel lbWinCount;
    private javax.swing.JLabel lbWinRate;
    private javax.swing.JProgressBar pgbProfileLoading;
    private javax.swing.JPanel plContainer;
    private javax.swing.JPanel plProfileBtn;
    private javax.swing.JTextField txName;
    private javax.swing.JTextField txYearOfBirth;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
