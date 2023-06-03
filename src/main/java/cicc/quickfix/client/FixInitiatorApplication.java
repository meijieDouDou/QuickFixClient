package cicc.quickfix.client;

import cicc.quickfix.client.field.*;
import cicc.quickfix.client.reuse.NewOrderSingleNew;
import cicc.quickfix.client.reuse.OrderCancelReplaceRequestNew;
import cicc.quickfix.client.utils.TimeTransfer;
import org.apache.log4j.Logger;
import quickfix.*;
import quickfix.Message;
import quickfix.field.*;
import quickfix.field.Currency;
import quickfix.fix42.*;
import quickfix.fix42.MessageCracker;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

import static cicc.quickfix.client.utils.TxtReader.getTxtData;


/**
 * Author by Mingyuan
 * Date 2022/04/20
 */
public class FixInitiatorApplication extends MessageCracker implements Application {

    private static final Logger logger = Logger.getLogger(FixInitiatorApplication.class);



     List<String> requstMsg = new ArrayList<>();

    List<String> responMsg = new ArrayList<>();

    public List<String> getRequstMsg() {
        return requstMsg;
    }

    public void setRequstMsg(List<String> requstMsg) {
        this.requstMsg = requstMsg;
    }

    public List<String> getResponMsg() {
        return responMsg;
    }

    public void setResponMsg(List<String> responMsg) {
        this.responMsg = responMsg;
    }


    TimeTransfer timeTransfer = new TimeTransfer();

    // 以下是Application的固定七件套
    @Override
    public void onCreate(SessionID sessionId) {
        logger.info("============onCreate========");
        System.out.println("onCreate is called");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        logger.info("============onLogon========");
        System.out.println("onLogon is called");
    }

    @Override
    public void onLogout(SessionID sessionId) {
        logger.info("============onLogout========");
        System.out.println("onLogout is called");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        String msg = message.toString().replace("", "|");
        logger.info("(Admin) S >> " + msg);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        String msg = message.toString().replace("", "|");
        logger.info("(Admin) R << " + msg);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        String msg = message.toString().replace("", "|");
        logger.info("(App) S >> " + msg);
        requstMsg.add("(App) S >> "+msg);
        System.out.println(message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        String msg = message.toString().replace("", "|");
        logger.info("(App) R << " + msg);
        responMsg.add("(App) R << " + msg);
    }

    // 以下是你可以自定义的消息接收器，来自MessageCracker
    @Override
    public void onMessage(ExecutionReport message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {

    }


    /**
     * 生成ClOrderID
     *
     * @return
     */
    public String generateID() {
        String datetime = timeTransfer.format(new Date(), "yyyyMMdd" + "-" + "HHmmss");
        String clSuffix = datetime.replaceFirst(String.valueOf(datetime.charAt(0)), "A");
        String rand = String.valueOf(new Random().nextInt(100000000));
        return clSuffix + "-" + rand;
    }

    public static List<String> getTxtData(File file) throws IOException {
        List<String> dataString = new ArrayList<String>(100);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line=br.readLine())!=null){
            dataString.add(line);
        }
        br.close();
        return dataString;

    }

    public static List<String> getTxtData(InputStream stream) throws IOException {
        List<String> dataString = new ArrayList<String>(100);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        while ((line=bufferedReader.readLine())!=null){
            dataString.add(line);
        }
        bufferedReader.close();
        return dataString;

    }


