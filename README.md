viewblock
=========

viewblock ��һ��JSP����ع���,��ͳ��WEB����ʽ,�ɿͻ��˷�������,����˽��ղ���������ת��һ��ҳ��.viewblock ���Խ�һ��ҳ�滮��Ϊ��ͬ���߼��鴦��,
�Դ���ͬ��ҵ��.   

ʾ��  
https://github.com/liyiorg/viewblock-example

�ص�
=========
ע���д  
֧���������,����ת��  
֧��JSP,Freemark ��ģ��  
֧���첽���ؿ�(����servlet3.0,Ŀǰֻ֧��apache-tomcat-7.0.50 ���ϰ汾)  
֧��spring IOC

ʹ�ó���
=========
viewblock �����ڴ�ҳ��,��һ������߼�����,ҳ�������ظ�ʹ��,�첽ҳ����ص���Ŀ,���Խ��MVC�ܹ�һ��ʹ��,��Spring MVC,struts

ʹ�÷�ʽ:
=========
1. ���� viewblock  

@ViewblockCollection
public class ExampleBlock {

	@Viewblock(name = "header", template = "header.jsp")
	public void header() {
		
	}
	
	@Viewblock(name = "footer", template = "footer.ftl")
	public void footer() {
		
	}

	@Viewblock(name = "content", template = "content.jsp")
	public void content(@BRequestParam(required=false) String name,BModelMap bModelMap){
		bModelMap.addAttribute("name", name);
	}
}

2. web.xml ����  
<filter>
	<filter-name>viewblock</filter-name>
	<filter-class>viewblock.core.ViewblockFilter</filter-class>
	<async-supported>true</async-supported>
	
	<init-param>
		<param-name>config_properties</param-name>
		<param-value>			
		pack_scan=example.*
		spring=false
		jsp_template=/WEB-INF/block
		freemarker=true
		freemarker_template=/WEB-INF/block
		freemarker_delay=0
		freemarker_encode=UTF-8
		</param-value>
	</init-param>
</filter>

3. JSP ��ǩ����  
<%@taglib uri="/viewblock" prefix="viewblock"%>  

4. �����  
<viewblock:block name="header"/>  
<viewblock:block name="content"/>   
<viewblock:block name="footer"/>  

