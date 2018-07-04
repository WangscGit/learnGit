/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.rest.editor.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.diagram.pojo.WorlkflowMainEntity;
import org.activiti.rest.diagram.services.IWorkflowBaseService;
import org.activiti.rest.editor.ModuleController;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cms_cloudy.controller.BaseController;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.util.ProjectConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
@RestController
public class ModelSaveRestResource extends BaseController implements ModelDataJsonConstants {
  
  protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

  @Autowired
  private RepositoryService repositoryService;
  @Autowired
	private IWorkflowBaseService workflowBaseService;
  @Autowired
  private ObjectMapper objectMapper;
  
  @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
  @ResponseStatus(value = HttpStatus.OK)
  public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values,HttpServletRequest req) {
    try {
      Model model = repositoryService.getModel(modelId);
      
      ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
      
      modelJson.put(MODEL_NAME, values.getFirst("name"));
      modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
      model.setMetaInfo(modelJson.toString());
      model.setName(values.getFirst("name"));
      
      repositoryService.saveModel(model);
      String uuu = new String( values.getFirst("json_xml").getBytes("utf-8"));
      System.out.println(uuu);
      String uuu1 = new String( values.getFirst("svg_xml").getBytes("utf-8"));
      System.out.println(uuu1);
      repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));
      
      InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
      TranscoderInput input = new TranscoderInput(svgStream);
      
      PNGTranscoder transcoder = new PNGTranscoder();
      // Setup output
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      TranscoderOutput output = new TranscoderOutput(outStream);
      
      // Do the transformation
      transcoder.transcode(input, output);
      final byte[] result = outStream.toByteArray();
      String result1 = new String(result);
      System.out.println(result1);
      repositoryService.addModelEditorSourceExtra(model.getId(), result);
      outStream.close();
      ModuleController models = new ModuleController();
      String deploymentId = models.deploy(modelId, req);
      //������̶������� _----_��������ȡ����_----_
      List<ProcessDefinition> processList = workflowBaseService.selectProcessDefinitionList(deploymentId);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
      HrUser user = getUserInfo(req);
     if(null != processList && processList.size()>0){
	      WorlkflowMainEntity processEntity = new WorlkflowMainEntity();
	      processEntity.setProcessDefinitionId(processList.get(0).getId());
	      processEntity.setProcessKey(processList.get(0).getKey());
	      processEntity.setProcessName(processList.get(0).getName());
	      processEntity.setProcessVersion(processList.get(0).getVersion()+"");
	      processEntity.setCdefine1(processList.get(0).getResourceName());//xml���
	      processEntity.setCdefine2(processList.get(0).getDiagramResourceName());//ͼƬ���
	      processEntity.setModelId(modelId);
	      if(null != user && StringUtils.isNotEmpty(user.getLoginName())){
		      processEntity.setProcessCreatePerson(user.getLoginName());
	      }else{
	    	  processEntity.setProcessCreatePerson("");
	      }
	      processEntity.setCdefine3(processList.get(0).getKey().equals("partProcess")?"0":processList.get(0).getKey());
	      processEntity.setProcessCreateTime(sdf.format(new Date()));
	      processEntity.setDeploymentId(deploymentId);
	      processEntity.setProcessState(ProjectConstants.PROCESS_STATE_WEIQIDONG);
	      workflowBaseService.insertWorkflowMain(processEntity);
     }
    } catch (Exception e) {
      LOGGER.error("Error saving model", e);
      throw new ActivitiException("Error saving model", e);
    }
  }
}
