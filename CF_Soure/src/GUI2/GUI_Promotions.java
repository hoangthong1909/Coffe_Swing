/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI2;

import DAL_Models.ENTITY_Promotion;
import DAL_Services.Promotion_Service;
import Utils.Check;
import Utils.dateHelper;
import Utils.dialogHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class GUI_Promotions extends javax.swing.JPanel {

    JPopupMenu menu = new JPopupMenu("Popup");
    Promotion_Service dao = new Promotion_Service();
    int row = -1;

    /**
     * Creates new form GUI_Promotions
     */
    public GUI_Promotions() {
        initComponents();
        tblGiamgia.getColumnModel().getColumn(0).setMinWidth(0);
        tblGiamgia.getColumnModel().getColumn(0).setMaxWidth(0);
        init();
//        lblID.setVisible(false);
    }

    void init() {
        filltoTable();
        clearForm();
    }

    void filltoTable() {
        DefaultTableModel model = (DefaultTableModel) tblGiamgia.getModel();
        model.setRowCount(0);
        try {
            List<ENTITY_Promotion> list = dao.select();
            for (ENTITY_Promotion pro : list) {
                Object[] row = {
                    pro.getIDPro(),
                    pro.getName(),
                    pro.getDiscountPromo() + "%",
                    dateHelper.dateToString(pro.getStartPromo(), "dd/MM/yyyy"),
                    dateHelper.dateToString(pro.getEndPromo(), "dd/MM/yyyy"),
                    pro.getStatus() ? "Đang diễn ra" : "Đã kết thúc"};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dialogHelper.alert(this, "Lỗi truy Vấn dữ liệu");
        }
    }

    void setForm(ENTITY_Promotion pro) {
        lblID.setText(String.valueOf(pro.getIDPro()));
        txtName.setText(pro.getName());
        txtChietKhau.setText(String.valueOf(pro.getDiscountPromo()));
        txtNgayBatdau.setDate(pro.getStartPromo());
        txtNgayKetThuc.setDate(pro.getEndPromo());
        txtMoTa.setText(pro.getDescription());
    }

    ENTITY_Promotion getform() {
        ENTITY_Promotion pro = new ENTITY_Promotion();
        pro.setIDPro(Integer.valueOf(lblID.getText()));
        pro.setName(txtName.getText());
        pro.setDiscountPromo(Integer.valueOf(txtChietKhau.getText()));
        pro.setStartPromo(txtNgayBatdau.getDate());
        pro.setEndPromo(txtNgayKetThuc.getDate());
        pro.setDescription(txtMoTa.getText());
        return pro;
    }

    void clearForm() {
        ENTITY_Promotion pro = new ENTITY_Promotion();
        pro.setStartPromo(dateHelper.now());
        pro.setEndPromo(dateHelper.add(15));
        this.setForm(pro);
        this.row = -1;
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }

    void edit() {
        try {
            int ID = (int) tblGiamgia.getValueAt(row, 0);
            ENTITY_Promotion pro = dao.findById(String.valueOf(ID));
            if (pro != null) {
                this.setForm(pro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dialogHelper.alert(this, "Lỗi Rồi DM");
        }

    }

    void insert() {
        ENTITY_Promotion pro = this.getform();
        try {
            dao.insert(pro);
            filltoTable();
            dialogHelper.alert(this, "A! Thành Công Rồi");
            clearForm();
        } catch (Exception e) {
            dialogHelper.alert(this, "Thất Bại :D");
            e.printStackTrace();
        }
    }

    void update() {
        ENTITY_Promotion pro = this.getform();
        try {
            dao.update(pro);
            filltoTable();
            dialogHelper.alert(this, "OK");
            clearForm();
        } catch (Exception e) {
            dialogHelper.alert(this, "Thất Bại :D");
            e.printStackTrace();
        }
    }

    void delete() {
        String ID = lblID.getText();
        dialogHelper.confirm(this, "Đừng xóa em mà");
        try {
            dao.delete(ID);
            filltoTable();
            dialogHelper.alert(this, "Xóa cc");
            clearForm();
        } catch (Exception e) {
            dialogHelper.alert(this, "Thất Bại :D");
            e.printStackTrace();
        }

    }

    void khoiphuc() {
        this.row = tblGiamgia.getSelectedRow();
        if (this.row >= 0) {
            int ID = (int) tblGiamgia.getValueAt(this.row, 0);
            ENTITY_Promotion pro = dao.findById(String.valueOf(ID));
            dialogHelper.confirm(this, "Mày Thích Khôi Phục Không?");
            if (pro.getStatus() == true) {
                dialogHelper.alert(this, "Chương Trình đang diễn ra Khôi Phục cc");
            } else {
                try {
                    dao.khoiphuc(String.valueOf(ID));
                    filltoTable();
                    dialogHelper.alert(this, "Khôi Phục Thành Công");
                } catch (Exception e) {
                    dialogHelper.alert(this, "Thất Bại");
                    e.printStackTrace();
                }
            }
        }
    }

    void xoa() {
        this.row = tblGiamgia.getSelectedRow();
        if (this.row >= 0) {
            int ID = (int) tblGiamgia.getValueAt(this.row, 0);
            ENTITY_Promotion pro = dao.findById(String.valueOf(ID));
            dialogHelper.confirm(this, "Bạn muốn Kết thúc Chương Trình Giảm Giá này?");
            if (pro.getStatus() == false) {
                dialogHelper.alert(this, "Chương Trình đã kết thúc từ trước");
            } else {
                try {
                    dao.delete(String.valueOf(ID));
                    filltoTable();
                    dialogHelper.alert(this, "Đã kết thúc");
                } catch (Exception e) {
                    dialogHelper.alert(this, "Thất Bại");
                    e.printStackTrace();
                }
            }
        }
    }

    public void Test() {
        menu.removeAll();
        JMenuItem item = new JMenuItem("Khôi Phục");
        JMenuItem item1 = new JMenuItem("Kết Thúc Chương Trình");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                khoiphuc();
            }
        });
        menu.add(item);
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoa();
            }

        });
        menu.add(item1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        txtNgayKetThuc = new com.toedter.calendar.JDateChooser();
        txtNgayBatdau = new com.toedter.calendar.JDateChooser();
        lblID = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtChietKhau = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGiamgia = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/giamgia.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tên Chương Trình");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Chiết Khấu (%)");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Ngày Bắt Đầu");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Ngày Kết Thúc");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Mô Tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        lblID.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(255, 0, 51));
        lblID.setText("00");

        txtChietKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChietKhauActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 102, 51));
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 51));
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 102, 51));
        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblGiamgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên Chương Trình", "Chiết Khấu", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGiamgia.setRowHeight(25);
        tblGiamgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGiamgiaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblGiamgiaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblGiamgiaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblGiamgia);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 51, 255));
        jLabel8.setText("%");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/giamgia1.gif"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/giamgia2.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtChietKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8))
                                    .addComponent(txtNgayBatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(125, 125, 125))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblID)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(426, 426, 426)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(296, 296, 296)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(599, 599, 599)
                                .addComponent(jLabel7)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(lblID)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtChietKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtNgayBatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(53, 53, 53)
                                        .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel6)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Check.checkNullText(txtName)
                && Check.checkNullText(txtChietKhau)) {
            if (Check.checkName(txtName)) {
                insert();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (Check.checkNullText(txtName)
                && Check.checkNullText(txtChietKhau)) {
            if (Check.checkName(txtName)) {
                update();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        delete();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblGiamgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGiamgiaMouseClicked
        try {
            if (evt.getClickCount() == 1) {
                this.row = tblGiamgia.getSelectedRow();
                if (this.row >= 0) {
                    edit();
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(true);
                    jButton3.setEnabled(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dialogHelper.alert(this, "Lỗi :<");
        }
        Test();
    }//GEN-LAST:event_tblGiamgiaMouseClicked

    private void tblGiamgiaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGiamgiaMouseReleased
        if (evt.isPopupTrigger()) {
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblGiamgiaMouseReleased

    private void tblGiamgiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGiamgiaMousePressed
        if (evt.isPopupTrigger()) {
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblGiamgiaMousePressed

    private void txtChietKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChietKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChietKhauActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblID;
    private javax.swing.JTable tblGiamgia;
    private javax.swing.JTextField txtChietKhau;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtName;
    private com.toedter.calendar.JDateChooser txtNgayBatdau;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
