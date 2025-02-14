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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class InvoiceGenerator {

    public String generateInvoice(OrdersDTO order) throws FileNotFoundException {
        String dest = "invoice_" + order.getOrderId() + ".pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        // Add title
        Paragraph title = new Paragraph("Order Invoice")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Add order details
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Paragraph orderDetails = new Paragraph("Order ID: " + order.getOrderId() +
                "\nOrder Date: " + (order.getCreatedAt() != null ? order.getCreatedAt().format(formatter) : "N/A") +
                "\nScheduled Time: " + (order.getScheduledTime() != null ? order.getScheduledTime().format(formatter) : "N/A") +
                "\nCustomer Name: " + (order.getCustomerFirstName() != null ? order.getCustomerFirstName() : "N/A") +
                "\nDelivery Address: " + (order.getDeliveryAddress() != null ? order.getDeliveryAddress() : "N/A") +
                "\nRestaurant Name: " + (order.getRestaurantName() != null ? order.getRestaurantName() : "N/A"))
                .setMarginTop(20);
        document.add(orderDetails);

        // Add table for menu items
        float[] columnWidths = {1, 5, 2, 2, 2};
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));

        // Add table headers
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

        // Add total amount
        Paragraph totalAmount = new Paragraph("Total Amount: ₹" + order.getTotalAmount())
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(20);
        document.add(table);
        document.add(totalAmount);

        // Close the document
        document.close();
        System.out.println("Invoice created successfully!");

        return dest;
    }
}