package cicc.quickfix.client.common.constant;

import cicc.quickfix.client.model.HkomsFixModel;

import java.util.HashMap;
import java.util.Map;

public class MapConstant {
    Map<String,String> map = new HashMap<String,String>();
    HkomsFixModel hkomsFixModel = new HkomsFixModel();

    public MapConstant() {
        map.put("1","account");
        map.put("9","bodyLength");
        map.put("35","msgType");
        map.put("34","msgSeqNum");
        map.put("49","senderCompID");
        map.put("52","sendingTime");
        map.put("56","targetCompID");
        map.put("115","onBehalfOfCompID");
        map.put("11","clOrdID");
        map.put("15","currency");
        map.put("21","handlInst");
        map.put("22","iDSource");
        map.put("38","orderQty");
        map.put("40","ordType");
        map.put("44","price");
        map.put("47","rule80A");
        map.put("48","securityID");
        map.put("50","senderSubID");
        map.put("54","side");
        map.put("55","symbol");
        map.put("57","targetSubID");
        map.put("59","timeInForce");
        map.put("60","transactTime");
        map.put("63","settlmntTyp");
        map.put("78","noAllocs");
        map.put("114","locateReqd");
        map.put("120","settlCurrency");
        map.put("167","securityType");
        map.put("207","securityExchange");
        map.put("5700","locateBroker");
        map.put("6011","partOnOpen");
        map.put("6012","partOnClose");
        map.put("6013","percentageOfVolume");
        map.put("6059","crossSpread");
        map.put("6084","displaySize");
        map.put("6069","priceImprove");
        map.put("7111","strategy");
        map.put("7113","startTime");
        map.put("7114","endTime");
        map.put("7126","volCap");
        map.put("10","checkSum");
        map.put("8","beginString");

        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MapConstant{" +
                "map=" + map +
                '}';
    }
}
