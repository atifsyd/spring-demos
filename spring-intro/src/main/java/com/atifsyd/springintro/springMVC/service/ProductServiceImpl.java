package com.atifsyd.springintro.springMVC.service;

import com.atifsyd.springintro.springMVC.model.ModelObject;
import com.atifsyd.springintro.springMVC.model.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService{

    @Override
    public List<ModelObject> listAll() {
        return super.listAll();
    }

    @Override
    public Product getById(Integer id) {
        return (Product) super.getById(id);
    }

    @Override
    public Product saveOrUpdate(Product modelObject) {
        return (Product) super.saveOrUpdate(modelObject);
    }

    @Override
    public void remove(Integer id) {
        super.remove(id);
    }

    protected void loadModelObjects() {
        List<Integer> ids = List.of(1, 2, 3, 4, 5);
        List<String> descriptions = List.of("Product 1", "Product 2", "Product 3", "Product 4", "Product 5");
        List<BigDecimal> prices = List.of(new BigDecimal(12.99), new BigDecimal(14.99),
                new BigDecimal(16.99), new BigDecimal(18.99), new BigDecimal(35.99));
        List<String> imageUrls = List.of("http://example.com/product1", "http://example.com/product2",
                "http://example.com/product3", "http://example.com/product4", "http://example.com/product5");
        modelMap = new HashMap<>();

        for(int i=0; i<ids.size(); i++) {
            Product product = new Product();
            product.setId(ids.get(i));
            product.setDescription(descriptions.get(i));
            product.setPrice(prices.get(i));
            product.setImageUrl(imageUrls.get(i));
            modelMap.put(ids.get(i), product);
        }
    }
}
