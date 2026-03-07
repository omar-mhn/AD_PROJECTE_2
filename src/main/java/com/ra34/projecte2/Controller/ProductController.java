package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ra34.projecte2.DTO.ErrorDTO;
import com.ra34.projecte2.DTO.ProductRequestDTO;
import com.ra34.projecte2.DTO.ProductResponseDTO;
import com.ra34.projecte2.Service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/csv")
    // <?> permite que el cuerpo de la respuesta sea de cualquier tipo
    public ResponseEntity<?> postcsv(@RequestParam("fileCsv") MultipartFile fileCsv){
        try{
            int total = productService.processCsv(fileCsv);
            return ResponseEntity.ok("Registres afegits: " + total);
        }catch(Exception e){
            ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(),e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ){
        try{
            ProductResponseDTO result = productService.findById(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(),e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
    }
    @GetMapping("/products")
    public ResponseEntity<?> getAll(){
        // no hace falta el try catch porque el Service no envia null sino un lista vacio si no existe
       List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    } 

    @PostMapping("/products")
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequestDTO product){
        try {
        ProductResponseDTO response = productService.saveProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    } catch (Exception e) {
        ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Error al crear el producte: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PatchMapping("/products/{id}/estoc")
    public ResponseEntity<?>updateEstoc(@PathVariable Long id, @RequestBody int estoc){
        try{
            ProductResponseDTO responseDTO = productService.updateEstoc(id, estoc);
            return ResponseEntity.ok(responseDTO);
        }catch(Exception e){
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
    }
    @GetMapping("/products/search/nom")
    public ResponseEntity<?>searchByName(@RequestParam String prefix){
        List<ProductResponseDTO> products = productService.searchByName(prefix);
        return ResponseEntity.ok(products);
    }
    
    
    
}

