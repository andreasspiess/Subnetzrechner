import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Subnetzrechner {

    public static void main(String[] args) {
        List<Integer> ipAddress = askForIpOrSubNetMask("IP address! ");
        System.out.println("Deine eingegebene IP ist:" + ipAddress);
        List<Integer> subnetMask = askForIpOrSubNetMask("subnet mask! ");
        while (!checkSubnetMask(subnetMask)) {
            subnetMask = askForIpOrSubNetMask("subnet mask! ");
        }
        System.out.println("Deine eingegebene Subnet Maske ist:" + subnetMask);
    }

    public static List<Integer> convertStringListToIntegerList(List<String> splitNumbers) {
        List<Integer> ipComponentsIntegerFormat = new ArrayList<>();
        for (String indexOfSplitNumbers : splitNumbers) {
            ipComponentsIntegerFormat.add(Integer.valueOf(indexOfSplitNumbers));
        }
        /* Elemente der String Liste (indexOfSplitNumbers) werden in Integer umgewandelt (Integer.valueOf)
         & an gleichen Index in der neuen Integer Liste hinzugefügt (add).*/
        return ipComponentsIntegerFormat;
    }

    private static boolean checkFormat(String input) {
        return checkIpLength(input) && checkThreeDots(input);
    }

    private static List<Integer> askForIpOrSubNetMask(String input) {
        System.out.print("Please enter your " + input);
        Scanner scanner = new Scanner(System.in);
        String ipOrSubNetMask = scanner.nextLine();
        // Erneute Eingabe
        if (!checkFormat(ipOrSubNetMask)) {
            return askForIpOrSubNetMask(input);
        }
        String[] splitAddresses = ipOrSubNetMask.split("\\.");
        List<String> splitNumbers = Arrays.asList(splitAddresses);
        if (!checkInputAreOnlyNumbers(splitNumbers)) {
            return askForIpOrSubNetMask(input);
        }
        if (!checkNumberMin0Max255(convertStringListToIntegerList(splitNumbers))) {
            return askForIpOrSubNetMask(input);
        }
        return convertStringListToIntegerList(splitNumbers);
    }

    public static boolean checkInputAreOnlyNumbers(List<String> ipComponentsStringFormat) {
        boolean result = true;
        for (String ipComponent : ipComponentsStringFormat) {
            if (!StringUtils.isNumeric(ipComponent)) {
                System.out.println("Your IP contain at least one letter.");
                result = false;
            }
        }
        return result;
    }

    public static boolean checkIpLength(String ip) {
        if (ip.length() < 7 || ip.length() > 15) {
            System.out.println("Your IP is to short/long.");
            return false;
        }
        return true;
    }

    public static boolean checkThreeDots(String ip) {
        char dotInIp = '.';
        int count = 0;
        for (int i = 0; i < ip.length(); i++) {
            if (ip.charAt(i) == dotInIp) {
                count++;
            }
        }
        if (count != 3) {
            System.out.println("Your IP don't contain three dots.");
            return false;
        }
        return true;
    }

    public static boolean checkNumberMin0Max255(List<Integer> ipComponentsIntegerFormat) {
        boolean result = true;
        for (Integer integer : ipComponentsIntegerFormat) {
            if (integer > 255 || integer < 0) {
                result = false;
                System.out.println("At least one number of your input was to small/big.");
                break;
            }
        }
        return result;
    }

    //umwandeln in binär
    public static String ipOrSubnetMaskToBinary(List<Integer> toBinary) {
        if (toBinary.size() != 4) {
            throw new IllegalArgumentException("illegal format!");
        }
        String resultFinalBinary = "";
        for (int i = 0; i < toBinary.size(); i++) {
            if (toBinary.get(i) < 0 || toBinary.get(i) > 255) {
                throw new IllegalArgumentException("illegal numbers!");
            } else {
                String binaryFormat = Integer.toBinaryString(toBinary.get(i));
                while (binaryFormat.length() < 8) {
                    binaryFormat = "0" + binaryFormat;
                }
                resultFinalBinary = resultFinalBinary + binaryFormat;
            }
        }
        return resultFinalBinary;
    }

    public static boolean checkSubnetMask(List<Integer> subnetMask) {
        boolean result = true;
        if (subnetMask.size() != 4) {
            throw new IllegalArgumentException("Invalid subnet mask format.");
        } else {
            String checkIfCorrectSubnetMask = ipOrSubnetMaskToBinary(subnetMask);
            if (checkIfCorrectSubnetMask.contains("01")) {
                System.out.println("Invalid subnet mask format.");
                result = false;
            }
        }
        return result;
    }
}



