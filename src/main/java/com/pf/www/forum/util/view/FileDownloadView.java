package com.pf.www.forum.util.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/* SpringMVC에서 파일 다운로드 기능을 위한 커스텀 뷰 클래스 생성
 * 
 * SpringFramwork에 AbstractView 클래스를 상속한 뷰 구현체를 사용하는 메서드를 이용하자.
 * 이 메서드의 기능은 모델 데이터를 뷰에 렌더링하는데 사용된다, SpringMvc 에서 JSP, Thymeleaf, JSON
 * 등을 렌더링 할때 이 메서드를 사용해서 내가 원하는 방식으로 렌더링 가능하다.*/
public class FileDownloadView extends AbstractView{
	private final static Logger log = LoggerFactory.getLogger(FileDownloadView.class);
	
	public FileDownloadView() {
		
		/* 생성자에서 setContentType 메서드를 호출해서 MIME 타입을 설정하자 */
		// TODO Auto-generated constructor stub
		setContentType("application/download; charset-UTF-8");
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// TODO Auto-generated method stub
		/* 모델에서 파일 정보 가져오기 */
		Map<String, Object> fileInfo = (Map<String, Object>) model.get("fileInfo");
		
		log.info(" renderMergedOutputModel :: downloadFile :" + fileInfo.get("downloadFile") +  ", orgFileNm : " + fileInfo.get("orgFileNm"));
		
		File file = (File) fileInfo.get("downloadFile");
		String orgFileNm = (String) fileInfo.get("orgFileNm");
		boolean ZipFile = (boolean)fileInfo.get("ZipFile");
		
		// 응답 헤더 설정
		response.setContentType(getContentType());
		response.setContentLength((int) file.length()); // 파일의 길이를 응답의 콘텐츠 길이로 설정정
		
		/* 사용자 에이전트 확인 */
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1; // Internet Explorer 인지 판단
		String fileNm = null;
	
		/* 인터넷 창에 따라 파일이름 인코딩 */
		fileNm = ie ? URLEncoder.encode(orgFileNm, "UTF-8") : new String(orgFileNm.getBytes("UTF-8"), "ISO-8859-1");
		
		response.setHeader("Content-Disposition",  "attachment; filename=\"" + fileNm + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary"); // 이진 파일 전송 명시
		
		// 파일 읽어와서 응답(response)에 보내기		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		try {
			/* 파일 입력 스트림 생성하고 파일을 읽어서 응답 출력 스트림에 복사 */
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
			
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException Ie) {
					// TODO: handle exception
				}
			}
			out.flush();
		}
		
		if(ZipFile) {
			file.delete(); 
		}
	}

}
