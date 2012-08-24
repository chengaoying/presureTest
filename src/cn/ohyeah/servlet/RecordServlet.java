package cn.ohyeah.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ohyeah.thread.ThreadManager;

@SuppressWarnings("serial")
public class RecordServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url = req.getParameter("url");
		System.out.println("url: "+url);
		int threadcount = Integer.parseInt(String.valueOf(req.getParameter("threadcount")));
		
		long t1 = System.currentTimeMillis();
		ThreadManager tm = new ThreadManager(threadcount, url, 11);
		
		int count = 0;
		try {
			count = tm.multiThreadTest();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		req.setAttribute("threadcount", threadcount);
		req.setAttribute("count", count);
		req.setAttribute("lastSeconds", (System.currentTimeMillis()-t1)/1000);
		req.getRequestDispatcher("result.jsp").forward(req, resp);
	}

}
