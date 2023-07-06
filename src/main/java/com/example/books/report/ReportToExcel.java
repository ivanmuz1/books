package com.example.books.report;

import com.example.books.entities.Form;
import com.example.books.repositories.BookRepository;
import com.example.books.repositories.FormRepository;
import com.example.books.repositories.PersonRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ReportToExcel {

    @Autowired
    FormRepository formRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    BookRepository bookRepository;

    public Integer daysOfDelay(LocalDate SpecifiedDate){

        LocalDate end = SpecifiedDate.withDayOfMonth(SpecifiedDate.getMonth().length(SpecifiedDate.isLeapYear()));
        System.out.println(end);

        return 1;
    }

    public void generateReport(LocalDate SpecifiedDate) throws IOException, ParseException {

        Integer month = SpecifiedDate.getMonthValue();
        Integer year = SpecifiedDate.getYear();

        List<Form> formList = formRepository.findUserByMonth(month, year);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Имя пользователя");
        header.createCell(1).setCellValue("Название книги");
        header.createCell(2).setCellValue("Дней просрочки за этот месяц");
        header.createCell(3).setCellValue("Пенни за месяц");
        header.createCell(4).setCellValue("Дата взятия");
        header.createCell(5).setCellValue("Дата отдачи");
        header.createCell(6).setCellValue("id");

        int rowNumber = 1;
        for(Form form: formList){
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(personRepository.findById(form.getReaderId()).get().getName());
            row.createCell(1).setCellValue(bookRepository.findById(form.getBookId()).get().getTitle());
            row.createCell(2).setCellValue(form.getDateReceipt());
            row.createCell(3).setCellValue(form.getPenalties());
            row.createCell(4).setCellValue(form.getDateReceipt().toString());
            row.createCell(5).setCellValue(form.getDateReturn().toString());
            row.createCell(6).setCellValue(form.getFormId().toString());
        }

        for(int i = 0; i < 6; ++i){
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream("отчет.xls")){
            workbook.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }
}
