package com.phaseos.util;

import java.util.Arrays;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/16/2019 | 10:54
 * __________________
 *
 *  [2016] J&M Plugin Development 
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of J&M Plugin Development and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to J&M Plugin Development
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from J&M Plugin Development.
 */
public class PaginatorUtil {

    public static Book getPages() {
        return null;
    }

    private class Book {

        java.util.Map<Integer, Page> pages;
        private String[] information;
        private int pageSize = 10;
        private int pageCount = 0;
        private String lineMarker = "-";

        public Book(String[] information) {
            this.information = information;
        }

        public Book(String[] information, int pageSize) {
            this.information = information;
            this.pageSize = pageSize;
        }

        public java.util.Map<Integer, Page> getPages() {

            // create pages
            while (information.length >= pageSize && pageSize > 0) {
                String[] pageContent = new String[pageSize <= information.length ? pageSize : information.length];
                        System.arraycopy(information, 0, pageContent, 0, pageContent.length);
                if (information.length < pageSize)
                    this.information = new String[]{};
                else
                    this.information = Arrays.copyOfRange(information, pageContent.length, this.information.length);

                this.pages.put(++pageCount, new Page(pageContent, lineMarker));
            }

            return this.pages;
        }

        public String getLineMarker() {
            return lineMarker;
        }

        public void setLineMarker(String lineMarker) {
            this.lineMarker = lineMarker;
        }
    }

    private class Page {
        private String[] information;
        private String lineMarker;

        public Page(String[] information, String lineMarker) {
            this.information = information;
            this.lineMarker = lineMarker;
        }

        public String getPage() {
            StringBuilder content = new StringBuilder();
            for (String line : information) {
                content.append(lineMarker).append(" ").append(line).append("\n");
            }

            return StringUtils.fmt(content.toString());
        }

        public void clear() {
            this.information = null;
        }
    }

}
