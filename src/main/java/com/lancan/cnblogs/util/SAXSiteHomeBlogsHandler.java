package com.lancan.cnblogs.util;

import android.text.TextUtils;

import com.lancan.cnblogs.Constants;
import com.lancan.cnblogs.entity.SiteHomeBlogsInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lan on 16-10-31.
 */

public class SAXSiteHomeBlogsHandler extends DefaultHandler {

    private List<SiteHomeBlogsInfo> siteHomeBlogsInfoList = null;
    private SiteHomeBlogsInfo siteHomeBlogsInfo = null;
    private String elementTag = null;
    private boolean isFeed = false;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        siteHomeBlogsInfoList = new ArrayList<SiteHomeBlogsInfo>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (Constants.XML_LOCALNAME_FEED.equals(localName)){
            isFeed = true;
        }else if (Constants.XML_LOCALNAME_ENTRY.equals(localName)){
            isFeed = false;
            siteHomeBlogsInfo = new SiteHomeBlogsInfo();
        }
        elementTag = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        String data = new String(ch,start,length);
        if (!TextUtils.isEmpty(data)){
            if (!TextUtils.isEmpty(elementTag) && siteHomeBlogsInfo != null){
                if (Constants.ELEMENT_ID.equals(elementTag) && !isFeed){
                    siteHomeBlogsInfo.setId(data);
                }else if (Constants.ELEMENT_TITLE.equals(elementTag) && !isFeed){
                    siteHomeBlogsInfo.setTitle(data);
                }else if (Constants.ELEMENT_UPDATED.equals(elementTag) && !isFeed){
                    siteHomeBlogsInfo.setUpdated(data);
                }else if (Constants.ELEMENT_AVATAR.equals(elementTag)){
                    siteHomeBlogsInfo.setAuthorAvatar(data);
                }else if (Constants.ELEMENT_NAME.equals(elementTag)){
                    siteHomeBlogsInfo.setAuthorName(data);
                }else if (Constants.ELEMENT_COMMENTS.equals(elementTag)){
                    siteHomeBlogsInfo.setComments(Integer.parseInt(data));
                }else if (Constants.ELEMENT_DIGGS.equals(elementTag)){
                    siteHomeBlogsInfo.setDiggs(Integer.parseInt(data));
                }else if (Constants.ELEMENT_PUBLISHED.equals(elementTag)){
                    siteHomeBlogsInfo.setPublished(data);
                }else if (Constants.ELEMENT_SUMMARY.equals(elementTag)){
                    siteHomeBlogsInfo.setSummary(data);
                }else if (Constants.ELEMENT_URL.equals(elementTag)){
                    siteHomeBlogsInfo.setAuthorUrl(data);
                }else if (Constants.ELEMENT_VIEWS.equals(elementTag)){
                    siteHomeBlogsInfo.setViews(Integer.parseInt(data));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (Constants.XML_LOCALNAME_ENTRY.equals(localName)){
            if (siteHomeBlogsInfoList != null){
                siteHomeBlogsInfoList.add(siteHomeBlogsInfo);
                siteHomeBlogsInfo = null;
            }
            elementTag = null;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public List<SiteHomeBlogsInfo> getSiteHomeBlogsInfoList(){
        return siteHomeBlogsInfoList;
    }
}
