package controller.employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import util.Tool;

public class BannerManagerView extends JFrame {
    private JPanel contentPane;
    private JTextField startField, endField;
    private JComboBox<String> bannerBox;
    private JLabel statusLabel;

    public BannerManagerView() {
        setTitle("GM 卡池時間管理系統");
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("卡池排期設定");
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        lblTitle.setBounds(130, 20, 200, 40);
        contentPane.add(lblTitle);

        JLabel lblBanner = new JLabel("選擇卡池:");
        lblBanner.setBounds(50, 80, 80, 25);
        contentPane.add(lblBanner);

        String[] banners = {"武士崛起", "騎士王傳說", "亞洲父母", "超絕武器神祭"};
        bannerBox = new JComboBox<>(banners);
        bannerBox.setBounds(140, 80, 200, 25);
        contentPane.add(bannerBox);

        JLabel lblStart = new JLabel("開始時間:");
        lblStart.setBounds(50, 130, 80, 25);
        contentPane.add(lblStart);

        startField = new JTextField("2026-02-25 00:00:00");
        startField.setBounds(140, 130, 200, 25);
        contentPane.add(startField);

        JLabel lblEnd = new JLabel("結束時間:");
        lblEnd.setBounds(50, 180, 80, 25);
        contentPane.add(lblEnd);

        endField = new JTextField("2026-03-25 23:59:59");
        endField.setBounds(140, 180, 200, 25);
        contentPane.add(endField);

        statusLabel = new JLabel("請輸入日期格式: YYYY-MM-DD HH:mm:ss", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GRAY);
        statusLabel.setBounds(20, 215, 400, 25);
        contentPane.add(statusLabel);

        JButton btnUpdate = new JButton("更新排期");
        btnUpdate.setBounds(100, 250, 110, 40);
        btnUpdate.addActionListener(e -> updateBannerTime());
        contentPane.add(btnUpdate);

        JButton btnBack = new JButton("返回");
        btnBack.setBounds(230, 250, 100, 40);
        btnBack.addActionListener(e -> {
            new GMUI().setVisible(true);
            dispose();
        });
        contentPane.add(btnBack);
    }

    private void updateBannerTime() {
        String name = (String) bannerBox.getSelectedItem();
        String start = startField.getText();
        String end = endField.getText();

        String sql = "UPDATE banners SET start_time = ?, end_time = ? WHERE banner_name = ?";
        try (Connection conn = Tool.getDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, start);
            ps.setString(2, end);
            ps.setString(3, name);
            int row = ps.executeUpdate();
            if (row > 0) {
                statusLabel.setText(" 更新成功！卡池 " + name + " 已重新排期");
                statusLabel.setForeground(new Color(0, 128, 0));
            }
        } catch (SQLException ex) {
            statusLabel.setText(" 格式錯誤！請檢查日期格式");
            statusLabel.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }
}