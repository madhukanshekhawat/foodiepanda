package com.pio.foodiepanda.controller;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.pio.foodiepanda.dto.OrderDetailDTO;
import com.pio.foodiepanda.dto.OrdersDTO;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class InvoiceGenerator {

    public String generateInvoice(OrdersDTO order) throws FileNotFoundException {
        String dest = "invoice_" + order.getOrderId() + ".pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        // Add Invoice Title
        Paragraph title = new Paragraph("Order Invoice")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Order ID (Placed Just Below Invoice Title)
        Paragraph orderIdParagraph = new Paragraph("Order ID: " + order.getOrderId())
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(orderIdParagraph);

        document.add(new Paragraph("\n")); // Space between sections

        // Customer Name & Order Date Table
        float[] infoColumnWidths = {5, 5};
        Table infoTable = new Table(infoColumnWidths);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        infoTable.addHeaderCell("Customer Name");
        infoTable.addHeaderCell("Order Date");

        infoTable.addCell(order.getCustomerFirstName() != null ? order.getCustomerFirstName() : "N/A");
        infoTable.addCell(order.getCreatedAt() != null ? order.getCreatedAt().toString() : "N/A");

        document.add(infoTable);
        document.add(new Paragraph("\n")); // Space between tables

        // Restaurant Details Table
        float[] restColumnWidths = {3, 4, 3};
        Table restaurantTable = new Table(restColumnWidths);
        restaurantTable.setWidth(UnitValue.createPercentValue(100));

        restaurantTable.addHeaderCell("Restaurant Name");
        restaurantTable.addHeaderCell("Restaurant Address");
        restaurantTable.addHeaderCell("Restaurant Contact");

        restaurantTable.addCell(order.getRestaurantName() != null ? order.getRestaurantName() : "N/A");
        restaurantTable.addCell(order.getRestaurantAddress() != null ? order.getRestaurantAddress() : "N/A");
        restaurantTable.addCell(order.getRestaurantContactNumber() != null ? order.getRestaurantContactNumber() : "N/A");

        document.add(restaurantTable);
        document.add(new Paragraph("\n")); // Space between tables

        // Delivery Address Table with Customer Contact
        float[] addressColumnWidths = {5, 5};
        Table addressTable = new Table(addressColumnWidths);
        addressTable.setWidth(UnitValue.createPercentValue(100));

        addressTable.addHeaderCell("Delivery Address");
        addressTable.addHeaderCell("Customer Contact");

        addressTable.addCell(order.getDeliveryAddress() != null ? order.getDeliveryAddress() : "N/A");
        addressTable.addCell(order.getCustomerContactNumber() != null ? order.getCustomerContactNumber() : "N/A");

        document.add(addressTable);
        document.add(new Paragraph("\n")); // Space between tables

        // Order Details Table
        float[] columnWidths = {1, 5, 2, 2, 2};
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));

        // Add Table Headers
        table.addHeaderCell("S.No");
        table.addHeaderCell("Item");
        table.addHeaderCell("Quantity");
        table.addHeaderCell("Unit Price");
        table.addHeaderCell("Total Price");

        List<OrderDetailDTO> orderDetailsList = order.getOrderDetails();
        for (int i = 0; i < orderDetailsList.size(); i++) {
            OrderDetailDTO detail = orderDetailsList.get(i);
            table.addCell(String.valueOf(i + 1));
            table.addCell(detail.getMenuItem() != null ? detail.getMenuItem() : "N/A");
            table.addCell(String.valueOf(detail.getQuantity()));
            table.addCell("₹" + detail.getPrice());
            table.addCell("₹" + (detail.getPrice() * detail.getQuantity()));
        }

        document.add(table);
        document.add(new Paragraph("\n")); // Space between total amount and order details table

        // Total Amount
        Paragraph totalAmount = new Paragraph("Total Amount: ₹" + order.getTotalAmount())
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(totalAmount);
        document.add(new Paragraph("\n")); // Space before T&C

        // Terms & Conditions
        Paragraph terms = new Paragraph("Terms & Conditions:\n" +
                "1. This invoice is system-generated and does not require a signature.\n" +
                "2. No refunds once the order has been delivered.\n" +
                "3. Please contact customer support for any issues with the order.\n" +
                "4. The total amount includes applicable taxes and charges.")
                .setFontSize(10);
        document.add(terms);

        // Close the Document
        document.close();
        System.out.println("Invoice created successfully!");

        return dest;
    }
}
