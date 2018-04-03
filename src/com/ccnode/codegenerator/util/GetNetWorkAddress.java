//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class GetNetWorkAddress {
    public GetNetWorkAddress() {
    }

    public static String GetAddress(String addressType) {
        String address = "";
        InetAddress lanIp = null;

        try {
            String ipAddress = null;
            Enumeration<NetworkInterface> net = null;
            net = NetworkInterface.getNetworkInterfaces();

            while(net.hasMoreElements()) {
                NetworkInterface element = (NetworkInterface)net.nextElement();
                Enumeration addresses = element.getInetAddresses();

                while(addresses.hasMoreElements() && element.getHardwareAddress() != null && element.getHardwareAddress().length > 0 && !isVMMac(element.getHardwareAddress())) {
                    InetAddress ip = (InetAddress)addresses.nextElement();
                    if (ip instanceof Inet4Address && ip.isSiteLocalAddress()) {
                        ipAddress = ip.getHostAddress();
                        lanIp = InetAddress.getByName(ipAddress);
                    }
                }
            }

            if (lanIp == null) {
                return null;
            }

            if ("ip".equals(addressType)) {
                address = lanIp.toString().replaceAll("^/+", "");
            } else {
                if (!"mac".equals(addressType)) {
                    throw new Exception("Specify \"ip\" or \"mac\"");
                }

                address = getMacAddress(lanIp);
            }
        } catch (UnknownHostException var8) {
            var8.printStackTrace();
        } catch (SocketException var9) {
            var9.printStackTrace();
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return address;
    }

    private static String getMacAddress(InetAddress ip) {
        String address = null;

        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < mac.length; ++i) {
                sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
            }

            address = sb.toString();
        } catch (SocketException var6) {
            var6.printStackTrace();
        }

        return address;
    }

    private static boolean isVMMac(byte[] mac) {
        if (null == mac) {
            return false;
        } else {
            byte[][] invalidMacs = new byte[][]{{0, 5, 105}, {0, 28, 20}, {0, 12, 41}, {0, 80, 86}, {8, 0, 39}, {10, 0, 39}, {0, 3, -1}, {0, 21, 93}};
            byte[][] var2 = invalidMacs;
            int var3 = invalidMacs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte[] invalid = var2[var4];
                if (invalid[0] == mac[0] && invalid[1] == mac[1] && invalid[2] == mac[2]) {
                    return true;
                }
            }

            return false;
        }
    }
}
