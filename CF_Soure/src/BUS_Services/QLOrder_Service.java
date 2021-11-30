/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS_Services;

import BUS_IServices.*;
import BUS_Models.BanButtons;
import BUS_Models.LSOrder;
import BUS_Models.SPChiTiet;
import BUS_Models.billCTT;
import DAL_IServices.IArea_Service;
import DAL_IServices.IOrder_Service;
import DAL_IServices.IProduct_Service;
import DAL_IServices.ITable_Service;
import DAL_Models.ENTITY_Area;
import DAL_Models.ENTITY_BILL;
import DAL_Models.ENTITY_Order;
import DAL_Models.ENTITY_Product;
import DAL_Models.ENTITY_Table;
import DAL_Services.Area_Service;
import DAL_Services.Order_Service;
import DAL_Services.Product_Service;
import DAL_Services.Table_Service;
import GUI2.JDialogTaoBan;
import Utils.JDBC;
import Utils.dateHelper;
import Utils.dialogHelper;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author phamd
 */
public class QLOrder_Service implements IQLOrder_Service {

    private final int ICON_WIDTH = 60;
    private final int ICON_HEIGHT = 60;
    private ITable_Service qlb;
    private IArea_Service qlk;
    private IProduct_Service lqp;
    private IOrder_Service qlo;
    private IQLTable_Service daoban;

    private int dong;
    private int listBa;
    private int khu;
    private String GroupBan;

    private Map<JButton, BanButtons> banButtonList = new HashMap<>();
    private JPanel pnlMain;
    private ArrayList<ENTITY_Table> listBan;
    private JButton firstButton;
    private JButton ButtonChuyen;
    private JButton ButtonGop;

    private javax.swing.JPopupMenu pmmXoaBan;
    private javax.swing.JPopupMenu pmmKhoiPhuc;
    private javax.swing.JPopupMenu pmmBanAo;
    private javax.swing.JPopupMenu pmmBtn;
    private javax.swing.JMenuItem mnXoaBan;
    private javax.swing.JMenuItem mnKhoiPhuc;
    private javax.swing.JMenuItem mnTaoBanAo;
    private javax.swing.JMenuItem mnChuyenBan;
    private javax.swing.JMenuItem mnGopBan;
    private javax.swing.JMenuItem mnNhomBan;
    private javax.swing.JMenuItem mnaddNhom;

    private DefaultTableModel model;
    String sql_all = "SELECT [IDProduct],ProductName,Price,Image,Status,TypeName,Size FROM [Product] Join ProductType on Product.IDType = ProductType.IDType";
    String SQL_liSu = "SELECT DISTINCT OrderDetail.IDOrder,TimeOder,EMP.NameEMP,Cus.CusName,OD.[Status] FROM OrderDetail  \n"
            + " JOIN [Order] OD ON OD.IDOrder = OrderDetail.IDOrder\n"
            + " LEFT JOIN Employee EMP ON EMP.UsernameEMP = OD.UsernameEMP\n"
            + " LEFT JOIN Customer Cus ON Cus.IDCust = OD.IDCust\n"
            + " WHERE IDTable = ? AND DateOrder = ?";
    String InsertOderDe = "INSERT INTO OrderDetail(IDOrder,IDProduct,IDTable,Quantity,Note,Status,Reason) VALUES (?,?,?,?,?,?,?) ";
    String SQL_chuaTT = "SELECT DISTINCT MAX(OrderDetail.IDOrder) FROM OrderDetail \n"
            + "JOIN [Order] OD ON OD.IDOrder = OrderDetail.IDOrder\n"
            + "WHERE IDTable = ? AND DateOrder = ? AND OD.[Status] = 1";
    String billCTT = "SELECT OrderDetail.IDOrder,TimeOder,OrderDetail.IDProduct,PR.ProductName,Size,Quantity,PR.Price,Note,OD.IDCust,EMP.NameEMP,OrderDetail.Status FROM OrderDetail\n"
            + "JOIN [Order] OD ON OD.IDOrder = OrderDetail.IDOrder\n"
            + "JOIN Product PR ON PR.IDProduct = OrderDetail.IDProduct\n"
            + " LEFT JOIN Employee EMP ON EMP.UsernameEMP = OD.UsernameEMP\n"
            + "JOIN ProductType on PR.IDType = ProductType.IDType\n"
            + "WHERE OrderDetail.IDOrder = ? ";
    String thanhToan = "UPDATE [Order] SET [Status] = 2 WHERE IDOrder = ?";
    String chuenBan = "UPDATE OrderDetail SET IDTable = ?,Note = ? WHERE IDOrder = ?";

