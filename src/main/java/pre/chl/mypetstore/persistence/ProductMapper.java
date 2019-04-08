package pre.chl.mypetstore.persistence;

import pre.chl.mypetstore.domain.Product;

import java.util.List;

public interface ProductMapper {
    //根据大类categoryId查询该类地Product
    List<Product> getProductListByCategory(String categoryId);
    //根据小类查询product对象
    Product getProduct(String productId);
    //根据关键字查询符合条件的product
    List<Product> searchProductList(String keywords);
}
