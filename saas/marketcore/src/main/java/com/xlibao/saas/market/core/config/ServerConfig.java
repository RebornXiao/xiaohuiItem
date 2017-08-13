package com.xlibao.saas.market.core.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class ServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    private static final String CONFIG_PATH = "resources" + File.separator + "config" + File.separator + "serverConfig.xml";

    private static String serverIP = "";
    private static int serverPort = 0;
    private static int listenerPort = 0;

    private static int readTimeout = 10;
    private static int writeTimeout = 10;
    private static int bothTimeout = 15;

    public void init(String rootPath) {
        SAXReader reader = new SAXReader();
        String serverConfigPath = rootPath + CONFIG_PATH;
        logger.info("配置文件完整路径：" + new File(serverConfigPath).getAbsolutePath());
        try {
            Document document = reader.read(new File(serverConfigPath));
            Element root = document.getRootElement();
            Element serverElement = root.element("server");

            serverIP = serverElement.elementText("serverIP");
            serverPort = Integer.parseInt(serverElement.elementText("serverPort"));

            listenerPort = Integer.parseInt(serverElement.elementText("listenerPort"));

            Element timeoutElement = root.element("timeout");
            readTimeout = Integer.parseInt(timeoutElement.elementText("read"));
            writeTimeout = Integer.parseInt(timeoutElement.elementText("write"));
            bothTimeout = Integer.parseInt(timeoutElement.elementText("both"));
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    public static String getServerIP() {
        return serverIP;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static int getListenerPort() {
        return listenerPort;
    }

    public static int getReadTimeout() {
        return readTimeout;
    }

    public static int getWriteTimeout() {
        return writeTimeout;
    }

    public static int getBothTimeout() {
        return bothTimeout;
    }
}