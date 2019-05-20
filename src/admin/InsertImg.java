package admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import api.DataLink;

public class InsertImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "pic";
 
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	/**
		 * Constructor of the object.
		 */
	public InsertImg() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		System.out.println("++++++++++request"+request.getParameter("id")+request.getParameter("spu"));
		System.out.println("++++++++++"+session.getAttribute("goodsID")+session.getAttribute("picID"));
    	int spu = Integer.parseInt(session.getAttribute("goodsID").toString());
    	int id = Integer.parseInt((String)session.getAttribute("picID").toString());
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		// 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            System.out.println("表单必须包含 enctype=multipart/form-data");
            json.put("status", "fail");
            json.put("message", "表单必须包含 enctype=multipart/form-data");
            out.write(json.toString());
            out.flush();
            out.close();
            return;
        }
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        //String fileName = new File(item.getName()).getName();
                    	String originFileName = new File(item.getName()).getName();    
                		String fileType = originFileName.substring(originFileName.lastIndexOf("."),originFileName.length()); 
                        String fileName = spu+fileType;
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(item.getName());
                        System.out.println(fileName);
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        // 更新用户头像
                        DataLink dataLink = new DataLink();
                        Connection conn = dataLink.linkData();
                        PreparedStatement stmt = conn.prepareStatement("insert into img(imgID,goodsID,imgSrc) values(?,?,?)");
                        stmt.setInt(1, id);
                        stmt.setInt(2, spu);
                        stmt.setString(3, UPLOAD_DIRECTORY + File.separator + fileName);
                        stmt.executeUpdate();
                        json.put("status", "success");
                        json.put("message", "图片上传成功");
                        out.write(json.toString());
                        out.flush();
                        out.close();
                        System.out.println("图片上传成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}

}
