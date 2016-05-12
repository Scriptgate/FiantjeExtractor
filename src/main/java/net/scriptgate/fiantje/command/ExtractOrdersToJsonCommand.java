package net.scriptgate.fiantje.command;

import net.scriptgate.fiantje.FiantjeApplication;
import net.scriptgate.fiantje.FiantjeException;
import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.fiantje.io.ExportException;
import net.scriptgate.fiantje.io.JsonService;

import java.util.List;

import static net.scriptgate.fiantje.FiantjeApplication.createFiantje;
import static net.scriptgate.util.FileUtil.getOutputDirectoryOrDefault;

public class ExtractOrdersToJsonCommand {

    public static void main(String[] args) throws CommandException {
        String username = System.getProperty("fiantje.username");
        String password = System.getProperty("fiantje.password");
        String output = System.getProperty("fiantje.output");

        new ExtractOrdersToJsonCommand().execute(username, password, output);
    }

    private void execute(String username, String password, String output) throws CommandException {
        try {
            FiantjeApplication fiantjeApplication = createFiantje();
            fiantjeApplication.login(username, password);

            List<Order> orders = fiantjeApplication.getOrders();

            new JsonService().export(orders, getOutputDirectoryOrDefault(output));
        } catch (ExportException | FiantjeException ex) {
            throw new CommandException(ex);
        }
    }

}
