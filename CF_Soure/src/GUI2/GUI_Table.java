/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI2;

import BUS_IServices.IQLTable_Service;
import BUS_Services.QLTable_Service;
import DAL_IServices.ITable_Service;
import DAL_Models.ENTITY_Area;
import DAL_Models.ENTITY_Table;
import DAL_Services.Area_Service;
import DAL_Services.Table_Service;
import Utils.Check;
import Utils.ThongBao;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 *
 * @author notak
 */
public class GUI_Table extends javax.swing.JPanel {

    Area_Service khu;
    Table_Service tbdao;
    JPopupMenu menu = new JPopupMenu("Popup");
    IQLTable_Service dao;
    int row = -1;
    Table_Service ban = new Table_Service();

    /**
     * Creates new form GUI_Table
     */
    public GUI_Table() {
        initComponents();
        tbdao = new Table_Service();
        dao = new QLTable_Service();
        khu = new Area_Service();
        txtMaBan.setEditable(false);
        dao.taoIDTable(txtMaBan);
        fillComboBoxKhu();
        dao.fillTable(tblTable);
        this.xoaform();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        txtMaBan = new javax.swing.JTextField();
        txtViTri = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbbKhu = new javax.swing.JComboBox<>();
        rSButtonIconD1 = new rojerusan.RSButtonIconD();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 102, 51));
        btnThem.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/add-item.png"))); // NOI18N
        btnThem.setText("Th??m");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 102, 51));
        btnClear.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/clear_item.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 102, 51));
        btnXoa.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/delete_item.png"))); // NOI18N
        btnXoa.setText("X??a");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 51));
        btnSua.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/edit_item.png"))); // NOI18N
        btnSua.setText("S???a");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("M?? b??n");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("V??? Tr??");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("T??m Ki???m");

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "M?? B??n", "Khu", "V??? Tr??", "T??nh Tr???ng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblTable);

        txtViTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViTriActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/tu1.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Khu");

        cbbKhu.setEditable(true);
        cbbKhu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        cbbKhu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbbKhu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbKhuMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbbKhuMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbbKhuMouseReleased(evt);
            }
        });
        cbbKhu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbKhuActionPerformed(evt);
            }
        });

        rSButtonIconD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/add-cart.png"))); // NOI18N
        rSButtonIconD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonIconD1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/1dantuanloc.gif"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/1dantuanloc.gif"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/1dantuanloc.gif"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/1dantuanloc.gif"))); // NOI18N

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyTyped(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/caythongnoelto.gif"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/1dantuanloc.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(27, 27, 27)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbbKhu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rSButtonIconD1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(160, 160, 160)
                                .addComponent(jLabel10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel8)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnThem)
                                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(133, 133, 133)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel7)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addGap(41, 41, 41)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cbbKhu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rSButtonIconD1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnThem)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSua)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnXoa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnClear)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(0, 143, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(187, 187, 187))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int vt = (Integer.parseInt(txtViTri.getText()));
        List<ENTITY_Table> list = tbdao.select_viTri(vt);
        if (list.size() == 0) {
            insert();
        } else {
            List<ENTITY_Table> ct = tbdao.select_CheckTrung(vt);
            if (ct.size() == 0) {

                tbdao.update_trung(cbbKhu.getSelectedItem().toString(), vt);
                load();
            } else {
                ThongBao.alert(this, "V??? tr?? ???? t???n t???i");
            }
        }


    }//GEN-LAST:event_btnThemActionPerformed
    void insert() {
        try {
            ENTITY_Table tbl = getModel();
            dao.insertMATABLE(tbl);
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void load() {
        dao.fillTable(tblTable);
        xoaform();
        ThongBao.alert(this, "Th??m th??nh c??ng");
    }

    public boolean checkTrungMa(JTextField txt) {
        txt.setBackground(white);
        if (tbdao.findById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            ThongBao.alert(this, txt.getName() + " ???? b??? t???n t???i.");
            return false;
        }
    }
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        xoaform();
        dao.fillTable(tblTable);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed
    void delete() {
        try {
            this.row = tblTable.getSelectedRow();
            if (this.row != 0) {

                dao.deleteTABLE(txtMaBan.getText());
                dao.fillTable(tblTable);
                xoaform();
                ThongBao.alert(this, "X??a th??nh c??ng");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (Check.checkNullText(txtViTri)) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed
    void update() {
        try {
            ENTITY_Table tbl = getModel();
            dao.updatetTABLE(tbl);
            dao.fillTable(tblTable);
            xoaform();
            ThongBao.alert(this, "C???p nh???p th??nh c??ng");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void tblTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTableMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblTable.getSelectedRow();
            if (this.row >= 0) {
                this.edit();
                // dao.fillTableIDArea(tblTable, cbbKhu);
                btnThem.setEnabled(false);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
            }
        }
        Test();
    }//GEN-LAST:event_tblTableMouseClicked
    private void fillComboBoxKhu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.cbbKhu.getModel();
        model.removeAllElements();
        List<ENTITY_Area> list = dao.selectIDArea();

        for (ENTITY_Area cd : list) {
            model.addElement(cd.getAreaName());
        }

    }
    private void txtViTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViTriActionPerformed

    }//GEN-LAST:event_txtViTriActionPerformed

    private void txtTimKiemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyTyped
        dao.fillTableByID(tblTable, txtTimKiem);

    }//GEN-LAST:event_txtTimKiemKeyTyped

    private void tblTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTableMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblTableMouseReleased

    private void tblTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTableMousePressed
        // TODO add your handling code here:
//        if (evt.isPopupTrigger()) {
//            menu.show(evt.getComponent(), evt.getX(), evt.getY());
//        }
    }//GEN-LAST:event_tblTableMousePressed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked

    }//GEN-LAST:event_jPanel1MouseClicked

    private void rSButtonIconD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonIconD1ActionPerformed

        String khu = JOptionPane.showInputDialog(this, "Nh???p khu b???n mu???n th??m");
        List<ENTITY_Area> list = this.khu.findById(khu);
        if (list.size() == 0) {
            if (khu != null) {
                this.khu.insert(khu);
                fillComboBoxKhu();
                ThongBao.alert(this, "Th??m khu th??nh c??ng!");
            }
        } else {
            ThongBao.alert(this, "Khu ???? t???n t???i");
        }
    }//GEN-LAST:event_rSButtonIconD1ActionPerformed

    private void cbbKhuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbKhuActionPerformed
        xoaKhu();
    }//GEN-LAST:event_cbbKhuActionPerformed

    private void cbbKhuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbKhuMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_cbbKhuMouseReleased

    private void cbbKhuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbKhuMousePressed
        // TODO add your handling code here:
        //        if (evt.isPopupTrigger()) {
        //            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        //        }
    }//GEN-LAST:event_cbbKhuMousePressed

    private void cbbKhuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbKhuMouseClicked
        xoaKhu();
    }//GEN-LAST:event_cbbKhuMouseClicked
    public void Test() {
        menu.removeAll();

        JMenuItem item = new JMenuItem("L???c Khu");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cbb = JOptionPane.showInputDialog("NH???p khu ??i ku:");
                dao.fillTableIDArea(tblTable, cbb);

            }
        });

        menu.add(item);
    }

    public void xoaKhu() {
        menu.removeAll();
        JMenuItem item = new JMenuItem("X??a khu");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String ma = cbbKhu.getSelectedItem().toString();
                if (ma != null) {

                    try {
                        khu.delete(ma);
                        fillComboBoxKhu();
                        ThongBao.alert(null, "X??a th??nh c??ng");
                    } catch (SQLException ex) {
                        ThongBao.alert(null, "Ch??a cho ch??a nh???ng khu c?? h??a ????n, ?????i b???n c???p nh???p sau =]]");
                        // Logger.getLogger(GUI_Table.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        menu.add(item);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbKhu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSButtonIconD rSButtonIconD1;
    private javax.swing.JTable tblTable;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
    ENTITY_Table getModel() {
        ENTITY_Table tbl = new ENTITY_Table();
        tbl.setIDTable(txtMaBan.getText());
        tbl.setIDArea(Integer.valueOf(cbbKhu.getSelectedItem().toString()));
        tbl.setLocation(Integer.valueOf(txtViTri.getText()));
        return tbl;
    }

    void xoaform() {
        this.txtViTri.setText("");
        this.txtTimKiem.setText("");
        this.cbbKhu.setSelectedIndex(0);
        dao.taoIDTable(txtMaBan);
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }

    void edit() {
        try {
            String maTB = (String) tblTable.getValueAt(this.row, 0);
            ENTITY_Table ban = this.ban.findById(maTB);
            this.setform(ban);
            updateStatus();
        } catch (Exception e) {
        }
    }

    void setform(ENTITY_Table tb) {
        String maTB = (String) tblTable.getValueAt(this.row, 0);
        txtMaBan.setText(maTB);
        txtViTri.setText(String.valueOf(tb.getLocation()));
        cbbKhu.setSelectedItem(tb.getIDArea());

    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblTable.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblTable.getRowCount() - 1;
        this.edit();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblTable.getRowCount() - 1);
        //Tr???ng th??i form

        // btnThem.setEnabled(edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        //tr???ng th??i ??i???u h?????ng

    }
}
