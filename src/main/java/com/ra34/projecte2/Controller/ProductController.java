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

    //Consultar tots els productes
    @GetMapping("/products")
    public ResponseEntity<?> getAll(){
        // no hace falta el try catch porque el Service no envia null sino un lista vacio si no existe
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    } 
    
    // Consultar un producte per id
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

    // Afegir un producte
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

    // Modificar tots els camps d’un producte
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

    // Modificar l’estoc de productes
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

    // Modificar el preu d’un producte
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

    // Borrat físic d'un producte
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

    // Borrat lógic d'un producte
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

    // Cerca per nom q contingui el valor de prefix i que el camp status sigui true
    @GetMapping("/products/search/nom")
    public ResponseEntity<List<ProductResponseDTO>>searchByName(@RequestParam String prefix){
        List<ProductResponseDTO> products = productService.searchByName(prefix);
        return ResponseEntity.ok(products);
    }
    
    // Cerca per condició i que el camp status sigui true
    @GetMapping("/products/search/condition")
    public ResponseEntity<List<ProductResponseDTO>>getByCondition(@RequestParam String condition){
        List<ProdcutsResponseDTO> results = productService.findByCondition(condition);
        return ResponseEntity.ok(results);
    }

    // Cerca per rang de preu, rating, o per camp i ordre, i que el camp status sigui true
    @GetMapping("/products/search/order")
    public ResponseEntity<?> getByOrderRating(
            @RequestParam(required = false) Double priceMin, 
            @RequestParam(required = false) Double priceMax, 
            @RequestParam(required = false) Double ratingMin, 
            @RequestParam(required = false) Double ratingMax, 
            @RequestParam(required = false) String prefix,
            @RequestParam String camp, 
            @RequestParam String order, 
            @RequestParam(required = false) Integer limit) {
            
        try {
            List<ProductResponseDTO> results;

            // 1. Si hi ha paràmetres de PREU, executem la cerca per preu
            if (priceMin != null && priceMax != null && prefix != null) {
                results = productService.getProductsBetweenPricesOrdered(priceMin, priceMax, prefix, camp, order);
            } 
            // 2. Si hi ha paràmetres de RATING, executem la cerca per rating
            else if (ratingMin != null && ratingMax != null) {
                results = productService.getProductsBetweenRatingsOrdered(ratingMin, ratingMax, camp, order);
            } 
            // 3. Si no hi ha filtres, executem la cerca simple
            else {
                results = productService.getProductsOrderedByCamp(camp, order);
            }

            return ResponseEntity.ok(results);

        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error en la cerca: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error); 
        }
    }
    
    // 5 productes que tenen millor relació qualitat - preu
    @GetMapping("products/qualitat-preu")
    public ResponseEntity<?> getBestQualityPriceRatio() {
        try {
            List<ProductResponseDTO> topProducts = productService.getTop5QualityPrice();
            return ResponseEntity.ok(topProducts);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtenir els productes per qualitat-preu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // 10 primers productes nous amb millor valoració
    @GetMapping("/products/productes-nous")
    public ResponseEntity<?> getNewProducts() {
        try {
            List<ProductResponseDTO> newProducts = productService.getNewProducts();
            return ResponseEntity.ok(newProducts);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error  al obtenir els productes nous: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Cerca per lots de 5 productes-files
    @GetMapping("/products/page")
    public ResponseEntity<?> getProductsPage(@RequestParam(defaultValue = "0") int page) { //valor per defecte 0~>primer bloc de 5 files(files 1-5). Quan el valor és "1"~>segón bloc de 5 files (files 6-10)
        try {
            List<ProductResponseDTO> results = productService.getProductsPaginated(page);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}

