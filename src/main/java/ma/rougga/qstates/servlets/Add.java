package ma.rougga.qstates.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
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
import ma.rougga.qstates.PasswordAuthentication;
import ma.rougga.qstates.controller.AgenceController;
import ma.rougga.qstates.controller.CibleController;
import ma.rougga.qstates.controller.ServiceController;
import ma.rougga.qstates.controller.UtilisateurController;
import ma.rougga.qstates.modal.Cible;
import ma.rougga.qstates.modal.Service;
import ma.rougga.qstates.modal.Utilisateur;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Add extends HttpServlet {

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
                        String id = request.getParameter("service");
                        String cibleAH = request.getParameter("cibleAH");
                        String cibleAM = request.getParameter("cibleAM");
                        String cibleAS = request.getParameter("cibleAS");

                        String cibleTH = request.getParameter("cibleTH");
                        String cibleTM = request.getParameter("cibleTM");
                        String cibleTS = request.getParameter("cibleTS");

                        String cibleD = request.getParameter("cibleD");
                        if (StringUtils.isNoneBlank(id, cibleAH, cibleAM, cibleAS, cibleTH, cibleTM, cibleTS, cibleD)) {

                            if (!Objects.equals(id, "0")) {
                                int cibleA = (Integer.parseInt(cibleAH) * 3600) + (Integer.parseInt(cibleAM) * 60) + Integer.parseInt(cibleAS);
                                int cibleT = (Integer.parseInt(cibleTH) * 3600) + (Integer.parseInt(cibleTM) * 60) + Integer.parseInt(cibleTS);

                                Service service = new ServiceController().getById(id);
                                if (service != null) {
                                    Cible cible = new Cible();
                                    cible.setServiceId(id);
                                    cible.setServiceName(service.getName());
                                    cible.setCibleA(cibleA);
                                    cible.setCibleT(cibleT);
                                    cible.setCiblePer(Double.parseDouble(cibleD));
                                    new CibleController().addCible(cible);
                                }
                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Cible est ajouté!", "UTF-8"));
                            } else {
                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Erreur sur les données", "UTF-8"));
                            }

                        } else {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("les champs vide", "UTF-8"));
                        }

                    } else if (Objects.equals(type, "user")) {
                        String username = request.getParameter("username");
                        String password = request.getParameter("password");
                        String password2 = request.getParameter("password2");
                        String grade = request.getParameter("grade");
                        String firstName = request.getParameter("firstName");
                        String lastName = request.getParameter("lastName");

                        if (StringUtils.isNoneBlank(username, password, password2, grade, firstName, lastName)) {
                            if (Objects.equals(password, password2)) {
                                Utilisateur user = new Utilisateur();
                                PasswordAuthentication pa = new PasswordAuthentication();
                                user.setId(UUID.randomUUID());
                                user.setUsername(username);
                                user.setLastName(lastName);
                                user.setFirstName(firstName);
                                user.setPassword(pa.hash(password.toCharArray()));
                                user.setDate(new Date());
                                user.setSponsor(request.getSession().getAttribute("user").toString());
                                user.setGrade(grade);
                                if (new UtilisateurController().AddUtilisateur(user)) {
                                    response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Utilisateur ajouté", "UTF-8"));
                                } else {
                                    response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Utilisateur n'est ajouté", "UTF-8"));
                                }
                            } else {
                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("les deux mots de passe est incorrect", "UTF-8"));
                            }
                        } else {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("les champs vide", "UTF-8"));
                        }

                    } else if (Objects.equals(type, "extra")) {
                        String id = request.getParameter("serviceNameExtra");
                        String extraH = request.getParameter("extraH");
                        String extraM = request.getParameter("extraM");
                        String extraS = request.getParameter("extraS");

                        if (id != null && extraH != null && extraM != null && extraS != null) {
                            if (!Objects.equals(id, "0")) {
                                int extra = (Integer.parseInt(extraH) * 3600) + (Integer.parseInt(extraM) * 60) + Integer.parseInt(extraS);
                                try {
                                    CfgHandler cfg = new CfgHandler(request);
                                    String path = cfg.getExtraFile(request);

                                    Document doc = cfg.getXml(path);
                                    Node extras = doc.getFirstChild();

                                    Element service = doc.createElement("service");
                                    extras.appendChild(service);

                                    Element idE = doc.createElement("id");
                                    idE.appendChild(doc.createTextNode(id));
                                    service.appendChild(idE);

                                    Element nameE = doc.createElement("name");
                                    ServiceController sc = new ServiceController();
                                    String serviceName = "service ?";
                                    Service s = sc.getById(String.valueOf(id));
                                    if (s != null) {
                                        serviceName = s.getName();
                                    }
                                    nameE.appendChild(doc.createTextNode(serviceName));

                                    service.appendChild(nameE);

                                    Element extraE = doc.createElement("extra");
                                    extraE.appendChild(doc.createTextNode(extra + ""));
                                    service.appendChild(extraE);

                                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                    Transformer transformer = transformerFactory.newTransformer();
                                    DOMSource source = new DOMSource(doc);
                                    StreamResult result = new StreamResult(new File(path));
                                    transformer.transform(source, result);
                                    response.sendRedirect("./settings.jsp?err=Extra%20ajoute.");

                                } catch (Exception e) {
                                    response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
                                }

                            }

                        }

                    } else if (Objects.equals(type, "goal")) {
                        String maxA = request.getParameter("maxA");
                        String goalT = request.getParameter("goalT");
                        if (StringUtils.isNoneBlank(maxA, goalT)) {
                            AgenceController.updateParameter("max_a", maxA);
                            AgenceController.updateParameter("goal_t", goalT);
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("L'objectif est modifiée", "UTF-8"));
                        } else {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("L'objectif n'est modifiée", "UTF-8"));
                        }
                    } else {
                        response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Erreur", "UTF-8"));
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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
