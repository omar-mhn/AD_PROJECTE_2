package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    // Càrrega massiva de dades d’un fitxer en format .csv
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

    // Modificar el preu d’un producte
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

    // Borrat físic d'un producte
    public void deleteProduct(Long id) {
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Producte no trobat");
        }
    }

    // Borrat lógic d'un producte
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

    // Cerca per nom q contingui el valor de prefix i que el camp status sigui true
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
    
    // Cerca per condició i que el camp status sigui true
    public List<ProductResponseDTO> findByCondition(String condition){
        List<Product> products = productRepository.findByConditionAndStatusTrue(Condition.valueOf(condition.toLowerCase()));
        List<ProductResponseDTO> responseDtos = new ArrayList<>();
        for(Product p : products){
            ProductResponseDTO responseDto = new ProductResponseDTO();
            responseDto.setId(p.getId());
            responseDto.setName(p.getName());
            responseDto.setDescription(p.getDescription());
            responseDto.setStock(p.getStock());
            responseDto.setPrice(p.getPrice());
            responseDto.setRating(p.getRating());
            responseDto.setCondition(p.getCondition());
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }

//====================================================================================================================================================================================================


    /*public List<ProductResponseDTO> getProductsBetweenPricesAndRatingOrderedByCamp(Double priceMin, Double priceMax, String camp, String order, int limit) {

        List<Product> productesActius;

        // 1. Avaluem direcció de l'ordre i valor del camp si és "rating" o "price"
        boolean isDesc = "desc".equalsIgnoreCase(order);

        if ("rating".equalsIgnoreCase(camp)) {
            productesActius = isDesc ? productRepository.findByStatusTrueOrderByRatingDesc() : productRepository.findByStatusTrueOrderByRatingAsc();
        } else {            
            productesActius = isDesc ? productRepository.findByStatusTrueOrderByPriceDesc() : productRepository.findByStatusTrueOrderByPriceAsc();
        }


        /* 
        // Definimos la dirección del orden 
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        // Creamos el objeto Pageable para gestionar el límite y el campo de orden dinámico
        PageRequest pageable = PageRequest.of(0, limit, Sort.by(direction, camp));

        List<Product> products = productRepository.findWithFiltresJPQL(priceMax, priceMin, prefix, pageable);

        List<ProductResponseDTO> dtos = new ArrayList();
        for(Product p : products){
            ProductResponseDTO dto = new ProductResponseDTO();
            BeanUtils.copyProperties(p, dto);
            dtos.add(dto);
        }
        return dtos;
    }*/

    public List<ProductResponseDTO> getProductsBetweenPricesOrdered(Double priceMin, Double priceMax, String prefix, String camp, String order) {

        if( camp.equals("1") || camp.equals("true")) {

            List<Product> productesActius;
            
            boolean isDesc = "desc".equalsIgnoreCase(order);

            if ("rating".equalsIgnoreCase(camp)) {
                productesActius = isDesc ? productRepository.findByStatusTrueOrderByRatingDesc() : productRepository.findByStatusTrueOrderByRatingAsc();
            } else {            
                productesActius = isDesc ? productRepository.findByStatusTrueOrderByPriceDesc() : productRepository.findByStatusTrueOrderByPriceAsc();
            }

        }
        
        // 1. Configurar ordenació
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, camp);
        
        // 2. Consulta a BBDD
        List<Product> products = productRepository.findByStatusTrueAndPriceBetween(priceMin, priceMax, sort);
        
        // 3. Transformació DTO aïllada
        List<ProductResponseDTO> result = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());
            
            result.add(dto);
        }
        
        return result;
    }


    public List<ProductResponseDTO> getProductsBetweenRatingsOrdered(Double ratingMin, Double ratingMax, String camp, String order) {
        // 1. Configurar ordenació
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, camp);
        
        // 2. Consulta a BBDD
        List<Product> products = productRepository.findByStatusTrueAndRatingBetween(ratingMin, ratingMax, sort);
        
        // 3. Transformació DTO aïllada
        List<ProductResponseDTO> result = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());
            
            result.add(dto);
        }
        
        return result;
    }

    public List<ProductResponseDTO> getProductsOrderedByCamp(String camp, String order) {
        
        List<Product> productesActius;

        // 1. Avaluem direcció de l'ordre i valor del camp si és "rating" o "price"
        boolean isDesc = "desc".equalsIgnoreCase(order);

        if ("rating".equalsIgnoreCase(camp)) {
            productesActius = isDesc ? productRepository.findByStatusTrueOrderByRatingDesc() : productRepository.findByStatusTrueOrderByRatingAsc();
        } else {            
            productesActius = isDesc ? productRepository.findByStatusTrueOrderByPriceDesc() : productRepository.findByStatusTrueOrderByPriceAsc();
        }
        
        // 2. Transformació a DTO 
        List<ProductResponseDTO> llistaFinal = new ArrayList<>();
        
        for (Product product : productesActius) {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());
            
            llistaFinal.add(dto);
        }        
        return llistaFinal;
    }

//====================================================================================================================================================================================================


    // 5 productes que tenen millor relació qualitat - preu
    public List<ProductResponseDTO> getTop5QualityPrice() {
        // Definimos el límite de 5 resultados 
        Pageable topFive = PageRequest.of(0, 5);
        
        List<Product> products = productRepository.findTopQualityPrice(topFive);
        
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductResponseDTO dto = new ProductResponseDTO();
            BeanUtils.copyProperties(p, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    // 10 primers productes nous amb millor valoració
    public List<ProductResponseDTO> getNewProducts() {
        // Definimos el límite de 10 resultados
        Pageable topTen = PageRequest.of(0, 10,)); 
        
        List<Product> products = productRepository.findBestNewProducts(topTen);

        List<ProductResponseDTO> result = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());
            
            result.add(dto);
        }        
        return result;
    }

    // Cerca per lots de 5 productes-files
    public List<ProductResponseDTO> getProductsPaginated(int pageNumber) {
        
        // 1. Creem l'objecte de paginació: pàgina sol·licitada, mida del bloc fixa a 5
        Pageable pageable = PageRequest.of(pageNumber, 5);        
        
        Page<Product> productPage = productRepository.findByStatusTrue(pageable); 
        List<Product> products = productPage.getContent();
        
        List<ProductResponseDTO> result = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());
            
            result.add(dto);
        }        
        return result;
    }

}