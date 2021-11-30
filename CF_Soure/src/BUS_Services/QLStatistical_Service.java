/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS_Services;

import BUS_IServices.*;
import Utils.JDBC;
import Utils.ThongBao;
import java.awt.CardLayout;
import java.awt.Dimension;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author phamd
 */
public class QLStatistical_Service implements IQLStatistical_Service {

    List<Object[]> msList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
    SimpleDateFormat formatThang = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatNam = new SimpleDateFormat("yyyy");

    @Override
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

    @Override
    public List<Object[]> getListTongMonvaHDNgay(Date date) {
        String sql = "{CALL getTongMvaTongHD(?)}";
        String[] cols = {"tongM", "TongHD"};
        return this.getListOfArray(sql, cols, date);
    }

    @Override
    public void setTongMonNgay(JLabel lblTM, JLabel lblHD, Date ngay) {
        List<Object[]> list = getListTongMonvaHDNgay(ngay);
        if (list != null) {
            for (Object[] o : list) {
                System.out.println("" + o[0] + o[1]);
                lblTM.setText(String.valueOf(o[0]));
                lblHD.setText(String.valueOf(o[1]));

            }
        }
    }

    @Override
    public List<Object[]> getListTongMonvaHDThang(int date) {
        String sql = "{CALL getTongMvaTongHDThang(?)}";
        String[] cols = {"tongM", "TongHD"};
        return this.getListOfArray(sql, cols, date);
    }

    @Override
    public void setTongMonThang(JLabel lblTM, JLabel lblHD, int thang) {
        List<Object[]> list = getListTongMonvaHDThang(thang);
        if (list != null) {
            for (Object[] o : list) {
                System.out.println("" + o[0] + o[1]);
                lblTM.setText(String.valueOf(o[0]));
                lblHD.setText(String.valueOf(o[1]));
            }
        }
    }

    @Override
    public List<Object[]> getListTongMonvaHDNam(int date) {
        String sql = "{CALL getTongMvaTongHDNam(?)}";
        String[] cols = {"tongM", "TongHD"};
        return this.getListOfArray(sql, cols, date);
    }

    @Override
    public List<Object[]> getListTongMonvaHDKhoang(Date ngayBD, Date ngayKT) {
        String sql = "{CALL getTongMvaTongHDKhoang(?,?)}";
        String[] cols = {"tongM", "TongHD"};
        return this.getListOfArray(sql, cols, ngayBD, ngayKT);
    }

    @Override
    public void setTongMonKhoang(JLabel lblTM, JLabel lblHD, Date ngayBD, Date ngayKT) {
        List<Object[]> list = getListTongMonvaHDKhoang(ngayBD, ngayKT);
        if (list != null) {
            for (Object[] o : list) {
                System.out.println("" + o[0] + o[1]);
                lblTM.setText(String.valueOf(o[0]));
                lblHD.setText(String.valueOf(o[1]));

            }
        }
    }

    @Override
    public void setTongMonNam(JLabel lblTM, JLabel lblHD, int nam) {
        List<Object[]> list = getListTongMonvaHDNam(nam);
        if (list != null) {
            for (Object[] o : list) {
                System.out.println("" + o[0] + o[1]);
                lblTM.setText(String.valueOf(o[0]));
                lblHD.setText(String.valueOf(o[1]));

            }
        }
    }

    @Override
    public List<Object[]> getListByTKNgay(Date ngay) {
        String sql = "{CALL DT_THONGKENGAY(?)}";
        String[] cols = {"Tien", "TimeOder"};
        return this.getListOfArray(sql, cols, ngay);
    }

    @Override
    public void setDataNgay(JPanel pnlNgay, Date jdateNgay) {

        List<Object[]> list = getListByTKNgay(jdateNgay);
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if (list != null) {
            for (Object[] o : list) {
                String s = String.valueOf(o[0]);
                float so = Float.valueOf(s);
                String gio = format.format(o[1]);
                System.out.println("" + so + gio);
                data.addValue(so, "Số tiền", gio);
            }
        }
        JFreeChart barChart = ChartFactory.createBarChart("Thống kê doanh thu ngày".toUpperCase(), "Thời gian", "Số Tiền", data,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(pnlNgay.getWidth(), 321));

        pnlNgay.removeAll();
        pnlNgay.setLayout(new CardLayout());
        pnlNgay.add(chartPanel);
        pnlNgay.validate();
        pnlNgay.repaint();
    }

    @Override
    public List<Object[]> getListByTKThang(int thang) {
        String sql = "{CALL DT_THONGKETHANG(?)}";
        String[] cols = {"Tien", "Ngay"};
        return this.getListOfArray(sql, cols, thang);
    }