    /**
     * New Order
     *
     * @param sessionID
     * @param filename
     * @return
     * @throws SessionNotFound
     * @throws IOException
     */
    public List<NewOrderSingle> sendNewOrderRequestByTxt(SessionID sessionID, String filename) throws SessionNotFound, IOException {

        NewOrderSingleNew order = new NewOrderSingleNew();
        List<NewOrderSingle> orderResultList = new ArrayList<NewOrderSingle>();

//        File file = new File("src/main/resources" + "/" + filename);

//        List<String> results = getTxtData(file);

//        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/opt/metersphere/data/body/quickfix/message.log");
//        List<String> results = getTxtData(stream);

        File file = new File("/opt/metersphere/data/body/quickfix/message.log");
        List<String> results = getTxtData(file);

        // 解析TXT内容，格式化特殊符号
        List<String> standardResults = new ArrayList<>();
        for (String standardTxt : results) {
            if (standardTxt.contains("")) {
                String resultTxt = standardTxt.replace("", "|");
                standardResults.add(resultTxt);
            } else {
                standardResults.add(standardTxt);
            }
        }
        for (String fixdata : standardResults) {
            // 转化为数组
            String[] split = fixdata.split("\\|");
            Map<String, String> map = new HashMap<>();

            // 筛选出35=D的数据的整体报文
            if (split[2].equals("35=D")) {
                // 按照每个Tag进行拆分
                for (String tag : split) {

                    int index = tag.indexOf("=");
                    String tagNum = tag.substring(0, index);
                    String tagContent = tag.substring(index + 1);
                    // 将每组数据插入map中
                    map.put(tagNum, tagContent);
                }

                // 按照map一一对应发送
                if (map.get("115") != null) {
                    order.getHeader().setString(OnBehalfOfCompID.FIELD, map.get("115"));
                }
                if (map.get("1") != null) {
                    order.set(new Account(map.get("1")));
                }
                if (map.get("11") != null) {
                    order.set(new ClOrdID(generateID()));
                }
                if (map.get("15") != null) {
                    order.set(new Currency(map.get("15")));
                }
                if (map.get("21") != null) {
                    order.set(new HandlInst(map.get("21").charAt(0)));
                }
                if (map.get("22") != null) {
                    order.set(new IDSource(map.get("22")));
                }
                if (map.get("38") != null) {
                    order.set(new OrderQty(Double.parseDouble(map.get("38"))));
                }
                if (map.get("40") != null) {
                    order.set(new OrdType(map.get("40").charAt(0)));
                }
                if (map.get("44") != null) {
                    order.set(new Price(Double.parseDouble(map.get("44"))));
                }
                if (map.get("47") != null) {
                    order.set(new Rule80A(map.get("47").charAt(0)));
                }
                if (map.get("48") != null) {
                    order.set(new SecurityID(map.get("48")));
                }
                if (map.get("50") != null) {
                    order.set(new SenderSubID(map.get("50")));
                }
                if (map.get("54") != null) {
                    order.set(new Side(map.get("54").charAt(0)));
                }
                if (map.get("55") != null) {
                    order.set(new Symbol(map.get("55")));
                }
                if (map.get("57") != null) {
                    order.set(new TargetSubID(map.get("57")));
                }
                if (map.get("59") != null) {
                    order.set(new TimeInForce(map.get("59").charAt(0)));
                }
                if (map.get("60") != null) {
                    order.set(new TransactTime(timeTransfer.parseString2LocalDateTime(map.get("60"), "yyyyMMdd-HH:mm:ss.SSS")));
                }
                if (map.get("63") != null) {
                    order.set(new SettlmntTyp(map.get("63").charAt(0)));
                }
                if (map.get("78") != null) {
                    order.set(new NoAllocs(Integer.parseInt(map.get("78"))));
                }
                if (map.get("114") != null) {
                    order.set(new LocateReqd(Boolean.parseBoolean(map.get("114"))));
                }
                if (map.get("120") != null) {
                    order.set(new SettlCurrency(map.get("120")));
                }
                if (map.get("167") != null) {
                    order.set(new SecurityType(map.get("167")));
                }
                if (map.get("207") != null) {
                    order.set(new SecurityExchange(map.get("207")));
                }
                if (map.get("5700") != null) {
                    order.set(new SenderSubID(map.get("5700")));
                }
                if (map.get("6011") != null) {
                    order.set(new PartOnOpen(Integer.parseInt(map.get("6011"))));
                }
                if (map.get("6011") != null) {
                    order.set(new PartOnClose(Integer.parseInt(map.get("6012"))));
                }
                if (map.get("6011") != null) {
                    order.set(new PercentageOfVolume(Double.parseDouble(map.get("6013"))));
                }
                if (map.get("6059") != null) {
                    order.set(new CrossSpread(Integer.parseInt(map.get("6059"))));
                }
                if (map.get("6084") != null) {
                    order.set(new DisplaySize(Integer.parseInt(map.get("6084"))));
                }
                if (map.get("6069") != null) {
                    order.set(new PriceImprove(Integer.parseInt(map.get("6069"))));
                }
                if (map.get("7111") != null) {
                    order.set(new Strategy(map.get("7111")));
                }
                if (map.get("7113") != null) {
                    order.set(new StartTime(map.get("7113")));
                }
                if (map.get("7114") != null) {
                    order.set(new EndTime(map.get("7114")));
                }
                if (map.get("7126") != null) {
                    order.set(new VolCap(Double.parseDouble(map.get("7126"))));
                }

                // 发送
                Session.sendToTarget(order, sessionID);
                orderResultList.add(order);

            }
        }
        return orderResultList;
    }

    /**
     * Cancel Order
     *
     * @param sessionID
     * @param filename
     * @return
     * @throws IOException
     * @throws SessionNotFound
     */
    public List<OrderCancelRequest> cancelOrderRequestByTxt(SessionID sessionID, String filename) throws IOException, SessionNotFound {
        List<NewOrderSingle> orderResultList = sendNewOrderRequestByTxt(sessionID, filename);
        List<OrderCancelRequest> orderCancelResultList = new ArrayList<>();
        OrderCancelRequest order = new OrderCancelRequest();
        // 解析下单返回报文
        for (NewOrderSingle orderResultResp : orderResultList) {
            String orderResult = orderResultResp.toString();
            String resp = orderResult.replace("", "|");
            String[] split = resp.split("\\|");
            Map<String, String> map = new HashMap<>();
            for (String tag : split) {

                int index = tag.indexOf("=");
                String tagNum = tag.substring(0, index);
                String tagContent = tag.substring(index + 1);
                // 将每组数据插入map中
                map.put(tagNum, tagContent);
                // 拼装撤单报文
                if (map.get("115") != null) {
                    order.getHeader().setString(OnBehalfOfCompID.FIELD, map.get("115"));
                }
                if (map.get("11") != null) {
                    order.set(new ClOrdID(generateID()));
                    order.set(new OrigClOrdID(map.get("11")));
                }
                if (map.get("22") != null) {
                    order.set(new IDSource(map.get("22")));
                }
                if (map.get("38") != null) {
                    order.set(new OrderQty(Double.parseDouble(map.get("38"))));
                }
                if (map.get("54") != null) {
                    order.set(new Side(map.get("54").charAt(0)));
                }
                if (map.get("55") != null) {
                    order.set(new Symbol(map.get("55")));
                }
                if (map.get("60") != null) {
                    order.set(new TransactTime(timeTransfer.parseString2LocalDateTime(map.get("60"), "yyyyMMdd-HH:mm:ss.SSS")));
                }

                // 发送
                Session.sendToTarget(order, sessionID);
                orderCancelResultList.add(order);

            }

        }


        return orderCancelResultList;
    }

