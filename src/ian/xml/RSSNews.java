package ian.xml;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* Example usage of XMLUtil XML processing utility */
class RSSItem {
	public String link;
	public String title;
	public String date;
	public String description;
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(title).append('\n');
		sb.append(date).append('\n');
		if (description.length() == 0) {
			sb.append("No description\n");
		} else {
			sb.append(description).append('\n');
		}
		sb.append(link);
		return sb.toString();
	}
}
public class RSSNews {
	public static void main(String[] args) throws Exception {
		InputStream is = new URL("http://news.yahoo.com/rss/").openStream();
		List<KeyValue> list = XMLUtil.createXMLList(is);
		RSSItem item = new RSSItem();
		List<RSSItem> itemList = new ArrayList<>();
		for (KeyValue x : list) {
			switch (x.key) {
				case "/rss/channel/item/":
					if (x.value.equals(XMLUtil.END)) {
						itemList.add(item);
						item = new RSSItem();
					}
				case "/rss/channel/item/link/#text":
					item.link = x.value;
					break;
				case "/rss/channel/item/title/#text":
					item.title = x.value;
					break;
				case "/rss/channel/item/pubDate/#text":
					item.date = x.value;
					break;
				case "/rss/channel/item/description/#text":
					item.description = clean(x.value);
					break;
			}
		}
		for (RSSItem x : itemList) {
			System.out.println(x);
			System.out.println();
		}
	}
	private static String clean(String value) {
		return value.replaceAll("<.*?>", "").trim();
	}
}
