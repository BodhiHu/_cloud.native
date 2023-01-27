package BodhiTree.tree.lib;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XML {
    private static XmlMapper mapperInstance;
    public static XmlMapper mapper () {
        if (mapperInstance == null) {
            mapperInstance = new XmlMapper();
        }
        return mapperInstance;
    }
}

