package com.jyramid.server;

import com.jyramid.config.Application;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JettyApplicationServer extends ApplicationServer {

    Application app;

    /**
     * Instantiates a new server instance for the application.
     *
     * @param app Application
     */
    public JettyApplicationServer(Application app) {
        super(app);
        this.app = app;
    }

    @Override
    public void serve() throws Exception {
        Server server = new Server(8080);
        server.setHandler(new JettyApplicationHandler());
        server.start();
        server.join();
    }

    protected class JettyApplicationHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            try {
                response.getWriter().write(app.getResultForPath(target).toString());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(e.getMessage());
            }
        }

    }

}
