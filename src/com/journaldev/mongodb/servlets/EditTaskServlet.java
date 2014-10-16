package com.journaldev.mongodb.servlets;
 
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.journaldev.mongodb.dao.MongoDBTaskDAO;
import com.journaldev.mongodb.model.Task;
import com.mongodb.MongoClient;
 
@WebServlet("/editTask")
public class EditTaskServlet extends HttpServlet {
 
  
 


	/**
	 * 
	 */
	private static final long serialVersionUID = -6554920927964049383L;

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for edit operation");
        }
        System.out.println("Task edit requested with id=" + id);
        System.out.println("check1");
        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBTaskDAO taskDAO = new MongoDBTaskDAO(mongo);
        System.out.println("check1");
        Task p = new Task();
      
        p.setId(id);
        p = taskDAO.readTask(p);
        request.setAttribute("task", p);
        
        List<Task> tasks = taskDAO.readAllTask();
        request.setAttribute("tasks", tasks);
 
        RequestDispatcher rd = getServletContext().getRequestDispatcher(
                "/todo.jsp");
        rd.forward(request, response);
    }
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id"); // keep it non-editable in UI
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for edit operation");
        }
 
        String task = request.getParameter("task");
        String status = request.getParameter("status");
        String duedate = request.getParameter("duedate");
        String priority = request.getParameter("priority");
        if ((task == null || task.equals(""))
                || (status == null || status.equals(""))|| (duedate == null || duedate.equals(""))|| (priority == null || priority.equals(""))) {
            request.setAttribute("error", "Task Can't be empty");
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBTaskDAO taskDAO = new MongoDBTaskDAO(mongo);
            Task p = new Task();
            p.setId(id);
            p.setTask(task);
            p.setPriority(priority);
            p.setStatus(status);
            p.setDuedate(duedate);
            request.setAttribute("person", p);
            List<Task> tasks = taskDAO.readAllTask();
            request.setAttribute("tasks", tasks);
 
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/todo.jsp");
            rd.forward(request, response);
        } else {
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBTaskDAO taskDAO = new MongoDBTaskDAO(mongo);
            Task p = new Task();
            p.setId(id);
            p.setTask(task);
            p.setPriority(priority);
            p.setStatus(status);
            p.setDuedate(duedate);
            taskDAO.updateTask(p);
            System.out.println("Task edited successfully with id=" + id);
            request.setAttribute("success", "Task edited successfully");
            List<Task> tasks = taskDAO.readAllTask();
            request.setAttribute("tasks", tasks);
 
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/todo.jsp");
            rd.forward(request, response);
        }
    }
 
}