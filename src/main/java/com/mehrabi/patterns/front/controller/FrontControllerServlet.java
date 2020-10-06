package com.mehrabi.patterns.front.controller;

import com.mehrabi.patterns.front.controller.commands.FrontCommand;
import com.mehrabi.patterns.front.controller.commands.UnknownCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontControllerServlet extends HttpServlet {
    @Override
    protected void doGet (
      HttpServletRequest request,
      HttpServletResponse response
    ) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        try {
            command.process();
        }catch (Exception ignored){

        }
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            Class<?> type = Class.forName(
              String.format(
                "com.mehrabi.patterns.front.controller.commands.%sCommand",
                request.getParameter("command")
              )
            );
            return  type
              .asSubclass(FrontCommand.class)
              .getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }
}
