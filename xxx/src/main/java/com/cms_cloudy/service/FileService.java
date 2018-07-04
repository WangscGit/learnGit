package com.cms_cloudy.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService {
	/**存储上传文件**/
   public void checkPcbFile(MultipartHttpServletRequest request, String filePath);

}
