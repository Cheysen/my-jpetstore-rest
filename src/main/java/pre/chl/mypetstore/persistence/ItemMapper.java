package pre.chl.mypetstore.persistence;

import pre.chl.mypetstore.domain.Item;

import java.util.List;
import java.util.Map;

public interface ItemMapper {

    void updateInventoryQuantity(Map<String, Object> param);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);
}
