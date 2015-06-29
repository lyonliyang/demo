/**
 * 
 */
package com.tencent.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * @author luo bohui
 * 
 */
public class MapConverter extends AbstractCollectionConverter {
	public MapConverter(final Mapper mapper) {
		super(mapper);
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(final Class type) {
		// 这里只列了HashMap一种情况
		return type.equals(HashMap.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void marshal(final Object source,
			final HierarchicalStreamWriter writer,
			final MarshallingContext context) {
		final Map map = (Map) source;
		for (final Iterator<Entry> iterator = map.entrySet().iterator(); iterator
				.hasNext();) {
			final Entry entry = iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, entry
					.getKey().toString(), Entry.class);

			writer.setValue(entry.getValue().toString());
			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(final HierarchicalStreamReader reader,
			final UnmarshallingContext context) {
		throw new UnsupportedOperationException(getClass().getName()
				+ "不支持将字符流转换为map的操作");
	}

}
