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
 
@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {
 
    
 
	private static final long serialVersionUID = 1439259047540812257L;

	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String task = request.getParameter("task");
        String status = request.getParameter("status");
        String duedate = request.getParameter("duedate");
        String priority = request.getParameter("priority");
        if ((task == null || task.equals(""))
                || (status == null || status.equals(""))|| (duedate == null || duedate.equals(""))|| (priority == null || priority.equals(""))) {
            request.setAttribute("error", "Mandatory Parameters Missing");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/todo.jsp");
            rd.forward(request, response);
        } else {
            Task p = new Task();
            p.setTask(task);
            p.setStatus(status);
            p.setDuedate(duedate);
            p.setPriority(priority);
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBTaskDAO taskDAO = new MongoDBTaskDAO(mongo);
            taskDAO.createTask(p);
            System.out.println("Task Added Successfully with id="+p.getId());
            request.setAttribute("success", "Task Added Successfully");
            List<Task> tasks = taskDAO.readAllTask();
            request.setAttribute("tasks", tasks);
 
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/todo.jsp");
            rd.forward(request, response);
        }
    }
 
}