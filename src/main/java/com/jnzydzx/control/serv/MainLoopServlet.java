package com.jnzydzx.control.serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class MainLoopServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        while (true) {
            System.out.println("MainLoop is running !!");
        }
    }
}
