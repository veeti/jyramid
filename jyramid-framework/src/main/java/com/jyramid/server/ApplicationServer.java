package com.jyramid.server;

import com.jyramid.config.Application;

public abstract class ApplicationServer {

    /**
     * Instantiates a new server instance for the application.
     * @param app Application
     */
    public ApplicationServer(Application app) { };

    /**
     * Starts the server.
     * @throws Exception Exception
     */
    public abstract void serve() throws Exception;

}
