/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS_Services;

import BUS_IServices.*;
import BUS_Models.SanPham;
import DAL_IServices.IProduct_Service;
import DAL_Models.ENTITY_ProductType;
import DAL_Services.Product_Service;
import Utils.JDBC;
import Utils.XImage;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author phamd
 */
public class QLMenu_Service implements IQLMenu_Service {

    private IProduct_Service ips;
    IProduct_Service dao = new Product_Service();
    String sql_all = "SELECT [IDProduct],ProductName,Price,Image,Status,TypeName,Size FROM [Product] "
            + "Join ProductType on Product.IDType = ProductType.IDType";
    String SELECT_BY_TypeName = "Select DISTINCT TypeName from ProductType";
    String SELECT_BY_Size = "SELECT IDType,Size FROM ProductType WHERE TypeName =?";
    String insert = "INSERT INTO [Product]([IDProduct], [ProductName], [Price], [Image],[Status],IDType) VALUES (?, ?, ?, ?,1,?)";

    @Override
    public void taoID(JLabel lbl) {
        try {
            ResultSet rs = JDBC.query("Select MAX(IDProduct) From Product");
            rs.next();
            rs.getString(1);
            if (rs.getString(1) == null) {
                lbl.setText("SP01");
            } else {
                long id = Long.parseLong(rs.getString(1).substring(2, rs.getString(1).length()));
                id++;
                lbl.setText("SP" + String.format("%02d", id).trim());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateSP(SanPham pro) {
        String sql = "UPDATE [Product] SET [ProductName] = ?, [Price] = ?, [Image]=?, IDType =? WHERE [IDProduct] = ?";
        try {
            JDBC.update(sql, pro.getProductName(), pro.getPrice(), pro.getImage(), pro.getIDType(), pro.getIDProduct());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(SanPham pro) {
        try {
            JDBC.update(insert,
                    pro.getIDProduct(),
                    pro.getProductName(),
                    pro.getPrice(),
                    pro.getImage(),
                    pro.getIDType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String IDProduct) {
        String sql = "UPDATE [Product] SET [Status]=0 WHERE [IDProduct] = ?";
        try {
            JDBC.update(sql, IDProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void khoiphuc(String IDProduct) {
        String sql = "UPDATE [Product] SET [Status]=1 WHERE [IDProduct] = ?";
        try {
            JDBC.update(sql, IDProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> select() {
        return (ArrayList<SanPham>) this.SelectBySQL(sql_all);
    }

    public List<SanPham> SelectBySQL(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                SanPham table = new SanPham();
                table.setIDProduct(rs.getString(1));
                table.setProductName(rs.getString(2));
                table.setPrice(rs.getFloat(3));
                table.setImage(rs.getString(4));
                table.setStatus(rs.getBoolean(5));
                table.setTypeName(rs.getString(6));
                table.setSize(rs.getString(7));
                list.add(table);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<ENTITY_ProductType> SelectByType(String sql, Object... args) {
        List<ENTITY_ProductType> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                ENTITY_ProductType table = new ENTITY_ProductType();
//                table.setIDType(rs.getInt("IDType"));
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

    public List<SanPham> SelectBySize(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                SanPham table = new SanPham();
                table.setIDType(rs.getInt("IDType"));
                table.setSize(rs.getString("Size"));
                list.add(table);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<ENTITY_ProductType> selectTypeName() {
        try {
            return this.SelectByType(SELECT_BY_TypeName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SanPham> selectSize(String type) {
        try {
            List<SanPham> list = this.SelectBySize(SELECT_BY_Size, type);
            System.out.println("list :" + list.size());
            return this.SelectBySize(SELECT_BY_Size, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void fillToTable(JTable tbl) {

        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
//        model.fireTableDataChanged();
//        TableRowSorter sorter = new TableRowSorter(model);
//        tblSanPham.setRowSorter(sorter);
//        sanpham1 sp1 = (sanpham1) cboLoai.getSelectedItem();
//        sorter.setRowFilter(RowFilter.regexFilter(sp1.TypeName));
        try {
            List<SanPham> list = this.select();
            for (SanPham pro : list) {
                Object[] row = {pro.getIDProduct(), pro.getProductName(), pro.getSize(), pro.getPrice(), pro.isStatus() ? "Đang sử dụng" : "Không sử dụng"};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadComboTypeName(JComboBox cbo) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo.getModel();
        model.removeAllElements();
        try {
            List<ENTITY_ProductType> list = this.selectTypeName();
            for (ENTITY_ProductType sp : list) {
                List<SanPham> lista = this.SelectBySize(SELECT_BY_Size, sp.getTypeName());
                for (SanPham sanPham : lista) {
                    sp.setIDType(sanPham.getIDType());
                }
                model.addElement(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadComboSize(JComboBox cbo, String type, JLabel lbl) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo.getModel();
        model.removeAllElements();
        try {
            List<SanPham> list = this.selectSize(type);
            for (SanPham sp : list) {
                if (list.size() ==1) {
                    if (sp.getSize()==null) {
                    cbo.addItem("Trống");
                    }else{
                        model.addElement(sp);
                    }
                } else {
                    model.addElement(sp);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    JFileChooser fileChooser = new JFileChooser();

    public void chonAnh(JLabel lbl) {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();//lay ra file dc chon
            XImage.save(file); // luu vao trong thu muc
            ImageIcon icon = XImage.read(file.getName()); //doc file tu thu muc
            lbl.setIcon(icon); //hien thi len lblhinh
            lbl.setToolTipText(file.getName()); //giu ten hinh trong tooltip
        }
    }   // Tim Kiem
    private DefaultTableModel model;

    public void timSP(JTextField txt, JTable tbl) {
        this.model = (DefaultTableModel) tbl.getModel();
        this.model.fireTableDataChanged();
        TableRowSorter Sorter = new TableRowSorter(this.model);
        tbl.setRowSorter(Sorter);
        Sorter.setRowFilter(RowFilter.regexFilter(txt.getText()));
    }
    // Tim Theo ID(de lay du lieu hien thi len form)

    public List<SanPham> SelectBySQL1(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                SanPham table = new SanPham();
                table.setIDProduct(rs.getString(1));
                table.setProductName(rs.getString(2));
                table.setPrice(rs.getFloat(3));
                table.setImage(rs.getString(4));
                table.setStatus(rs.getBoolean(5));
                table.setIDType(rs.getInt(7));
                table.setTypeName(rs.getString(8));
                table.setSize(rs.getString(9));
                list.add(table);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public SanPham findById(String IDProduct) {
        String sql = "Select * from Product join ProductType on Product.IDType =ProductType.IDType where IDProduct=?";
        List<SanPham> list = this.SelectBySQL1(sql, IDProduct);
        if (list.isEmpty()) {
            return null;
        }
        SanPham sp = list.get(0);
        System.out.println(sp.getIDProduct());
        System.out.println(sp.getIDType());
        System.out.println(sp.getImage());
        System.out.println(sp.getPrice());
        System.out.println(sp.getProductName());
        System.out.println(sp.getTypeName());
        System.out.println(sp.getSize());
        return list.get(0);
    }
}
