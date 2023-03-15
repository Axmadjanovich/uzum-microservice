package uz.nt.productservice.service;


import dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import uz.nt.productservice.dto.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface ProductService {

    /**
     * Is used to add new products to product table;
     * @param productDto
     * @return
     * @throws IOException
     */

    ResponseDto<ProductDto> addNewProduct(ProductDto productDto) throws IOException;

    /**
     * Update's products according to it's changes
     * @param productDto
     * @return
     */

    ResponseDto<ProductDto> updateProduct(ProductDto productDto);

    /**
     * Returns all products that exist in database product table
     * @param page
     * @param size
     * @return
     */

    ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size);

    /**
     * Returns product according to it's id
     * @param id
     * @return
     */

    ResponseDto<ProductDto> getProductById(Integer id);

//    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);

    ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params);

    /**
     * This method is used to return products list in descending order
     * @param sort
     * @return
     */

    ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort);

//    ResponseDto<Page<ProductDto>> getExpensiveProducts();

    /**
     * This mehtod is used to return Most expensive products of each category!
     * @return
     */
    ResponseDto<List<ProductDto>> getExpensiveProducts();

    /**
     * This method returns all products that occurs in salesList as a Stream.
     * @param salesList
     * @return
     */
    ResponseDto<Stream<ProductDto>> getProductsForSales(List<Integer> salesList);

    /**
     * This method returns products with less amount according to their indicators;
     * @return
     */

    ResponseDto<Stream<ProductDto>> getAllProductsWithLessAmount();
}
