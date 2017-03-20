package zjicm.xmt130806228.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import zjicm.xmt130806228.service.StudentService;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.opensymphony.xwork2.ActionSupport;
/*
 * 缃戦〉涓婁紶excel鏂囦欢
 * 瑙ｆ瀽骞跺湪鏁版嵁搴撲腑鎻掑叆鏁版嵁
 * 娉ㄦ剰璇诲彇娴佺▼鐨勮�鍚堝拰鏄撻厤缃▼搴�
 */
public class StudentImportAction extends ActionSupport{
	String info;
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	//涓婁紶鏂囦欢鐨勫弬鏁版牸寮�
	private File xls;
    private String xlsFileName;
    private String xlsContentType;
	public File getXls() {
		return xls;
	}
	public void setXls(File xls) {
		this.xls = xls;
	}
	public String getXlsFileName() {
		return xlsFileName;
	}
	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}
	public String getXlsContentType() {
		return xlsContentType;
	}
	public void setXlsContentType(String xlsContentType) {
		this.xlsContentType = xlsContentType;
	}

	@Autowired
	StudentService ss;
	
   
	public String execute() throws Exception{
		
		
        //鍒涘缓杈撳叆娴�
        InputStream is = new FileInputStream(xls);
        //鍒涘缓鐖剁洰褰�
        String root = ServletActionContext.getServletContext().getRealPath("/WEB-INF/stuInfo");
        if (!new File(root).exists()) { // 濡傛灉璺緞涓嶅瓨鍦紝鍒涘缓  
        	new File(root).mkdirs();
        }  
        
        //鍒涘缓杈撳嚭娴佷互鍙婄洰鐨勬枃浠�
        System.out.println("XlsFileName:"+xlsFileName);
        String suffix = xlsFileName.substring(xlsFileName.lastIndexOf('.'));
        System.out.println("suffix:"+suffix);
        String pre = Long.valueOf(new Date().getTime()).toString();
        String saveFileName = pre+suffix;
        System.out.println(saveFileName);
        OutputStream os = new FileOutputStream(new File(root,saveFileName));
        System.out.println("fileFileName: " + xlsFileName);
      
        byte[] buffer = new byte[1024];
        int length = 0;
        
        while(-1 != (length = is.read(buffer)))
        {
            os.write(buffer,0,length);
        }
        
        os.close();
        is.close();
        try{
        	info = ss.importStu(xls);
        }catch(Exception e){
        	e.printStackTrace();
        	info ="info ";
        }
        
		return SUCCESS;
		
        
    }
	
}


