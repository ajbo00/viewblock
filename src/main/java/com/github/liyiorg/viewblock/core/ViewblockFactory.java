package com.github.liyiorg.viewblock.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.liyiorg.viewblock.annotation.Viewblock;
import com.github.liyiorg.viewblock.annotation.ViewblockCollection;
import com.github.liyiorg.viewblock.exception.ViewBlockException;
import com.github.liyiorg.viewblock.exception.ViewBlockSpringBeanNotFindException;

public class ViewblockFactory {

	private static Logger logger = LoggerFactory.getLogger(ViewblockFactory.class);

	// block object map
	private static Map<String, ViewblockObject> map = new HashMap<String, ViewblockObject>();

	private static boolean useSpring = false;

	protected static void setUseSpring(boolean use) {
		useSpring = use;
	}

	/**
	 * get block
	 * 
	 * @param name
	 *            name
	 * @return ViewblockObject
	 */
	protected static ViewblockObject getBlock(String name) {
		return map.get(name);
	}

	/**
	 * get class by @ViewblockCollection
	 * 
	 * @param pack
	 * @return list
	 */
	private static List<Class<?>> scanClasses(String pack) {
		ClassFilter classFilter = new ClassFilter();
		classFilter.packageName(pack);
		classFilter.annotation(ViewblockCollection.class);
		return CPScanner.scanClasses(classFilter);
	}

	/**
	 * filter block
	 * 
	 * @param pack
	 * @throws ViewBlockSpringBeanNotFindException
	 */
	protected static void scanBlock(String pack) throws ViewBlockSpringBeanNotFindException {
		List<Class<?>> list = scanClasses(pack);

		for (Class<?> c : list) {
			ViewblockCollection collection = c.getAnnotation(ViewblockCollection.class);
			String pname = collection.name();

			// 判断是否为spring 的 bean
			boolean inspring = false;
			Object object = null;
			if (useSpring) {
				if (!"".equals(collection.spring())) {
					object = SpringProxy.getBean(collection.spring());
				} else {
					object = SpringProxy.getBean(c);
				}
				if (object != null) {
					inspring = true;
				} else {
					logger.error("VIEWBLOCK con't find spring bean {}", c.getName());
					throw new ViewBlockSpringBeanNotFindException("");
				}
			}
			if (!inspring) {
				try {
					object = c.newInstance();
				} catch (InstantiationException e) {

					e.printStackTrace();
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				}
			}
			for (Method method : c.getDeclaredMethods()) {
				Viewblock block = method.getAnnotation(Viewblock.class);
				if (block != null) {
					String bname = block.name();
					if (pname != null && !"".equals(pname)) {
						bname = pname + ":" + bname;
					}
					ViewblockObject blockObject = new ViewblockObject();
					blockObject.setName(bname);
					blockObject.setMethod(method);
					blockObject.setObject(object);
					blockObject.setTemplate(block.template());
					blockObject.setClassName(c.getName());
					if (map.containsKey(bname)) {
						try {
							throw new ViewBlockException("VIEWBLOCK [" + bname + "] existed,can't set same name");
						} catch (ViewBlockException e) {

							e.printStackTrace();
						}
					} else {
						map.put(bname, blockObject);
					}
					logger.info("VIEWBLOCK class:{} name:[{}] {}", c.getName(), bname,
							"".equals(block.template()) ? "" : "template:[" + block.template() + "]");
				}
			}
		}
	}

}
