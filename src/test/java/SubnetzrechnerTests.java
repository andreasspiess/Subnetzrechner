import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubnetzrechnerTests {

    @Test
    void checkIpLength() {
        assertFalse(Subnetzrechner.checkIpLength("XXXXXX"));
        assertTrue(Subnetzrechner.checkIpLength("XXXXXXX"));
        assertFalse(Subnetzrechner.checkIpLength("XXXXXXXXXXXXXXXX"));
        assertTrue(Subnetzrechner.checkIpLength("XXXXXXXXXXXXXXX"));
    }

    @Test
    void checkThreeDots() {
        assertTrue(Subnetzrechner.checkThreeDots("192.168.10.0"));
        assertFalse(Subnetzrechner.checkThreeDots("192.168.0"));
        assertFalse(Subnetzrechner.checkThreeDots("192.168.10.0.90"));
    }

    @Test
    void checkInputAreOnlyNumbers() {
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of("x", "342", "442", "43")));
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of("x", "y42", "442", "4 ")));
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of("255", "X", "255", "4 ")));
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of("255", "150", "X", "4 ")));
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of("255", "150", "154", "X")));
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of(" ", " ", " ", " ")));
        assertFalse(Subnetzrechner.checkInputAreOnlyNumbers(List.of(" ", "255", "154", "199")));
        assertTrue(Subnetzrechner.checkInputAreOnlyNumbers(List.of("255", "255", "154", "199")));
    }

    @Test
    void checkNumberMin0Max255() {
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(256, 0, 0, 0)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(0, 256, 0, 0)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(0, 0, 256, 0)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(0, 0, 0, 256)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(-1, 0, 0, 0)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(0, -1, 0, 0)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(0, 0, -1, 0)));
        assertFalse(Subnetzrechner.checkNumberMin0Max255(List.of(0, 0, 0, -1)));
        assertTrue(Subnetzrechner.checkNumberMin0Max255(List.of(0, 255, 0, 0)));
        assertTrue(Subnetzrechner.checkNumberMin0Max255(List.of(255, 255, 0, 0)));
        assertTrue(Subnetzrechner.checkNumberMin0Max255(List.of(255, 255, 255, 0)));
        assertTrue(Subnetzrechner.checkNumberMin0Max255(List.of(255, 255, 255, 0)));
        assertTrue(Subnetzrechner.checkNumberMin0Max255(List.of(192, 172, 138, 0)));

    }

    @Test
    public void testListConversion() {
        List<String> stringList = List.of("192", "168", "2", "1");
        List<Integer> expected = List.of(192, 168, 2, 1);

        assertIterableEquals(expected, Subnetzrechner.convertStringListToIntegerList(stringList));
    }

    @Test
    public void testSubnetMask() {
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(128, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(192, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(224, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(240, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(248, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(252, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(254, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 0, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 128, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 192, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 224, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 240, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 248, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 252, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 254, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 0, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 128, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 192, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 224, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 240, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 248, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 252, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 254, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 0)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 128)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 192)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 224)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 240)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 248)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 252)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 254)));
        assertTrue(Subnetzrechner.checkSubnetMask(List.of(255, 255, 255, 255)));
        assertFalse(Subnetzrechner.checkSubnetMask((List.of(180, 0, 5, 3))));
        assertFalse(Subnetzrechner.checkSubnetMask((List.of(255, 0, 5, 3))));
        assertFalse(Subnetzrechner.checkSubnetMask((List.of(192, 0, 255, 255))));
        assertFalse(Subnetzrechner.checkSubnetMask((List.of(255, 0, 0, 1))));
    }

    @Test
    public void testIpToBinary() {
        assertEquals("11111111111111111111111111100000", Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(255, 255, 255, 224)));
        assertEquals("11111111100000000000000000000000", Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(255, 128, 0, 0)));
        assertEquals("00000001000000010000000100000000", Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(1, 1, 1, 0)));

        try {
            //noinspection ResultOfMethodCallIgnored
            Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(290, 1, 1, 0));
            fail("invalid ip should not be converted");
        } catch (IllegalArgumentException e) {
            // should fail
            assertTrue(true);
        }
    }

    @Test
    public void testID() {
        List<Integer> inputIP = List.of(192, 168, 0, 1);
        List<Integer> inputSNM = List.of(255, 255, 255, 0);
        String expectedNetID = "192.168.0.0";
        String netIDBinary = Subnetzrechner.calculatingBinaryNetID(Subnetzrechner.convertIpOrSubnetMaskToBinary(inputIP), Subnetzrechner.convertIpOrSubnetMaskToBinary(inputSNM));
        String actualNetID = Subnetzrechner.convertBinaryAddressToDecimalAddress(Subnetzrechner.convertBinaryStringToIntegerList(netIDBinary));
        assertEquals(expectedNetID, actualNetID);
    }

    @Test
    public void testBroadcastAddress() {
        String inputID = Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(192, 168, 0, 0));
        String inputSmn = Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(255, 255, 255, 0));
        String expected = Subnetzrechner.convertIpOrSubnetMaskToBinary(List.of(192, 168, 0, 255));
        assertEquals(expected, Subnetzrechner.calculatingBroadcastBinary(inputSmn, inputID));
    }

    @Test
    public void possibleNumberOfHosts() {
        assertEquals(254, Subnetzrechner.calculatingAmountOfHosts("11111111111111111111111100000000"));
        assertEquals(510, Subnetzrechner.calculatingAmountOfHosts("11111111111111111111111000000000"));
        assertEquals(1022, Subnetzrechner.calculatingAmountOfHosts("11111111111111111111110000000000"));
        assertEquals(2046, Subnetzrechner.calculatingAmountOfHosts("11111111111111111111100000000000"));

    }
}