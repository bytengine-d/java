package cn.bytengine.d.sa;

import cn.bytengine.d.lang.CollectionTools;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 组合的SaIdentificationFinder
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CompositeJakartaServletSaIdentificationFinder implements JakartaServletSaIdentificationFinder {
    private final Set<JakartaServletSaIdentificationFinder> finders = new LinkedHashSet<>();

    /**
     * 给定指定顺序的SaIdentificationFinder集合，创建组合SaIdentificationFinder.
     *
     * @param finders 指定顺序的SaIdentificationFinder集合
     */
    public CompositeJakartaServletSaIdentificationFinder(Collection<JakartaServletSaIdentificationFinder> finders) {
        this.finders.addAll(finders);
    }

    /**
     * 给定指定顺序的SaIdentificationFinder集合，创建组合SaIdentificationFinder.
     *
     * @param finders 指定顺序的SaIdentificationFinder集合
     */
    public CompositeJakartaServletSaIdentificationFinder(JakartaServletSaIdentificationFinder... finders) {
        this.finders.addAll(Arrays.asList(finders));
    }

    @Override
    public SaIdentification find(WebSaConfig saConfig, HttpServletRequest request) {
        if (CollectionTools.isEmpty(finders)) {
            return null;
        }
        SaIdentification saIdentification = null;
        for (JakartaServletSaIdentificationFinder finder : finders) {
            if (finder == null) {
                continue;
            }
            saIdentification = finder.find(saConfig, request);
            if (saIdentification != null) {
                break;
            }
        }
        return saIdentification;
    }
}
