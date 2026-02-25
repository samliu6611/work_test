package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DrawRecord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String player_name;
	private String machine_name;
	private String item_name;
	private Timestamp draw_time; // 這裡使用 SQL 的 Timestamp 來對應資料庫時間

	public DrawRecord() {
		super();
	}

	public DrawRecord(String player_name, String machine_name, String item_name, Timestamp draw_time) {
		super();
		this.player_name = player_name;
		this.machine_name = machine_name;
		this.item_name = item_name;
		this.draw_time = draw_time;
	}

	// --- 以下為 Getter 與 Setter ---

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public String getMachine_name() {
		return machine_name;
	}

	public void setMachine_name(String machine_name) {
		this.machine_name = machine_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Timestamp getDraw_time() {
		return draw_time;
	}

	public void setDraw_time(Timestamp draw_time) {
		this.draw_time = draw_time;
	}
}