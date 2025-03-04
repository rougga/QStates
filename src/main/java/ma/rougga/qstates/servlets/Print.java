package ma.rougga.qstates.servlets;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qstates.Export;

public class Print extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            if (Objects.equals(request.getSession().getAttribute("user"), null)) {
                response.sendRedirect("./index.jsp");
            } else {
                String type = request.getParameter("type").trim();
                String format = request.getParameter("format").trim().toLowerCase();
                String date1 = request.getParameter("date1").trim();
                String date2 = request.getParameter("date2").trim();
                if (Objects.equals(type, "gbl")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGblExcel(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                            
                        }
                    }//GBL XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportGblPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//GBL PDF
                }//GBL
                else if (Objects.equals(type, "emp")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportEmpExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//EMP XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportEmpPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//EMP PDF
                }//EMP
                else if (Objects.equals(type, "empser")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportEmpServiceExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//EMP XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportEmpServicePDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//EMP PDF
                }//EMPSER
                else if (Objects.equals(type, "gch")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGchExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//GCH XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportGchPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//GCH PDF
                }//GCH
                else if (Objects.equals(type, "gchserv")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGchServiceExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//EMP XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportGchServicePDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//EMP PDF
                }//GCHSER
                else if (Objects.equals(type, "ndt")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdtExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDT XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportNdtPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDT PDF

                }//NDT
                else if (Objects.equals(type, "ndtt")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdttExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTT XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportNdttPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTT PDF

                }//NDTT
                else if (Objects.equals(type, "ndta")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdtaExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTA XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportNdtaPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTA PDF

                }//NDTA
                else if (Objects.equals(type, "ndtsa")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportNdtsaExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportNdtsaPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA PDF

                }//NDTSA
                else if (Objects.equals(type, "gla")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGlaExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportGlaPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA PDF

                }//GLA
                else if (Objects.equals(type, "glt")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportGltExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportGltPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//NDTSA PDF

                }//GLT
                else if (Objects.equals(type, "apl")) {
                    if (Objects.equals(format, "excel")) {
                        if (new Export().exportAplExcel(response, request, date1, date2) == null) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//APL XL
                    else if (Objects.equals(format, "pdf")) {
                        if (new Export().exportAplPDF(response, request, date1, date2) == 0) {
                            response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                        }
                    }//APL PDF

                }//APL
                else {
                    response.sendRedirect("./report.jsp?type=" + type + "&date1=" + date1 + "&date2=" + date2 + "&err=erreur%20dans%20impression");
                    
                }
            }
        } catch (Exception e) {
            response.sendRedirect("./report.jsp?err=" + e.getMessage());
        }
    }

}