    @Override
    public void setDataThang(JPanel pnlNgay, int thang) {
        try {
            List<Object[]> list = getListByTKThang(thang);
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if (list != null) {
            for (Object[] o : list) {
                String t = String.valueOf(o[0]);
                float tien = Float.valueOf(t);
                String ngay = String.valueOf(formatThang.format(o[1]));

                data.addValue(tien, "Số tiền", ngay);
            }
        }
        JFreeChart barChart = ChartFactory.createBarChart("Thống kê doanh thu tháng".toUpperCase(), "Thời gian", "Số Tiền", data,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(pnlNgay.getWidth(), 321));

        pnlNgay.removeAll();
        pnlNgay.setLayout(new CardLayout());
        pnlNgay.add(chartPanel);
        pnlNgay.validate();
        pnlNgay.repaint();
        } catch (Exception e) {
          //  e.printStackTrace();
        }
    }

    @Override
    public List<Object[]> getListByTKNam(int nam) {
        String sql = "{CALL DT_THONGKENAM(?)}";
        String[] cols = {"Tien", "Thang"};
        return this.getListOfArray(sql, cols, nam);
    }

    @Override
    public void setDataNam(JPanel pnlNgay, int nam) {

        List<Object[]> list = getListByTKNam(nam);
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if (list != null) {
            for (Object[] o : list) {
                String s = String.valueOf(o[0]);
                float so = Float.valueOf(s);
                String thang = String.valueOf(o[1]);
                //  System.out.println("" + so + thang);
                data.addValue(so, "Số tiền", "Tháng " + thang);
            }
        }
        JFreeChart barChart = ChartFactory.createBarChart("Thống kê doanh thu năm".toUpperCase(), "Thời gian", "Số Tiền", data,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(pnlNgay.getWidth(), 321));

        pnlNgay.removeAll();
        pnlNgay.setLayout(new CardLayout());
        pnlNgay.add(chartPanel);
        pnlNgay.validate();
        pnlNgay.repaint();
    }

    @Override
    public List<Object[]> getListByTKKhoangList(Date ngayBD, Date ngayKT) {
        String sql = "{CALL DT_THONGKEKHOANG(?,?)}";
        String[] cols = {"Tien", "Ngay"};
        return this.getListOfArray(sql, cols, ngayBD, ngayKT);
    }

    @Override
    public void setDataKhoang(JPanel pnlNgay, Date ngayBD, Date ngayKT) {
        List<Object[]> list = getListByTKKhoangList(ngayBD, ngayKT);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (list != null) {
            for (Object[] o : list) {
                String s = String.valueOf(o[0]);
                float so = Float.valueOf(s);
                String ngay = formatThang.format(o[1]);

                //   System.out.println("" + so + ngay);
                dataset.addValue(so, "Số tiền", ngay);
            }
        }
        JFreeChart barChart = ChartFactory.createBarChart("Thống kê doanh thu theo khoảng".toUpperCase(), "Thời gian", "Số Tiền", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(pnlNgay.getWidth(), 321));

        pnlNgay.removeAll();
        pnlNgay.setLayout(new CardLayout());
        pnlNgay.add(chartPanel);
        pnlNgay.validate();
        pnlNgay.repaint();
    }

    public List<Object[]> getListBysendMail(int nam) {
        String sql = "{CALL sendmailNam(?)}";
        String[] cols = {"IDHD", "UsernameEMP", "DateOrder", "TimeOder", "Reason"};
        return this.getListOfArray(sql, cols, nam);
    }

    @Override
    public void sendmail(String message) {
        try {
            String host = "smtp.gmail.com";
            String user = "manh1qn@gmail.com";
            String pass = "Manh0988307480";
            String to = "manh1qn@gmail.com";
            String subject = "Báo cáo";

            boolean sessionDebug = false;
            //!.Tạo 1 dối tượng Properties
            Properties pros = new Properties();
            pros.put("mail.smtp.auth", "true");
            pros.put("mail.smtp.starttls.enable", "true");
            pros.put("mail.smtp.host", "smtp.gmail.com");//2.Chỉ ra máy chủ mail của gg
            pros.put("mail.smtp.port", 587);//3.Chỉ ra port : 587 Cổng vào ra dữ liệu
            pros.put("mail.smtp.starttls.required", "true");
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            Session mailSession = Session.getInstance(pros,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);//Tài khoản Gmail của bạn và pass
                }
            }
            );
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            //Gán giá trị cho các thuộc tính đôi tượng msg
            msg.setFrom(new InternetAddress(user));//5.Từ địa chỉ Gmail nào gưởi đi
            //            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));//Tù Gmail gưởi đến mai nào
            msg.setSubject(subject);//Tiêu đề thư
            msg.setText(message);//Nội dung thư
