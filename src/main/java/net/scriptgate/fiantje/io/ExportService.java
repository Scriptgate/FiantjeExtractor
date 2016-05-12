package net.scriptgate.fiantje.io;

import net.scriptgate.fiantje.domain.Order;

import java.io.File;
import java.util.List;

interface ExportService {

    void export(List<Order> orders, File outputDirectory) throws ExportException;

}
