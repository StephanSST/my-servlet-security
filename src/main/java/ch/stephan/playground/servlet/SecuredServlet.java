package ch.stephan.playground.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.keycloak.KeycloakSecurityContext;

@WebServlet(name = "SecuredServlet", urlPatterns = "/service", loadOnStartup = 1)
@ServletSecurity(@HttpConstraint(rolesAllowed = {"RACFPrefixDT10WPC", "AuthenticatedRole"}))
public class SecuredServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String TD = "<td>";
  private static final String END_TD = "</td>";
  private static final String TR = "<tr>";
  private static final String END_TR = "</tr>";

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    httpServletResponse.setContentType("text/html");
    PrintWriter printWriter = httpServletResponse.getWriter();
    printWriter.println("<html><body>");

    printWriter.println("<h1>OIDC Keycloak/RH SSO Test Servlet</h1>");

    printWriter.println("<p>");
    printWriter.println("Working with Keycloak at URL [" + System.getProperty("baloise.keycloak.realm.url") + "].");
    printWriter.println("</p>");

    printWriter.println("<p>");
    printWriter.println("<b>OIDC access token: </b><br>" + getAccessToken(httpServletRequest));
    printWriter.println("</p>");
    
    printWriter.println("<p>");
    printWriter.println("<b>Environment variables and System properties: </b><br>" + showEnvVars());
    printWriter.println("</p>");

    printWriter.println("</body></html>");
  }

  private String showEnvVars() {
    StringBuilder builder = new StringBuilder();
    builder.append("<table>");
    builder.append(TR + getEnvVar("clientsecret_sst_test") + END_TR);

    System.getenv().keySet().stream()//
        .forEach(e -> builder.append(TR + getEnvVar(e) + END_TR));

    builder.append("</table>");
    return builder.toString();
  }

  private String getEnvVar(String key) {
    return TD + "env." + key + END_TD + TD + System.getenv(key) + END_TD;
  }

  private String getAccessToken(HttpServletRequest httpServletRequest) {
    KeycloakSecurityContext context = (KeycloakSecurityContext) httpServletRequest.getAttribute(KeycloakSecurityContext.class.getName());
    if (context == null) {
      return null;
    }
    return "Bearer " + context.getTokenString();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    System.out.println("Servlet erkannt, init aufgerufen!");
    super.init(config);
  }

}
