package com.ra34.projecte2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ra34.projecte2.DTO.ErrorDTO;
import com.ra34.projecte2.DTO.ProductResponseDTO;
import com.ra34.projecte2.Service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ){
        try{
            ProductResponseDTO result = productService.findById(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(),e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
    }
    
    
}
