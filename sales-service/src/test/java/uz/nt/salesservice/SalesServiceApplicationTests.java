package uz.nt.salesservice;

import dto.ResponseDto;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.rest.ExceptionHandlerResource;
import uz.nt.salesservice.service.SalesService;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.time.LocalDateTime;

@SpringBootTest
class SalesServiceApplicationTests {
    @Autowired
    SalesService salesService;

    @Test
    @DisplayName("Add sales valid value")
    void addSAlesValidValue() {
        SalesDto salesDto = new SalesDto();
        salesDto.setPrice(1200);
        salesDto.setProductId(1);
        salesDto.setExpirationDate("2023-03-18 00:00:00");

        ResponseDto<SalesDto> response = salesService.addSales(salesDto);

        assertEquals(response.getCode(),0);
        assertTrue(response.isSuccess());
        assertEquals(response.getMessage(), AppStatusMessages.OK);
    }
//    @Test
//    @DisplayName("add sales not valid date format")
//    void addSalesNotValidDateFormat(){
//        SalesDto salesDto = new SalesDto();
//        salesDto.setPrice(1200);
//        salesDto.setProductId(1);
//        salesDto.setExpirationDate("20233-03-18 00:00:00");
//
//        ResponseDto<SalesDto> response = salesService.addSales(salesDto);
//        assertEquals(response.getErrors().size(),1);
//    }
    @Test
    @DisplayName("Get by exists id")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/insert-test-value.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-value.sql")
    void getExistsId(){
        ResponseDto<SalesDto> response = salesService.getById(1);
        assertAll(
                ()->assertEquals(response.getCode(), AppStatusCodes.OK_CODE,"found"),
                ()->assertTrue(response.isSuccess(),"product found success")
        );
    }

    @Test
    @DisplayName("Get by not exists")
    void getNotExistsId(){
        ResponseDto<SalesDto> response = salesService.getById(3241);
        assertAll(
                ()->assertEquals(response.getCode(), AppStatusCodes.NOT_FOUND_ERROR_CODE,"not found"),
                ()->assertFalse(response.isSuccess(),"not found success or id null")
        );
    }
    @Test
    @DisplayName("delete by id if Found")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/insert-test-value.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-value.sql")
    void deleteByIdIfFound(){
        ResponseDto<SalesDto> responce = salesService.deleteById(1);
        assertAll(
                ()->assertTrue(responce.isSuccess(),"delete by id: id"),
                ()->assertEquals(responce.getCode(),AppStatusCodes.OK_CODE, "delete by id: code"),
                ()->assertEquals(responce.getMessage(),AppStatusMessages.OK,"delete by id: message")
        );
    }
    @Test
    @DisplayName("delete by id if not found")
    void deleteByIdIfNotFound(){
        ResponseDto<SalesDto> responce = salesService.getById(123412);
        assertAll(
                ()->assertFalse(responce.isSuccess(),"delete by id if not found: id"),
                ()->assertEquals(responce.getCode(),AppStatusCodes.NOT_FOUND_ERROR_CODE, "delete by id not found: code"),
                ()->assertEquals(responce.getMessage(),AppStatusMessages.NOT_FOUND,"delete by id not found: message")
        );
    }

}
