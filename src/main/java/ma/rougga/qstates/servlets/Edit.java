package ma.rougga.qstates.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.controller.CibleController;
import ma.rougga.qstates.controller.ServiceController;
import ma.rougga.qstates.modal.Cible;
import ma.rougga.qstates.modal.Service;
import org.apache.commons.lang3.StringUtils;

public class Edit extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                if (Objects.equals(request.getSession().getAttribute("grade"), "adm")) {

                    String type = request.getParameter("type").trim();
                    if (Objects.equals(type, "cible")) {
                        String id = request.getParameter("service").trim();
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
                                    new CibleController().updateCible(cible);
                                }
                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Cible est modifié!", "UTF-8"));
                            } else {
                                response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("Erreur sur les données", "UTF-8"));
                            }

                        } else {
                            response.sendRedirect("./settings.jsp?err=" + URLEncoder.encode("les champs vide", "UTF-8"));
                        }
                    }

                }else{
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
