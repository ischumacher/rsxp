# RSXP - Ridiculously simple XML parser

RSXP flattens hierachical XML into a simple list. Paths ending in / are the start or end of tag block and which was is indicated by a value of #START# or #END#. Atributes paths end in @attribute-name. Text node paths end in #text

For example the following XML:

    <?xml version="1.0" encoding="UTF-8"?>
    <catalog>
        <book id="bk101">
            <author>Gambardella, Matthew</author>
            <title>XML Developer's Guide</title>
            <genre>Computer</genre>
            <price>44.95</price>
            <publish_date>2000-10-01</publish_date>
            <description>An in-depth look at creating applications 
                with XML.</description>
        </book>
        <book id="bk102">
            <author>Ralls, Kim</author>
            <title>Midnight Rain</title>
            <genre>Fantasy</genre>
            <price>5.95</price>
            <publish_date>2000-12-16</publish_date>
            <description>A former architect battles corporate zombies, 
                an evil sorceress, and her own childhood to become queen 
                of the world.</description>
        </book>
    </catalog>

is flattened to:  (I've removed some whitespace to clean things up).

    /catalog/: #START#
    /catalog/#text: 
    /catalog/book/: #START#
    /catalog/book/@id: bk101
    /catalog/book/#text: 
    /catalog/book/author/: #START#
    /catalog/book/author/#text: Gambardella, Matthew
    /catalog/book/author/: #END#
    /catalog/book/#text: 
    /catalog/book/title/: #START#
    /catalog/book/title/#text: XML Developer's Guide
    /catalog/book/title/: #END#
    /catalog/book/#text: 
    /catalog/book/genre/: #START#
    /catalog/book/genre/#text: Computer
    /catalog/book/genre/: #END#
    /catalog/book/#text: 
    /catalog/book/price/: #START#
    /catalog/book/price/#text: 44.95
    /catalog/book/price/: #END#
    /catalog/book/#text: 
    /catalog/book/publish_date/: #START#
    /catalog/book/publish_date/#text: 2000-10-01
    /catalog/book/publish_date/: #END#
    /catalog/book/#text: 
    /catalog/book/description/: #START#
    /catalog/book/description/#text: An in-depth look at creating applications with XML.
    /catalog/book/description/: #END#
    /catalog/book/#text: 
    /catalog/book/: #END#
    /catalog/#text: 
    /catalog/book/: #START#
    /catalog/book/@id: bk102
    /catalog/book/#text: 
    /catalog/book/author/: #START#
    /catalog/book/author/#text: Ralls, Kim
    /catalog/book/author/: #END#
    /catalog/book/#text: 
    /catalog/book/title/: #START#
    /catalog/book/title/#text: Midnight Rain
    /catalog/book/title/: #END#
    /catalog/book/#text: 
    /catalog/book/genre/: #START#
    /catalog/book/genre/#text: Fantasy
    /catalog/book/genre/: #END#
    /catalog/book/#text: 
    /catalog/book/price/: #START#
    /catalog/book/price/#text: 5.95
    /catalog/book/price/: #END#
    /catalog/book/#text: 
    /catalog/book/publish_date/: #START#
    /catalog/book/publish_date/#text: 2000-12-16
    /catalog/book/publish_date/: #END#
    /catalog/book/#text: 
    /catalog/book/description/: #START#
    /catalog/book/description/#text: A former architect battles corporate zombies, 
                an evil sorceress, and her own childhood to become queen 
                of the world.
    /catalog/book/description/: #END#
    /catalog/book/#text:     
    /catalog/book/: #END#
    /catalog/#text: 
    /catalog/: #END#

An example is included which demonstrates how easy extacting information from this flattened XML structure. The example [RSSNews.java](src/ian/xml/RSSNews.java), extracts information from yahoo RSS XML and printes out the title, date, description and url link for each RSS item.
