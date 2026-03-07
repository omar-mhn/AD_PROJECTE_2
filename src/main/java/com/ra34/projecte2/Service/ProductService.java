package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public ProductResponseDTO findById(Long id){
        // si otional existe lo extrae si no lanza una excepción
        Product p = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producte no trobat amb ID: " + id));
        ProductResponseDTO dto = new ProductResponseDTO();
        // hacer mapping sin escribir manuelmente todo;
        BeanUtils.copyProperties(p, dto);

    return dto;
    }
}
