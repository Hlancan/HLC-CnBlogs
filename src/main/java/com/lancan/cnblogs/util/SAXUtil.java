package com.lancan.cnblogs.util;


import com.lancan.cnblogs.entity.HotNewsInfo;
import com.lancan.cnblogs.entity.SiteHomeBlogsInfo;


import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by lan on 16-10-31.
 */

public class SAXUtil {

    private static SAXParserFactory factory = null;
    private static SAXParser parser = null;

    private static void initSAXParser(){
        if (factory == null){
            factory = SAXParserFactory.newInstance();
        }
    }

    public static List<HotNewsInfo> getHotNewsList(InputStream inputStream) throws Exception{
        initSAXParser();
        SAXHotNewsInfoHandler handler= new SAXHotNewsInfoHandler();
        if (parser == null){
            parser = factory.newSAXParser();
            parser.parse(inputStream,handler);
        }else {
            parser.parse(inputStream,handler);
        }

        return handler.getHotNewsInfoList();
    }

    public static List<SiteHomeBlogsInfo> getSiteHomeBlogsList(InputStream inputStream) throws Exception{
        initSAXParser();
        SAXSiteHomeBlogsHandler handler = new SAXSiteHomeBlogsHandler();
        if (parser == null){
            parser = factory.newSAXParser();
            parser.parse(inputStream,handler);
        }else {
            parser.parse(inputStream,handler);
        }
        return handler.getSiteHomeBlogsInfoList();
    }

}
