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
 
@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
 
   
 
  

	
	private static final long serialVersionUID = -887057758479978748L;

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for delete operation");
        }
        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBTaskDAO personDAO = new MongoDBTaskDAO(mongo);
        Task p = new Task();
        p.setId(id);
        personDAO.deleteTask(p);
        System.out.println("Task deleted successfully with id=" + id);
        request.setAttribute("success", "Task deleted successfully");
        List<Task> tasks = personDAO.readAllTask();
        request.setAttribute("tasks", tasks);
 
        RequestDispatcher rd = getServletContext().getRequestDispatcher(
                "/todo.jsp");
        rd.forward(request, response);
    }
 
}