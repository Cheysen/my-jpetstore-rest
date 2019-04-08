package pre.chl.mypetstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pre.chl.mypetstore.domain.*;
import pre.chl.mypetstore.service.CatalogService;
import java.util.List;

@RestController
@CrossOrigin
public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    @GetMapping("/catalog/category/{categoryId}")
    public List<Product> products (@PathVariable("categoryId") String categoryId){
        System.out.println(categoryId);
        return catalogService.getProductListByCategory(categoryId);
    }
}
