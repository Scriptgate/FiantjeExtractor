package net.scriptgate.fiantje.command;

import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.fiantje.io.ExportException;
import net.scriptgate.fiantje.io.ExcelService;
import net.scriptgate.fiantje.io.JsonService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static net.scriptgate.util.FileUtil.getOutputDirectoryOrDefault;

public class TranslateJsonToExcelCommand {

    public static void main(String[] args) throws CommandException {
        String inputFilename = System.getProperty("fiantje.input");
        String output = System.getProperty("fiantje.output");

        new TranslateJsonToExcelCommand().execute(inputFilename, output);
    }

    private void execute(String input, String output) throws CommandException {
        try {
            JsonService jsonService = new JsonService();
            ExcelService excelService = new ExcelService();

            List<Order> orders = jsonService.read(new File(input));
            excelService.export(orders, getOutputDirectoryOrDefault(output));
        } catch (IOException | ExportException ex) {
            throw new CommandException(ex);
        }
    }

}
