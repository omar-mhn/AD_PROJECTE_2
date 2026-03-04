package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra34.projecte2.Model.Condition;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public int processCsv(MultipartFile file) throws Exception{
        int count = 0;
        int liniaNum = 0;
       try( BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String linia;
            br.readLine();
            liniaNum++;
            while ((linia = br.readLine()) != null) {
                liniaNum ++;
                String []data = linia.split(",");
                try{
                    Product p = new Product();
                    p.setName( data[0].trim());
                    p.setDescription(data[1].trim());
                    p.setStock(Integer.parseInt(data[2].trim()));
                    p.setPrice(Double.parseDouble(data[3].trim()));
                    p.setRating(Double.parseDouble(data[4].trim()));
                    p.setCondition(Condition.valueOf(data[5].trim()));
                    productRepository.save(p);
                    count ++;
                }catch(Exception e){
                    throw new Exception("Error a la linia "+ liniaNum);
                }
            }
       }
       return count;
    }
}
