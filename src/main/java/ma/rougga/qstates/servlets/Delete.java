package ma.rougga.qstates.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import ma.rougga.qstates.CfgHandler;
import ma.rougga.qstates.controller.CibleController;
import ma.rougga.qstates.controller.UtilisateurController;
import ma.rougga.qstates.modal.Utilisateur;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Delete extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {
                    String type = request.getParameter("type").trim();
                    if (Objects.equals(type, "cible")) {
                        String id = request.getParameter("id").trim();

                        if (new CibleController().deleteCible(id)) {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Cible est supprimé", "UTF-8"));
                        } else {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Cible n'est supprimé", "UTF-8"));
                        }

                    }
                    if (Objects.equals(type, "user")) {
                        String username = request.getParameter("username").trim();
                        UtilisateurController uc = new UtilisateurController();
                        Utilisateur user = uc.getUtilisateurByUsername(username);
                        if (user != null) {
                            if (uc.deleteUtilisateurById(user.getId())) {

                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Utilisateur est supprimé", "UTF-8"));
                            } else {

                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Utilisateur n'est pas supprimé", "UTF-8"));
                            }
                        } else {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Utilisateur non trouvé", "UTF-8"));
                        }

                    }
                    if (Objects.equals(type, "extra")) {
                        String id = request.getParameter("id").trim();
                        try {
                            CfgHandler cfg = new CfgHandler(request);
                            String path = cfg.getExtraFile(request);
                            //String path = "C:\\Users\\bouga\\Desktop\\OffReport\\web\\cfg\\extra.xml";
                            Document doc = cfg.getXml(path);
                            Node extras = doc.getFirstChild();
                            NodeList nList = extras.getChildNodes();
                            for (int i = 0; i < nList.getLength(); i++) {
                                Node nNode = nList.item(i);
                                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNode;
                                    if (Objects.equals(eElement.getElementsByTagName("id").item(0).getTextContent(), id)) {
                                        extras.removeChild(nNode);
                                    }
                                }

                            }
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);
                            StreamResult result = new StreamResult(new File(path));
                            transformer.transform(source, result);
                            response.sendRedirect("./settings.jsp?err=Extra%20supprime.#userBtn");
                        } catch (IOException | ParserConfigurationException | TransformerException | DOMException | SAXException e) {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
                        }
                    }

                } else {
                    response.sendRedirect("./home.jsp");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
