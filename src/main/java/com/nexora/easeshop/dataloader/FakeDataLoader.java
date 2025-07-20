package com.nexora.easeshop.dataloader;

import com.github.javafaker.Faker;
import com.nexora.easeshop.models.Product;
import com.nexora.easeshop.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;

@Configuration
public class FakeDataLoader {
    //@Bean
    CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("fr"));
            Random random = new Random();

            for (int i = 0; i < 50; i++) {
                Product product = new Product();
                product.setName(faker.commerce().productName());
                product.setDescription(faker.lorem().sentence());
                product.setPrice(BigDecimal.valueOf(random.nextInt(10000) / 100.0));
                product.setImageProductUrl("https://picsum.photos/seed/" + faker.internet().uuid() + "/300/300");

                productRepository.save(product);
            }

            System.out.println("📦 50 produits générés avec succès !");
        };
    }
}
