package org.activiti.rest.editor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.rest.diagram.services.IWorkflowBaseService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cms_cloudy.controller.BaseController;

@Controller
@RequestMapping("/model")
public class ModuleController extends BaseController{
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	private Logger logger = LoggerFactory.getLogger(ModuleController.class);
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IWorkflowBaseService workflowBaseService;
	  /**
	   * 
	   */
	  @RequestMapping(value = "list")
	  public ModelAndView modelList() {
	    ModelAndView mav = new ModelAndView("/modeler");
	    List list = repositoryService.createModelQuery().list();
	    mav.addObject("list", list);
	    return mav;
	  }
	  
	@RequestMapping(value = "create")
	  public void create(@RequestParam("workFlowModelName") String name, @RequestParam("key") String key, @RequestParam("workFlowDescription") String description,
	          HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
	    try {
	    	String encodeName = name;//流程名称加密传输开始
	    	name = name.replace(" ", "+");
	    	encodeName = encodeName.replace(" ", "+");
	    	name = new String(Base64.decodeBase64(name), "UTF-8");//流程名称加密传输结束
	    	ObjectMapper objectMapper = new ObjectMapper();
	      ObjectNode editorNode = objectMapper.createObjectNode();
	      editorNode.put("id", "canvas");
	      editorNode.put("resourceId", "canvas");
	      ObjectNode stencilSetNode = objectMapper.createObjectNode();
	      stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
	      editorNode.put("stencilset", stencilSetNode);
	      Model modelData = repositoryService.newModel();
	 
	      ObjectNode modelObjectNode = objectMapper.createObjectNode();
	      modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
	      modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
	      description = StringUtils.defaultString(description);
	      modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
	      modelData.setMetaInfo(modelObjectNode.toString());
	      modelData.setName(name);
	      modelData.setKey(StringUtils.defaultString(key));
	 
	      repositoryService.saveModel(modelData);
	      repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	      response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId()+"&formType="+key+"&presName="+encodeName);
	    } catch (Exception e) {
	      logger.error("创建模型失败：", e);
	    }
	  }
	 /**
	   * 根据Model部署流程
	   */
	  @RequestMapping(value = "deploy/{modelId}")
	  public String deploy(@PathVariable("modelId") String modelId, HttpServletRequest req) {
	    try {
	      Model modelData = processEngine.getRepositoryService().getModel(modelId);
	      ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(processEngine.getRepositoryService().getModelEditorSource(modelData.getId()));
	      byte[] bpmnBytes = null;
	      BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
	      bpmnBytes = new BpmnXMLConverter().convertToXML(model);
	      String processName = modelData.getName() + ".bpmn20.xml";
//	      String bytes=new String(bpmnBytes);
	      String bytes = new String(bpmnBytes,"UTF-8");
	      Deployment deployment = processEngine.getRepositoryService().createDeployment().name(modelData.getName()).addString(processName,bytes).deploy();
          //生成流程设置界面的流程图片-----------------------
	       viewPic(deployment.getId(),req);
//	      redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
	     return deployment.getId();
	    } catch (Exception e) {
	      logger.error("根据模型部署流程失败：modelId={}", modelId, e);
	    }
	    return "";
	  }
	  /**
	   * 生成图片
	   * @param deploymentId
	   */
		public void viewPic(String deploymentId,HttpServletRequest req){
	    	//获得图片资源名称
	    	List<String> list = processEngine.getRepositoryService()
	    			                                              .getDeploymentResourceNames(deploymentId);
	        //定义图片资源名称
	    	String resourceName = "";
	    	if(list != null && list.size()>0){
	    		for(String name : list){
	    			 if(name.indexOf(".png") >= 0){
	    				 resourceName = name;
	    			 }
	    		}
	    	}
	    	//获得图片的输入流
	    	InputStream in = processEngine.getRepositoryService()
	    			                                             .getResourceAsStream(deploymentId, resourceName);
	    	//定义图片存放位置
	    	 String path1 = req.getSession().getServletContext().getRealPath("");
	         String path = path1+"\\"+"workflowImg";
	         System.out.println(path);
	    	File file = new File(path+File.separator+resourceName);
	    	try {
	    		//将输入流读写到磁盘下
				FileUtils.copyInputStreamToFile(in,file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	  /**
	   * 导出model的xml文件
	   */
	  @RequestMapping(value = "export/{modelId}")
	  public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
	    try {
	      Model modelData = repositoryService.getModel(modelId);
	      BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
	      JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
	      BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
	      exportAndReadXMLFile(bpmnModel);
	      BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
	      byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
	      ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
	      IOUtils.copy(in, response.getOutputStream());
	      String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
	      response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	      response.flushBuffer();
	    } catch (Exception e) {
	      logger.error("导出model的xml文件失败：modelId={}", modelId, e);
	    }
	  }
	  /**
	     * 删除部署的流程，级联删除流程实例
	     *
	     * @param deploymentId 流程部署ID
	     */
	    @RequestMapping(value = "/delete-deployment")
	    public String deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId) {
	        repositoryService.deleteDeployment(deploymentId, true);
	        return "redirect:process-list";
	    }

//	    protected BpmnModel readXMLFile() throws Exception {
//	      InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream(getResource());
//	      XMLInputFactory xif = XMLInputFactory.newInstance();
//	      InputStreamReader in = new InputStreamReader(xmlStream, "UTF-8");
//	      XMLStreamReader xtr = xif.createXMLStreamReader(in);
//	      return new BpmnXMLConverter().convertToBpmnModel(xtr);
//	    }
	    protected BpmnModel exportAndReadXMLFile(BpmnModel bpmnModel) throws Exception {
	      byte[] xml = new BpmnXMLConverter().convertToXML(bpmnModel);
	      System.out.println("xml " + new String(xml, "UTF-8"));
	      XMLInputFactory xif = XMLInputFactory.newInstance();
	      InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(xml), "UTF-8");
	      XMLStreamReader xtr = xif.createXMLStreamReader(in);
	      return new BpmnXMLConverter().convertToBpmnModel(xtr);
	    }

		public IWorkflowBaseService getWorkflowBaseService() {
			return workflowBaseService;
		}

		public void setWorkflowBaseService(IWorkflowBaseService workflowBaseService) {
			this.workflowBaseService = workflowBaseService;
		}

}
