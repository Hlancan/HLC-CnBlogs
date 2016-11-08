package com.lancan.cnblogs.util;

import android.text.TextUtils;
import android.util.Log;

import com.lancan.cnblogs.Constants;
import com.lancan.cnblogs.entity.HotNewsInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lan on 16-10-31.
 */

class SAXHotNewsInfoHandler extends DefaultHandler {

    private HotNewsInfo hotNewsInfo = null;
    private List<HotNewsInfo> hotNewsInfoList = null;
    private boolean isFeed = false;
    private StringBuilder sbSummary = null;
    private String elementTag = null;

    public SAXHotNewsInfoHandler() {
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        hotNewsInfoList = new ArrayList<HotNewsInfo>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (Constants.XML_LOCALNAME_FEED.equals(localName)){
            isFeed = true;
        }else if (Constants.XML_LOCALNAME_ENTRY.equals(localName)){
            isFeed = false;
            hotNewsInfo = new HotNewsInfo();
        }

        if (Constants.XML_LOCALNAME_LINK.equals(localName) && !isFeed){
            if (hotNewsInfo != null){
                hotNewsInfo.setLink(attributes.getValue(Constants.XML_LINK_TAG));
            }
        }

        elementTag = localName;

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String data = new String(ch,start,length);
        if (TextUtils.isEmpty(data)){
            if (!TextUtils.isEmpty(elementTag) && hotNewsInfo != null){
                if (Constants.ELEMENT_SOURCE_NAME.equals(elementTag)){
                    hotNewsInfo.setSourceName("未知来源");
                }
            }
        }else {
            if (!TextUtils.isEmpty(elementTag) && hotNewsInfo != null){
                if (Constants.ELEMENT_ID.equals(elementTag) && !isFeed){
                    hotNewsInfo.setId(data);
                }else if (Constants.ELEMENT_TITLE.equals(elementTag) && !isFeed){
                    hotNewsInfo.setTitle(data);
                }else if (Constants.ELEMENT_UPDATED.equals(elementTag) && !isFeed){
                    hotNewsInfo.setUpdated(data);
                }else if (Constants.ELEMENT_DIGGS.equals(elementTag)){
                    hotNewsInfo.setDiggs(Integer.parseInt(data));
                }else if (Constants.ELEMENT_COMMENTS.equals(elementTag)){
                    hotNewsInfo.setComments(Integer.parseInt(data));
                }else if (Constants.ELEMENT_PUBLISHED.equals(elementTag)){
                    hotNewsInfo.setPublished(data);
                }else if (Constants.ELEMENT_SOURCE_NAME.equals(elementTag)){
                    hotNewsInfo.setSourceName(data);
                }else if (Constants.ELEMENT_VIEWS.equals(elementTag)){
                    hotNewsInfo.setViews(Integer.parseInt(data));
                }else if (Constants.ELEMENT_SUMMARY.equals(elementTag)){
//                    sbSummary.append(data);
                    hotNewsInfo.setSummary(data);
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (Constants.XML_LOCALNAME_ENTRY.equals(localName)){
//            hotNewsInfo.setSummary(sbSummary.toString());
//            Log.d("CnBlogs","endElement >>> " + hotNewsInfo.toString());
            hotNewsInfoList.add(hotNewsInfo);
//            sbSummary.setLength(0);
            hotNewsInfo = null;
        }
        elementTag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public List<HotNewsInfo> getHotNewsInfoList(){
        return hotNewsInfoList;
    }
}
