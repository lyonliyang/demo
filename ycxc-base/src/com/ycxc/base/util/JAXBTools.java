package com.ycxc.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JAXBTools {
	public static final Log log = LogFactory.getLog(JAXBTools.class);

	public static <T> String marshal(final JAXBContext context, final T chart)
			throws JAXBException {

		final Marshaller marshaller = context.createMarshaller();

		final StringWriter stringWriter = new StringWriter();

		marshaller.marshal(chart, stringWriter);

		final String chartXml = stringWriter.toString();
		return chartXml;
	}

	public static <T> String marshal(final T chart, final Class<T> clazz)
			throws JAXBException {
		final Marshaller marshaller = getMarshaller(clazz);

		final StringWriter stringWriter = new StringWriter();

		marshaller.marshal(chart, stringWriter);

		final String chartXml = stringWriter.toString();
		return chartXml;
	}

	public static <T> void marshal(final T chart, final Class<T> clazz, final File file)
			throws JAXBException {
		final Marshaller marshaller = getMarshaller(clazz);

		marshaller.marshal(chart, file);
	}

	public static <T> void marshal(final T chart, final Class<T> clazz,
			final OutputStream os) throws JAXBException {
		final Marshaller marshaller = getMarshaller(clazz);

		marshaller.marshal(chart, os);
	}

	public static <T> void marshal(final T chart, final Class<T> clazz, final String path)
			throws JAXBException {
		final Marshaller marshaller = getMarshaller(clazz);

		marshaller.marshal(chart, new File(path));
	}

	/**
	 * 将xml文件转化为java对象
	 * 
	 * @author luo bohui
	 * @date 2015年5月6日
	 * @param templateFile
	 * @param clazz
	 * @return
	 * @throws JAXBException
	 */
	public static <T> T unmarshal(final File templateFile, final Class<T> clazz)
			throws JAXBException {
		final Unmarshaller unmarshaller = getUnmarshaller(clazz);
		@SuppressWarnings("unchecked")
		final T chart = (T) unmarshaller.unmarshal(templateFile);
		return chart;
	}

	public static <T> T unmarshal(final InputStream xmlStream, final Class<T> clazz)
			throws JAXBException {
		final Unmarshaller unmarshaller = getUnmarshaller(clazz);
		@SuppressWarnings("unchecked")
		final T chart = (T) unmarshaller.unmarshal(xmlStream);
		return chart;
	}

	/**
	 * 将xml文件转化为java对象
	 * 
	 * @author luo bohui
	 * @date 2015年5月6日
	 * @param templateFile
	 * @param context
	 * @return
	 * @throws JAXBException
	 */
	public static <T> T unmarshal(final File templateFile, final JAXBContext context)
			throws JAXBException {
		final Unmarshaller unmarshaller = context.createUnmarshaller();

		@SuppressWarnings("unchecked")
		final T chart = (T) unmarshaller.unmarshal(templateFile);

		return chart;
	}

	/**
	 * 将filtPath对应xml转化为java对象
	 * 
	 * @author luo bohui
	 * @date 2015年5月6日
	 * @param filePath
	 * @param clazz
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static <T> T unmarshal(final String filePath, final Class<T> clazz)
			throws JAXBException, FileNotFoundException {
		final Unmarshaller unmarshaller = getUnmarshaller(clazz);

		@SuppressWarnings("unchecked")
		final T chart = (T) unmarshaller
				.unmarshal(new FileInputStream(filePath));

		return chart;
	}

	public static <T> T unmarshal(final String template, final JAXBContext context)
			throws JAXBException {

		final Unmarshaller unmarshaller = context.createUnmarshaller();

		// Unmarshal the XML in the stringWriter back into an object
		@SuppressWarnings("unchecked")
		final T chart = (T) unmarshaller.unmarshal(new StringReader(template));

		return chart;
	}

	private static <T> Marshaller getMarshaller(final Class<T> clazz)
			throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(clazz);
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		return marshaller;
	}

	private static <T> Unmarshaller getUnmarshaller(final Class<T> clazz)
			throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(clazz);
		final Unmarshaller marshaller = context.createUnmarshaller();
		return marshaller;
	}

}
