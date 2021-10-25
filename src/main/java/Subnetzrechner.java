import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Subnetzrechner {

    public static void main(String[] args) {
        List<Integer> ipAddress = askForIpOrSubNetMask("IP address! ");
        List<Integer> subnetMask = askForIpOrSubNetMask("subnet mask! ");
        while (!checkSubnetMask(subnetMask)) {
            subnetMask = askForIpOrSubNetMask("subnet mask! ");
        }
        System.out.println(ipOrSubnetMaskToBinary(ipAddress) + " IP");
        String binarySubnetMask = ipOrSubnetMaskToBinary(subnetMask);
        System.out.println(binarySubnetMask + " subnet mask");
        System.out.println("____________________________________________");
        String binaryNetID = calculatingNetIDBinary(ipOrSubnetMaskToBinary(ipAddress), binarySubnetMask);
        System.out.println(binaryNetID + " ID (\u2259 IP & subnet mask) --> " + binaryToDecimal(convertBinaryStringToIntegerList(binaryNetID)));
        String binaryBroadcast = calculatingBroadcastBinary(binarySubnetMask, binaryNetID);
        System.out.println(calculatingBroadcastBinary(binarySubnetMask, binaryNetID) + "BC (Host-Anteil der ID auf 1) --> " + binaryToDecimal(convertBinaryStringToIntegerList(binaryBroadcast)));
    }

    public static String calculatingBroadcastBinary(String binarySubnetMask, String binaryNetID) {
        String broadcast;
        int counterOfZeros = 0;
        if (binarySubnetMask.indexOf(0) == 1 && binarySubnetMask.indexOf(binarySubnetMask.length()) == 0 && binarySubnetMask.contains("01")) {
            counterOfZeros = binarySubnetMask.lastIndexOf("0") - binarySubnetMask.indexOf("0") + 1;
            String onesToReplaceHostSection = "";
            for (int i = 1; i <= counterOfZeros; i++) {
                onesToReplaceHostSection += onesToReplaceHostSection + "1";
            }
            broadcast = binaryNetID.substring(binaryNetID.indexOf(0), binaryNetID.length() - counterOfZeros) + onesToReplaceHostSection;
        } else {
            throw new IllegalArgumentException();
        }
        return broadcast;
    }

    public static String calculatingNetIDBinary(String ip, String subNetMask) {
        StringBuilder netId = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (ip.charAt(i) == subNetMask.charAt(i) && subNetMask.charAt(i) == '1') {
                netId.append("1");
            } else {
                netId.append("0");
            }
        }
        return netId.toString();
    }

    public static String binaryToDecimal(List<String> binaryComponents) {
        List<Integer> decimalInteger = new ArrayList<>();
        StringBuilder decimalStringDotsInclusive = new StringBuilder();
        decimalInteger.add(Integer.parseInt(binaryComponents.get(0), 2));
        decimalInteger.add(Integer.parseInt(binaryComponents.get(1), 2));
        decimalInteger.add(Integer.parseInt(binaryComponents.get(2), 2));
        decimalInteger.add(Integer.parseInt(binaryComponents.get(3), 2));
        for (int i = 0; i < decimalInteger.size(); i++) {
            decimalStringDotsInclusive.append(decimalInteger.get(i)).append(".");
        }
        decimalStringDotsInclusive = new StringBuilder(decimalStringDotsInclusive.substring(0, decimalStringDotsInclusive.lastIndexOf(".")));
        return decimalStringDotsInclusive.toString();
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

    public static List<String> convertBinaryStringToIntegerList(String binary) {
        List<String> binaryNumbers = new ArrayList<>();
        binaryNumbers.add(binary.substring(0, 8));
        binaryNumbers.add(binary.substring(8, 16));
        binaryNumbers.add(binary.substring(16, 24));
        binaryNumbers.add(binary.substring(24, 32));
        return binaryNumbers;
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



