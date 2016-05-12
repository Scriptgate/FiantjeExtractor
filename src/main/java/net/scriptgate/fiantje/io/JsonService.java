package net.scriptgate.fiantje.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import net.scriptgate.fiantje.domain.Order;
import net.scriptgate.util.JsonUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static net.scriptgate.util.FileUtil.getUniqueFileNameWithTimestamp;

public class JsonService implements ExportService {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void export(List<Order> orders, File outputDirectory) throws ExportException {
        try {
            outputDirectory.mkdirs();

            File jsonFile = getUniqueFileNameWithTimestamp(outputDirectory, "orders", "json");
            jsonFile.createNewFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
                writer.write(JsonUtil.toJson(orders));
            }
        } catch (IOException ex) {
            throw new ExportException(ex);
        }
    }

    public List<Order> read(File input) throws IOException {
        ObjectReader reader = new ObjectMapper().readerFor(Order.class);
        return reader.<Order>readValues(input).readAll();
    }

}
