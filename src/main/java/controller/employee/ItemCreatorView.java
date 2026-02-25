package controller.employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.GachaService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemCreatorView extends JFrame {

    private JTextField nameField;
    private JComboBox<String> rarityComboBox;
    private JComboBox<String> seriesComboBox; // 新增：系列下拉選單
    private JTextField probField;
    private JButton submitButton;
    private JLabel resultLabel;

    private GachaService gachaService = new GachaService();

    public ItemCreatorView() {
        setTitle("轉蛋機後台 - 新增道具系統");
        setSize(480, 439); // 稍微調高視窗高度以容納新欄位
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 改為 DISPOSE 避免關閉整個程式
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // 1. 道具名稱
        JLabel nameLabel = new JLabel("道具名稱:", SwingConstants.RIGHT);
        nameLabel.setBounds(52, 76, 107, 34);
        nameField = new JTextField();
        nameField.setBounds(196, 77, 188, 34);
        getContentPane().add(nameLabel);
        getContentPane().add(nameField);

        // 2. 稀有度
        JLabel rarityLabel = new JLabel("稀有度:", SwingConstants.RIGHT);
        rarityLabel.setBounds(52, 120, 107, 34);
        String[] rarities = {"SSR", "SR", "R", "N"};
        rarityComboBox = new JComboBox<>(rarities);
        rarityComboBox.setBounds(196, 120, 188, 34);
        getContentPane().add(rarityLabel);
        getContentPane().add(rarityComboBox);

        // 3. 所屬系列 (新增欄位)
        JLabel seriesLabel = new JLabel("所屬系列:", SwingConstants.RIGHT);
        seriesLabel.setBounds(52, 164, 107, 34);
        String[] banners = {"常駐卡池", "武士崛起", "騎士王傳說", "亞洲父母", "超絕武器神祭"};
        seriesComboBox = new JComboBox<>(banners);
        seriesComboBox.setBounds(196, 164, 188, 34);
        getContentPane().add(seriesLabel);
        getContentPane().add(seriesComboBox);

        // 4. 中獎機率
        JLabel probLabel = new JLabel("中獎機率:", SwingConstants.RIGHT);
        probLabel.setBounds(52, 208, 107, 34);
        probField = new JTextField("1.0");
        probField.setBounds(196, 208, 188, 34);
        getContentPane().add(probLabel);
        getContentPane().add(probField);

        // 5. 提交按鈕
        submitButton = new JButton("自動產生編號並新增");
        submitButton.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        submitButton.setBounds(122, 260, 200, 40);
        getContentPane().add(submitButton);

        // 6. 結果顯示
        resultLabel = new JLabel("準備就緒", SwingConstants.CENTER);
        resultLabel.setBounds(50, 310, 350, 34);
        resultLabel.setForeground(Color.BLUE);
        getContentPane().add(resultLabel);

        // 標題面板
        JPanel panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));
        panel.setBounds(10, 10, 444, 56);
        getContentPane().add(panel);
        panel.setLayout(null);
        JLabel lblTitle = new JLabel("物品管理系統");
        lblTitle.setFont(new Font("微軟正黑體", Font.PLAIN, 28));
        lblTitle.setBounds(135, 10, 180, 36);
        panel.add(lblTitle);
        
        JButton btnBack = new JButton("返回");
        btnBack.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		GMUI gMUI=new GMUI();
        		gMUI.setVisible(true);
				dispose();
        	}
        });
        btnBack.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        btnBack.setBounds(122, 354, 200, 40);
        getContentPane().add(btnBack);

        // 按鈕事件
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String rarity = (String) rarityComboBox.getSelectedItem();
                String series = (String) seriesComboBox.getSelectedItem(); // 取得選擇的系列
                
                try {
                    double prob = Double.parseDouble(probField.getText());
                    if (name.trim().isEmpty()) {
                        resultLabel.setText("錯誤：道具名稱不能為空！");
                        resultLabel.setForeground(Color.RED);
                        return;
                    }

                    // 呼叫更新後的 Service 方法
                    String resultMessage = gachaService.createNewItem(name, rarity, prob, series);
                    
                    resultLabel.setText(resultMessage);
                    resultLabel.setForeground(new Color(0, 128, 0));
                    nameField.setText("");
                    
                } catch (NumberFormatException ex) {
                    resultLabel.setText("錯誤：機率格式不正確！");
                    resultLabel.setForeground(Color.RED);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ItemCreatorView().setVisible(true));
    }
}