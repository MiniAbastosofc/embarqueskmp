package com.alan.curso.springboot.di.factura.springboot_difactura;

import com.alan.curso.springboot.di.factura.springboot_difactura.models.Item;
import com.alan.curso.springboot.di.factura.springboot_difactura.models.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource(value = "classpath:data.properties", encoding = "UTF-8")
public class AppConfig {
    @Bean
//    @Primary
    List<Item> itemsInvoice() {
        Product p1 = new Product("Camara Sony", 800);
        Product p2 = new Product("Bicicleta Bianchi 26", 1200);
        return Arrays.asList(new Item(p1, 2), new Item(p2, 4));
    }

    @Bean("default")
    List<Item> itemsInvoiceOficce() {
        Product p1 = new Product("Monitor Asus 24", 700);
        Product p2 = new Product("Notebook Razer", 2400);
        Product p3 = new Product("Impresora Hp", 826);
        Product p4 = new Product("Escritorio Oficina", 789);
        return Arrays.asList(new Item(p1, 2), new Item(p2, 4), new Item(p3, 3), new Item(p4, 9));
    }
}
