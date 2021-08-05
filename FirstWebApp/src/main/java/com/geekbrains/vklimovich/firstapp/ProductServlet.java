package com.geekbrains.vklimovich.firstapp;

import org.slf4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "ProductServlet", urlPatterns = "/prodlist")
public class ProductServlet extends HttpServlet {
    private static final int PROD_NUM = 10;
    private static Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Log: ProductServlet GET");

        PrintWriter writer = resp.getWriter();
        Product[] products = Product.randomProductList(PROD_NUM);

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Product list</title>");
        writer.println("<style type='text/css'>");
        writer.println("table {");
        writer.println("border-collapse: collapse;");
        writer.println("border-spacing: 0px;");
        writer.println("}");
        writer.println("td {");
        writer.println("border-spacing: 0px;");
        writer.println("padding: 15px;");
        writer.println("}");
        writer.println("td.par {");
        writer.println("background-color: AntiqueWhite;");
        writer.println("}");
        writer.println("td.inpar {");
        writer.println("background-color: Snow;");
        writer.println("}");
        writer.println("th {");
        writer.println("border-bottom: 2px solid black;");
        writer.println("border-spacing: 0px;");
        writer.println("padding: 15px;");
        writer.println("background-color: AntiqueWhite;");
        writer.println("}");
        writer.println("</style>");
        writer.println("</head>");

        writer.println("<h2>Product list:</h2>");
        writer.println("<table>");
        writer.println("<thead>");
        writer.println("<tr><th>Product</th><th>Cost</th></tr>");
        writer.println("</thead>");
        writer.println("<tbody>");
        for (int i = 0; i < products.length;i++) {
            String cl = i % 2 == 0?"inpar":"par";
            writer.println("<tr>");
            writer.println("<td class='" + cl + "'>" + products[i].getTitle() + "</td>");
            writer.println("<td class='" + cl + "'>" + products[i].getCost() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</tbody>");
        writer.println("</table>");
        writer.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Log: ProductServlet POST");
        resp.getWriter().printf("<html><body><h1>POST request</h1></body></html>");
    }

    @Override
    public void destroy() {
        logger.debug("ProductServlet destroy");
    }

    @Override
    public void init() throws ServletException {
        logger.debug("ProductServlet init");
    }
}
