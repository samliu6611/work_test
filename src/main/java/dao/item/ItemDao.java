package dao.item;

import java.util.List;
import model.Item;

public interface ItemDao {
	// 新增道具
	void add(Item item);
	
	// 取得目前道具總數 (用來產生流水號)
	int getItemCount();
	
	// 根據機台名稱撈取道具 (抽獎時會用到)
	List<Item> select_by_machine(String machine_name);
}