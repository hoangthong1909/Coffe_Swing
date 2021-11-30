/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS_Services;

import DAL_Models.ENTITY_Product;
import Utils.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamd
 */
public class QLHoaDOn_Service {

     public List<ENTITY_Product> SelectBySQL(String sql, Object... args) {
        List<ENTITY_Product> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                ENTITY_Product customer = new ENTITY_Product();
                customer.setProductName(rs.getString(1));

                list.add(customer);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getListHoaDon() {
        String sql = "{CALL getListHoaDon}";
        String[] cols = {"IDHD", "NameEMP", "DateOrder", "TimeOder", "Reason", "TongTien", "Status"};
        return this.getListOfArray(sql, cols);
    }
    public List<Object[]> getListHoaDonTHANG(int thang) {
        String sql = "{CALL getListHoaDonThang(?)}";
        String[] cols = {"IDHD", "NameEMP", "DateOrder", "TimeOder", "Reason", "TongTien", "Status"};
        return this.getListOfArray(sql, cols,thang);
    }
    public List<ENTITY_Product> getListDoUong(String idHD) {
        String sql = "select ProductName from Product p join OrderDetail od on p.IDProduct=od.IDProduct where od.IDOrder=?";
        return SelectBySQL(sql, idHD);
    }

    public void fillTable(JTable tbl) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
                String tt = null;
                String doUong="";
                String nv="";
        List<Object[]> list = getListHoaDon();
        if (list != null) {
            for (Object[] o : list) {
        List<ENTITY_Product> listdoUong = getListDoUong(String.valueOf(o[0]));
        doUong="";
                for (ENTITY_Product odu : listdoUong) {
                        doUong = doUong+ odu.getProductName()+",";
                }
                
                int ma = Integer.valueOf(String.valueOf(o[6]));
                if (ma==1) {
                    tt="Chưa thanh toán";
                }else if (ma==2) {
                    tt="Đã thanh toán";
                }else if (ma==3) {                  
                    tt="Đã hủy";
                }
                
                Object[] row = new Object[]{
                    o[0],
                    o[1]==null?"ADMIN":o[1],
                    o[2],
                    o[3],
                    o[4],
                    doUong,
                    o[5],
                    tt                  
                };
                model.addRow(row);
                
            }
        }
    }
    public void fillTabletHANG(JTable tbl,int thang) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
                String tt = null;
                String doUong="";
                String nv="";
        List<Object[]> list = getListHoaDonTHANG(thang);
        if (list != null) {
            for (Object[] o : list) {
        List<ENTITY_Product> listdoUong = getListDoUong(String.valueOf(o[0]));
        doUong="";
                for (ENTITY_Product odu : listdoUong) {
                        doUong = doUong+ odu.getProductName()+",";
                }
                
                int ma = Integer.valueOf(String.valueOf(o[6]));
                if (ma==1) {
                    tt="Chưa thanh toán";
                }else if (ma==2) {
                    tt="Đã thanh toán";
                }else if (ma==3) {                  
                    tt="Đã hủy";
                }
                
                Object[] row = new Object[]{
                    o[0],
                    o[1]==null?"ADMIN":o[1],
                    o[2],
                    o[3],
                    o[4],
                    doUong,
                    o[5],
                    tt                  
                };
                model.addRow(row);
                
            }
        }
    }
}
