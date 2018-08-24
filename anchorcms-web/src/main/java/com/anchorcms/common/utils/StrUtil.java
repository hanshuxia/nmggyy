package com.anchorcms.common.utils;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.Node;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static com.anchorcms.common.constants.Constants.UTF8;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2016-10-31
 * @Desc string工具类，提供静态方法，不可被实例化
 */
public class StrUtil {
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:20
     * @Desc 禁止实例化
     */
    private StrUtil() {
    }


    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:20
     * @Desc url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
     */
    public static String handelUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if (url.equals("") || url.startsWith("http://")
                || url.startsWith("https://")) {
            return url;
        } else {
            return "http://" + url.trim();
        }
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:21
     * @Desc 如果str为空，则返回null。
     */

    public static String[] splitAndTrim(String str, String sep, String sep2) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (!StringUtils.isBlank(sep2)) {
            str = StringUtils.replace(str, sep2, sep);
        }
        String[] arr = StringUtils.split(str, sep);
        // trim
        for (int i = 0, len = arr.length; i < len; i++) {
            arr[i] = arr[i].trim();
        }
        return arr;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:21
     * @Desc 文本转html
     */
    public static String txt2htm(String txt) {
        if (StringUtils.isBlank(txt)) {
            return txt;
        }
        StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
        char c;
        boolean doub = false;
        for (int i = 0; i < txt.length(); i++) {
            c = txt.charAt(i);
            if (c == ' ') {
                if (doub) {
                    sb.append(' ');
                    doub = false;
                } else {
                    sb.append("&nbsp;");
                    doub = true;
                }
            } else {
                doub = false;
                switch (c) {
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    case '\n':
                        sb.append("<br/>");
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:21
     * @Desc 剪切文本。如果进行了剪切，则在文本后加上"..."编码小于256的作为一个字符，大于256的作为两个字符。
     */
    public static String textCut(String s, int len, String append) {
        if (s == null) {
            return null;
        }
        int slen = s.length();
        if (slen <= len) {
            return s;
        }
        // 最大计数（如果全是英文）
        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        for (; count < maxCount && i < slen; i++) {
            if (s.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
        }
        if (i < slen) {
            if (count > maxCount) {
                i--;
            }
            if (!StringUtils.isBlank(append)) {
                if (s.codePointAt(i - 1) < 256) {
                    i -= 2;
                } else {
                    i--;
                }
                return s.substring(0, i) + append;
            } else {
                return s.substring(0, i);
            }
        } else {
            return s;
        }
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:24
     * @Desc 切割html
     */
    public static String htmlCut(String s, int len, String append) {
        String text = html2Text(s, len * 2);
        return textCut(text, len, append);
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:24
     * @Desc html to text
     */
    public static String html2Text(String html, int len) {
        try {
            Lexer lexer = new Lexer(html);
            Node node;
            StringBuilder sb = new StringBuilder(html.length());
            while ((node = lexer.nextNode()) != null) {
                if (node instanceof TextNode) {
                    sb.append(node.toHtml());
                }
                if (sb.length() > len) {
                    break;
                }
            }
            return sb.toString();
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:25
     * @Desc 分词
     */
    public static String getKeywords(String keyword, boolean smart) {
        StringReader reader = new StringReader(keyword);
        IKSegmenter iks = new IKSegmenter(reader, smart);
        StringBuilder buffer = new StringBuilder();
        try {
            Lexeme lexeme;
            while ((lexeme = iks.next()) != null) {
                buffer.append(lexeme.getLexemeText()).append(',');
            }
        } catch (IOException e) {
        }
        //去除最后一个,
        if (buffer.length() > 0) {
            buffer.setLength(buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:25
     * @Desc 换行
     */
    public static String removeHtmlTagP(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            htmlStr.replace("</p>", "\n");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }
    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:25
     * @Desc 去除html标签
     */
    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:26
     * @Desc 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
     */
    public static boolean contains(String str, String search) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
            return false;
        }
        String reg = StringUtils.replace(search, "*", ".*");
        Pattern p = Pattern.compile(reg);
        return p.matcher(str).matches();
    }

    public static boolean containsKeyString(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        if (str.contains("'") || str.contains("\"") || str.contains("\r")
                || str.contains("\n") || str.contains("\t")
                || str.contains("\b") || str.contains("\f")) {
            return true;
        }
        return false;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:26
     * @Desc 补充字符串
     */
    public static String addCharForString(String str, int strLength,char c,int position) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                if(position==1){
                    //右补充字符c
                    sb.append(c).append(str);
                }else{
                    //左补充字符c
                    sb.append(str).append(c);
                }
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:26
     * @Desc  将""和'转义
     */
    public static String replaceKeyString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:27
     * @Desc 单引号转化成双引号
     */
    public static String replaceString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\"").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }

    public static String getSuffix(String str) {
        int splitIndex = str.lastIndexOf(".");
        return str.substring(splitIndex + 1);
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:27
     * @Desc 补齐不足长度
     */
    public static String lpad(int length, Long number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:27
     * @Desc 保留两位小数（四舍五入）
     */
    public static Double retainTwoDecimal(double value){
        long l1 = Math.round(value*100); //四舍五入
        double ret = l1/100.0; //注意:使用 100.0 而不是 100
        return ret;
    }



    /**
     * @Author 阁楼麻雀
     * @Date 2016-10-31 10:27
     * @Desc 将容易引起xss漏洞的半角字符直接替换成全角字符
     */
    public static  String xssEncode(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        try {
            s = URLDecoder.decode(s, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //< > ' " \ / # &
        s = s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        s = s.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        s = s.replaceAll("'", "&#39;");
        s = s.replaceAll("eval\\((.*)\\)", "");
        s = s.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        s = s.replaceAll("script", "");
        s = s.replaceAll("#", "＃");
        s = s.replaceAll("%", "％");
        return s;
    }

    public static void main(String args[]) {
        System.out.println(replaceKeyString("&nbsp;\r" + "</p>"));
    }
}
