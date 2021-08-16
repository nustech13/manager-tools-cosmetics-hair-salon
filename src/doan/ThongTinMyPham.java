package doan;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class ThongTinMyPham extends javax.swing.JPanel {

    /**
     * Creates new form ThongTinMyPham
     */
    String ma = "", date = "", maDH = "";
    byte[] filename;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String username;
    public ThongTinMyPham(String username) {
        this.username = username;
        initComponents();
        this.setBackground(Color.PINK);
        ImageIcon icon = new ImageIcon(new ImageIcon("src/images/search.png").getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH));
        jButton_Search.setIcon(icon);
        TinhToan();
        jLabel_TongTien.setText(String.format("%,d", loadTongTien()));
        Load_MyPham();
        jLabel2.setText("Tổng Tiền:");
        
        
    }
    public ThongTinMyPham() {
       
        
    }
    private void Load_MyPham(){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setNumRows(0);
        String sql = "select * from NHAPMYPHAM";
        Vector<String> vt;
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    vt = new Vector<>();
                    vt.add(rs.getString("MADONHANG").trim());
                    vt.add(rs.getString("MAMYPHAM").trim());
                    vt.add(layTenMP(rs.getString("MAMYPHAM").trim()));
                    vt.add(rs.getString("NGAYNHAP").trim());
                    vt.add(rs.getString("NGAYSANXUAT").trim());
                    vt.add(rs.getString("HANSUDUNG").trim());
                    vt.add(rs.getString("SOLUONG").trim());
                    vt.add(String.format("%,d", rs.getLong("GIATIEN")));
                    vt.add(rs.getString("TAIKHOAN").trim());
                    dtm.addRow(vt);
                }
                jTable1.setModel(dtm);
                jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(jTable1.getSelectedRow() >= 0){
                            maDH = jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
                            ma = jTable1.getValueAt(jTable1.getSelectedRow(), 1) + "";
                            filename = layHinh(ma.trim());
                            ImageIcon icon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(jLabel_Hinh.getWidth(), jLabel_Hinh.getHeight(), Image.SCALE_SMOOTH));
                            jLabel_Hinh.setIcon(icon);
                            
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
    private void Load_MyPhamDate(String date){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setNumRows(0);
        String sql = "select * from NHAPMYPHAM where NGAYNHAP = '" + date + "'";
        Vector<String> vt;
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    vt = new Vector<>();
                    vt.add(rs.getString("MADONHANG").trim());
                    vt.add(rs.getString("MAMYPHAM").trim());
                    vt.add(layTenMP(rs.getString("MAMYPHAM").trim()));
                    vt.add(rs.getString("NGAYNHAP").trim());
                    vt.add(rs.getString("NGAYSANXUAT").trim());
                    vt.add(rs.getString("HANSUDUNG").trim());
                    vt.add(rs.getString("SOLUONG").trim());
                    vt.add(String.format("%,d", rs.getLong("GIATIEN")));
                    dtm.addRow(vt);
                }
                jTable1.setModel(dtm);
                jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(jTable1.getSelectedRow() >= 0){
                            maDH = jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "";
                            ma = jTable1.getValueAt(jTable1.getSelectedRow(), 1) + "";
                            filename = layHinh(ma.trim());
                            ImageIcon icon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(jLabel_Hinh.getWidth(), jLabel_Hinh.getHeight(), Image.SCALE_SMOOTH));
                            jLabel_Hinh.setIcon(icon);
                            
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
    private byte [] layHinh(String maMP){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        byte[] hinh = null;
        String sql = "select HINHANH from MYPHAM where MAMYPHAM = '" + maMP + "'";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                hinh = rs.getBytes("HINHANH");
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DungCu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hinh;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel_Hinh = new javax.swing.JLabel();
        jButton_NhapMyPham = new javax.swing.JButton();
        jButton_Xoa = new javax.swing.JButton();
        jButton_CapNhat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton_Search = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel_TongTien = new javax.swing.JLabel();

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ ĐƠN HÀNG", "MÃ MỸ PHẨM", "TÊN MỸ PHẨM", "NGÀY NHẬP", "NGÀY SẢN XUẤT", "HẠN SỬ DỤNG", "SỐ LƯỢNG", "THÀNH TIỀN", "NGƯỜI NHẬP"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel_Hinh.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel_Hinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Hinh.setText("NO IMAGE");
        jLabel_Hinh.setToolTipText("");
        jLabel_Hinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_HinhMouseClicked(evt);
            }
        });

        jButton_NhapMyPham.setBackground(new java.awt.Color(51, 255, 255));
        jButton_NhapMyPham.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_NhapMyPham.setText("NHẬP MỸ PHẨM");
        jButton_NhapMyPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NhapMyPhamActionPerformed(evt);
            }
        });

        jButton_Xoa.setBackground(new java.awt.Color(51, 255, 255));
        jButton_Xoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_Xoa.setText("XÓA");
        jButton_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_XoaActionPerformed(evt);
            }
        });

        jButton_CapNhat.setBackground(new java.awt.Color(51, 255, 255));
        jButton_CapNhat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_CapNhat.setText("CẬP NHẬT");
        jButton_CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CapNhatActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("SEARCH:");

        jButton_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Tổng Tiền:");

        jLabel_TongTien.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel_TongTien.setText("                                                   ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel_Hinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jButton_NhapMyPham)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72)
                                        .addComponent(jButton_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel_TongTien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_NhapMyPham, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_Hinh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_NhapMyPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NhapMyPhamActionPerformed
        new NhapMyPham(username).setVisible(true);
        
    }//GEN-LAST:event_jButton_NhapMyPhamActionPerformed

    private void jButton_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_XoaActionPerformed
        if(!ma.equals("") && !maDH.equals("")){
            xoaNhapMyPham(maDH, ma);
            JOptionPane.showMessageDialog(this, "Xóa Đơn Nhập Mỹ Phẩm Thành Công");
            jLabel_TongTien.setText(String.format("%,d", loadTongTien()));
            Load_MyPham();
        }
        else{
            JOptionPane.showMessageDialog(this, "Chưa Chọn Đơn Nhập Mỹ Phẩm");
        }
    }//GEN-LAST:event_jButton_XoaActionPerformed

    private void jButton_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CapNhatActionPerformed
        jLabel2.setText("Tổng Tiền:");
        jLabel_TongTien.setText(String.format("%,d", loadTongTien()));
        Load_MyPham();
    }//GEN-LAST:event_jButton_CapNhatActionPerformed
    private void TinhToan(){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "select * from NHAPMYPHAM";
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    suaGiaTien(rs.getString("MADONHANG"), rs.getString("MAMYPHAM"), rs.getInt("SOLUONG"));
                }
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void suaGiaTien(String maDon, String ma, int SL){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "UPDATE NHAPMYPHAM SET GIATIEN = ? WHERE MAMYPHAM = ? AND MADONHANG = ?";
        try{
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setLong(1, (long) SL*LayDonGia(ma));
            ps.setString(2, ma);
            ps.setString(3, maDon);
            ps.executeUpdate();
        }
        catch(SQLException ex){
            Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int LayDonGia(String ma){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int donGia = 0;
        String sql = "select DONGIA from MYPHAM WHERE MAMYPHAM = '" + ma + "'";
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    donGia = rs.getInt("DONGIA");
                }
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donGia;
    }
    private void jButton_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchActionPerformed
        jLabel_TongTien.setText("");
        jLabel2.setText("");
        if(jDateChooser1.getDate() != null){
            date = sdf.format(jDateChooser1.getDate());
        }
        int KTDate = 0;
        if(jDateChooser1.getDate() == null){
            KTDate = 1;
            JOptionPane.showMessageDialog(this, "Ngày Nhập Trống");
        }
        else if(KTDate != 1){
            int check = kiemTraNgayNhap(date);
            if(check == 1){
                Load_MyPhamDate(date);
            }
            else{
                JOptionPane.showMessageDialog(this, "Ngày Này Không Nhập Hàng");
            }
        }
    }//GEN-LAST:event_jButton_SearchActionPerformed

    private void jLabel_HinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_HinhMouseClicked
        new HienAnh(filename).setVisible(true);
    }//GEN-LAST:event_jLabel_HinhMouseClicked
    private int kiemTraNgayNhap(String date){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int tontai = 0;
        String sql = "select * from NHAPMYPHAM where NGAYNHAP = '" + date + "'";
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    tontai = 1;
                }
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tontai;
    }
    private long loadTongTien(){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        long sum = 0;
        
        String sql = "select GIATIEN from NHAPMYPHAM";
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    sum += rs.getLong("GIATIEN");
                }
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sum;
    }
    private int kiemTraQL(String username){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        int tontai = 0;
        String CV = "Quản Lý";
        String sql = "select * from NHANVIEN where TAIKHOAN = '" + username + "'";
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    if(CV.equals(rs.getString("CHUCVU").trim()) || rs.getString("CHUCVU").trim().equals("Admin")){
                        tontai = 1;
                    }
                }
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tontai;
    }
    private void xoaNhapMyPham(String maDH, String maMP){
        Connection ketNoi = KetNoiSQL.layKetNoi();
        String sql = "delete from NHAPMYPHAM where MADONHANG = ? AND MAMYPHAM = ?";
        try{
            try (PreparedStatement ps = ketNoi.prepareStatement(sql)) {
                ps.setString(1, maDH);
                ps.setString(2, maMP);
                ps.executeUpdate();
            }
            ketNoi.close();
        }
        catch(SQLException ex){
            Logger.getLogger(MyPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_CapNhat;
    private javax.swing.JButton jButton_NhapMyPham;
    private javax.swing.JButton jButton_Search;
    private javax.swing.JButton jButton_Xoa;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Hinh;
    private javax.swing.JLabel jLabel_TongTien;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
