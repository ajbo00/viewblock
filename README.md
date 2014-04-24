viewblock
---------

viewblock ��һ��JSP����ع���,���ڴ����ӵ�ҵ��������ҳ������.

ʾ��  
https://github.com/liyiorg/viewblock-example

�ص�
---------
ע���д  
֧���������,����ת��  
֧��JSP,Freemark ��ģ��  
֧���첽���ؿ�(servlet3.0,Ŀǰֻ֧��apache-tomcat-7.0.50 ���ϰ汾)  
֧��spring IOC

ʹ�ó���
---------
viewblock �����ڴ�ҳ��,��һ������߼�����,ҳ�������ظ�ʹ��,�첽ҳ����ص���Ŀ,���Խ��MVC�ܹ�һ��ʹ��,��Spring MVC,struts

ʹ�÷�ʽ:
---------
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
&lt;filter&gt;   
	&lt;filter-name&gt;viewblock&lt;/filter-name&gt;   
	&lt;filter-class&gt;viewblock.core.ViewblockFilter&lt;/filter-class&gt;   
	&lt;async-supported&gt;true&lt;/async-supported&gt;   
	
	&lt;init-param&gt;   
		&lt;param-name&gt;config_properties&lt;/param-name&gt;   
		&lt;param-value&gt;  	  		
		pack_scan=example.*    
		spring=false    
		jsp_template=/WEB-INF/block    
		freemarker=true    
		freemarker_template=/WEB-INF/block    
		freemarker_delay=0    
		freemarker_encode=UTF-8    
		&lt;/param-value&gt;    
	&lt;/init-param&gt;    
&lt;/filter&gt;  

3. JSP ��ǩ����  
&lt;%@taglib uri="/viewblock" prefix="viewblock"%&gt;  

4. �����  
&lt;viewblock:block name="header"/&gt;  
&lt;viewblock:block name="content"/&gt;   
&lt;viewblock:block name="footer"/&gt;  

