package net.scriptgate.fiantje.io;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.fiantje.domain.OrderItem;
import net.scriptgate.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static jxl.format.Colour.BLACK;
import static jxl.format.ScriptStyle.NORMAL_SCRIPT;
import static jxl.format.UnderlineStyle.NO_UNDERLINE;
import static jxl.format.UnderlineStyle.SINGLE;
import static jxl.write.WritableFont.*;

public class ExcelService implements ExportService {

    private static final WritableFont TEXT_FONT = new WritableFont(ARIAL, 11, NO_BOLD, false, NO_UNDERLINE, BLACK, NORMAL_SCRIPT);
    private static final WritableCellFormat H1_FORMAT = new WritableCellFormat(new WritableFont(ARIAL, 14, BOLD, false, SINGLE, BLACK, NORMAL_SCRIPT));
    private static final WritableCellFormat H2_FORMAT = new WritableCellFormat(new WritableFont(ARIAL, 11, BOLD, false, SINGLE, BLACK, NORMAL_SCRIPT));
    private static final WritableCellFormat TEXT_FORMAT = new WritableCellFormat(TEXT_FONT);
    private static final WritableCellFormat INTEGER_FORMAT = new WritableCellFormat(TEXT_FONT, NumberFormats.INTEGER);
    private static final WritableCellFormat NUMBER_FORMAT = new WritableCellFormat(TEXT_FONT, new NumberFormat("#.00"));
    private static final WritableCellFormat DATE_FORMAT = new WritableCellFormat(TEXT_FONT, new DateFormat("dd/MM/yyyy"));
    private static final WritableCellFormat DATE_HOUR_FORMAT = new WritableCellFormat(TEXT_FONT, new DateFormat("dd/MM/yyyy hh:mm"));

    @Override
    public void export(List<Order> orders, File outputDirectory) throws ExportException {
        try {
            WritableWorkbook workbook = createWorkbook(outputDirectory);

            WritableSheet sheet = workbook.createSheet("Orders", 0);
            Collections.sort(orders, (orderA, orderB) -> orderA.getOrderDate().compareTo(orderB.getOrderDate()));
            writeOrders(orders, sheet);

            //TODO: make an autoClosable workbook implementation
            workbook.write();
            workbook.close();
        } catch (WriteException | IOException ex) {
            throw new ExportException(ex);
        }
    }

    private void writeOrders(Collection<Order> orders, WritableSheet sheet) throws WriteException {
        int row = 1;

        sheet.addCell(new Label(0, row, "Order", H1_FORMAT));
        sheet.addCell(new Label(5, row, "Item", H1_FORMAT));
        row++;

        sheet.addCell(new Label(0, row, "ID", H2_FORMAT));
        sheet.addCell(new Label(1, row, "Order Date", H2_FORMAT));
        sheet.addCell(new Label(2, row, "Delivery Date", H2_FORMAT));
        sheet.addCell(new Label(3, row, "Total", H2_FORMAT));
        sheet.addCell(new Label(4, row, "Status", H2_FORMAT));
        sheet.addCell(new Label(5, row, "Description", H2_FORMAT));
        sheet.addCell(new Label(6, row, "Amount", H2_FORMAT));
        sheet.addCell(new Label(7, row, "Remarks", H2_FORMAT));
        row++;

        for (Order order : orders) {
            sheet.addCell(new Label(0, row, order.getId(), TEXT_FORMAT));
            sheet.addCell(new DateTime(1, row, order.getOrderDate(), DATE_HOUR_FORMAT));
            sheet.addCell(new DateTime(2, row, order.getDeliveryDate(), DATE_FORMAT));
            sheet.addCell(new Number(3, row, order.getTotal(), NUMBER_FORMAT));
            sheet.addCell(new Label(4, row, order.getStatus(), TEXT_FORMAT));
            for (OrderItem orderItem : order.getItems()) {
                sheet.addCell(new Label(5, row, orderItem.getDescription(), TEXT_FORMAT));
                sheet.addCell(new Number(6, row, orderItem.getAmount(), INTEGER_FORMAT));
                sheet.addCell(new Label(7, row, orderItem.getRemarks(), TEXT_FORMAT));
                row++;
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private WritableWorkbook createWorkbook(File outputDirectory) throws IOException {
        outputDirectory.mkdirs();

        File outputFile = FileUtil.getUniqueFileNameWithTimestamp(outputDirectory, "orders", "xls");
        outputFile.createNewFile();

        return Workbook.createWorkbook(outputFile);
    }


}
