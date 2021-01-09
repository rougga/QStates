package main;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import main.handler.TitleHandler;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.xml.sax.SAXException;

public class Export {

    public Export() {
    }

    public String getRandomName() {
        return (new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date()) + (new Random()).nextInt(100000);
    }

    //Excel
    public int exportGblExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGblTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGblTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("nb_t", gtable.get(i).get(1));
                    row.put("nb_tt", gtable.get(i).get(2));
                    row.put("nb_ta", gtable.get(i).get(3));
                    row.put("nb_ttl1", gtable.get(i).get(4));
                    row.put("nb_tsa", gtable.get(i).get(5));
                    row.put("perapt", gtable.get(i).get(6));
                    row.put("perttl1pt", gtable.get(i).get(7));
                    row.put("persapt", gtable.get(i).get(8));
                    row.put("avga", gtable.get(i).get(9));
                    row.put("nb_ca", gtable.get(i).get(10));
                    row.put("perca", gtable.get(i).get(11));
                    row.put("avgt", gtable.get(i).get(12));
                    row.put("nb_ct", gtable.get(i).get(13));
                    row.put("perct", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("service", gtable.get(gtable.size() - 1).get(0));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(5));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(6));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(7));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(8));
                subT.put("avga", gtable.get(gtable.size() - 1).get(9));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(10));
                subT.put("perca", gtable.get(gtable.size() - 1).get(11));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(12));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(13));
                subT.put("perct", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                stream.close();
                return 0;
            }
        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public Map exportEmpExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateEmpTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("emp", gtable.get(i).get(0));
                    row.put("nb_t", gtable.get(i).get(1));
                    row.put("nb_tt", gtable.get(i).get(2));
                    row.put("nb_ta", gtable.get(i).get(3));
                    row.put("nb_ttl1", gtable.get(i).get(4));
                    row.put("nb_tsa", gtable.get(i).get(5));
                    row.put("perapt", gtable.get(i).get(6));
                    row.put("perttl1pt", gtable.get(i).get(7));
                    row.put("persapt", gtable.get(i).get(8));
                    row.put("avga", gtable.get(i).get(9));
                    row.put("nb_ca", gtable.get(i).get(10));
                    row.put("perca", gtable.get(i).get(11));
                    row.put("avgt", gtable.get(i).get(12));
                    row.put("nb_ct", gtable.get(i).get(13));
                    row.put("perct", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("emp", gtable.get(gtable.size() - 1).get(0));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(5));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(6));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(7));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(8));
                subT.put("avga", gtable.get(gtable.size() - 1).get(9));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(10));
                subT.put("perca", gtable.get(gtable.size() - 1).get(11));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(12));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(13));
                subT.put("perct", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportEmpServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateEmpServiceTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("emp", gtable.get(i).get(0));
                    row.put("service", gtable.get(i).get(1));
                    row.put("nb_t", gtable.get(i).get(2));
                    row.put("nb_tt", gtable.get(i).get(3));
                    row.put("nb_ta", gtable.get(i).get(4));
                    row.put("nb_ttl1", gtable.get(i).get(5));
                    row.put("nb_tsa", gtable.get(i).get(6));
                    row.put("perapt", gtable.get(i).get(7));
                    row.put("perttl1pt", gtable.get(i).get(8));
                    row.put("persapt", gtable.get(i).get(9));
                    row.put("avga", gtable.get(i).get(10));
                    row.put("nb_ca", gtable.get(i).get(11));
                    row.put("perca", gtable.get(i).get(12));
                    row.put("avgt", gtable.get(i).get(13));
                    row.put("nb_ct", gtable.get(i).get(14));
                    row.put("perct", gtable.get(i).get(15));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("emp", gtable.get(gtable.size() - 1).get(0));
                subT.put("service", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(5));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(6));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(7));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(8));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(9));
                subT.put("avga", gtable.get(gtable.size() - 1).get(10));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(11));
                subT.put("perca", gtable.get(gtable.size() - 1).get(12));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(13));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(14));
                subT.put("perct", gtable.get(gtable.size() - 1).get(15));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./report.jsp?err=" + e.getMessage());
        }
        return null;
    }

    public Map exportGchExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGchTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGchTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("gch", gtable.get(i).get(0));
                    row.put("nb_t", gtable.get(i).get(1));
                    row.put("nb_tt", gtable.get(i).get(2));
                    row.put("nb_ta", gtable.get(i).get(3));
                    row.put("nb_ttl1", gtable.get(i).get(4));
                    row.put("nb_tsa", gtable.get(i).get(5));
                    row.put("perapt", gtable.get(i).get(6));
                    row.put("perttl1pt", gtable.get(i).get(7));
                    row.put("persapt", gtable.get(i).get(8));
                    row.put("avga", gtable.get(i).get(9));
                    row.put("nb_ca", gtable.get(i).get(10));
                    row.put("perca", gtable.get(i).get(11));
                    row.put("avgt", gtable.get(i).get(12));
                    row.put("nb_ct", gtable.get(i).get(13));
                    row.put("perct", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("gch", gtable.get(gtable.size() - 1).get(0));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(5));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(6));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(7));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(8));
                subT.put("avga", gtable.get(gtable.size() - 1).get(9));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(10));
                subT.put("perca", gtable.get(gtable.size() - 1).get(11));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(12));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(13));
                subT.put("perct", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportGchServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGchServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGchServiceTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("gch", gtable.get(i).get(0));
                    row.put("service", gtable.get(i).get(1));
                    row.put("nb_t", gtable.get(i).get(2));
                    row.put("nb_tt", gtable.get(i).get(3));
                    row.put("nb_ta", gtable.get(i).get(4));
                    row.put("nb_ttl1", gtable.get(i).get(5));
                    row.put("nb_tsa", gtable.get(i).get(6));
                    row.put("perapt", gtable.get(i).get(7));
                    row.put("perttl1pt", gtable.get(i).get(8));
                    row.put("persapt", gtable.get(i).get(9));
                    row.put("avga", gtable.get(i).get(10));
                    row.put("nb_ca", gtable.get(i).get(11));
                    row.put("perca", gtable.get(i).get(12));
                    row.put("avgt", gtable.get(i).get(13));
                    row.put("nb_ct", gtable.get(i).get(14));
                    row.put("perct", gtable.get(i).get(15));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("gch", gtable.get(gtable.size() - 1).get(0));
                subT.put("service", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(5));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(6));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(7));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(8));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(9));
                subT.put("avga", gtable.get(gtable.size() - 1).get(10));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(11));
                subT.put("perca", gtable.get(gtable.size() - 1).get(12));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(13));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(14));
                subT.put("perct", gtable.get(gtable.size() - 1).get(15));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdtExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets edités Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdtTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {

                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    for (int j = 1; j < gtable.get(i).size(); j++) {
                        row.put("h" + (j - 1), gtable.get(i).get(j));
                    }
                    table.add(row);
                }
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdttExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets traités Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdttTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {

                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    for (int j = 1; j < gtable.get(i).size(); j++) {
                        row.put("h" + (j - 1), gtable.get(i).get(j));
                    }
                    table.add(row);
                }
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdtaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets absents Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdtaTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {

                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    for (int j = 1; j < gtable.get(i).size(); j++) {
                        row.put("h" + (j - 1), gtable.get(i).get(j));
                    }
                    table.add(row);
                }
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdtsaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets sans affectation Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdtsaTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {

                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    for (int j = 1; j < gtable.get(i).size(); j++) {
                        row.put("h" + (j - 1), gtable.get(i).get(j));
                    }
                    table.add(row);
                }
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportGlaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGlaTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Grille d'attente Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGlaTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("t0_15", gtable.get(i).get(1));
                    row.put("t15_30", gtable.get(i).get(2));
                    row.put("t30_1", gtable.get(i).get(3));
                    row.put("t1_130", gtable.get(i).get(4));
                    row.put("t130_2", gtable.get(i).get(5));
                    row.put("t2_5", gtable.get(i).get(6));
                    row.put("t5_10", gtable.get(i).get(8));
                    row.put("t10_20", gtable.get(i).get(9));
                    row.put("t20_30", gtable.get(i).get(10));
                    row.put("t30_45", gtable.get(i).get(11));
                    row.put("t45_50", gtable.get(i).get(12));
                    row.put("t50", gtable.get(i).get(13));
                    row.put("total", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("service", gtable.get(gtable.size() - 1).get(0));
                subT.put("t0_15", gtable.get(gtable.size() - 1).get(1));
                subT.put("t15_30", gtable.get(gtable.size() - 1).get(2));
                subT.put("t30_1", gtable.get(gtable.size() - 1).get(3));
                subT.put("t1_130", gtable.get(gtable.size() - 1).get(4));
                subT.put("t130_2", gtable.get(gtable.size() - 1).get(5));
                subT.put("t2_5", gtable.get(gtable.size() - 1).get(6));
                subT.put("t5_10", gtable.get(gtable.size() - 1).get(8));
                subT.put("t10_20", gtable.get(gtable.size() - 1).get(9));
                subT.put("t20_30", gtable.get(gtable.size() - 1).get(10));
                subT.put("t30_45", gtable.get(gtable.size() - 1).get(11));
                subT.put("t45_50", gtable.get(gtable.size() - 1).get(12));
                subT.put("t50", gtable.get(gtable.size() - 1).get(13));
                subT.put("total", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("t", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportGltExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGltTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Grille traitement Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGltTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("t0_15", gtable.get(i).get(1));
                    row.put("t15_30", gtable.get(i).get(2));
                    row.put("t30_1", gtable.get(i).get(3));
                    row.put("t1_130", gtable.get(i).get(4));
                    row.put("t130_2", gtable.get(i).get(5));
                    row.put("t2_5", gtable.get(i).get(6));
                    row.put("t5_10", gtable.get(i).get(8));
                    row.put("t10_20", gtable.get(i).get(9));
                    row.put("t20_30", gtable.get(i).get(10));
                    row.put("t30_45", gtable.get(i).get(11));
                    row.put("t45_50", gtable.get(i).get(12));
                    row.put("t50", gtable.get(i).get(13));
                    row.put("total", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("service", gtable.get(gtable.size() - 1).get(0));
                subT.put("t0_15", gtable.get(gtable.size() - 1).get(1));
                subT.put("t15_30", gtable.get(gtable.size() - 1).get(2));
                subT.put("t30_1", gtable.get(gtable.size() - 1).get(3));
                subT.put("t1_130", gtable.get(gtable.size() - 1).get(4));
                subT.put("t130_2", gtable.get(gtable.size() - 1).get(5));
                subT.put("t2_5", gtable.get(gtable.size() - 1).get(6));
                subT.put("t5_10", gtable.get(gtable.size() - 1).get(8));
                subT.put("t10_20", gtable.get(gtable.size() - 1).get(9));
                subT.put("t20_30", gtable.get(gtable.size() - 1).get(10));
                subT.put("t30_45", gtable.get(gtable.size() - 1).get(11));
                subT.put("t45_50", gtable.get(gtable.size() - 1).get(12));
                subT.put("t50", gtable.get(gtable.size() - 1).get(13));
                subT.put("total", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("t", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportAplExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getAplTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Detail d'appel Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateAplTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("num", gtable.get(i).get(1));
                    row.put("ticket_time", gtable.get(i).get(2));
                    row.put("call_time", gtable.get(i).get(3));
                    row.put("start_time", gtable.get(i).get(4));
                    row.put("finish_time", gtable.get(i).get(5));
                    row.put("win", gtable.get(i).get(6));
                    row.put("emp", gtable.get(i).get(7));
                    row.put("da", gtable.get(i).get(8));
                    row.put("dt", gtable.get(i).get(9));
                    row.put("status", gtable.get(i).get(10).subSequence(gtable.get(i).get(10).indexOf(">") + 1, gtable.get(i).get(10).lastIndexOf("<")));
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    //PDF
    public int exportGblPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {

        try {
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGblTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateGblTable(request, date1, date2, db);
            float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
            return generatePDF(db, title, gtable, tbl.getGblCols(), columnWidths, response);
        } catch (Exception ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int exportEmpPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        try {
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getEmpTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateEmpTable(request, date1, date2, db);
            float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
            return generatePDF(db, title, gtable, tbl.getEmpCols(), columnWidths, response);
        } catch (Exception ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int exportEmpServicePDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        try {
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getEmpSerTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateEmpServiceTable(request, date1, date2, db);
            float[] columnWidths = {6, 7, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
            return generatePDF(db, title, gtable, tbl.getEmpServiceCols(), columnWidths, response);
        } catch (Exception ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int exportGchPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        try {
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGchTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateGchTable(request, date1, date2, db);
            float[] columnWidths = {6, 6, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
            return generatePDF(db, title, gtable, tbl.getGchCols(), columnWidths, response);
        } catch (Exception ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int exportGchServicePDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        try {
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGchServTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateGchServiceTable(request, date1, date2, db);
            float[] columnWidths = {6, 6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
            return generatePDF(db, title, gtable, tbl.getGchServiceCols(), columnWidths, response);
        } catch (Exception ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int exportNdtPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int exportNdttPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        System.err.println("Printing PDF...");
        try {
            String filename = getRandomName() + ".pdf";
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGblTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateGblTable(request, date1, date2, db);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                //fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                //metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QStates");
                document.addSubject(title);

                //title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                //table
                String[] cols = tbl.getGblCols();
                float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                //filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                //filling table with data
                //row loop
                for (int i = 0; i < gtable.size(); i++) {
                    if (Objects.equals(gtable.get(i).get(3), "Sous-Totale")) {
                        for (int j = 3; j < gtable.get(i).size(); j++) {
                            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(c);
                        }
                    } else if (Objects.equals(gtable.get(i).get(3), "Totale")) {
                        for (int j = 3; j < gtable.get(i).size(); j++) {
                            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setBackgroundColor(BaseColor.YELLOW);
                            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(c);
                        }
                    } else {
                        for (int j = 3; j < gtable.get(i).size(); j++) {
                            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(c);
                        }
                    }
                }

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                //footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                //sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtaPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int exportNdtsaPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int exportGlaPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        System.err.println("Printing PDF...");
        try {
            String filename = getRandomName() + ".pdf";
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGlaTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateGlaTable(request, date1, date2, db);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                //fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                //metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                //title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                //table
                String[] cols = tbl.getGlaCols();
                float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(40f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                //filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    if (i != 8) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(new BaseColor(184, 61, 186));
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                //filling table with data
                //row loop
                PdfPCell c = new PdfPCell(new Phrase(String.valueOf(db), tableCell));
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c.setRowspan(gtable.size());
                table.addCell(c);
                for (int i = 0; i < gtable.size(); i++) {
                    if (Objects.equals(gtable.get(i).get(3), "Sous-Totale")) {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            if (j != 7) {
                                c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    } else if (Objects.equals(gtable.get(i).get(3), "Totale")) {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            if (j != 7) {
                                c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    } else {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            if (j != 7) {
                                c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }
                }

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                //footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +" - "+ CfgHandler.APP + " v"+CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                //sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGltPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        System.err.println("Printing PDF...");
        try {
            String filename = getRandomName() + ".pdf";
            String db = request.getSession().getAttribute("db") + "";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGltTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList<String>> gtable = tbl.generateGltTable(request, date1, date2, db);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                //fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                //metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                //title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                //table
                String[] cols = tbl.getGltCols();
                float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(40f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                //filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    if (i != 8) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(new BaseColor(184, 61, 186));
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                //filling table with data
                //row loop
                PdfPCell c = new PdfPCell(new Phrase(String.valueOf(db), tableCell));
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c.setRowspan(gtable.size());
                table.addCell(c);
                for (int i = 0; i < gtable.size(); i++) {
                    if (Objects.equals(gtable.get(i).get(3), "Sous-Totale")) {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            if (j != 7) {
                                c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    } else if (Objects.equals(gtable.get(i).get(3), "Totale")) {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            if (j != 7) {
                                c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    } else {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            if (j != 7) {
                                c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }
                }

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                //footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +" - "+ CfgHandler.APP + " v"+CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                //sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportAplPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int generatePDF(String db, String title, List<ArrayList<String>> gtable, String[] cols, float[] colWidths,
            HttpServletResponse response) {
        System.err.println("Printing PDF...");
        try {
            String filename = getRandomName() + ".pdf";

            if (gtable != null && gtable.size() > 0) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                //fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                //metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                //title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                //table
                PdfPTable table = new PdfPTable(colWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(40f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                //filling table with headers
                for (String col : cols) {
                    PdfPCell c1 = new PdfPCell(new Phrase(col, tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(new BaseColor(184, 61, 186));
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                //filling table with data
                //row loop
                PdfPCell c = new PdfPCell(new Phrase(String.valueOf(db), tableCell));
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c.setRowspan(gtable.size());
                table.addCell(c);
                for (int i = 0; i < gtable.size(); i++) {
                    if (Objects.equals(gtable.get(i).get(0), "Sous-Totale")) {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(c);
                        }
                    } else if (Objects.equals(gtable.get(i).get(0), "Totale")) {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setBackgroundColor(BaseColor.YELLOW);
                            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(c);
                        }
                    } else {
                        for (int j = 0; j < gtable.get(i).size(); j++) {
                            c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(c);
                        }
                    }
                }

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                //footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                //sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }
}
