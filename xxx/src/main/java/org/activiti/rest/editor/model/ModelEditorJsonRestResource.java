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

//import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
@RestController
public class ModelEditorJsonRestResource implements ModelDataJsonConstants {
  
  protected static final Logger LOGGER = LoggerFactory.getLogger(ModelEditorJsonRestResource.class);
  
  @Autowired
  private RepositoryService repositoryService;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @RequestMapping(value="/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
  public ObjectNode getEditorJson(@PathVariable String modelId) {
    ObjectNode modelNode = null;
    
    Model model = repositoryService.getModel(modelId);
      
    if (model != null) {
      try {
        if (StringUtils.isNotEmpty(model.getMetaInfo())) {
          modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
        } else {
          modelNode = objectMapper.createObjectNode();
          modelNode.put(MODEL_NAME, model.getName());
        }
        modelNode.put(MODEL_ID, model.getId());
        ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
            new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
        modelNode.put("model", editorJsonNode);
        
      } catch (Exception e) {
        LOGGER.error("Error creating model JSON", e);
        throw new ActivitiException("Error creating model JSON", e);
      }
    }
    return modelNode;
  }
//  public void jjlin(){
//	  ObjectMapper objectMapper = new ObjectMapper();
//      String modelId = (String) getRequest().getAttributes().get("modelId");
//      // System.out.println("json " + modelForm.getFirstValue("json_xml"));
//
//      try {
//
//          /*
//           * ObjectNode modelNode = (ObjectNode)
//           * objectMapper.readTree(modelForm.getFirstValue("json_xml"));
//           * JsonToBpmnExport converter = new JsonToBpmnExport(modelNode);
//           * byte[] bpmnBytes = converter.convert();
//           * System.out.println("bpmn " + new String(bpmnBytes));
//           */
//
//          RepositoryService repositoryService = ProcessEngines
//                  .getDefaultProcessEngine().getRepositoryService();
//          Model model = repositoryService.getModel(modelId);
//
//          ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model
//                  .getMetaInfo());
//
//          modelJson.put(MODEL_NAME, modelForm.getFirstValue("name"));
//          modelJson.put(MODEL_DESCRIPTION,
//                  modelForm.getFirstValue("description"));
//          model.setMetaInfo(modelJson.toString());
//          model.setName(modelForm.getFirstValue("name"));
//
//                   
//          byte[] svg_xml=modelForm.getFirstValue("svg_xml").getBytes("UTF-8");
//       
//           
//          //转成UTF-8 然后使用base64编码
//          String gbk=new String(modelForm.getFirstValue("json_xml").getBytes("GBK"));
//          String iso = new String(gbk.getBytes("UTF-8"),"ISO-8859-1");
//          String utf8=new String(iso.getBytes("ISO-8859-1"),"UTF-8");
//          byte[]  json_xml=new BASE64Encoder().encode(utf8.getBytes("UTF-8")).getBytes();
//          repositoryService.addModelEditorSource(model.getId(),json_xml);
//           
//           
//           
//          InputStream svgStream = new ByteArrayInputStream(svg_xml);
//          TranscoderInput input = new TranscoderInput(svgStream);
//
//          PNGTranscoder transcoder = new PNGTranscoder();
//          // Setup output
//          ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//          TranscoderOutput output = new TranscoderOutput(outStream);
//
//          // Do the transformation
//          transcoder.transcode(input, output);
//          final byte[] result = outStream.toByteArray();
//          repositoryService.addModelEditorSourceExtra(model.getId(), result);
//          outStream.close();
//               
//          repositoryService.saveModel(model);
//           
//      } catch (Exception e) {
//          LOGGER.error("Error saving model", e);
//          setStatus(Status.SERVER_ERROR_INTERNAL);
//      }
//  }
//  }
}
