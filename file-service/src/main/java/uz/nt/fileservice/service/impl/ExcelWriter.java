package uz.nt.fileservice.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import uz.nt.productservice.dto.ProductDto;


import java.io.IOException;
import java.util.List;

@Configuration
public class ExcelWriter {

    public XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet sheet;

    public void writeHeaderLine() {
        sheet = workbook.createSheet("Products");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 16);
        style.setFont(font);

        createCell(row, 0, "ID");
        createCell(row, 1, "Name");
        createCell(row, 2, "Price");
        createCell(row, 3, "Amount");
        createCell(row, 4, "Description");
        createCell(row, 5, "isAvailable");

    }

    public void createCell(Row row, int columnCount, Object value) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
    }

    public void writeDataLines(List<ProductDto> productDtoList) throws IOException {
        int rowCount = 1;

        for (ProductDto product : productDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;


            createCell(row, columnCount++, product.getId());
            createCell(row, columnCount++, product.getName());
            createCell(row, columnCount++, product.getPrice());
            createCell(row, columnCount++, product.getAmount());
            createCell(row, columnCount++, product.getDescription());
            createCell(row, columnCount++, product.getIsAvailable());


        }
    }


}
