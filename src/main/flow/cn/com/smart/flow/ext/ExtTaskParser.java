package cn.com.smart.flow.ext;


import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.impl.TaskParser;
import org.w3c.dom.Element;

/**
 * 自定义任务解析器
 * @author lmq
 */
public class ExtTaskParser extends TaskParser {
	
    private static final String ATTR_ASSIGNEEDISPLAY = "assigneeDisplay";
    private static final String ATTR_TASK_ATTACHMENT = "taskAttachment";
    private static final String ATTR_FORM_PROP = "formPropIds";
    private static final String ATTR_IS_SUG = "isSug";
    private static final String ATTR_IS_TAKE_TASK = "isTakeTask";
    
    private static final String ATTR_IS_CONCURRENT = "isConcurrent";
    private static final String ATTR_IS_EXE_ASSIGNER = "isExeAssigner";
    private static final String ATTR_SELECT_ASSIGNER_STYLE = "selectAssignerStyle";
    private static final String ATTR_IS_DEPART_FILTER = "isDepartFilter";
    private static final String ATTR_IS_PRINT = "isPrint";
    
    
    @Override
    protected void parseNode(NodeModel node, Element element) {
        super.parseNode(node, element);
        ExtTaskModel task = (ExtTaskModel)node;
        task.setAssigneeDisplay(element.getAttribute(ATTR_ASSIGNEEDISPLAY));
        task.setTaskAttachment(element.getAttribute(ATTR_TASK_ATTACHMENT));
        task.setFormPropIds(element.getAttribute(ATTR_FORM_PROP));
        task.setIsSug(element.getAttribute(ATTR_IS_SUG));
        task.setIsTakeTask(element.getAttribute(ATTR_IS_TAKE_TASK));
        
        task.setIsConcurrent(element.getAttribute(ATTR_IS_CONCURRENT));
        task.setIsExeAssigner(element.getAttribute(ATTR_IS_EXE_ASSIGNER));
        task.setSelectAssignerStyle(element.getAttribute(ATTR_SELECT_ASSIGNER_STYLE));
        task.setIsDepartFilter(element.getAttribute(ATTR_IS_DEPART_FILTER));
        task.setIsPrint(element.getAttribute(ATTR_IS_PRINT));
        
    }

    @Override
    protected NodeModel newModel() {
        return new ExtTaskModel();
    }
}
