package ma.rougga.qstates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CfgHandler {

    //MetaData
    public static final String APP = "QStates";
    public static final String VERSION = "6.0";
    public static final String COMPANY = "ROUGGA";
    public static final String CLIENT = "NST";
    //Data files
    private final static String CFG_FILE = "\\cfg\\cfg.properties";
    private final static String USER_FILE = "\\cfg\\db\\users.xml";
    private final static String CIBLE_FILE = "\\cfg\\db\\cible.xml";
    private final static String EXTRA_FILE = "\\cfg\\db\\extra.xml";
    private final static String TITLE_FILE = "\\cfg\\db\\title.xml";
    //excel

    private final String gblTempExcel = "\\cfg\\excel\\gbltemp.xlsx";
    private final String empTempExcel = "\\cfg\\excel\\emptemp.xlsx";
    private final String empServTempExcel = "\\cfg\\excel\\empservtemp.xlsx";
    private final String GchTempExcel = "\\cfg\\excel\\gchtemp.xlsx";
    private final String GchservTempExcel = "\\cfg\\excel\\gchservtemp.xlsx";
    private final String ndtTempExcel = "\\cfg\\excel\\ndttemp.xlsx";
    private final String glaTempExcel = "\\cfg\\excel\\glatemp.xlsx";
    private final String gltTempExcel = "\\cfg\\excel\\glttemp.xlsx";
    private final String aplTempExcel = "\\cfg\\excel\\apltemp.xlsx";
    //Pages

    public static String PAGE_HOME = "/QStates/home.jsp";
    public static String PAGE_REPORT = "/QStates/report.jsp";
    public static String PAGE_TASK = "/QStates/setting/taches.jsp";

    //declaration        
    private static HttpServletRequest request;
    private static FileReader FR = null;
    private static Properties prop = null;
    
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String getFormatedDateAsString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return format.format(date);
        } else {
            return null;
        }
        
    }

    public static Date getFormatedDateAsDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            try {
                return format.parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(CfgHandler.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getFormatedTime(Float Sec) {
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
     public static String getGrade(String grd) {
        switch (grd) {
            case "adm":
                return "Administrateur";
            case "sv":
                return "Superviseur";
            case "user":
                return "Utilisateur";
        }
        return "erreur!";
    }
    
    public CfgHandler(HttpServletRequest r) throws FileNotFoundException, IOException {
        this.request = r;
    }

    public static String getPropertie(String name,HttpServletRequest req) throws IOException {

        prop = new Properties();
        FR = new FileReader(getCfgFile(req));
        prop.load(FR);
        String val = prop.getProperty(name);
        return val;

    }

    public void closeFR() throws IOException {
        FR.close();
    }

    public void addPropertie(String name, String val,HttpServletRequest req) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.setProperty(name, val);
        FileOutputStream f = new FileOutputStream(getCfgFile(req));
        p.store(f, "daasdasd");
        f.close();
        p.clear();
    }

    public static Document getXml(String path) throws ParserConfigurationException, SAXException, IOException {
        File xml = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public int getCibleA(String id,HttpServletRequest req) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {

        int cibleA = 0;
        String path = getCibleFile(req);
        Document doc = getXml(path);
        Node cibles = doc.getFirstChild();
        NodeList nList = cibles.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id)) {
                    cibleA = Integer.parseInt(eElement.getElementsByTagName("cibleA").item(0).getTextContent());
                }
            }

        }
        return cibleA;
    }

    public int getCibleT(String id,HttpServletRequest req) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {

        int cibleT = 0;
        String path = getCibleFile(req);
        Document doc = getXml(path);
        Node cibles = doc.getFirstChild();
        NodeList nList = cibles.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id)) {
                    cibleT = Integer.parseInt(eElement.getElementsByTagName("cibleT").item(0).getTextContent());
                }
            }

        }
        return cibleT;
    }

    public static String getCfgFile(HttpServletRequest req) {
        return req.getServletContext().getRealPath(CFG_FILE);
    }

    public static String getUserFile(HttpServletRequest req) {
        return req.getServletContext().getRealPath(USER_FILE);
    }

    public static String getCibleFile(HttpServletRequest req) {
        return req.getServletContext().getRealPath(CIBLE_FILE);
    }

    public static String getExtraFile(HttpServletRequest req) {
        return req.getServletContext().getRealPath(EXTRA_FILE);
    }

    public String getGblTempExcel() {
        return request.getServletContext().getRealPath(gblTempExcel);
    }

    public String getEmpTempExcel() {
        return request.getServletContext().getRealPath(empTempExcel);
    }

    public String getGchTempExcel() {
        return request.getServletContext().getRealPath(GchTempExcel);
    }

    public String getEmpServTempExcel() {
        return request.getServletContext().getRealPath(empServTempExcel);
    }

    public String getGchServTempExcel() {
        return request.getServletContext().getRealPath(GchservTempExcel);
    }

    public String getNdtTempExcel() {
        return request.getServletContext().getRealPath(ndtTempExcel);
    }

    public String getGlaTempExcel() {
        return request.getServletContext().getRealPath(glaTempExcel);
    }

    public String getGltTempExcel() {
        return request.getServletContext().getRealPath(gltTempExcel);
    }

    public String getAplTempExcel() {
        return request.getServletContext().getRealPath(aplTempExcel);
    }

    public String getTitleFile() {
        return request.getServletContext().getRealPath(TITLE_FILE);
    }

    
    
}
