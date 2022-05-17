package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.entity.Product;
import com.example.demo.repository.productRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class ProductTest {
	 
	@Autowired
	private productRepository productRepository;
	
	@Test
	//@Rollback(false) //to keep transaction in database
	@Order(1)
	public void addProduct()
	{
		Product product = new Product();
		product.setName("Television");
		product.setPrice("10000");
		Product savedProduct = productRepository.save(product);
		assertNotNull(savedProduct);
	}
	
	@Test
	@Order(3)
	public void findProductByNameExist()
	{
		String product_name="Televisionbb";
		Product returnedProduct = productRepository.findByName(product_name);
		//assertThat(returnedProduct.getName()).isEqualTo(returnedProduct);
		assertNull(returnedProduct);

	}
	
	@Test
	@Order(4)
	public void findProductByNameNotExist()
	
	{
		String name ="Fridge";
		Product returnedProduct = productRepository.findByName(name);
		assertNull(returnedProduct);
	}
	
	@Test
	@Order(5)
	public void updateProduct()
	{
		String name="Computer";
		Product product = new Product();
		product.setName(name);
		product.setPrice("2000000");
		product.setId(3);
		
		productRepository.save(product);
		
		Product updatedProduct =productRepository.findByName(name);
		assertNotNull(updatedProduct);
		
	}
	
	@Test
	@Order(2)
	public void getProductList()
	{
		List<Product> productList=productRepository.findAll();
		assertThat(productList).size().isGreaterThan(0);
	}
	
	@Test
	//@Rollback(false)
	@Order(6)
	public void deleteProduct()
	{
		int id = 6;
	  	productRepository.deleteById(id);
	  	
	  	boolean notExist= productRepository.findById(id).isPresent();
	  	
	  	assertFalse(notExist);
	}
}
