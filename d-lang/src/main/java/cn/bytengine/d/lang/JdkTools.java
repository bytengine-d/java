package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class JdkTools {
    private JdkTools() {
    }

    public static final int JVM_VERSION;

    public static final boolean IS_JDK8;
    public static final boolean IS_AT_LEAST_JDK17;
    public static final boolean IS_ANDROID;

    static {
        // JVM版本
        JVM_VERSION = _getJvmVersion();
        IS_JDK8 = 8 == JVM_VERSION;
        IS_AT_LEAST_JDK17 = JVM_VERSION >= 17;

        // JVM名称
        final String jvmName = _getJvmName();
        IS_ANDROID = jvmName.equals("Dalvik");
    }

    private static String _getJvmName() {
        return System.getProperty("java.vm.name");
    }

    private static int _getJvmVersion() {
        int jvmVersion = -1;

        try {
            String javaSpecVer = System.getProperty("java.specification.version");
            if (CharSequenceTools.isNotBlank(javaSpecVer)) {
                if (javaSpecVer.startsWith("1.")) {
                    javaSpecVer = javaSpecVer.substring(2);
                }
                if (javaSpecVer.indexOf('.') == -1) {
                    jvmVersion = Integer.parseInt(javaSpecVer);
                }
            }
        } catch (Throwable ignore) {
            // 默认JDK8
            jvmVersion = 8;
        }

        return jvmVersion;
    }

}
