package model;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String item_no;      // 對應 getItem_no()
    private String item_name;    // 對應 getItem_name()
    private String rarity;
    private double probability;
    private String machine_name; // 【關鍵修復】補上機台名稱屬性

    // 1. 空的建構子
    public Item() {}

    // 2. 包含所有屬性的建構子
    public Item(String item_no, String item_name, String rarity, double probability, String machine_name) {
        this.item_no = item_no;
        this.item_name = item_name;
        this.rarity = rarity;
        this.probability = probability;
        this.machine_name = machine_name;
    }

    // --- 以下是所有的 Getter 和 Setter ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getItem_no() { return item_no; }
    public void setItem_no(String item_no) { this.item_no = item_no; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public double getProbability() { return probability; }
    public void setProbability(double probability) { this.probability = probability; }

    // 💡 補上這個之後，ItemDaoImpl 就不會再報錯了！
    public String getMachine_name() { return machine_name; }
    public void setMachine_name(String machine_name) { this.machine_name = machine_name; }
}