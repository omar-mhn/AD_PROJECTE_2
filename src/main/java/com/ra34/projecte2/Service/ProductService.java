package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            String linia =br.readLine();
            while ((linia = br.readLine()) != null) {
                liniaNum ++;
                if(linia.trim().isEmpty()) continue;
                String []data = linia.split(",");
                if (data.length < 6) {
                throw new Exception("Falten columnes a la línia " + liniaNum);
                }
                try{
                    Product p = new Product();
                    p.setName( data[0].trim());
                    p.setDescription(data[1].trim());
                    p.setStock(Integer.parseInt(data[2].trim()));
                    p.setPrice(Double.parseDouble(data[3].trim()));
                    p.setRating(Double.parseDouble(data[4].trim()));
                    p.setCondition(Condition.valueOf(data[5].trim().toLowerCase()));
                    p.setStatus(true);
                    productRepository.save(p);
                    count ++;
                }catch(Exception e){
                    throw new Exception("Error a la linia "+ liniaNum + ": " + e.getMessage() );
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
        Product productGuardado = productRepository.save(p);
        
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        BeanUtils.copyProperties(productGuardado, responseDTO);
        return responseDTO;
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
}