    /**
     * Replace Order
     * 1.数量改大 2.数量改小 3.修改orderType 4.价格改大 5.价格改小 6.修改MOO 7.修改MOC 8.修改startTime 9.修改endTime
     *
     * @param sessionID
     * @param filename
     * @return
     * @throws IOException
     * @throws SessionNotFound
     */
    public List<OrderCancelReplaceRequest> replaceOrderRequestByTxt(SessionID sessionID, String filename, String replaceTag) throws IOException, SessionNotFound {
        List<NewOrderSingle> orderResultList = sendNewOrderRequestByTxt(sessionID, filename);
        List<OrderCancelReplaceRequest> orderReplaceResultList = new ArrayList<>();
        OrderCancelReplaceRequestNew order = new OrderCancelReplaceRequestNew();
        // 解析下单返回报文
        for (NewOrderSingle orderResultResp : orderResultList) {
            String orderResult = orderResultResp.toString();
            String resp = orderResult.replace("", "|");
            String[] split = resp.split("\\|");
            Map<String, String> map = new HashMap<>();
            for (String tag : split) {

                int index = tag.indexOf("=");
                String tagNum = tag.substring(0, index);
                String tagContent = tag.substring(index + 1);
                // 将每组数据插入map中
                map.put(tagNum, tagContent);
                // 拼装改单报文
                if (map.get("57") != null) {
                    order.set(new TargetSubID(map.get("57")));
                }
                if (map.get("115") != null) {
                    order.getHeader().setString(OnBehalfOfCompID.FIELD, map.get("115"));
                }
                if (map.get("1") != null) {
                    order.set(new Account(map.get("1")));
                }
                if (map.get("11") != null) {
                    order.set(new ClOrdID(generateID()));
                    order.set(new OrigClOrdID(map.get("11")));
                }
                if (map.get("21") != null) {
                    order.set(new HandlInst(map.get("21").charAt(0)));
                }
                if (map.get("22") != null) {
                    order.set(new IDSource(map.get("22")));
                }
                // 改单传入1,2时为改Qty 1为qty改大，2为qty改小
                if (map.get("38") != null) {
                    switch (replaceTag) {
                        case "1":
                            order.set(new OrderQty(Double.parseDouble(map.get("38")) + 100));
                            break;
                        case "2":
                            order.set(new OrderQty(Double.parseDouble(map.get("38")) - 100));
                            break;
                        default:
                            order.set(new OrderQty(Double.parseDouble(map.get("38"))));
                            break;
                    }
                }
                // 改单传入3时为改OrdType
                if (map.get("40") != null) {
                    if ("3".equals(replaceTag)) {
                        if (map.get("40").equals("1")) {
                            order.set(new OrdType('2'));
                            order.set(new Price(11));
                        } else {
                            order.set(new OrdType('1'));
                        }
                    } else {
                        order.set(new OrdType(map.get("40").charAt(0)));
                        if (map.get("44") != null) {
                            order.set(new Price(Double.parseDouble(map.get("44"))));
                        }
                    }
                }
                // 改单传入4，5为改价格 4为价格改大，5为价格改小
                if (map.get("44") != null) {
                    if ("4".equals(replaceTag)) {
                        order.set(new Price(Double.parseDouble(map.get("44")) + 0.01));
                    } else if ("5".equals(replaceTag)) {
                        order.set(new Price(Double.parseDouble(map.get("44")) - 0.01));
                    }
                }
                if (map.get("47") != null) {
                    order.set(new Rule80A(map.get("47").charAt(0)));
                }
                if (map.get("55") != null) {
                    order.set(new Symbol(map.get("55")));
                }
                if (map.get("60") != null) {
                    order.set(new TransactTime(timeTransfer.parseString2LocalDateTime(map.get("60"), "yyyyMMdd-HH:mm:ss.SSS")));
                }
                if (map.get("7111") != null) {
                    order.set(new Strategy(map.get("7111")));
                }
                // 改单传入5,6为改MOO MOC


                // 改单传入7,8为改startTime endTime


            }
        }
        return orderReplaceResultList;


    }
}









