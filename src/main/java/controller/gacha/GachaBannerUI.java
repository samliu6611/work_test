package controller.gacha;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import model.Customer;
import service.GachaService;

public class GachaBannerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private GachaService gachaService = new GachaService(); //
    private Customer currentCustomer; 

    private int currentIndex = 0;
    private String[] banners = {"常駐卡池", "新手卡池", "武士崛起", "騎士王傳說", "亞洲父母", "超絕武器神祭"};
    private String[] descriptions = {
        "常駐兵器與神話武器大集結！",
        "【限定一次】保底必中常駐 SSR！",
        "【期間特選】和風武士道名刀！",
        "【期間特選】圓桌武士傳奇兵器！",
        "【極度危險】痛感滿分！童年陰影系列大放送！",
        "【狂歡神祭】三大特選混池！機率 UP！"
    };

    // UI 元件宣告 (類別變數)
    private JLabel lblBannerName;
    private JLabel lblDescription;
    private JLabel lblTime; 
    private JTextArea resultArea;
    private JButton btnDraw1;   
    private JButton btnDraw10;  

    /**
     * 更新畫面的方法 (同步資料庫時間與按鈕狀態)
     */
    private void updateBannerDisplay() {
        lblBannerName.setText(banners[currentIndex]);
        lblDescription.setText(descriptions[currentIndex]);
        
        // 從 Service 獲取資料庫內的真實時間
        String realTime = gachaService.getBannerTimeRange(banners[currentIndex]);
        lblTime.setText(realTime);
        
        // 檢查是否在開放時間內
        boolean isOpen = gachaService.isBannerOpen(banners[currentIndex]);
        
        if (isOpen) {
            btnDraw1.setEnabled(true);
            btnDraw10.setEnabled(true);
            btnDraw1.setText("召喚 1 次");
            btnDraw10.setText("召喚 10 次");
            btnDraw1.setBackground(new Color(255, 215, 0));
        } else {
            btnDraw1.setEnabled(false);
            btnDraw10.setEnabled(false);
            btnDraw1.setText("尚未開放");
            btnDraw10.setText("尚未開放");
            btnDraw1.setBackground(Color.GRAY);
        }
    }

    public GachaBannerUI(Customer loggedInCustomer) {
        this.currentCustomer = loggedInCustomer;
        String playerName = (loggedInCustomer != null) ? loggedInCustomer.getCustomer_name() : "訪客"; //

        setTitle("卡池召喚系統");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 150, 715, 530);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(25, 25, 112)); // FGO 風格
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- 左上角返還按鈕 ---
        JButton btnBack = new JButton("返回");
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        btnBack.setBounds(10, 5, 85, 30);
        btnBack.addActionListener(e -> {
            new GachaGameUI().setVisible(true); //
            dispose();
        });
        contentPane.add(btnBack);

        // --- 卡池展示區 ---
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(0, 0, 51));
        bannerPanel.setBounds(20, 45, 660, 130);
        bannerPanel.setLayout(null);
        bannerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        contentPane.add(bannerPanel);

        lblBannerName = new JLabel("", SwingConstants.CENTER);
        lblBannerName.setForeground(Color.WHITE);
        lblBannerName.setFont(new Font("微軟正黑體", Font.BOLD, 28));
        lblBannerName.setBounds(80, 10, 500, 40);
        bannerPanel.add(lblBannerName);

        lblDescription = new JLabel("", SwingConstants.CENTER);
        lblDescription.setForeground(Color.YELLOW);
        lblDescription.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        lblDescription.setBounds(80, 55, 500, 25);
        bannerPanel.add(lblDescription);
        
        lblTime = new JLabel("", SwingConstants.CENTER);
        lblTime.setForeground(new Color(200, 200, 200));
        lblTime.setFont(new Font("微軟正黑體", Font.ITALIC, 14));
        lblTime.setBounds(80, 85, 500, 25);
        bannerPanel.add(lblTime);

        JButton btnLeft = new JButton("<");
        btnLeft.setBounds(10, 40, 60, 50);
        bannerPanel.add(btnLeft);

        JButton btnRight = new JButton(">");
        btnRight.setBounds(590, 40, 60, 50);
        bannerPanel.add(btnRight);

        // --- 按鈕與結果顯示區 ---
        JButton btnInfo = new JButton("召喚詳情 (機率)");
        btnInfo.setBounds(20, 185, 200, 40);
        contentPane.add(btnInfo);

        btnDraw1 = new JButton("召喚 1 次");
        btnDraw1.setBounds(350, 185, 160, 50);
        btnDraw1.setBackground(new Color(255, 215, 0));
        contentPane.add(btnDraw1);

        btnDraw10 = new JButton("召喚 10 次");
        btnDraw10.setBounds(520, 185, 160, 50);
        btnDraw10.setBackground(new Color(255, 140, 0));
        btnDraw10.setForeground(Color.WHITE);
        contentPane.add(btnDraw10);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(20, 245, 660, 230);
        contentPane.add(scrollPane);

        // 初始化更新畫面
        updateBannerDisplay();

        // ============================================================
        // 事件監聽綁定
        // ============================================================
        
        // 左右切換
        btnLeft.addActionListener(e -> { currentIndex = (currentIndex - 1 + banners.length) % banners.length; updateBannerDisplay(); });
        btnRight.addActionListener(e -> { currentIndex = (currentIndex + 1) % banners.length; updateBannerDisplay(); });
        
        // 召喚詳情
        btnInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, gachaService.getBannerProbabilities(banners[currentIndex])));
        
        // 單抽一次 (內含新手池單抽攔截邏輯)
        btnDraw1.addActionListener(e -> {
            String res = gachaService.drawItemForPlayer(playerName, banners[currentIndex]);
            resultArea.append(res + "\n");
            resultArea.setCaretPosition(resultArea.getDocument().getLength());
        });

        // 10 連抽 (已移除新手池攔截，交由 Service 處理)
        btnDraw10.addActionListener(e -> {
            String currentBanner = banners[currentIndex];
            resultArea.append("─── ★ 執行 10 連召喚 [" + currentBanner + "] ★ ───\n");
            
            List<String> res = gachaService.drawTenItemsForPlayer(playerName, currentBanner);
            for(String s : res) {
                resultArea.append(s + "\n");
            }
            resultArea.setCaretPosition(resultArea.getDocument().getLength());
        });
    }
}