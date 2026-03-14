package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ra34.projecte2.DTO.ErrorDTO;
import com.ra34.projecte2.DTO.ProductRequestDTO;
import com.ra34.projecte2.DTO.ProductResponseDTO;
import com.ra34.projecte2.Service.ProductService;

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
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
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

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDTO){
        try {
            ProductResponseDTO response = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Error actualitzant el producte: " + e.getMessage());            
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

    @PatchMapping("/products/{id}/preu")
    public ResponseEntity<?>updatePreu(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO){
        try{
            ProductResponseDTO responseDTO = productService.updatePrice(id, productRequestDTO.getPrice());
            return ResponseEntity.ok(responseDTO);
        }catch(Exception e){
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
    }

    @GetMapping("/products/search/nom")
    public ResponseEntity<List<ProductResponseDTO>>searchByName(@RequestParam String prefix){
        List<ProductResponseDTO> products = productService.searchByName(prefix);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/search/order")
    public ResponseEntity<List<ProductResponseDTO>>getByOrder(@RequestParam String camp, @RequestParam String order){
        List<ProductResponseDTO> results = productService.findAllOrderByPrice(order);
        return ResponseEntity.ok(results);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Producte eliminat correctament");
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/products/logic/{id}")
    public ResponseEntity<?> deleteLogicProduct(@PathVariable Long id){
        try {
            productService.deleteLogicProduct(id);
            return ResponseEntity.ok("Producte eliminat correctament");
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }    
    @GetMapping("products/search/orders")
    public ResponseEntity<List<ProductResponseDTO>> getAdvancedSearch(@RequestParam Double priceMin, @RequestParam Double priceMax,@RequestParam String prefix, @RequestParam String camp,@RequestParam String order,@RequestParam int limit){
        List<ProductResponseDTO> results = productService.findAdvancedSearch(priceMin, priceMax, prefix, camp, order, limit);
        return ResponseEntity.ok(results);

    }
    
}

