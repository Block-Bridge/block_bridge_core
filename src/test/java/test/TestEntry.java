package test;

import me.quickscythe.Api;
import me.quickscythe.api.v1.webapp.BinaryUtils;
import org.json.JSONObject;

public class TestEntry {

    static Api botApp;
    static Api serverApp;

    public static void main(String[] args) {
//

        BinaryUtils utils = new BinaryUtils();
        String start = new JSONObject().put("test", "test").put("test2",3).toString(2);
        System.out.println("Start: " + start);
        String convert = utils.toBinary(start);
        System.out.println("Convert: " + convert);
        String revert = utils.fromBinary(convert);
        System.out.println("Revert: " + revert);

    }

    public static Api getBotApp() {
        return botApp;
    }
}
