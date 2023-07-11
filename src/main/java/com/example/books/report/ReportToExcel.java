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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class ReportToExcel {

    @Autowired
    FormRepository formRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    BookRepository bookRepository;

    public Integer daysOfDelay(LocalDate firstDay, LocalDate endDay, Date DateReturn, Date DateDelivery){

        LocalDate LastDateDelivery = DateDelivery.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if(DateReturn == null){
            if(LastDateDelivery.isBefore(firstDay)){return (int) ChronoUnit.DAYS.between(firstDay, endDay) + 1;}
            else if (LastDateDelivery.isAfter(endDay)) {return 0;}
        }
        else {
            LocalDate DateIsReturning = DateReturn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(LastDateDelivery.isBefore(firstDay)){
                if(DateIsReturning.isBefore(endDay)) {return (int) ChronoUnit.DAYS.between(firstDay, DateIsReturning) + 1;}
                else {return (int) ChronoUnit.DAYS.between(firstDay, endDay) + 1;}
            }
            else if(LastDateDelivery.isAfter(DateIsReturning)){return 0;}
        }
        return 0;
    }

    public void generateReport(LocalDate SpecifiedDate) throws IOException, ParseException {

        LocalDate firstDay = SpecifiedDate.withDayOfMonth(1);
        LocalDate endDay = SpecifiedDate.withDayOfMonth(SpecifiedDate.lengthOfMonth());

        List<Form> formList = formRepository.findUserByMonth(firstDay, endDay);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Имя пользователя");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Название книги");
        header.createCell(3).setCellValue("Дата взятия");
        header.createCell(4).setCellValue("Дата возврата");
        header.createCell(5).setCellValue("Дней просрочки за этот месяц");
        header.createCell(6).setCellValue("Пенни за месяц");

        int rowNumber = 1;
        for(Form form: formList){

            Row row = sheet.createRow(rowNumber++);

            row.createCell(0).setCellValue(personRepository.findById(form.getReaderId()).get().getName());
            row.createCell(1).setCellValue(personRepository.findById(form.getReaderId()).get().getEmail());
            row.createCell(2).setCellValue(bookRepository.findById(form.getBookId()).get().getTitle());
            row.createCell(3).setCellValue(form.getDateReceipt().toString());

            if(form.getDateReturn() == null){
                row.createCell(4).setCellValue("Не вернул");
            }else{
                row.createCell(4).setCellValue(form.getDateReturn().toString());
            }

            Integer daysDelay = daysOfDelay(firstDay, endDay, form.getDateReturn(), form.getDateDelivery());

            row.createCell(5).setCellValue(daysDelay); //дни просрочки
            row.createCell(6).setCellValue(daysDelay * 5); //кол-во пенни
        }

        for(int i = 0; i < 7; ++i){
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
