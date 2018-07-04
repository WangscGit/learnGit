package org.activiti.rest.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.converter.AssociationXMLConverter;
import org.activiti.bpmn.converter.BaseBpmnXMLConverter;
import org.activiti.bpmn.converter.BoundaryEventXMLConverter;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.converter.BusinessRuleTaskXMLConverter;
import org.activiti.bpmn.converter.CallActivityXMLConverter;
import org.activiti.bpmn.converter.CatchEventXMLConverter;
import org.activiti.bpmn.converter.ComplexGatewayXMLConverter;
import org.activiti.bpmn.converter.DataStoreReferenceXMLConverter;
import org.activiti.bpmn.converter.EndEventXMLConverter;
import org.activiti.bpmn.converter.EventGatewayXMLConverter;
import org.activiti.bpmn.converter.ExclusiveGatewayXMLConverter;
import org.activiti.bpmn.converter.InclusiveGatewayXMLConverter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.converter.ManualTaskXMLConverter;
import org.activiti.bpmn.converter.ParallelGatewayXMLConverter;
import org.activiti.bpmn.converter.ReceiveTaskXMLConverter;
import org.activiti.bpmn.converter.ScriptTaskXMLConverter;
import org.activiti.bpmn.converter.SendTaskXMLConverter;
import org.activiti.bpmn.converter.SequenceFlowXMLConverter;
import org.activiti.bpmn.converter.ServiceTaskXMLConverter;
import org.activiti.bpmn.converter.StartEventXMLConverter;
import org.activiti.bpmn.converter.TaskXMLConverter;
import org.activiti.bpmn.converter.TextAnnotationXMLConverter;
import org.activiti.bpmn.converter.ThrowEventXMLConverter;
import org.activiti.bpmn.converter.UserTaskXMLConverter;
import org.activiti.bpmn.converter.ValuedDataObjectXMLConverter;
import org.activiti.bpmn.converter.alfresco.AlfrescoStartEventXMLConverter;
import org.activiti.bpmn.converter.alfresco.AlfrescoUserTaskXMLConverter;
import org.activiti.bpmn.converter.child.DocumentationParser;
import org.activiti.bpmn.converter.child.IOSpecificationParser;
import org.activiti.bpmn.converter.child.MultiInstanceParser;
import org.activiti.bpmn.converter.export.ActivitiListenerExport;
import org.activiti.bpmn.converter.export.BPMNDIExport;
import org.activiti.bpmn.converter.export.CollaborationExport;
import org.activiti.bpmn.converter.export.DataStoreExport;
import org.activiti.bpmn.converter.export.DefinitionsRootExport;
import org.activiti.bpmn.converter.export.MultiInstanceExport;
import org.activiti.bpmn.converter.export.ProcessExport;
import org.activiti.bpmn.converter.export.SignalAndMessageDefinitionExport;
import org.activiti.bpmn.converter.parser.BpmnEdgeParser;
import org.activiti.bpmn.converter.parser.BpmnShapeParser;
import org.activiti.bpmn.converter.parser.DataStoreParser;
import org.activiti.bpmn.converter.parser.DefinitionsParser;
import org.activiti.bpmn.converter.parser.ExtensionElementsParser;
import org.activiti.bpmn.converter.parser.ImportParser;
import org.activiti.bpmn.converter.parser.InterfaceParser;
import org.activiti.bpmn.converter.parser.ItemDefinitionParser;
import org.activiti.bpmn.converter.parser.LaneParser;
import org.activiti.bpmn.converter.parser.MessageFlowParser;
import org.activiti.bpmn.converter.parser.MessageParser;
import org.activiti.bpmn.converter.parser.ParticipantParser;
import org.activiti.bpmn.converter.parser.PotentialStarterParser;
import org.activiti.bpmn.converter.parser.ProcessParser;
import org.activiti.bpmn.converter.parser.SignalParser;
import org.activiti.bpmn.converter.parser.SubProcessParser;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DateDataObject;
import org.activiti.bpmn.model.DoubleDataObject;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.IntegerDataObject;
import org.activiti.bpmn.model.LongDataObject;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StringDataObject;
import org.activiti.bpmn.model.SubProcess;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class BpmvXmlConver
  implements BpmnXMLConstants
{
  protected static final Logger LOGGER = LoggerFactory.getLogger(BpmnXMLConverter.class);
  
  protected static final String BPMN_XSD = "org/activiti/impl/bpmn/parser/BPMN20.xsd";
  
  protected static final String DEFAULT_ENCODING = "UTF-8";
  protected static Map<String, BaseBpmnXMLConverter> convertersToBpmnMap = new HashMap();
  protected static Map<Class<? extends BaseElement>, BaseBpmnXMLConverter> convertersToXMLMap = new HashMap();
  
  protected ClassLoader classloader;
  
  protected List<String> userTaskFormTypes;
  
  protected List<String> startEventFormTypes;
  protected BpmnEdgeParser bpmnEdgeParser = new BpmnEdgeParser();
  protected BpmnShapeParser bpmnShapeParser = new BpmnShapeParser();
  protected DefinitionsParser definitionsParser = new DefinitionsParser();
  protected DocumentationParser documentationParser = new DocumentationParser();
  protected ExtensionElementsParser extensionElementsParser = new ExtensionElementsParser();
  protected ImportParser importParser = new ImportParser();
  protected InterfaceParser interfaceParser = new InterfaceParser();
  protected ItemDefinitionParser itemDefinitionParser = new ItemDefinitionParser();
  protected IOSpecificationParser ioSpecificationParser = new IOSpecificationParser();
  protected DataStoreParser dataStoreParser = new DataStoreParser();
  protected LaneParser laneParser = new LaneParser();
  protected MessageParser messageParser = new MessageParser();
  protected MessageFlowParser messageFlowParser = new MessageFlowParser();
  protected MultiInstanceParser multiInstanceParser = new MultiInstanceParser();
  protected ParticipantParser participantParser = new ParticipantParser();
  protected PotentialStarterParser potentialStarterParser = new PotentialStarterParser();
  protected ProcessParser processParser = new ProcessParser();
  protected SignalParser signalParser = new SignalParser();
  protected SubProcessParser subProcessParser = new SubProcessParser();
  
  static
  {
    addConverter(new EndEventXMLConverter());
    addConverter(new StartEventXMLConverter());
    
    addConverter(new BusinessRuleTaskXMLConverter());
    addConverter(new ManualTaskXMLConverter());
    addConverter(new ReceiveTaskXMLConverter());
    addConverter(new ScriptTaskXMLConverter());
    addConverter(new ServiceTaskXMLConverter());
    addConverter(new SendTaskXMLConverter());
    addConverter(new UserTaskXMLConverter());
    addConverter(new TaskXMLConverter());
    addConverter(new CallActivityXMLConverter());
    
    addConverter(new EventGatewayXMLConverter());
    addConverter(new ExclusiveGatewayXMLConverter());
    addConverter(new InclusiveGatewayXMLConverter());
    addConverter(new ParallelGatewayXMLConverter());
    addConverter(new ComplexGatewayXMLConverter());
    
    addConverter(new SequenceFlowXMLConverter());
    
    addConverter(new CatchEventXMLConverter());
    addConverter(new ThrowEventXMLConverter());
    addConverter(new BoundaryEventXMLConverter());
    
    addConverter(new TextAnnotationXMLConverter());
    addConverter(new AssociationXMLConverter());
    
    addConverter(new DataStoreReferenceXMLConverter());
    
    addConverter(new ValuedDataObjectXMLConverter(), StringDataObject.class);
    addConverter(new ValuedDataObjectXMLConverter(), BooleanDataObject.class);
    addConverter(new ValuedDataObjectXMLConverter(), IntegerDataObject.class);
    addConverter(new ValuedDataObjectXMLConverter(), LongDataObject.class);
    addConverter(new ValuedDataObjectXMLConverter(), DoubleDataObject.class);
    addConverter(new ValuedDataObjectXMLConverter(), DateDataObject.class);
    
    addConverter(new AlfrescoStartEventXMLConverter());
    addConverter(new AlfrescoUserTaskXMLConverter());
  }
  
  public static void addConverter(BaseBpmnXMLConverter converter) {
//    addConverter(converter, converter.getBpmnElementType());
  }
  
  public static void addConverter(BaseBpmnXMLConverter converter, Class<? extends BaseElement> elementType) {
//    convertersToBpmnMap.put(converter.getXMLElementName(), converter);
    convertersToXMLMap.put(elementType, converter);
  }
  
  public void setClassloader(ClassLoader classloader) {
    this.classloader = classloader;
  }
  
  public void setUserTaskFormTypes(List<String> userTaskFormTypes) {
    this.userTaskFormTypes = userTaskFormTypes;
  }
  
  public void setStartEventFormTypes(List<String> startEventFormTypes) {
    this.startEventFormTypes = startEventFormTypes;
  }
  
  public void validateModel(InputStreamProvider inputStreamProvider) throws Exception {
    Schema schema = createSchema();
    
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(inputStreamProvider.getInputStream()));
  }
  
  public void validateModel(XMLStreamReader xmlStreamReader) throws Exception {
    Schema schema = createSchema();
    
    Validator validator = schema.newValidator();
    validator.validate(new StAXSource(xmlStreamReader));
  }
  
  protected Schema createSchema() throws SAXException {
    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    Schema schema = null;
    if (this.classloader != null) {
      schema = factory.newSchema(this.classloader.getResource("org/activiti/impl/bpmn/parser/BPMN20.xsd"));
    }
    
    if (schema == null) {
      schema = factory.newSchema(BpmnXMLConverter.class.getClassLoader().getResource("org/activiti/impl/bpmn/parser/BPMN20.xsd"));
    }
    
    if (schema == null) {
      throw new XMLException("BPMN XSD could not be found");
    }
    return schema;
  }
  
  public BpmnModel convertToBpmnModel(InputStreamProvider inputStreamProvider, boolean validateSchema, boolean enableSafeBpmnXml) {
    return convertToBpmnModel(inputStreamProvider, validateSchema, enableSafeBpmnXml, "UTF-8");
  }
  
  public BpmnModel convertToBpmnModel(InputStreamProvider inputStreamProvider, boolean validateSchema, boolean enableSafeBpmnXml, String encoding) {
    XMLInputFactory xif = XMLInputFactory.newInstance();
    
    if (xif.isPropertySupported("javax.xml.stream.isReplacingEntityReferences")) {
      xif.setProperty("javax.xml.stream.isReplacingEntityReferences", Boolean.valueOf(false));
    }
    
    if (xif.isPropertySupported("javax.xml.stream.isSupportingExternalEntities")) {
      xif.setProperty("javax.xml.stream.isSupportingExternalEntities", Boolean.valueOf(false));
    }
    
    if (xif.isPropertySupported("javax.xml.stream.supportDTD")) {
      xif.setProperty("javax.xml.stream.supportDTD", Boolean.valueOf(false));
    }
    
    InputStreamReader in = null;
    try {
      in = new InputStreamReader(inputStreamProvider.getInputStream(), encoding);
      XMLStreamReader xtr = xif.createXMLStreamReader(in);
      try
      {
        if (validateSchema)
        {
          if (!enableSafeBpmnXml) {
            validateModel(inputStreamProvider);
          } else {
            validateModel(xtr);
          }
          
          in = new InputStreamReader(inputStreamProvider.getInputStream(), encoding);
          xtr = xif.createXMLStreamReader(in);
        }
      }
      catch (Exception e) {
        throw new XMLException(e.getMessage(), e);
      }
      
      return convertToBpmnModel(xtr);
    } catch (UnsupportedEncodingException e) {
      throw new XMLException("The bpmn 2.0 xml is not UTF8 encoded", e);
    } catch (XMLStreamException e) {
      throw new XMLException("Error while reading the BPMN 2.0 XML", e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          LOGGER.debug("Problem closing BPMN input stream", e);
        }
      }
    }
  }
  
  public BpmnModel convertToBpmnModel(XMLStreamReader xtr) {
    BpmnModel model = new BpmnModel();
    model.setStartEventFormTypes(this.startEventFormTypes);
    model.setUserTaskFormTypes(this.userTaskFormTypes);
    try {
      Process activeProcess = null;
      List<SubProcess> activeSubProcessList = new ArrayList();
      while (xtr.hasNext()) {
        try {
          xtr.next();
        } catch (Exception e) {
          LOGGER.debug("Error reading XML document", e);
          throw new XMLException("Error reading XML", e);
        }
        
        if ((xtr.isEndElement()) && ("subProcess".equals(xtr.getLocalName()))) {
          activeSubProcessList.remove(activeSubProcessList.size() - 1);
        }
        
        if ((xtr.isEndElement()) && ("transaction".equals(xtr.getLocalName()))) {
          activeSubProcessList.remove(activeSubProcessList.size() - 1);
        }
        
        if (xtr.isStartElement())
        {
          if ("definitions".equals(xtr.getLocalName())) {
            this.definitionsParser.parse(xtr, model);
          }
          else if ("signal".equals(xtr.getLocalName())) {
            this.signalParser.parse(xtr, model);
          }
          else if ("message".equals(xtr.getLocalName())) {
            this.messageParser.parse(xtr, model);
          }
          else if ("error".equals(xtr.getLocalName()))
          {
            if (StringUtils.isNotEmpty(xtr.getAttributeValue(null, "id"))) {
              model.addError(xtr.getAttributeValue(null, "id"), xtr.getAttributeValue(null, "errorCode"));
            }
            
          }
          else if ("import".equals(xtr.getLocalName())) {
            this.importParser.parse(xtr, model);
          }
          else if ("itemDefinition".equals(xtr.getLocalName())) {
            this.itemDefinitionParser.parse(xtr, model);
          }
          else if ("dataStore".equals(xtr.getLocalName())) {
            this.dataStoreParser.parse(xtr, model);
          }
          else if ("interface".equals(xtr.getLocalName())) {
            this.interfaceParser.parse(xtr, model);
          }
          else if ("ioSpecification".equals(xtr.getLocalName())) {
            this.ioSpecificationParser.parseChildElement(xtr, activeProcess, model);
          }
          else if ("participant".equals(xtr.getLocalName())) {
            this.participantParser.parse(xtr, model);
          }
          else if ("messageFlow".equals(xtr.getLocalName())) {
            this.messageFlowParser.parse(xtr, model);
          }
          else if ("process".equals(xtr.getLocalName()))
          {
            Process process = this.processParser.parse(xtr, model);
            if (process != null) {
              activeProcess = process;
            }
          }
          else if ("potentialStarter".equals(xtr.getLocalName())) {
            this.potentialStarterParser.parse(xtr, activeProcess);
          }
          else if ("lane".equals(xtr.getLocalName())) {
            this.laneParser.parse(xtr, activeProcess, model);
          }
          else if ("documentation".equals(xtr.getLocalName()))
          {
            BaseElement parentElement = null;
            if (!activeSubProcessList.isEmpty()) {
              parentElement = (BaseElement)activeSubProcessList.get(activeSubProcessList.size() - 1);
            } else if (activeProcess != null) {
              parentElement = activeProcess;
            }
            this.documentationParser.parseChildElement(xtr, parentElement, model);
          }
          else if ((activeProcess == null) && ("textAnnotation".equals(xtr.getLocalName()))) {
            String elementId = xtr.getAttributeValue(null, "id");
//            TextAnnotation textAnnotation = (TextAnnotation)new TextAnnotationXMLConverter().convertXMLToElement(xtr, model);
//            textAnnotation.setId(elementId);
//            model.getGlobalArtifacts().add(textAnnotation);
          }
          else if ((activeProcess == null) && ("association".equals(xtr.getLocalName()))) {
            String elementId = xtr.getAttributeValue(null, "id");
//            Association association = (Association)new AssociationXMLConverter().convertXMLToElement(xtr, model);
//            association.setId(elementId);
//            model.getGlobalArtifacts().add(association);
          }
          else if ("extensionElements".equals(xtr.getLocalName())) {
            this.extensionElementsParser.parse(xtr, activeSubProcessList, activeProcess, model);
          }
          else if ("subProcess".equals(xtr.getLocalName())) {
            this.subProcessParser.parse(xtr, activeSubProcessList, activeProcess);
          }
          else if ("transaction".equals(xtr.getLocalName())) {
            this.subProcessParser.parse(xtr, activeSubProcessList, activeProcess);
          }
          else if ("BPMNShape".equals(xtr.getLocalName())) {
            this.bpmnShapeParser.parse(xtr, model);
          }
          else if ("BPMNEdge".equals(xtr.getLocalName())) {
            this.bpmnEdgeParser.parse(xtr, model);
          }
          else if ((!activeSubProcessList.isEmpty()) && ("multiInstanceLoopCharacteristics".equalsIgnoreCase(xtr.getLocalName())))
          {
            this.multiInstanceParser.parseChildElement(xtr, (BaseElement)activeSubProcessList.get(activeSubProcessList.size() - 1), model);
          }
          else if ((convertersToBpmnMap.containsKey(xtr.getLocalName())) && 
            (activeProcess != null)) {
            BaseBpmnXMLConverter converter = (BaseBpmnXMLConverter)convertersToBpmnMap.get(xtr.getLocalName());
            converter.convertToBpmnModel(xtr, model, activeProcess, activeSubProcessList);
          }
        }
      }
      
      for (Process process : model.getProcesses()) {
        for (Pool pool : model.getPools()) {
          if (process.getId().equals(pool.getProcessRef())) {
            pool.setExecutable(process.isExecutable());
          }
        }
        processFlowElements(process.getFlowElements(), process);
      }
    }
    catch (XMLException e) {
      throw e;
    }
    catch (Exception e) {
      LOGGER.error("Error processing BPMN document", e);
      throw new XMLException("Error processing BPMN document", e);
    }
    return model;
  }
  
  private void processFlowElements(Collection<FlowElement> flowElementList, BaseElement parentScope) {
    for (FlowElement flowElement : flowElementList) {
      if ((flowElement instanceof SequenceFlow)) {
        SequenceFlow sequenceFlow = (SequenceFlow)flowElement;
        FlowNode sourceNode = getFlowNodeFromScope(sequenceFlow.getSourceRef(), parentScope);
        if (sourceNode != null) {
          sourceNode.getOutgoingFlows().add(sequenceFlow);
        }
        FlowNode targetNode = getFlowNodeFromScope(sequenceFlow.getTargetRef(), parentScope);
        if (targetNode != null) {
          targetNode.getIncomingFlows().add(sequenceFlow);
        }
      } else if ((flowElement instanceof BoundaryEvent)) {
        BoundaryEvent boundaryEvent = (BoundaryEvent)flowElement;
        FlowElement attachedToElement = getFlowNodeFromScope(boundaryEvent.getAttachedToRefId(), parentScope);
        if (attachedToElement != null) {
          boundaryEvent.setAttachedToRef((Activity)attachedToElement);
          ((Activity)attachedToElement).getBoundaryEvents().add(boundaryEvent);
        }
      } else if ((flowElement instanceof SubProcess)) {
        SubProcess subProcess = (SubProcess)flowElement;
        processFlowElements(subProcess.getFlowElements(), subProcess);
      }
    }
  }
  
  private FlowNode getFlowNodeFromScope(String elementId, BaseElement scope) {
    FlowNode flowNode = null;
    if (StringUtils.isNotEmpty(elementId)) {
      if ((scope instanceof Process)) {
        flowNode = (FlowNode)((Process)scope).getFlowElement(elementId);
      } else if ((scope instanceof SubProcess)) {
        flowNode = (FlowNode)((SubProcess)scope).getFlowElement(elementId);
      }
    }
    return flowNode;
  }
  
  public byte[] convertToXML(BpmnModel model) {
    return convertToXML(model, "UTF-8");
  }
  
  public byte[] convertToXML(BpmnModel model, String encoding)
  {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      
      XMLOutputFactory xof = XMLOutputFactory.newInstance();
      OutputStreamWriter out = new OutputStreamWriter(outputStream, encoding);
      
      XMLStreamWriter writer = xof.createXMLStreamWriter(out);
      XMLStreamWriter xtw = new IndentingXMLStreamWriter(writer);
      
      DefinitionsRootExport.writeRootElement(model, xtw, encoding);
      CollaborationExport.writePools(model, xtw);
      DataStoreExport.writeDataStores(model, xtw);
      SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, xtw);
      
      for (Process process : model.getProcesses())
      {
        if ((!process.getFlowElements().isEmpty()) || (!process.getLanes().isEmpty()))
        {
          ProcessExport.writeProcess(process, xtw);
          
          for (FlowElement flowElement : process.getFlowElements()) {
            createXML(flowElement, model, xtw);
          }
          
          for (Artifact artifact : process.getArtifacts()) {
            createXML(artifact, model, xtw);
          }
          
          xtw.writeEndElement();
        }
      }
      BPMNDIExport.writeBPMNDI(model, xtw);
      
      xtw.writeEndElement();
      xtw.writeEndDocument();
      
      xtw.flush();
      
      outputStream.close();
      
      xtw.close();
      
      return outputStream.toByteArray();
    }
    catch (Exception e) {
      LOGGER.error("Error writing BPMN XML", e);
      throw new XMLException("Error writing BPMN XML", e);
    }
  }
  
  private void createXML(FlowElement flowElement, BpmnModel model, XMLStreamWriter xtw) throws Exception
  {
    if ((flowElement instanceof SubProcess))
    {
      SubProcess subProcess = (SubProcess)flowElement;
      xtw.writeStartElement("subProcess");
      xtw.writeAttribute("id", subProcess.getId());
      if (StringUtils.isNotEmpty(subProcess.getName())) {
        xtw.writeAttribute("name", subProcess.getName());
      } else {
        xtw.writeAttribute("name", "subProcess");
      }
      
      if ((subProcess instanceof EventSubProcess)) {
        xtw.writeAttribute("triggeredByEvent", "true");
      }
      
      if (StringUtils.isNotEmpty(subProcess.getDocumentation()))
      {
        xtw.writeStartElement("documentation");
        xtw.writeCharacters(subProcess.getDocumentation());
        xtw.writeEndElement();
      }
      
      boolean didWriteExtensionStartElement = ActivitiListenerExport.writeListeners(subProcess, false, xtw);
      
      didWriteExtensionStartElement = BpmnXMLUtil.writeExtensionElements(subProcess, didWriteExtensionStartElement, model.getNamespaces(), xtw);
      if (didWriteExtensionStartElement)
      {
        xtw.writeEndElement();
      }
      
      MultiInstanceExport.writeMultiInstance(subProcess, xtw);
      
      for (FlowElement subElement : subProcess.getFlowElements()) {
        createXML(subElement, model, xtw);
      }
      
      for (Artifact artifact : subProcess.getArtifacts()) {
        createXML(artifact, model, xtw);
      }
      
      xtw.writeEndElement();
    }
    else
    {
      BaseBpmnXMLConverter converter = (BaseBpmnXMLConverter)convertersToXMLMap.get(flowElement.getClass());
      
      if (converter == null) {
        throw new XMLException("No converter for " + flowElement.getClass() + " found");
      }
      
      converter.convertToXML(xtw, flowElement, model);
    }
  }
  
  private void createXML(Artifact artifact, BpmnModel model, XMLStreamWriter xtw) throws Exception
  {
    BaseBpmnXMLConverter converter = (BaseBpmnXMLConverter)convertersToXMLMap.get(artifact.getClass());
    
    if (converter == null) {
      throw new XMLException("No converter for " + artifact.getClass() + " found");
    }
    
    converter.convertToXML(xtw, artifact, model);
  }
}

