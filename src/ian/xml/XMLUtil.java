package ian.xml;

import java.io.ByteArrayInputStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {
   private static class XMLHandler extends DefaultHandler {
      private final List<KeyValue> list = new ArrayList<KeyValue>() {
			@Override
			public String toString() {
				return XMLUtil.toString(this);
			}
		};
      private final String ipath[] = new String[1024];
      private int level = 0;
      private StringBuffer text = null;
      @Override
      public void characters(char[] ch, int start, int length) throws SAXException {
         if (text == null) {
            text = new StringBuffer();
         }
         text.append(new String(ch, start, length));
      }
      @Override
      public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
         if (text != null) {
            if (text.length() > 0) {
               list.add(new KeyValue(getPath() + "#text", text.toString()));
            }
            text = null;
         }
         list.add(new KeyValue(getPath(), END));
         --level;
      }
      private String getPath() {
         final StringBuilder buf = new StringBuilder();
         buf.append('/');
         for (int i = 0; i < level; ++i) {
            buf.append(ipath[i]).append('/');
         }
         return buf.toString();
      }
      public List<KeyValue> getXMLList() {
         return list;
      }
      @Override
      public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
            throws SAXException {
         if (text != null) {
            if (text.length() > 0) {
               list.add(new KeyValue(getPath() + "#text", text.toString()));
            }
            text = null;
         }
         ipath[level] = qName;
         ++level;
         final int length = atts.getLength();
         list.add(new KeyValue(getPath(), START));
         for (int i = 0; i < length; ++i) {
            list.add(new KeyValue(getPath() + "@" + atts.getQName(i), atts.getValue(i)));
         }
      }
   }
   public static final String START = "#START#";
   public static final String END = "#END#";
   public static List<KeyValue> createXMLList(String xml) throws Exception {
		return createXMLList(new ByteArrayInputStream(xml.getBytes("utf-8")));
	}
   public static List<KeyValue> createXMLList(InputStream is) throws Exception {
      final XMLHandler handler = new XMLHandler();
      final SAXParser p = SAXParserFactory.newInstance().newSAXParser();
      p.parse(is, handler);
      return handler.getXMLList();
   }
	public static String toString(List<KeyValue> list) {
		StringBuilder sb = new StringBuilder();
		for(KeyValue x:list) {
			sb.append(x).append("\n");
		}
		return sb.toString();
	}
}
