package cn.bytengine.d.sa;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 复合用户信息提供者
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class CompositeIdentityInfoProvider implements IdentityInfoProvider {
    private final Set<IdentityInfoProvider> providers = new LinkedHashSet<>();

    /**
     * 给定指定顺序的SaIdentificationFinder集合，创建组合SaIdentificationFinder.
     *
     * @param providers 指定顺序的SaIdentificationFinder集合
     */
    public CompositeIdentityInfoProvider(Collection<IdentityInfoProvider> providers) {
        this.providers.addAll(providers);
    }

    /**
     * 给定指定顺序的SaIdentificationFinder集合，创建组合SaIdentificationFinder.
     *
     * @param providers 指定顺序的SaIdentificationFinder集合
     */
    public CompositeIdentityInfoProvider(IdentityInfoProvider... providers) {
        this.providers.addAll(Arrays.asList(providers));
    }

    @Override
    public IdentityInfo get(SaIdentification saIdentification) {
        IdentityInfo identityInfo = null;
        for (IdentityInfoProvider provider : providers) {
            identityInfo = provider.get(saIdentification);
            if (identityInfo != null) {
                break;
            }
        }
        return identityInfo;
    }
}