////            Transport.send(msg);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            ThongBao.alert(null, "Gửi thành công");
        } catch (Exception e) {
        }
    }

    public List<Object[]> getBillHuyNam(int nam) {
        String sql = "{CALL getBillHuyNam(?)}";
        String[] cols = {"tongBillHuy"};
        return this.getListOfArray(sql, cols, nam);
    }

    @Override
    public void guiBCN(int nam) {

        String message = "";
        try {
            List<Object[]> list1 = getListBysendMail(nam);
            List<Object[]> list2 = getListTongMonvaHDNam(nam);
            List<Object[]> list3 = getBillHuyNam(nam);
            List<Object[]> list4 = getListByTKNam(nam);

            if (list1 != null) {
                for (Object[] o : list2) {
                    if (list2 != null) {
                        if (list4 != null) {
                            for (Object[] oT : list4) {
                                message = message + "\t\t\t\tBáo cáo trong năm\n"
                                        + "|---------------------------------------------------------------------------|\n"
                                        + " |                                                  \t\t\t\t\t|\n"
                                        + "\t\tTổng món bán trong năm: " + String.valueOf(o[0]) + "\t\t\n"
                                        + "\t\tTổng bill trong năm: " + String.valueOf(o[1]) + "\t\t\n"
                                        + "\t\tTổng tiền trong năm: " + String.valueOf(oT[0]) + "VNĐ" + "\t\t\n";
                                if (list3 != null) {
                                    for (Object[] oh : list3) {
                                        message = message + "\t\tTổng bill bị hủy: " + String.valueOf(oh[0]) + "\t\t\n"
                                                + " |                                                   \t\t\t\t\t|\n"
                                                + "|---------------------------------------------------------------------------|" + "\n\n";
                                    }
                                }
                                for (Object[] oo : list1) {
                                    System.out.println("" + o[0] + o[1]);
                                    message = message + "\t|-----------------------------------------------------|\n"
                                            + "\t|\tMã hóa đơn bị hủy là: " + String.valueOf(oo[0] + "\t\n"
                                                    + "\t|\tMã nhân viên order: " + String.valueOf(oo[1])) + "\t\n"
                                            + "\t|\tNgày :  " + String.valueOf(oo[2] + "\t\n"
                                                    + "\t|\tGiờ: " + String.valueOf(oo[3]) + "\t\n"
                                                    + "\t|\tLý do: " + String.valueOf(oo[4]) + "\t\n"
                                                    + "\t|-----------------------------------------------------|\n"
                                            );

                                }
                            }
                        }

                    }
                }
            }
            sendmail(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> getListBysendMailNgay(Date ngay) {
        String sql = "{CALL sendmailNgay(?)}";
        String[] cols = {"IDHD", "UsernameEMP", "DateOrder", "TimeOder", "Reason"};
        return this.getListOfArray(sql, cols, ngay);
    }

    public List<Object[]> getBillHuyNgay(Date ngay) {
        String sql = "{CALL getBillHuyNgay(?)}";
        String[] cols = {"tongBillHuy"};
        return this.getListOfArray(sql, cols, ngay);
    }

    @Override
    public void guiBCNgay(Date ngay) {
        String message = "";
        try {
            List<Object[]> list1 = getListBysendMailNgay(ngay);
            List<Object[]> list2 = getListTongMonvaHDNgay(ngay);
            List<Object[]> list3 = getBillHuyNgay(ngay);
            List<Object[]> list4 = getListByTKNgay(ngay);

            for (Object[] o : list2) {
                message = message + "\t\t\t\tBáo cáo trong ngày\n"
                        + "|---------------------------------------------------------------------------|\n"
                        + " |                                                  \t\t\t\t\t|\n"
                        + "\t\tTổng món bán trong ngay: " + String.valueOf(o[0]) + "\t\t\n"
                        + "\t\tTổng hóa đơn trong ngay: " + String.valueOf(o[1]) + "\t\t\n";

                for (Object[] oh : list3) {
                    message = message + "\t\tTổng hóa đơn bị hủy trong ngày: " + String.valueOf(oh[0]) + "\t\t\n"
                            + " |                                                   \t\t\t\t\t|\n";
                    if (list4.size() >= 1) {

                        for (Object[] oT : list4) {

                            message = message + "\t\tDoanh thu trong ngay: " + String.valueOf(oT[0]) + "VNĐ" + "\t\t\n"
                                    + "|---------------------------------------------------------------------------|" + "\n\n";
                            break;
                        }
                    }
                }
                for (Object[] oo : list1) {
                    System.out.println("" + o[0] + o[1]);
                    message = message + "\t|-----------------------------------------------------|\n"
                            + "\t|\tMã hóa đơn bị hủy là: " + String.valueOf(oo[0] + "\t\n"
                                    + "\t|\tMã nhân viên order: " + String.valueOf(oo[1])) + "\t\n"
                            + "\t|\tNgày: " + String.valueOf(oo[2] + "\t\n"
                                    + "\t|\tGiờ: " + String.valueOf(oo[3]) + "\t\n"
                                    + "\t|\tLý do: " + String.valueOf(oo[4]) + "\t\n"
                                    + "\t|-----------------------------------------------------|\n"
                            );

                }
            }

            sendmail(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
