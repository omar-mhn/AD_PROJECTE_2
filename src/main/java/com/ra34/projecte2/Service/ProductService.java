package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra34.projecte2.DTO.ProductRequestDTO;
import com.ra34.projecte2.DTO.ProductResponseDTO;
import com.ra34.projecte2.Model.Condition;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional()
    public int processCsv(MultipartFile file) throws Exception{
        int count = 0;
        int liniaNum = 1;
        try( BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String linia = br.readLine();
            while ((linia = br.readLine()) != null) {
                liniaNum ++;
                if(linia.trim().isEmpty()) continue;
                String []data = linia.split(",");
                if (data.length < 6) {
                throw new Exception("Falten columnes a la línia " + liniaNum);
                }
                try{
                    ProductRequestDTO dto = new ProductRequestDTO();
                    dto.setName( data[0].trim());
                    dto.setDescription(data[1].trim());
                    dto.setStock(Integer.parseInt(data[2].trim()));
                    dto.setPrice(Double.parseDouble(data[3].trim()));
                    dto.setRating(Double.parseDouble(data[4].trim()));
                    dto.setCondition(Condition.valueOf(data[5].trim().toLowerCase()));
                    dto.setStatus(true);
                    Product p = new Product();
                    BeanUtils.copyProperties(dto, p);
                    productRepository.save(p);
                    count ++;
                }catch(Exception e){
                    throw new Exception("Error a la linia nº "+ liniaNum + ": " + e.getMessage() );
                }
            }
        }
        return count;
    }
    
    // Consultar un producte per id
    public ProductResponseDTO findById(Long id){
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            ProductResponseDTO dto = new ProductResponseDTO();
            // hacer mapping sin escribir manuelmente todo;
            BeanUtils.copyProperties(p.get(), dto);
            return dto;
        }
        throw new RuntimeException("Producte no trobat");
    }
    

    //Consultar tots els productes
    public List<ProductResponseDTO> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for(Product p : products){
            ProductResponseDTO dto = new ProductResponseDTO();
            BeanUtils.copyProperties(p, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    // Afegir un producte
    public ProductResponseDTO saveProduct(ProductRequestDTO productDTO){
        Product p = new Product();
        BeanUtils.copyProperties(productDTO, p);
        p.setStatus(true);
        p.setDataCreated(new Timestamp(System.currentTimeMillis()));
        Product productGuardado = productRepository.save(p);
        
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        BeanUtils.copyProperties(productGuardado, responseDTO);
        return responseDTO;
    }

    // Actualitzar un producte sencer
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDTO){
        Optional<Product> p = productRepository.findById(id);

        if(p.isPresent()){
            Product product = p.get(); // extraiem l'objecte real de forma segura
            // Actualitzem els atributs del producte manualment per tenir més control
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());
            product.setRating(productDTO.getRating());
            product.setCondition(productDTO.getCondition());

            product.setStatus(true);
            product.setDataUpdated(new Timestamp(System.currentTimeMillis()));

            Product productUpdated = productRepository.save(product);
            // Entitat -> DTO
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setId(productUpdated.getId());
            responseDTO.setName(productUpdated.getName());
            responseDTO.setDescription(productUpdated.getDescription());
            responseDTO.setStock(productUpdated.getStock());
            responseDTO.setPrice(productUpdated.getPrice());
            responseDTO.setRating(productUpdated.getRating());
            responseDTO.setCondition(productUpdated.getCondition());
            
            return responseDTO;
        } else {
            throw new RuntimeException("Producte no trobat");
        }
    }

    //Modificar l’estoc de productes
    public ProductResponseDTO updateEstoc(Long id, int stock ){
        Optional<Product> pOpt = productRepository.findById(id);
        if(pOpt.isPresent()){
            Product p = pOpt.get();
            p.setStock(stock);
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            BeanUtils.copyProperties(p, productResponseDTO);
            return productResponseDTO; 
        }
        throw new RuntimeException("Producte no trobat amb ID: " + id);
        
    }

    public ProductResponseDTO updatePrice(Long id, double price){
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            Product product = p.get();
            product.setPrice(price);
            product.setDataUpdated(new Timestamp(System.currentTimeMillis()));

            Product productUpdated = productRepository.save(product);
            // Entitat -> DTO
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setId(productUpdated.getId());
            responseDTO.setName(productUpdated.getName());
            responseDTO.setDescription(productUpdated.getDescription());
            responseDTO.setStock(productUpdated.getStock());
            responseDTO.setPrice(productUpdated.getPrice());
            responseDTO.setRating(productUpdated.getRating());
            responseDTO.setCondition(productUpdated.getCondition());
            return responseDTO;
        } else {
            throw new RuntimeException("Producte no trobat");
        }

    }

    public List<ProductResponseDTO> searchByName(String prefix){
        List<Product> products = productRepository.findByNameContainingAndStatusTrue(prefix);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for(Product p : products){
            ProductResponseDTO dto = new ProductResponseDTO();
            BeanUtils.copyProperties(p, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductResponseDTO> findAllOrderByPrice(String order){
        Sort sort = order.equalsIgnoreCase("desc") 
                ? Sort.by("price").descending() 
                : Sort.by("price").ascending();
        
        List<Product> products = productRepository.findByStatusTrue(sort);
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductResponseDTO dto = new ProductResponseDTO();
            BeanUtils.copyProperties(p, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    public void deleteProduct(Long id) {
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Producte no trobat");
        }
    }

    public void deleteLogicProduct(Long id){
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            Product product = p.get();
            product.setStatus(false);
            product.setDataUpdated(new Timestamp(System.currentTimeMillis()));
            productRepository.save(product);
        } else {
            throw new RuntimeException("Producte no trobat");
        }
    }
}
