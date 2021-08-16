/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doan;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.parser.DTDConstants;

/**
 *
 * @author Admin
 */
public class ThongTinTinhTrang extends javax.swing.JPanel {

    /**
     * Creates new form ThongTinTinhTrang
     */
    String loai = "", ma = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String username = "";

    public ThongTinTinhTrang() {
        initComponents();
    }
    
    public ThongTinTinhTrang(String username) {
        this.username = username;
        initComponents();
        this.setBackground(Color.PINK);
        loai = (String) jComboBox1.getSelectedItem();
        Load_MyPham();
        jTextField_MaPhieu.setVisible(false);
        jDateChooser_Ngay.setVisible(false);
        jTextField_Ma.setEditable(false);
        jTextField_Ten.setEditable(false);
        jLabel_MaPhieu.setVisible(false);
        jLabel_NgayMuon.setVisible(false);
        if(layChucVu(username).equals("Nhân Viên")){
            jButton_Sua.setVisible(false);
            jButton_Xoa.setVisible(false);
        }
        else{
            jButton_Sua.setVisible(false);
            jButton_Xoa.setVisible(true);
        }
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == jComboBox1){
                    loai = (String) jComboBox1.getSelectedItem();
                    if(loai.equals("Mỹ Phẩm")){
                        jButton_Dung.setText("SỬ DỤNG MỸ PHẨM");
                        jTextField_Ma.setText("");
                        jTextField_Ten.setText("");
                        jTextField_SL.setText("");
                        jTextField_MaPhieu.setText("");
                        jDateChooser_Ngay.setDate(null);
                        jTable1.setModel(new DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Mã Mỹ Phẩm", "Tên Mỹ Phẩm", "Số Lượng Tốt", "Số Lượng Đã Sử Dụng", "Số Lượng Hết Hạn"
                            }
                        ));
                        Load_MyPham();
                        jTextField_MaPhieu.setVisible(false);
                        jDateChooser_Ngay.setVisible(false);
                        jLabel_MaPhieu.setVisible(false);
                        jLabel_NgayMuon.setVisible(false);
                        if(layChucVu(username).equals("Nhân Viên")){
                            jButton_Sua.setVisible(false);
                            jButton_Xoa.setVisible(false);
                        }
                        else{
                            jButton_Sua.setVisible(false);
                            jButton_Xoa.setVisible(true);
                        }
                    }
                    else{
                        jButton_Dung.setText("MƯỢN DỤNG CỤ");
                        jTextField_Ma.setText("");
                        jTextField_Ten.setText("");
                        jTextField_SL.setText("");
                        jTable1.setModel(new DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Mã Dụng Cụ", "Tên Dụng Cụ", "Số Lượng Tốt", "Số Lượng Đang Sử Dụng", "Số Lượng Hỏng"
                            }
                        ));
                        Load_DungCu();
                        jTextField_MaPhieu.setVisible(true);
                        jDateChooser_Ngay.setVisible(true);
                        jLabel_MaPhieu.setVisible(true);
                        jLabel_NgayMuon.setVisible(true);
                        if(layChucVu(username).equals("Nhân Viên")){
                            jButton_Sua.setVisible(false);
                            jButton_Xoa.setVisible(false);
                        }
                        else{
                            jButton_Sua.setVisible(true);
                            jButton_Xoa.setVisible(true);
                        }
                    }
                }
            }
        });
        
    }
    private void Load_MyPham(){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setNumRows(0);
        String sql = "select * from TINHTRANGMYPHAM";
        Vector<String> vt;
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    vt = new Vector<>();
                    vt.add(rs.getString("MAMYPHAM"));
                    vt.add(layTenMP(rs.getString("MAMYPHAM").trim()));
                    vt.add(rs.getString("SLMPT"));
                    vt.add(rs.getString("SLMPSD"));
                    vt.add(rs.getString("SLMPH"));
                    dtm.addRow(vt);
                }
                jTable1.setModel(dtm);
                jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(jTable1.getSelectedRow() >= 0){
                            ma = jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
                            jTextField_Ma.setText(ma);
                            jTextField_Ten.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1) + "");
                            
                            
                        }
                    }
                    
                });
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DungCu.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    private void Load_DungCu(){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setNumRows(0);
        String sql = "select * from TINHTRANGDUNGCU";
        Vector<String> vt;
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    vt = new Vector<>();
                    vt.add(rs.getString("MADUNGCU"));
                    vt.add(layTenDC(rs.getString("MADUNGCU").trim()));
                    vt.add(rs.getString("SLDCT"));
                    vt.add(rs.getString("SLDCSD"));
                    vt.add(rs.getString("SLDCH"));
                    dtm.addRow(vt);
                }
                jTable1.setModel(dtm);
                jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(jTable1.getSelectedRow() >= 0){
                            ma = jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
                            jTextField_Ma.setText(ma);
                            jTextField_Ten.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1) + "");
                            
                            
                        }
                    }
                    
                });
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DungCu.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private String layTenMP(String maMP){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String ten = "";
        String sql = "select TENMYPHAM from MYPHAM where MAMYPHAM = '" + maMP + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ten = rs.getString("TENMYPHAM");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DungCu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ten;
    }
    private String layTenDC(String maDC){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String ten = "";
        String sql = "select TENDUNGCU from DUNGCU where MADUNGCU = '" + maDC + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ten = rs.getString("TENDUNGCU");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DungCu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ten;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton_Dung = new javax.swing.JButton();
        jLabel_MaPhieu = new javax.swing.JLabel();
        jLabel_Ma = new javax.swing.JLabel();
        jLabel_Ten = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel_NgayMuon = new javax.swing.JLabel();
        jTextField_MaPhieu = new javax.swing.JTextField();
        jTextField_Ma = new javax.swing.JTextField();
        jTextField_Ten = new javax.swing.JTextField();
        jTextField_SL = new javax.swing.JTextField();
        jDateChooser_Ngay = new com.toedter.calendar.JDateChooser();
        jButton_Xoa = new javax.swing.JButton();
        jButton_Sua = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Mỹ Phẩm", "Tên Mỹ Phẩm", "Số Lượng Tốt", "Số Lượng Đã Sử Dụng", "Số Lượng Hết Hạn"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("SEARCH:");

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mỹ Phẩm", "Dụng Cụ" }));

        jButton_Dung.setBackground(new java.awt.Color(0, 204, 255));
        jButton_Dung.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_Dung.setText("SỬ DỤNG MỸ PHẨM");
        jButton_Dung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DungActionPerformed(evt);
            }
        });

        jLabel_MaPhieu.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel_MaPhieu.setText("Mã Phiếu Mượn:");

        jLabel_Ma.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel_Ma.setText("Mã Mỹ Phẩm:");

        jLabel_Ten.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel_Ten.setText("Tên Mỹ Phẩm:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Số Lượng:");

        jLabel_NgayMuon.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel_NgayMuon.setText("Ngày Mượn:");

        jTextField_MaPhieu.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField_MaPhieu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_MaPhieuKeyTyped(evt);
            }
        });

        jTextField_Ma.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jTextField_Ten.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jTextField_SL.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField_SL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_SLKeyTyped(evt);
            }
        });

        jButton_Xoa.setBackground(new java.awt.Color(0, 204, 255));
        jButton_Xoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_Xoa.setText("XÓA");
        jButton_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_XoaActionPerformed(evt);
            }
        });

        jButton_Sua.setBackground(new java.awt.Color(0, 204, 255));
        jButton_Sua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_Sua.setText("SỬA CHỮA");
        jButton_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel_MaPhieu)
                                        .addComponent(jLabel_Ma)
                                        .addComponent(jLabel_Ten))
                                    .addComponent(jLabel_NgayMuon, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField_SL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(jTextField_Ten, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(jTextField_Ma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(jDateChooser_Ngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jTextField_MaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 22, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton_Dung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_Xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_Sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(54, 54, 54))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_MaPhieu)
                            .addComponent(jTextField_MaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Ma)
                            .addComponent(jTextField_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Ten)
                            .addComponent(jTextField_Ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_SL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel_NgayMuon)
                            .addComponent(jDateChooser_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addComponent(jButton_Dung, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_DungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DungActionPerformed
        String select = (String) jComboBox1.getSelectedItem();
        int SL = 0;
        if(!jTextField_SL.getText().equals("")){
            SL = Integer.valueOf(jTextField_SL.getText());
        }
        String maDC = jTextField_Ma.getText();
        if(select.equals("Mỹ Phẩm")){
            int KTmaMP = 0, KTSL = 0;
            if(maDC.equals("")){
                KTmaMP = 1;
                JOptionPane.showMessageDialog(this, "Chưa Chọn Mỹ Phẩm");
            }
            else if(jTextField_SL.getText().equals("")){
                KTSL = 1;
                JOptionPane.showMessageDialog(this, "Chưa Nhập Số Lượng");
            }
            else if(KTmaMP != 1 && KTSL != 1){
                int SLTot = laySLMP(maDC);
                int SLSD = laySLMPSD(maDC);
                if(SLTot >= SL){
                    suDungMyPham(maDC, (SLTot - SL), (SLSD + SL));
                    JOptionPane.showMessageDialog(this, "Lấy Sử Dụng Mỹ Phẩm Thành Công");
                    Load_MyPham();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Số Lượng Mỹ Phẩm Không Đủ! Vui Lòng Nhập Lại Số Lượng");
                }
            }
        }else{
            try {
                String maPhieu = jTextField_MaPhieu.getText();
                String date = "";
                if(jDateChooser_Ngay.getDate() != null){
                    date = sdf.format(jDateChooser_Ngay.getDate());
                }   int KTMaPhieu = 0, KTDATE = 0, KTmaDC = 0, KTSL = 0;
                if(maPhieu.equals("")){
                    KTMaPhieu = 1;
                    JOptionPane.showMessageDialog(this, "Mã Phiếu Mượn Trống");
                }
                if(maDC.equals("")){
                    KTmaDC = 1;
                    JOptionPane.showMessageDialog(this, "Chưa Chọn Dụng Cụ");
                }
                else if(jTextField_SL.getText().equals("")){
                    KTSL = 1;
                    JOptionPane.showMessageDialog(this, "Chưa Nhập Số Lượng");
                }
                else if(jDateChooser_Ngay.getDate() == null){
                    KTDATE = 1;
                    JOptionPane.showMessageDialog(this, "Ngày Mượn Trống");
                }
                else if(!ChuanHoa.kiemTraDateToday(jDateChooser_Ngay.getDate())){
                    KTDATE = 1;
                    JOptionPane.showMessageDialog(this, "Ngày Mượn Không Hợp lệ");
                }
                else if(!maPhieu.equals("") && kiemTraPhieuMuon(maPhieu) == 1){
                    KTMaPhieu = 1;
                    JOptionPane.showMessageDialog(this, "Mã Phiếu Mượn Đã Tồn Tại");
                }
                else if(KTMaPhieu != 1 && KTDATE != 1 && KTmaDC != 1 && KTSL != 1){
                    int SLTot = laySLDC(maDC);
                    int SLSD = laySLDCSD(maDC);
                    if(SLTot >= SL){
                        lapPhieuMuon(maPhieu, maDC, SL, date, username);
                        suDungDungCu(maDC, (SLTot - SL), (SLSD + SL));
                        JOptionPane.showMessageDialog(this, "Mượn Dụng Cụ Thành Công");
                        Load_DungCu();
                    }
                    else{
                    JOptionPane.showMessageDialog(this, "Số Lượng Dụng Cụ Không Đủ! Vui Lòng Nhập Lại Số Lượng");
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(ThongTinTinhTrang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_DungActionPerformed

    private void jTextField_MaPhieuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_MaPhieuKeyTyped
        if(jTextField_MaPhieu.getText().length() >= 10){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_MaPhieuKeyTyped

    private void jTextField_SLKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SLKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)){
           evt.consume();
        }
        if(jTextField_SL.getText().length() >= 5){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_SLKeyTyped

    private void jButton_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_XoaActionPerformed
        String nut = "xoa";
        new XoaSua(loai, ma, nut).setVisible(true);
        
    }//GEN-LAST:event_jButton_XoaActionPerformed

    private void jButton_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SuaActionPerformed
        String nut = "sua";
        new XoaSua(loai, ma, nut).setVisible(true);
    }//GEN-LAST:event_jButton_SuaActionPerformed
    private void lapPhieuMuon(String maPhieu, String maDC, int SLMuon, String date, String user){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "INSERT INTO PHIEUMUON VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, maPhieu);
            ps.setString(2, maDC);
            ps.setInt(3, SLMuon);
            ps.setString(4, date);
            ps.setString(5, user);
            ps.executeUpdate();
            
        }
        catch(SQLException ex){
            Logger.getLogger(ThongTinTinhTrang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void suDungMyPham(String maMP, int SL1, int SL2){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "UPDATE TINHTRANGMYPHAM SET SLMPT = ?, SLMPSD = ? WHERE MAMYPHAM = ?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setInt(1, SL1);
            ps.setInt(2, SL2);
            ps.setString(3, maMP);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ThongTinTinhTrang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int laySLMP(String maMP){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int SL = 0;
        String sql = "select SLMPT from TINHTRANGMYPHAM where MAMYPHAM = '" + maMP + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SL = rs.getInt("SLMPT");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(NhapMyPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SL;
    }
    private int laySLMPSD(String maMP){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int SL = 0;
        String sql = "select SLMPSD from TINHTRANGMYPHAM where MAMYPHAM = '" + maMP + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SL = rs.getInt("SLMPSD");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(NhapMyPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SL;
    }
    private void suDungDungCu(String maDC, int SL1, int SL2){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "UPDATE TINHTRANGDUNGCU SET SLDCT = ?, SLDCSD = ? WHERE MADUNGCU = ?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setInt(1, SL1);
            ps.setInt(2, SL2);
            ps.setString(3, maDC);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ThongTinTinhTrang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int laySLDC(String maDC){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int SL = 0;
        String sql = "select SLDCT from TINHTRANGDUNGCU where MADUNGCU = '" + maDC + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SL = rs.getInt("SLDCT");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(NhapMyPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SL;
    }
    private int laySLDCSD(String maDC){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int SL = 0;
        String sql = "select SLDCSD from TINHTRANGDUNGCU where MADUNGCU = '" + maDC + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SL = rs.getInt("SLDCSD");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(NhapMyPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SL;
    }
    private String layChucVu(String username){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "select CHUCVU from NHANVIEN WHERE TAIKHOAN = '" + username + "'";
        String kt = "";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                kt = rs.getString("CHUCVU").trim();
            }
            ketNoi.close();
            
        }
        catch(SQLException ex){
            Logger.getLogger(ThongTinNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kt;
    }
    private int kiemTraPhieuMuon(String maPM){
        int tontai = 0;
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "select * from PHIEUMUON where MAPHIEUMUON = '" + maPM + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tontai = 1;
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(MyPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tontai;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Dung;
    private javax.swing.JButton jButton_Sua;
    private javax.swing.JButton jButton_Xoa;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser_Ngay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_Ma;
    private javax.swing.JLabel jLabel_MaPhieu;
    private javax.swing.JLabel jLabel_NgayMuon;
    private javax.swing.JLabel jLabel_Ten;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField_Ma;
    private javax.swing.JTextField jTextField_MaPhieu;
    private javax.swing.JTextField jTextField_SL;
    private javax.swing.JTextField jTextField_Ten;
    // End of variables declaration//GEN-END:variables
}
