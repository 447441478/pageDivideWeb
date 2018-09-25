package cn.hncu.Pet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.Pet.service.IPetService;
import cn.hncu.Pet.service.PetService;
import cn.hncu.domain.Pet;
import cn.hncu.pub.BaseServlet;

@WebServlet("/PetServlet")
public class PetServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// 注入service
	private IPetService service = new PetService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取用户所要查询的页
		String strCurrentPage = request.getParameter("currentPage");
		int currentPage = 1;
		try {
			currentPage = Integer.valueOf(strCurrentPage);
		} catch (NumberFormatException e) {
			// 这里能出异常一般是用户黑服务器
			//e.printStackTrace();
		}
		try {
			// 一页的记录数
			int size = 10;
			// 查询出 countPage 和 datas
			Map<String, Object> result = service.queryByCurrentPage(currentPage, size);
			// 获取总的页数
			int countPage = (int) result.get("countPage");

			// 需要显示的页码的个数
			int showLen = 10;
			// 获得起始页码，和结束页码
			Map<String, Integer> show = getShowSatrAndShowEnd(currentPage, showLen, countPage);
			
			// 把存放showStart和showEnd的  'Map' 放入 request 容器中
			request.setAttribute("show", show);

			// 再把当前页封装过去
			result.put("currentPage", currentPage);
			// 把结果放入 request 容器中
			request.setAttribute("result", result);
			// 请求转发
			request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			// 按理这样应该重定向到错误页面，这里就算了。
			throw new RuntimeException(e);
		}

	}

	/**
	 * 模糊查询
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @throws ServletException
	 * @throws IOException
	 */
	public void likeQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户所要查询的页
		String strCurrentPage = request.getParameter("currentPage");
		int currentPage = 1;
		try {
			currentPage = Integer.valueOf(strCurrentPage);
		} catch (NumberFormatException e) {
			// 这里能出异常一般是用户黑服务器
			//e.printStackTrace();
		}
		//获取查询条件
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		Pet pet = new Pet();
		pet.setName(name);
		pet.setColor(color);
		request.getSession().setAttribute("pet", pet);
		try {
			// 一页的记录数
			int size = 10;
			// 查询出 countPage 和 datas
			Map<String, Object> result = service.likeQueryByCurrentPage(currentPage, size,pet);
			// 获取总的页数
			int countPage = (int) result.get("countPage");

			// 需要显示的页码的个数
			int showLen = 10;
			// 获得起始页码，和结束页码
			Map<String, Integer> show = getShowSatrAndShowEnd(currentPage, showLen, countPage);
			
			// 把存放showStart和showEnd的  'Map' 放入 request 容器中
			request.setAttribute("show", show);

			// 再把当前页封装过去
			result.put("currentPage", currentPage);
			
			// 封装查询串并放入 request 容器中
			String queryString = "action=likeQuery&name="+name+"&color="+color+"&";
			request.setAttribute("queryString", queryString);
			
			// 把结果放入 request 容器中
			request.setAttribute("result", result);
			// 请求转发
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			// 按理这样应该重定向到错误页面，这里就算了。
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获得显示页的起始，和显示页的结束
	 * @param currentPage 当前页
	 * @param showLen 显示页码的个数
	 * @param countPage 总的页数
	 * @return 封装了showStart和showEnd的Map集合
	 */
	private Map<String, Integer> getShowSatrAndShowEnd( int currentPage,int showLen,int countPage ){
		HashMap<String, Integer> show = new HashMap<String, Integer>();
		// 初始化起始页码，和结束页码
		int showStart = 1;
		int showEnd = 1;
		// 当 '所需显示的页码个数' 大于等于 '总的页数' 时
		if (showLen >= countPage) {
			showStart = 1; // 起始页为1
			showEnd = countPage; // 结束页为总的页数
		} else {
			// 当 '当前页' 小于等于 '所需显示的页码个数'的一半时
			if (currentPage <= showLen / 2) {
				showStart = 1; // 起始页为1
				showEnd = showLen; // 结束页为 '所需显示的页码个数'
			} else if (currentPage > countPage - showLen / 2) {
				// 当 '当前页' 大于 '总的页数'-('所需显示的页码个数'的一半)时
				showEnd = countPage;
				showStart = countPage - showLen + 1;
			} else {
				showStart = currentPage - showLen / 2;
				showEnd = showStart + showLen - 1;
			}
		}
		show.put("showStart", showStart);
		show.put("showEnd", showEnd);
		return show;
	}

}
