package pre.chl.mypetstore.persistence;

import pre.chl.mypetstore.domain.LineItem;

import java.util.List;

public interface LineItemMapper {

    List<LineItem> getLineItemsByOrderId(int orderId);

    void insertLineItem(LineItem lineItem);
}
