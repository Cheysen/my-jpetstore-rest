package pre.chl.mypetstore.service;

import pre.chl.mypetstore.domain.Category;
import pre.chl.mypetstore.domain.Item;
import pre.chl.mypetstore.domain.Product;

import java.util.List;

public interface CatalogService {
     List<Category> getCategoryList();


     Category getCategory(String categoryId);


     Product getProduct(String productId);


     List<Product> getProductListByCategory(String categoryId);


    // TODO enable using more than one keyword
     List<Product> searchProductList(String keyword);


    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);


    boolean isItemInStock(String itemId);

}
