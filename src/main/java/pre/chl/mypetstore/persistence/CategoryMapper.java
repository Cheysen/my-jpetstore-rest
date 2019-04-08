package pre.chl.mypetstore.persistence;

import pre.chl.mypetstore.domain.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> getCategoryList();
    Category getCategory(String categoryId);
}