    public QLOrder_Service(JPanel that, JButton btnVaoBan, JLabel lblBan, JTable tblOder, JTable tblLichSu, JPanel PanlPanelLS, JPanel Oder, JTextField txtMaHD, JTextField txtMaKH, JTextField txtNameEMP, JLabel TimeOrder, JTextField txtTong, JPanel PanCac) {
        this.qlb = new Table_Service();
        this.qlk = new Area_Service();
        this.qlo = new Order_Service();
        this.lqp = new Product_Service();
        this.daoban = new QLTable_Service();
        this.listBan = new ArrayList<ENTITY_Table>();
        this.pnlMain = new JPanel();
        pmmBtn = new javax.swing.JPopupMenu();
        pmmBanAo = new javax.swing.JPopupMenu();
        pmmXoaBan = new javax.swing.JPopupMenu();
        pmmKhoiPhuc = new javax.swing.JPopupMenu();
        mnChuyenBan = new javax.swing.JMenuItem();
        mnTaoBanAo = new javax.swing.JMenuItem();
        mnGopBan = new javax.swing.JMenuItem();
        mnXoaBan = new javax.swing.JMenuItem();
        mnKhoiPhuc = new javax.swing.JMenuItem();
        mnNhomBan = new javax.swing.JMenuItem();
        mnaddNhom = new javax.swing.JMenuItem();
        ClassLoader classLoader = this.getClass().getClassLoader();

        mnChuyenBan.setBackground(new java.awt.Color(255, 204, 102));
        mnChuyenBan.setText("Chuyển bàn");
        mnChuyenBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChuyenBanActionPerformed(evt);
            }
        });
        pmmBtn.add(mnChuyenBan);

        mnGopBan.setBackground(new java.awt.Color(255, 204, 102));
        mnGopBan.setText("Gộp bàn");
        mnGopBan.setToolTipText("");
        mnGopBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGopBanActionPerformed(evt);
            }

            private void mnGopBanActionPerformed(ActionEvent evt) {
                if (evt.getSource().getClass() == JMenuItem.class) {
                    dialogHelper.alert(null, "Chọn bàn chuyển đến nhá Pro");
                    ButtonGop = firstButton;
                }
            }
        });
        pmmBtn.add(mnGopBan);
        mnNhomBan.setBackground(new java.awt.Color(255, 204, 102));
        mnNhomBan.setText("Tạo nhóm");
        mnNhomBan.setToolTipText("");
        mnNhomBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTaoNhomActionPerformed(evt);
            }

            private void mnTaoNhomActionPerformed(ActionEvent evt) {
                if (evt.getSource().getClass() == JMenuItem.class) {
                    BanButtons bn = banButtonList.get(firstButton);
                    ENTITY_Table tbl = new ENTITY_Table();
                    tbl.setIDArea(khu);
                    tbl.setIDTable(bn.getIDTalbe());
                    tbl.setStatus(0);
                    GUI_Dialog.GUI_TaoNhom taonhom = new GUI_Dialog.GUI_TaoNhom(pnlMain, true, tbl, that, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                    taonhom.setVisible(true);

                }
            }
        });
        pmmBtn.add(mnNhomBan);
        //----------------------------------------------------------------------------------------------------------------------Tạo bàn -----------------------------------------------------------
        mnTaoBanAo.setBackground(new java.awt.Color(255, 204, 102));
        mnTaoBanAo.setText("Tạo bàn nhanh");
        mnTaoBanAo.setToolTipText("");
        mnTaoBanAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTaoBanAoActionPerformed(evt);
            }

            private void mnTaoBanAoActionPerformed(ActionEvent evt) {
                if (evt.getSource().getClass() == JMenuItem.class) {
                    //==============Tạo mã bàn tự sinh=========//
                    String IDTable = "";
                    try {
                        String sql = "Select MAX([IDTable]) from [Table]";
                        ResultSet rs = JDBC.query(sql);
                        rs.next();
                        rs.getString(1);
                        if (rs.getString(1) == null) {
                            IDTable = "TB001";
                        } else {
                            long id = Long.parseLong(rs.getString(1).substring(2, rs.getString(1).length()));
                            id++;
                            IDTable = "TB" + String.format("%03d", id);
                        }
                        //=====================================
                        ENTITY_Table tbl = new ENTITY_Table();
                        tbl.setIDArea(khu);
                        tbl.setIDTable(IDTable);
                        tbl.setStatus(0);
                        JDialogTaoBan taoBan = new JDialogTaoBan(pnlMain, true, tbl, that, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                        taoBan.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        pmmBanAo.add(mnTaoBanAo);
        //-----------------------------------------------------------------------------------------------Xóa bàn=================           
        mnXoaBan.setBackground(new java.awt.Color(255, 204, 102));
        mnXoaBan.setText("Xóa bàn");

        URL imagePath = classLoader.getResource("Icon/" + "Button-Close-icon" + ".png");
        Image imgBan = new ImageIcon(imagePath).getImage();
        Icon iconBan = new ImageIcon(imgBan.getScaledInstance(20, 20, imgBan.SCALE_SMOOTH));
        mnXoaBan.setIcon(iconBan);
        mnXoaBan.setToolTipText("");
        mnXoaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnXoaBanActionPerformed(evt);
            }

            private void mnXoaBanActionPerformed(ActionEvent evt) {
                if (evt.getSource().getClass() == JMenuItem.class) {
                    BanButtons bn = banButtonList.get(firstButton);
                    //=====================================                                                                        
                    xoaBan(bn.getIDTalbe());
                    taoTable(that, khu, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                }
            }
        });
        pmmXoaBan.add(mnXoaBan);
        //-==================================================Hết xóa bàn
        mnaddNhom.setBackground(new java.awt.Color(255, 204, 102));
        mnaddNhom.setText("Thêm vào nhóm");
        mnaddNhom.setToolTipText("");
        mnaddNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnaddNhomActionPerformed(evt);
            }

            private void mnaddNhomActionPerformed(ActionEvent evt) {
                if (evt.getSource().getClass() == JMenuItem.class) {
                    BanButtons bn = banButtonList.get(firstButton);
                    ENTITY_Table tbl = new ENTITY_Table();
                    tbl.setIDArea(khu);
                    tbl.setIDTable(bn.getIDTalbe());
                    tbl.setStatus(0);
                    GUI_Dialog.GUI_AddNhom taonhom = new GUI_Dialog.GUI_AddNhom(pnlMain, true, tbl, that, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                    taonhom.setVisible(true);
                }
            }
        });
        pmmXoaBan.add(mnaddNhom);
        //-----------------------------------------------------------------------------------------------Khôi phục bàn=====
        mnKhoiPhuc.setBackground(new java.awt.Color(255, 204, 102));
        mnKhoiPhuc.setText("Khôi phục bàn");
        mnKhoiPhuc.setToolTipText("");
        mnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnXoaBanActionPerformed(evt);
            }

            private void mnXoaBanActionPerformed(ActionEvent evt) {
                if (evt.getSource().getClass() == JMenuItem.class) {
                    BanButtons bn = banButtonList.get(firstButton);
                    khoiPhucBan(bn.getIDTalbe());
                    taoTable(that, khu, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                }
            }
        });
        pmmKhoiPhuc.add(mnKhoiPhuc);
        //-==================================================Hết Khôi phục bàn
    }

    @Override
    public void taoTable(JPanel that, int cbbkhu, JButton btnVaoBan, JLabel lblBan, JTable tblOder, JTable tblLichSu, JPanel PanlPanelLS, JPanel Oder, JTextField txtMaHD, JTextField txtMaKH, JTextField txtNameEMP, JLabel TimeOrder, JTextField txtTong, JPanel PanCac) {
        this.listBan = (ArrayList<ENTITY_Table>) this.qlb.SQLKhu(cbbkhu);
        this.khu = cbbkhu;
        this.listBa = listBan.size();
        ClassLoader classLoader = that.getClass().getClassLoader();
        pnlMain.removeAll();
        pnlMain.setBounds(0, 230, 695, 765);
        pnlMain.setBackground(new java.awt.Color(0, 102, 255));
//        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Bàn"));
//         JScrollPane sn = new JScrollPane();
//         sn.setViewportView(pnlMain);
//         pnlMain.add(sn);
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(400, 900));
        jp.setBackground(new java.awt.Color(0, 102, 255));
        JScrollPane js = new JScrollPane(jp,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setPreferredSize(new Dimension(680, 780));        
        jp.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Bàn"));
        pnlMain.add(js);
        for (ENTITY_Table ban : listBan) {
            URL imagePath = classLoader.getResource("Icon/" + ban.getLocation() + ".png");
            Image imgBan = new ImageIcon(imagePath).getImage();
            Icon iconBan = new ImageIcon(imgBan.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, imgBan.SCALE_SMOOTH));
            JButton button = new JButton();
            button.setIcon(iconBan);
            button.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            button.setPreferredSize(new Dimension(150, 100));
            int cs = ban.getStatus();
            switch (cs) {
                case 0:
                    button.setBackground(Color.GREEN);
                    button.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            selectedButtonPopup(e, button);
                        }

                        private void selectedButtonPopup(MouseEvent e, JButton button) {
                            if (e.getSource().getClass() == button.getClass()) {
                                JButton b = (JButton) e.getSource();
                                if (e.isPopupTrigger()) {
                                    pmmXoaBan.show(e.getComponent(), e.getX(), e.getY());
                                    b.setSelected(true);
                                }
                            }
                        }
                    });
                    break;
                case 1:
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                    button.setBackground(Color.red);
                    String s = this.OrderCTT(txtMaHD, ban.getIDTable());
                    ArrayList<billCTT> list = (ArrayList<billCTT>) this.SelectBill(billCTT, s);
                    for (billCTT cTT : list) {
                        button.setHorizontalTextPosition(0);
                        button.setFont(new Font("Dialog", 8, 16));
                        String go = format.format(cTT.getTimeOrder());
                        button.setText("Time : " + go);
                        button.setVerticalTextPosition(3);
                    }
                    button.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            selectedButtonPopup(e, button);
                        }

                        private void selectedButtonPopup(MouseEvent e, JButton button) {
                            if (e.getSource().getClass() == button.getClass()) {
                                JButton b = (JButton) e.getSource();
                                if (e.isPopupTrigger()) {
                                    pmmBtn.show(e.getComponent(), e.getX(), e.getY());
                                    b.setSelected(true);
                                }
                            }
                        }
                    });
                    break;
                case 2:
                    button.setBackground(Color.GRAY);
                    button.setHorizontalTextPosition(0);
                    button.setFont(new Font("Dialog", 8, 8));
                    button.setText("Không hoạt động");
                    button.setVerticalTextPosition(3);
                    button.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            selectedButtonPopup(e, button);
                        }

                        private void selectedButtonPopup(MouseEvent e, JButton button) {
                            if (e.getSource().getClass() == button.getClass()) {
                                JButton b = (JButton) e.getSource();
                                if (e.isPopupTrigger()) {
                                    pmmKhoiPhuc.show(e.getComponent(), e.getX(), e.getY());
                                    b.setSelected(true);
                                }
                            }
                        }
                    });
                    break;
            }
            //Hiển thị màu theo nhóm
            if (ban.getTableGroup() != null) {
                String tn = ban.getTableGroup();
                switch (tn) {
                    case "Nhóm 1":
                        button.setBackground(Color.orange);
                        break;
                    case "Nhóm 2":
                        button.setBackground(Color.yellow);
                        break;
                    case "Nhóm 3":
                        button.setBackground(Color.pink);
                        break;
                    case "Nhóm 4":
                        button.setBackground(Color.magenta);
                        break;
                    case "Nhóm 5":
                        button.setBackground(Color.lightGray);
                        break;
                    case "Nhóm 6":
                        button.setBackground(Color.cyan);
                        break;
                    case "Nhóm 7":
                        button.setBackground(Color.DARK_GRAY);
                        break;
                    case "Nhóm 8":
                        button.setBackground(Color.white);
                        break;
                    case "Nhóm 9":
                        button.setBackground(Color.green);
                        break;
                    case "Nhóm 10":
                        button.setBackground(Color.blue);
                        break;
                }
            }
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TableSelectedHandler(e, btnVaoBan, lblBan, that, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac, cbbkhu);
                }
            });
            jp.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    selectedButtonPopup(e, jp);
                }
                private void selectedButtonPopup(MouseEvent e, JPanel pan) {
                    if (e.getSource().getClass() == pan.getClass()) {
                        if (e.isPopupTrigger()) {
                            pmmBanAo.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
            });
            jp.add(button);
            BanButtons banButton = new BanButtons(button, ban.getIDTable(), cs, ban.getLocation(), ban.getTableGroup() == null ? "" : ban.getTableGroup());
            banButtonList.put(button, banButton);
        }
        that.add(pnlMain, new Integer(10), 0);
        // Clearing my JFrame and render again
        that.revalidate();
        that.repaint();
    }

    private void mnChuyenBanActionPerformed(ActionEvent evt) {
        if (evt.getSource().getClass() == JMenuItem.class) {
            dialogHelper.alert(null, "Chọn bàn chuyển đến nhá Pro");
            this.ButtonChuyen = firstButton;
        }
    }

    private void TableSelectedHandler(ActionEvent e, JButton btnVaoBan, JLabel lblBan, JPanel that, JTable tblOder, JTable tblLichSu, JPanel PanlPanelLS, JPanel Oder, JTextField txtMaHD, JTextField txtMaKH, JTextField txtNameEMP, JLabel TimeOrder, JTextField txtTong, JPanel PanCac, int khu) {
        String IDOrderCu = txtMaHD.getText();
        String IDOrderMoi = "";
        if (e.getSource().getClass() == JButton.class) {
            JButton selectedButton = (JButton) e.getSource();
            this.firstButton = selectedButton;
            BanButtons banButton = banButtonList.get(selectedButton);
            this.GroupBan = banButton.getTableGroup();
            lblBan.setToolTipText(banButton.getTableGroup());
            lblBan.setText(String.valueOf(banButton.getIDTalbe()));
            model = (DefaultTableModel) tblOder.getModel();
            if (banButton.getStatus() == 0) {//------------------------------Bàn không có khách
                btnVaoBan.setEnabled(true);
//                Oder.setVisible(false);
                CardLayout card = (CardLayout) PanCac.getLayout();
                card.show(PanCac, "card2");
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();
                that.revalidate();
                LichSu(PanlPanelLS, tblLichSu, banButton);
                txtMaHD.setText("");
                if (!banButton.getTableGroup().equals("")) {
                    btnVaoBan.setEnabled(false);
                    card.show(PanCac, "card4");
                }
                this.dong = 0;
            } else if (banButton.getStatus() == 1) {//-----------------------------------------Bàn có khách
                model = (DefaultTableModel) tblOder.getModel();
                model.setRowCount(0);
                this.OrderCTT(txtMaHD, banButton.getIDTalbe());
                this.billTable(txtMaHD, txtNameEMP, txtMaKH, TimeOrder, tblOder, banButton.getIDTalbe());
                IDOrderMoi = txtMaHD.getText();
                this.dong = tblOder.getRowCount();
                btnVaoBan.setEnabled(false);
                CardLayout card = (CardLayout) PanCac.getLayout();
                card.show(PanCac, "card3");
//                PanlPanelLS.setVisible(false);
//                Oder.setVisible(true);
                this.tongTien(txtTong, tblOder);
            } else {
                btnVaoBan.setEnabled(false);
                CardLayout card = (CardLayout) PanCac.getLayout();
                card.show(PanCac, "card4");
            }
            if (ButtonChuyen != null) {//----------------------------Nếu bọn chuyển bàn hoặc gộp bàn
                if (banButton.getStatus() == 1) {
                    dialogHelper.alert(null, "Bàn này có người rồi chuển cc j ?");
                    return;
                } else if (banButton.getStatus() == 2) {
                    dialogHelper.alert(null, "Bàn này cất rồi gọi chủ ra đi ");
                    return;
                }
                BanButtons ban = banButtonList.get(ButtonChuyen);
//                dialogHelper.alert(null, "Chuyển từ bàn :" + ban.getIDTalbe() + "Đến" + banButton.getIDTalbe());
                chuenBan(banButton.getIDTalbe(), "Chuyển từ bàn :" + ban.getIDTalbe() + "Đến" + banButton.getIDTalbe(), IDOrderCu);
                updatebnGuoi();
                String sql = "UPDATE [Table] SET [Status] = 0 WHERE IDTable = ?";
                try {
                    JDBC.update(sql, ban.getIDTalbe());
                } catch (SQLException ex) {
                    Logger.getLogger(QLOrder_Service.class.getName()).log(Level.SEVERE, null, ex);
                }
                taoTable(that, khu, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                ButtonChuyen = null;
            }
            if (ButtonGop != null) {//----------------------------Nếu bọn chuyển bàn hoặc gộp bàn
                if (banButton.getStatus() == 0) {
                    dialogHelper.alert(null, "Bàn này có ai đâu mà gộp hả ?");
                    return;
                } else if (banButton.getStatus() == 2) {
                    dialogHelper.alert(null, "Bàn này cất rồi gọi chủ ra đi ");
                    return;
                }
                BanButtons ban = banButtonList.get(ButtonGop);
                if (dialogHelper.confirm(null, "Bạn có muốn gộp hóa đơn vào luôn không")) {
                    chuenBan(banButton.getIDTalbe(), "Chuyển từ bàn :" + ban.getIDTalbe() + "Đến" + banButton.getIDTalbe(), IDOrderCu);
                    gopBanvsHD(banButton.getIDTalbe(), IDOrderCu, IDOrderMoi);
                } else {
                    chuenBan(banButton.getIDTalbe(), "Chuyển từ bàn :" + ban.getIDTalbe() + "Đến" + banButton.getIDTalbe(), IDOrderCu);
                }

                String sql = "UPDATE [Table] SET [Status] = 0 WHERE IDTable = ?";
                try {
                    JDBC.update(sql, ban.getIDTalbe());
                } catch (SQLException ex) {
                    Logger.getLogger(QLOrder_Service.class.getName()).log(Level.SEVERE, null, ex);
                }
                taoTable(that, khu, btnVaoBan, lblBan, tblOder, tblLichSu, PanlPanelLS, Oder, txtMaHD, txtMaKH, txtNameEMP, TimeOrder, txtTong, PanCac);
                ButtonGop = null;
            }
        }
    }

    public void chuenBan(String IDTable, String Note, String IDOder) {
        try {
            JDBC.update(chuenBan, IDTable, Note, IDOder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gopBanvsHD(String IDTable, String IDOderCu, String IDOrderMoi) {
        String sql = "UPDATE OrderDetail SET IDTable = ?,IDOrder = ? WHERE IDOrder = ?";
        try {
            JDBC.update(sql, IDTable, IDOderCu, IDOrderMoi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LichSu(JPanel PanlPanelLS, JTable tblLichSu, BanButtons banButton) {
        PanlPanelLS.setVisible(true);
        model = (DefaultTableModel) tblLichSu.getModel();
        model.setRowCount(0);
        this.model.setColumnIdentifiers(new Object[]{"IDOrder", "TimeOder", "Name NV", "NameKH", "Trạng Thái"});
        ArrayList<LSOrder> list = this.LichSu(SQL_liSu, banButton.getIDTalbe().trim(), dateHelper.DATE_FORMATER2.format(dateHelper.now()).trim());
        for (LSOrder lSOrder : list) {
            Object[] row = new Object[]{
                lSOrder.getIDOrder(),
                dateHelper.Time_FORMATER.format(lSOrder.getTimeOder()),
                lSOrder.getNameEMP() == null ? "Admin" : lSOrder.getNameEMP(),
                lSOrder.getCusName(), this.StatusOr(lSOrder.getStatus())
            };
            model.addRow(row);
        }
    }

    private String StatusOr(int n) {
        String kh = "Chưa thanh toán";
        if (n == 3) {
            kh = "Đã hủy";
        } else if (n == 1) {
            kh = "Chưa thanh toán";
        } else {
            kh = "Đã thanh toán";
        }
        return kh;
    }

    public ArrayList<SPChiTiet> select() {
        return (ArrayList<SPChiTiet>) this.SelectBySQL(sql_all);
    }

    public ArrayList<LSOrder> LichSu(String sql, Object... args) {
        ArrayList<LSOrder> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                LSOrder ls = new LSOrder();
                ls.setIDOrder(rs.getString("IDOrder"));
                ls.setNameEMP(rs.getString("NameEMP"));
                ls.setCusName(rs.getString("CusName"));
                ls.setStatus(rs.getInt("Status"));
                ls.setTimeOder(rs.getTime("TimeOder"));
                list.add(ls);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<SPChiTiet> SelectBySQL(String sql, Object... args) {
        List<SPChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                SPChiTiet table = new SPChiTiet();
                table.setIDProduct(rs.getString("IDProduct"));
                table.setImage(rs.getString("Image"));
                table.setPrice(rs.getInt("Price"));
                table.setProductName(rs.getString("ProductName"));
                table.setStatus(rs.getBoolean("Status"));
                table.setSize(rs.getString("Size"));
                table.setTypeName(rs.getString("TypeName"));
                list.add(table);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<billCTT> SelectBill(String sql, Object... args) {
        List<billCTT> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                billCTT table = new billCTT();
                table.setIDProduct(rs.getString("IDProduct"));
                table.setIDCust(rs.getString("IDCust"));
                table.setIDOrderString(rs.getString("IDOrder"));
                table.setNote(rs.getString("Note"));
                table.setProductName(rs.getString("ProductName"));
                table.setSize(rs.getString("Size"));
                table.setTimeOrder(rs.getTime("TimeOder"));
                table.setPrice(rs.getInt("Price"));
                table.setQuantity(rs.getInt("Quantity"));
                table.setNameEMP(rs.getString("NameEMP"));
                table.setStatus(rs.getBoolean("Status"));
                list.add(table);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ENTITY_Area> getkhu() {
        return (ArrayList<ENTITY_Area>) this.qlk.select();
    }

    @Override
    public ArrayList<ENTITY_Product> getsp() {
        return (ArrayList<ENTITY_Product>) this.lqp.select();
    }

    @Override
    public void hienTableSP(JTable tbl) {
        this.model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
        this.model.setColumnIdentifiers(new Object[]{"IDSP", "Loại", "Tên SP", "Size", "Giá", ""});
        TableColumnModel columnModel = tbl.getColumnModel();
        columnModel.getColumn(3).setMaxWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(5).setMaxWidth(60);
        try {
            ArrayList<SPChiTiet> list = this.select();
            for (SPChiTiet cd : list) {
                Object[] row = {
                    cd.getIDProduct(),
                    cd.getTypeName(),
                    cd.getProductName(),
                    cd.getSize(),
                    cd.getPrice(),
                    //                    cd.isStatus() ? "Còn" : "Hết",
                    "Thêm"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hienTableOder(JTable tblOD) {
        this.model = (DefaultTableModel) tblOD.getModel();
        this.model.setRowCount(0);
        this.model.setColumnIdentifiers(new Object[]{"IDOrder", "IDSP", "Tên SP", "Size", "Giá", "SL", "Ghi chú", "Hủy", "", "Khác"});
        TableColumnModel columnModel = tblOD.getColumnModel();
        columnModel.getColumn(5).setMaxWidth(60);
        columnModel.getColumn(5).setMinWidth(60);
        columnModel.getColumn(3).setMaxWidth(40);
        columnModel.getColumn(0).setMaxWidth(60);
        columnModel.getColumn(0).setMaxWidth(60);
        columnModel.getColumn(1).setMinWidth(40);
        columnModel.getColumn(1).setMaxWidth(40);
        columnModel.getColumn(7).setMaxWidth(60);
        columnModel.getColumn(8).setMaxWidth(60);
        columnModel.getColumn(9).setMaxWidth(50);
        columnModel.getColumn(9).setMinWidth(50);
    }

    @Override
    public void timSP(JTextField txt, JTable tbl) {
        this.model = (DefaultTableModel) tbl.getModel();
        this.model.fireTableDataChanged();
        TableRowSorter Sorter = new TableRowSorter(this.model);
        tbl.setRowSorter(Sorter);
        Sorter.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }

    @Override
    public void taoHD(JTextField txt) {
        try {
            ResultSet rs = JDBC.query("SELECT MAX(IDOrder) FROM [Order]");
            rs.next();
            rs.getString(1);
            if (rs.getString(1) == null) {
                txt.setText("OD001");
            } else {
                long id = Long.parseLong(rs.getString(1).substring(2, rs.getString(1).length()));
                id++;
                txt.setText("OD" + String.format("%03d", id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String OrderCTT(JTextField txt, String IDTable) {
        try {
            ResultSet rs = JDBC.query(SQL_chuaTT, IDTable, dateHelper.DATE_FORMATER2.format(dateHelper.now()).trim());
            rs.next();
            String s = rs.getString(1);
            txt.setText(s);
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatebnGuoi() {
        String sql = "UPDATE [Table] SET [Status] = 1 WHERE IDTable = ?";
        JButton selectedButton = firstButton;
        BanButtons banButton = banButtonList.get(selectedButton);
        try {
            JDBC.update(sql, banButton.getIDTalbe());
        } catch (SQLException ex) {
            Logger.getLogger(QLOrder_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertOr(JTextField txt) {
        ENTITY_Order or = new ENTITY_Order();
        or.setIDOrder(txt.getText());
        this.qlo.insert(or);
    }

    @Override
    public void insertOderDe(ENTITY_BILL bill) {
        try {
            JDBC.update(InsertOderDe, bill.getIDOrder(), bill.getIDProduct(), bill.getIDTable(), bill.getQuantity(), bill.getNote(), bill.getStatus(), bill.getReason());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lichsuOrder(JPanel PanlPanelLS, JTable tblLichSu) {
        BanButtons banButton = banButtonList.get(firstButton);
        this.LichSu(PanlPanelLS, tblLichSu, banButton);
    }

    @Override
    public void thanhToan(JTextField txtMaHD) {

        try {
            JDBC.update(thanhToan, txtMaHD.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int dongC() {
        return dong;
    }

    @Override
    public void bill(JTextField txtMaHD, JTextField txtNameEMP, JTextField txtMaKH, JLabel TimeOrder, JTable tblOrder) {
        model = (DefaultTableModel) tblOrder.getModel();
        model.setRowCount(0);
        ArrayList<billCTT> list = (ArrayList<billCTT>) this.SelectBill(billCTT, txtMaHD.getText());
        for (billCTT cTT : list) {
            txtMaKH.setText(cTT.getIDCust());
            txtNameEMP.setText(cTT.getNameEMP() == null ? "Admin" : cTT.getNameEMP());
            TimeOrder.setText(dateHelper.Time_FORMATER.format(cTT.getTimeOrder()));
            Object[] row = new Object[]{
                cTT.getIDOrderString(),
                cTT.getIDProduct(),
                cTT.getProductName(),
                cTT.getSize(),
                cTT.getPrice(),
                cTT.getQuantity(),
                cTT.getNote(), cTT.isStatus() ? "Hủy" : "", "Xóa", false
            };
            model.addRow(row);
        }
    }

    @Override
    public double tongTien(JTextField txttong, JTable tblOder) {
        NumberFormat formatter = new DecimalFormat("#,###");
        double total = 0;
        int line = tblOder.getRowCount();
        for (int i = 0; i < line; i++) {
            if (tblOder.getValueAt(i, 6).toString().equals("")) {
                double gia = Double.valueOf(tblOder.getValueAt(i, 4).toString());
                int SL = Integer.valueOf(tblOder.getValueAt(i, 5).toString());
                double thanhtien = gia * SL;
                total += thanhtien;
            }
        }
        txttong.setText(formatter.format(total) + "VNĐ");
        return total;
    }

    @Override
    public void updatebnThanhToan(JTextField txtMaHD) {
        String sql = "UPDATE [Table] SET [Status] = 0 WHERE IDTable = ?";
        JButton selectedButton = firstButton;
        BanButtons banButton = banButtonList.get(selectedButton);
        System.out.println(OrderCTT(txtMaHD, banButton.getIDTalbe()));
        if (OrderCTT(txtMaHD, banButton.getIDTalbe()) != null) {// Kiểm tra còn đơn nào chưa thanh toán không
            return;
        }
        try {
            JDBC.update(sql, banButton.getIDTalbe());
        } catch (SQLException ex) {
            Logger.getLogger(QLOrder_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateOderDe(ENTITY_BILL bill) {
        String sql = "UPDATE OrderDetail SET Note = ?,Quantity=? WHERE IDOrder = ? AND IDProduct = ? AND Note = ?";
        try {
            JDBC.update(sql, bill.getNote(), bill.getQuantity(), bill.getIDOrder(), bill.getIDProduct(), bill.getNote());
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(QLOrder_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void huyMon(JTextField txtMaHD, String Reason, String IDProduct, String Note) {
        String sql = "UPDATE OrderDetail SET [Status] = 1, Reason = ? WHERE IDOrder = ? and IDProduct = ? and Note = ?";
        try {
            JDBC.update(sql, Reason, txtMaHD.getText(), IDProduct, Note);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaBan(String IDTable) {
        String sql = "UPDATE [Table] SET [Status] = 2 WHERE IDTable = ?";
        try {
            JDBC.update(sql, IDTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void khoiPhucBan(String IDTable) {
        String sql = "UPDATE [Table] SET [Status] = 0 WHERE IDTable = ?";
        try {
            JDBC.update(sql, IDTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void huyDon(String txtMaHD, String Reason) {
        String sql = "UPDATE [Order] SET [Status] = 3,Reason =? WHERE IDOrder = ?";
        try {
            JDBC.update(sql, Reason, txtMaHD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tachHDon(String txtMaHDCu, String txtMaHDMoi, String IDProduct, String Note, String IDTable) {
        String sql = "UPDATE OrderDetail SET IDOrder = ?,IDTable = ? WHERE IDProduct = ? AND Note = ? AND IDOrder = ?";
        try {
            JDBC.update(sql, txtMaHDMoi, IDTable, IDProduct, Note, txtMaHDCu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taoNhom(String tenNhom, String IDTable) {
        String sql = "UPDATE [TABLE] SET TableGroup = ? WHERE IDTable = ?";
        try {
            JDBC.update(sql, tenNhom, IDTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void xoaNhom(String nhom) {
        String sql = "UPDATE [TABLE] SET TableGroup = NULL WHERE TableGroup = ?";
//        BanButtons sn = banButtonList.get(firstButton);
//        System.out.println(sn.getTableGroup());
        if (nhom != null) {
            try {
                JDBC.update(sql, nhom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void billTable(JTextField txtMaHD, JTextField txtNameEMP, JTextField txtMaKH, JLabel TimeOrder, JTable tblOrder, String IDTable) {
        model = (DefaultTableModel) tblOrder.getModel();
        model.setRowCount(0);
        String sql = "SELECT OrderDetail.IDOrder,TimeOder,OrderDetail.IDProduct,PR.ProductName,Size,Quantity,PR.Price,Note,OD.IDCust,EMP.NameEMP,OrderDetail.Status FROM OrderDetail\n"
                + "JOIN [Order] OD ON OD.IDOrder = OrderDetail.IDOrder\n"
                + "JOIN Product PR ON PR.IDProduct = OrderDetail.IDProduct\n"
                + "LEFT JOIN Employee EMP ON EMP.UsernameEMP = OD.UsernameEMP\n"
                + "JOIN ProductType on PR.IDType = ProductType.IDType\n"
                + "WHERE IDTable = ? AND OD .[Status] = 1";
        ArrayList<billCTT> list = (ArrayList<billCTT>) this.SelectBill(sql, IDTable);
        for (billCTT cTT : list) {
            txtMaKH.setText(cTT.getIDCust());
            txtNameEMP.setText(cTT.getNameEMP() == null ? "Admin" : cTT.getNameEMP());
            TimeOrder.setText(dateHelper.Time_FORMATER.format(cTT.getTimeOrder()));
            Object[] row = new Object[]{
                cTT.getIDOrderString(),
                cTT.getIDProduct(),
                cTT.getProductName(),
                cTT.getSize(),
                cTT.getPrice(),
                cTT.getQuantity(),
                cTT.getNote(), cTT.isStatus() ? "Hủy" : "", "Xóa", false
            };
            model.addRow(row);
        }
    }

    @Override
    public void ReloadCombobox(JComboBox cbb) {
        cbb.removeAllItems();
        cbb.addItem("Không có");
        cbb.addItem("Khách hàng VIP");
        String sql = "select * from Promotions where StartPromo <= ? and EndPromo >= ?";
        try {
            Date now = new Date();
            ResultSet rs = JDBC.query(sql, dateHelper.DATE_FORMATER2.format(now), dateHelper.DATE_FORMATER2.format(now));
            while (rs.next()) {
                cbb.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            dialogHelper.alert(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
    }

    @Override
    public void txtMaKHCaretUpdate(JTextField txtMaKH, JTextField txtdis1, JLabel lbl, JLabel lbIDCus, JLabel lbNameCus, JLabel lbDateCus, JLabel lbDateEndCus, JLabel lbDisCus) {
        String sql = "Select * from Customer where IDCust = ?";
        try {
            ResultSet rs = JDBC.query(sql, txtMaKH.getText());
            if (!rs.next()) {
                lbl.setText("Mã thẻ không tồn tại!");
                lbl.setForeground(Color.red);
                txtdis1.setText("0");
//                    ResetPnInfor();
            } else {
                lbl.setText("Thành công.");
                lbl.setForeground(Color.YELLOW);
                lbIDCus.setText(rs.getString(1));
                lbNameCus.setText(rs.getString(2));
                lbDateEndCus.setText(rs.getString(4));
                lbDateCus.setText(rs.getString(3));
                lbDisCus.setText(rs.getString(7) + "%");
                txtdis1.setText(rs.getString(7));
            }
        } catch (SQLException ex) {
            dialogHelper.alert(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
    }

}
