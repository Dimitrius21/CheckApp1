package bzh.test.clevertec.action;

import bzh.test.clevertec.enities.Check;
import bzh.test.clevertec.enities.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.CharArrayWriter;


/**
 * Класс, формирующий результирующий чек ввиде XML и выводящий его в заданный ресурс
 * наследник клааса AbstractPrinter, переопределяет метод getOutputString
 */
public class PrintCheckToXml extends AbstractPrinter {
    private static final Logger logger = LoggerFactory.getLogger(PrintCheckToXml.class);

    public PrintCheckToXml(Check check, OutDataInterface out) {
        super(check, out);
    }

    /**
     * Метод формирует XML представление объекта Check
     * @return строку в виде XML
     */
    @Override
    public String getOutputString() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = documentFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element root = document.createElement("Check");
            document.appendChild(root);
            Element checkName = document.createElement("CheckName");
            checkName.setTextContent(check.getName());
            root.appendChild(checkName);
            Element shop = document.createElement("Shop");
            shop.setTextContent(check.getShop());
            root.appendChild(shop);
            Element when = document.createElement("DateTime");
            when.setTextContent(check.getTimeCreating().toString());
            root.appendChild(when);
            Element items = document.createElement("Items");
            root.appendChild(items);
            long totalDiscount = 0;
            long totalAmount = 0;
            for (Item item : check.getItems()) {
                int discount = item.getDiscount();
                int amount = item.getAmount();
                Element note = document.createElement("Item");
                items.appendChild(note);
                Element name = document.createElement("Name");
                name.setTextContent(item.getProductName());
                note.appendChild(name);
                Element qtity = document.createElement("Quantity");
                qtity.setTextContent(Integer.toString(item.getQuantity()));
                note.appendChild(qtity);
                Element price = document.createElement("Price");
                price.setTextContent(convertMoneyToString(item.getPrice()));
                note.appendChild(price);
                Element total = document.createElement("Amount");
                total.setTextContent(convertMoneyToString(item.getAmount()));
                note.appendChild(total);
                totalAmount += amount;
                if (discount > 0) {
                    Element discountSum = document.createElement("Discount");
                    discountSum.setTextContent(convertMoneyToString(discount));
                    note.appendChild(discountSum);
                    totalDiscount += discount;
                }
                logger.debug("Item descr:{}, price {}, qtity{}, amount {}, discount {}\n", item.getDiscount(), item.getPrice(), item.getQuantity(), amount, discount);
            }
            if (totalDiscount > 0) {
                Element discountAll = document.createElement("TotalDiscount");
                discountAll.setTextContent(convertMoneyToString(totalDiscount));
                root.appendChild(discountAll);
                totalAmount -= totalDiscount;
            }
            Element AmountAll = document.createElement("TotalAmount");
            AmountAll.setTextContent(convertMoneyToString(totalAmount));
            root.appendChild(AmountAll);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource dom = new DOMSource(document);
            CharArrayWriter wr = new CharArrayWriter();
            StreamResult out = new StreamResult(wr);
            transformer.transform(dom, out);
            return wr.toString();
        } catch (ParserConfigurationException | TransformerException ex) {
            logger.error("Error of transform to XML: {} ", ex.getMessage());
            return "";
        }
    }
}
