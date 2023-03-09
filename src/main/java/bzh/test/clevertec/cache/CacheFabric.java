package bzh.test.clevertec.cache;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс реализует фабрику, создающую объект кэша исходя из параметров записанных в application.yml
 */
public enum CacheFabric {
    LFU(CacheLfu::new),
    LRU(CacheLru::new);
    private final Function<Integer, Cacheable> cacheProduce;
    private static final String DEFAULT_TYPE = "LRU";
    private static final String DEFAULT_SIZE = "3";

    CacheFabric(Function<Integer, Cacheable> create) {
        this.cacheProduce = create;
    }

    /**
     * Метод читает параметы записанные в application.yml и создает объект кэша
     * @return объект кэша, реализующего интерфейс Cacheable
     */
    public static Cacheable getCacheInstance() {
        Map<String, String> prop = new HashMap<>();
        try {
            URL path = ClassLoader.getSystemResource("application.yml");
            Path p = Paths.get(path.toURI());
            List<String> content = Files.readAllLines(p);
            int i = -1;
            for (String s : content) {
                i++;
                if (s.matches("^cache\\s*:\\s*")) {
                    i++;
                    break;
                }
            }
            if (i < content.size() && i >= 0) {
                Pattern p1 = Pattern.compile("^\\s+(type)\\s*:\\s*(.*)$");
                Pattern p2 = Pattern.compile("^\\s+(size)\\s*:\\s*(.*)$");
                while (i < content.size()) {
                    String s = content.get(i);
                    if (!s.startsWith(" ")) {
                        break;
                    }
                    Matcher m = p1.matcher(s);
                    if (m.matches()) {
                        prop.put(m.group(1), m.group(2));
                    } else {
                        m = p2.matcher(s);
                        if (m.matches()) {
                            prop.put(m.group(1), m.group(2).trim());
                        }
                    }
                    i++;
                }
            }
        } catch (URISyntaxException | IOException ex) {
        }
        if (!prop.containsKey("type")) {
            prop.put("type", DEFAULT_TYPE);
        }
        if (!prop.containsKey("size")) {
            prop.put("size", DEFAULT_SIZE);
        }
        return CacheFabric.valueOf(prop.get("type").toUpperCase()).cacheProduce.apply(Integer.parseInt(prop.get("size")));
    }
}
