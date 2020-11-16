package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;
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

    private Properties prop = null;
    private final String cfgFile = "\\cfg\\cfg.properties";
    private final String userFile = "\\cfg\\users.xml";
    private final String cibleFile = "\\cfg\\cible.xml";
    private final String extraFile = "\\cfg\\extra.xml";
    private final String tempxls = "\\cfg\\temp.xls";
    private final String gblTempExcel = "\\cfg\\gbltemp.xlsx";
    private final String empTempExcel = "\\cfg\\emptemp.xlsx";
    private final String empServTempExcel = "\\cfg\\empservtemp.xlsx";
    private final String GchTempExcel = "\\cfg\\gchtemp.xlsx";
    private final String GchservTempExcel = "\\cfg\\gchservtemp.xlsx";
    private final String ndtTempExcel = "\\cfg\\ndttemp.xlsx";
    private final String glaTempExcel = "\\cfg\\glatemp.xlsx";
    private final String gltTempExcel = "\\cfg\\glttemp.xlsx";
    private final String aplTempExcel = "\\cfg\\apltemp.xlsx";
    private String url;
    private String appPath;
    private HttpServletRequest request;
    private OutputStream output;
    private FileReader FR = null;

    public CfgHandler(HttpServletRequest r) throws FileNotFoundException, IOException {
        this.request = r;
        url = request.getServletContext().getRealPath(File.separator);
        appPath = url.substring(0, url.indexOf("OffReport") + 9);
        
        appPath="C:\\Users\\bouga\\Desktop\\ProjectsCurrent\\OffReport\\web";
    }

    public String getPropertie(String name) throws IOException {

        prop = new Properties();
        FR = new FileReader(getCfgFile());
        prop.load(FR);
        String val = prop.getProperty(name);
        return val;

    }

    public void closeFR() throws IOException {
        FR.close();
    }

    public void addPropertie(String name, String val) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.setProperty(name, val);
        FileOutputStream f = new FileOutputStream(getCfgFile());
        p.store(f, "daasdasd");
        f.close();
        p.clear();
    }

    public Document getXml(String path) throws ParserConfigurationException, SAXException, IOException {
        File xml = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public int getCibleA(String id) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {

        int cibleA = 0;
        String path= getCibleFile();
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

    public int getCibleT(String id) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        
        int cibleT = 0;
        String path= getCibleFile();
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

    public String getUrl() {
        return url;
    }

    public String getAppPath() {
        return appPath;
    }

    public String getTempxls() {
        return tempxls;
    }
    public String getCfgFile() {
        return appPath+cfgFile;
    }

    public String getUserFile() {
        return appPath + userFile;
    }

    public String getCibleFile() {
        return appPath + cibleFile;
    }

    public String getExtraFile() {
        return appPath + extraFile;
    }
     public String getGblTempExcel() {
        return appPath + gblTempExcel;
    }

    public String getEmpTempExcel() {
        return appPath + empTempExcel;
    }

    public String getGchTempExcel() {
        return appPath + GchTempExcel;
    }

    public String getEmpServTempExcel() {
        return appPath + empServTempExcel;
    }

    public String getGchServTempExcel() {
        return appPath + GchservTempExcel;
    }

    public String getNdtTempExcel() {
        return appPath + ndtTempExcel;
    }

    public String getGlaTempExcel() {
        return appPath + glaTempExcel;
    }

    public String getGltTempExcel() {
        return appPath + gltTempExcel;
    }

    public String getAplTempExcel() {
        return appPath + aplTempExcel;
    }


}
